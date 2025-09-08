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
        return affiliationPort.getLineage(new Version.LineageId(lineageId)).stream()
                .map(AffiliationResultMapper::toResult).toList();
    }

    @Override
    public AffiliationResult createAffiliationLineage(UUID collegeId, UUID majorId, LocalDate startDate, LocalDate endDate, String versionDescription) {
        log.info("Create affiliation lineage with collegeId={}, majorId={}", collegeId, majorId);

        College college = collegePort.get(new College.Id(collegeId)).orElseThrow(CollegeNotFoundException::new);
        Major major = majorPort.get(new Major.Id(majorId)).orElseThrow(MajorNotFoundException::new);

        log.info("Now i found college={}, major={}", college, major);

        Version version = Version.builder()
                .lineageId(new Version.LineageId(UUID.randomUUID()))
                .lineageScope(Version.LineageScope.AFFILIATION)
                .versionNo(0)
                .versionDescription(versionDescription)
                .validFrom(startDate)
                .validTo(endDate)
                .build();

        log.info("Creating new version={}", version);

        Affiliation domain = Affiliation.builder()
                .version(version)
                .college(college)
                .major(major)
                .build();

        return AffiliationResultMapper.toResult(affiliationPort.save(domain));
    }

    @Override
    public AffiliationResult successionMajorLineage(UUID lineageId, UUID majorId, LocalDate startDate, LocalDate endDate, String versionDescription) {
        Major major = majorPort.get(new Major.Id(majorId)).orElseThrow(MajorNotFoundException::new);

        Affiliation prev = affiliationPort.getCurrent(new Version.LineageId(lineageId)).orElseThrow(AffiliationLineageNotFoundException::new);
        Affiliation next = prev
                .withVersion(prev.version().next(startDate, endDate, versionDescription))
                .toBuilder()
                .major(major)
                .build();

        return AffiliationResultMapper.toResult(affiliationPort.save(next));
    }

    @Override
    public AffiliationResult successionCollegeLineage(UUID lineageId, UUID collegeId, LocalDate startDate, LocalDate endDate, String versionDescription) {
        College college = collegePort.get(new College.Id(collegeId)).orElseThrow(CollegeNotFoundException::new);

        Affiliation prev = affiliationPort.getCurrent(new Version.LineageId(lineageId)).orElseThrow(AffiliationLineageNotFoundException::new);
        Affiliation next = prev
                .withVersion(prev.version().next(startDate, endDate, versionDescription))
                .toBuilder()
                .college(college)
                .build();

        return AffiliationResultMapper.toResult(affiliationPort.save(next));
    }
}
