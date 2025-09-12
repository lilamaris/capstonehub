package com.icnet.capstonehub.application.service;

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
        return null;
    }

    @Override
    public AffiliationResult appendAffiliationLineage(AffiliationLineageAppendCommand command) {
        return null;
    }

    @Override
    public AffiliationResult amendAffiliationLineage(AffiliationLineageAmendCommand command) {
        return null;
    }
}
