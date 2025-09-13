package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.exception.AffiliationLineageNotFoundException;
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

import java.time.LocalDateTime;
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
        var now = LocalDateTime.now();
        return affiliationPort.getLineageOfSnapshot(new Lineage.SharedId(lineageSharedId), now).stream()
                .map(AffiliationResultMapper::toResult)
                .toList();
    }

    @Override
    public List<AffiliationResult> getAffiliationLineage(UUID lineageSharedId, LocalDateTime txAt) {
        return affiliationPort.getLineageOfSnapshot(new Lineage.SharedId(lineageSharedId), txAt).stream()
                .map(AffiliationResultMapper::toResult)
                .toList();
    }

    @Override
    public AffiliationResult initialAffiliationLineage(AffiliationLineageInitialCommand command) {
        var now = LocalDateTime.now();
        var collegeId = new College.Id(command.collegeId());
        var majorId = new Major.Id(command.majorId());

        var initial = Affiliation.initial(collegeId, majorId, now, command.validAt());

        return AffiliationResultMapper.toResult(affiliationPort.save(initial));
    }

    @Override
    public AffiliationResult appendAffiliationLineage(AffiliationLineageAppendCommand command) {
        var now = LocalDateTime.now();
        var lineageSharedId = new Lineage.SharedId(command.lineageSharedId());
        var collegeId = new College.Id(command.collegeId());
        var majorId = new Major.Id(command.majorId());

        var headAffiliation = affiliationPort.getSnapshotOfRecord(lineageSharedId, command.validAt(), now)
                .orElseThrow(AffiliationLineageNotFoundException::new);
        var appendTransition = headAffiliation.append(collegeId, majorId, now, command.validAt());
        affiliationPort.save(appendTransition.previous());

        return AffiliationResultMapper.toResult(affiliationPort.save(appendTransition.next()));
    }

    @Override
    public AffiliationResult amendAffiliationLineage(AffiliationLineageAmendCommand command) {
        var now = LocalDateTime.now();
        var lineageSharedId = new Lineage.SharedId(command.lineageSharedId());
        var versionSharedId = new Version.SharedId(command.versionSharedId());
        var collegeId = new College.Id(command.collegeId());
        var majorId = new Major.Id(command.majorId());

        var headAffiliation = affiliationPort.getSnapshotOfRecord(lineageSharedId, versionSharedId, now)
                .orElseThrow(AffiliationLineageNotFoundException::new);
        var amendTransition = headAffiliation.amend(collegeId, majorId, now, command.versionDescription());
        affiliationPort.save(amendTransition.previous());

        return AffiliationResultMapper.toResult(affiliationPort.save(amendTransition.next()));
    }
}
