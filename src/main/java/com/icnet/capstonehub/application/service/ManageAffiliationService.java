package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.ManageAffiliationUseCase;
import com.icnet.capstonehub.application.port.in.command.AssignAffiliationCommand;
import com.icnet.capstonehub.application.port.in.command.ReassignAffiliationCommand;
import com.icnet.capstonehub.application.port.out.AffiliationPort;
import com.icnet.capstonehub.application.port.out.mapper.AffiliationMapper;
import com.icnet.capstonehub.application.port.out.response.AffiliationResponse;
import com.icnet.capstonehub.domain.Affiliation;
import com.icnet.capstonehub.domain.College;
import com.icnet.capstonehub.domain.Major;
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
        Affiliation affiliation = Affiliation.builder()
                .collegeId(new College.CollegeId(command.collegeId()))
                .majorId(new Major.MajorId(command.majorId()))
                .effective(new EffectivePeriod(command.effectiveStartDate(), command.effectiveEndDate()))
                .build();

        return affiliationMapper.toResponse(affiliationPort.assignMajorToCollege(affiliation));
    }

    @Override
    public AffiliationResponse reassignMajorToCollege(ReassignAffiliationCommand command) {
        Affiliation affiliation = Affiliation.builder()
                .collegeId(new College.CollegeId(command.collegeId()))
                .majorId(new Major.MajorId(command.majorId()))
                .effective(new EffectivePeriod(command.effectiveStartDate(), command.effectiveEndDate()))
                .build();

        affiliationPort.rejectMajorToCollege(affiliation);
        return affiliationMapper.toResponse(affiliationPort.assignMajorToCollege(affiliation));
    }
}
