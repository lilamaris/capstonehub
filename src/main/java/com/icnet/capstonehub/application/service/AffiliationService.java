package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.exception.AffiliationLineageNotFoundException;
import com.icnet.capstonehub.application.exception.CollegeNotFoundException;
import com.icnet.capstonehub.application.exception.MajorNotFoundException;
import com.icnet.capstonehub.application.port.in.AffiliationUseCase;
import com.icnet.capstonehub.application.port.in.command.AffiliationLineageAmendCommand;
import com.icnet.capstonehub.application.port.in.command.AffiliationLineageAppendCommand;
import com.icnet.capstonehub.application.port.in.command.AffiliationLineageInitialCommand;
import com.icnet.capstonehub.application.port.in.mapper.AffiliationResultMapper;
import com.icnet.capstonehub.application.port.in.result.AffiliationResult;
import com.icnet.capstonehub.application.port.out.AffiliationPort;
import com.icnet.capstonehub.application.port.out.CollegePort;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.domain.model.*;
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
    public List<AffiliationResult> getAffiliationLineage(UUID lineageSharedId) {
        var now = LocalDate.now();
        return affiliationPort.getLineageOfSnapshot(new Lineage.SharedId(lineageSharedId), now).stream()
                .map(AffiliationResultMapper::toResult)
                .toList();
    }

    @Override
    public List<AffiliationResult> getAffiliationLineage(UUID lineageSharedId, LocalDate txAt) {
        return affiliationPort.getLineageOfSnapshot(new Lineage.SharedId(lineageSharedId), txAt).stream()
                .map(AffiliationResultMapper::toResult)
                .toList();
    }

    @Override
    public AffiliationResult initialAffiliationLineage(AffiliationLineageInitialCommand command) {
        var now = LocalDate.now();
        var collegeId = new College.Id(command.collegeId());
        var majorId = new Major.Id(command.majorId());

        College college = collegePort.get(collegeId).orElseThrow(CollegeNotFoundException::new);
        Major major = majorPort.get(majorId).orElseThrow(MajorNotFoundException::new);

        var versionSharedId = new Version.SharedId(UUID.randomUUID());
        var lineageSharedId = new Lineage.SharedId(UUID.randomUUID());

        Period txPeriod = Period.fromToInfinity(now);
        Version version = Version.builder()
                .sharedId(versionSharedId)
                .versionNo(0)
                .versionDescription(command.versionDescription())
                .txPeriod(txPeriod)
                .build();

        Period validPeriod = Period.builder()
                .from(command.validFrom())
                .to(command.validTo())
                .build();
        Lineage lineage = Lineage.builder()
                .sharedId(lineageSharedId)
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
                command={}
                created=(domain={})
                """, command, domain);

        return AffiliationResultMapper.toResult(affiliationPort.save(domain));
    }

    @Override
    public AffiliationResult appendAffiliationLineage(AffiliationLineageAppendCommand command) {
        var now = LocalDate.now();
        var collegeId = new College.Id(command.collegeId());
        var majorId = new Major.Id(command.majorId());

        College college = collegePort.get(collegeId).orElseThrow(CollegeNotFoundException::new);
        Major major = majorPort.get(majorId).orElseThrow(MajorNotFoundException::new);

        var lineageSharedId = new Lineage.SharedId(command.lineageSharedId());
        var versionSharedId = new Version.SharedId(UUID.randomUUID());

        Affiliation prev = affiliationPort.getSnapshotOfRecord(lineageSharedId, now, now).
                orElseThrow(AffiliationLineageNotFoundException::new);

        Period txPeriod = Period.fromToInfinity(now);
        Period validPeriod = Period.pair(command.validFrom(), command.validTo());

        Affiliation prevClose = prev
                .withLineage(prev.lineage().closeValid(command.validTo()))
                .toBuilder()
                .build();
        affiliationPort.save(prevClose);

        Version newVersion = Version.builder()
                .sharedId(versionSharedId)
                .versionNo(0)
                .versionDescription(command.versionDescription())
                .txPeriod(txPeriod)
                .build();

        Lineage nextLineage = prev.lineage().next(validPeriod);

        Affiliation next = prev
                .withLineage(nextLineage)
                .toBuilder()
                .version(newVersion)
                .college(college)
                .major(major)
                .build();

        log.info("""
                Append Affiliation Lineage Local Variables
                command={}
                created=(
                    prev={},
                    prevClose={},
                    next={})""", command, prev, prevClose, next);

        return AffiliationResultMapper.toResult(affiliationPort.save(next));
    }

    @Override
    public AffiliationResult amendAffiliationLineage(AffiliationLineageAmendCommand command) {
        var now = LocalDate.now();
        var collegeId = new College.Id(command.collegeId());
        var majorId = new Major.Id(command.majorId());

        College college = collegePort.get(collegeId).orElseThrow(CollegeNotFoundException::new);
        Major major = majorPort.get(majorId).orElseThrow(MajorNotFoundException::new);

        Lineage.SharedId lineageSharedId = new Lineage.SharedId(command.lineageSharedId());
        Version.SharedId versionSharedId = new Version.SharedId(command.versionSharedId());

        Period txPeriod = Period.fromToInfinity(now);

        Affiliation prev = affiliationPort.getSnapshotOfRecord(lineageSharedId, versionSharedId, now)
                .orElseThrow(AffiliationLineageNotFoundException::new);

        Affiliation prevClose = prev
                .withVersion(prev.version().closeTx(now))
                .toBuilder()
                .build();

        affiliationPort.save(prevClose);

        Version nextVersion = prev.version().next(txPeriod, command.versionDescription());

        Affiliation next = prev
                .withVersion(nextVersion)
                .toBuilder()
                .college(college)
                .major(major)
                .build();

        log.info("""
                Amend Affiliation Lineage Local Variables
                command={}
                created=(
                    prev={},
                    prevClose={},
                    next={})""", command, prev, prevClose, next);

        return AffiliationResultMapper.toResult(affiliationPort.save(next));
    }
}
