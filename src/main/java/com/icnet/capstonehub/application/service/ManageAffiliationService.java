package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.ManageAffiliationUseCase;
import com.icnet.capstonehub.application.port.in.command.AssignAffiliationCommand;
import com.icnet.capstonehub.application.port.out.AffiliationPort;
import com.icnet.capstonehub.application.port.out.mapper.AffiliationMapper;
import com.icnet.capstonehub.application.port.out.response.AffiliationResponse;
import com.icnet.capstonehub.domain.common.EffectivePeriod;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ManageAffiliationService implements ManageAffiliationUseCase {
    private final AffiliationPort affiliationPort;
    private final AffiliationMapper affiliationMapper;

    @Override
    public AffiliationResponse assignMajorToCollege(AssignAffiliationCommand command) {
        EffectivePeriod period = new EffectivePeriod(command.effectiveStartDate(), command.effectiveEndDate());
        return affiliationMapper.toResponse(affiliationPort.assignMajorToCollege(command.majorId(), command.collegeId(), period));
    }
}
