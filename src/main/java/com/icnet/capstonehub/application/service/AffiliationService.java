package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.exception.AffiliationLineageNotFoundException;
import com.icnet.capstonehub.application.exception.CollegeNotFoundException;
import com.icnet.capstonehub.application.exception.MajorNotFoundException;
import com.icnet.capstonehub.application.port.in.AffiliationUseCase;
import com.icnet.capstonehub.application.port.in.mapper.AffiliationResultMapper;
import com.icnet.capstonehub.application.port.in.result.AffiliationResult;
import com.icnet.capstonehub.application.port.out.AffiliationPort;
import com.icnet.capstonehub.application.port.out.CollegePort;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.domain.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AffiliationService implements AffiliationUseCase {
    private final AffiliationPort affiliationPort;

    private final CollegePort collegePort;
    private final MajorPort majorPort;

    @Override
    public List<AffiliationResult> getAffiliationLineage(UUID lineageId) {
        Lineage.LineageId lId = new Lineage.LineageId(lineageId);
        LocalDate  now = LocalDate.now();
        return affiliationPort.getLineageSnapshotAtTx(lId, now).stream()
                .map(AffiliationResultMapper::toResult).toList();
    }

    @Override
    public AffiliationResult initialAffiliationLineage(UUID collegeId, UUID majorId, LocalDate validFrom, LocalDate validTo, String versionDescription) {
        College.Id cId = new College.Id(collegeId);
        Major.Id mId = new Major.Id(majorId);

        College college = collegePort.get(cId).orElseThrow(CollegeNotFoundException::new);
        Major major = majorPort.get(mId).orElseThrow(MajorNotFoundException::new);

        Version version = Version.builder()
                .versionNo(0)
                .versionDescription(versionDescription)
                .txPeriod(Period.nowOpen())
                .build();

        Period validPeriod = new Period(validFrom, validTo);
        Lineage.LineageId lId = new Lineage.LineageId(UUID.randomUUID());
        Lineage lineage = Lineage.builder()
                .lineageId(lId)
                .scope(Lineage.Scope.AFFILIATION)
                .validPeriod(validPeriod)
                .build();

        Affiliation domain = Affiliation.builder()
                .version(version)
                .lineage(lineage)
                .college(college)
                .major(major)
                .build();

        log.info("""
                [Service] Initialize Affiliation Lineage Local Variables
                request=(collegeId={}, majorId={}, validFrom={}, validTo={}, versionDescription={}),
                created=(domain={})
                """, collegeId, majorId, validFrom, validTo, versionDescription, domain);

        return AffiliationResultMapper.toResult(affiliationPort.save(domain));
    }

    @Override
    public AffiliationResult appendAffiliationLineage(UUID lineageId, UUID collegeId, UUID majorId, LocalDate validFrom, LocalDate validTo, String versionDescription) {
        College.Id cId = new College.Id(collegeId);
        Major.Id mId = new Major.Id(majorId);

        College college = collegePort.get(cId).orElseThrow(CollegeNotFoundException::new);
        Major major = majorPort.get(mId).orElseThrow(MajorNotFoundException::new);

        Version version = Version.builder()
                .versionNo(0)
                .versionDescription(versionDescription)
                .txPeriod(Period.nowOpen())
                .build();

        Period validPeriod = new Period(validFrom, validTo);
        Lineage.LineageId lId = new Lineage.LineageId(lineageId);

        Affiliation prev = affiliationPort.getLineageHead(lId).orElseThrow(AffiliationLineageNotFoundException::new);
        Affiliation next = prev
                .withLineage(prev.lineage().next(validPeriod))
                .toBuilder()
                .version(version)
                .college(college)
                .major(major)
                .build();

        log.info("""
                Append Affiliation Lineage Local Variables
                request=(collegeId={}, majorId={}, validFrom={}, validTo={}, versionDescription={}),
                created=(next={})
                """, collegeId, majorId, validFrom, validTo, versionDescription, next);

        return AffiliationResultMapper.toResult(affiliationPort.save(next));
    }

    @Override
    public AffiliationResult amendAffiliationLineage(UUID lineageId, UUID affiliationId, UUID collegeId, UUID majorId, LocalDate validFrom, LocalDate validTo, String versionDescription) {
        log.info("Amend affiliation lineage lineageId={}, validFrom={}, validTo={}, versionDescription={}", lineageId, validFrom, validTo, versionDescription);

        College.Id cId = new College.Id(collegeId);
        Major.Id mId = new Major.Id(majorId);

        College college = collegePort.get(cId).orElseThrow(CollegeNotFoundException::new);
        Major major = majorPort.get(mId).orElseThrow(MajorNotFoundException::new);

        Period txPeriod = Period.nowOpen();

        Lineage.LineageId lId = new Lineage.LineageId(lineageId);
        Period validPeriod = new Period(validFrom, validTo);
        Affiliation prev = affiliationPort.getLineageHead(lId).orElseThrow(AffiliationLineageNotFoundException::new);

        Affiliation closedPrev = prev
                .withLineage(prev.lineage().closeValid(validFrom))
                .withVersion(prev.version().closeTx(validFrom))
                .toBuilder()
                .build();
        affiliationPort.save(closedPrev);

        Affiliation next = prev
                .withLineage(prev.lineage().next(validPeriod))
                .withVersion(prev.version().next(txPeriod, versionDescription))
                .toBuilder()
                .college(college)
                .major(major)
                .build();

        return AffiliationResultMapper.toResult(affiliationPort.save(next));
    }
}
