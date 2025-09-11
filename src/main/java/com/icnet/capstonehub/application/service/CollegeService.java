package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.CollegeUseCase;
import com.icnet.capstonehub.application.port.in.command.CollegeCreateCommand;
import com.icnet.capstonehub.application.port.in.mapper.CollegeResultMapper;
import com.icnet.capstonehub.application.port.in.result.CollegeResult;
import com.icnet.capstonehub.application.port.out.CollegePort;
import com.icnet.capstonehub.domain.model.College;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CollegeService implements CollegeUseCase {
    private final CollegePort collegePort;

    @Override
    public List<CollegeResult> getAll() {
        return collegePort.getAll().stream().map(CollegeResultMapper::toResult).toList();
    }

    @Override
    public CollegeResult createCollege(CollegeCreateCommand command) {
        College domain = College.builder().name(command.name()).build();
        return CollegeResultMapper.toResult(collegePort.save(domain));
    }
}
