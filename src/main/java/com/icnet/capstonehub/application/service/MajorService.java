package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.MajorUseCase;
import com.icnet.capstonehub.application.port.in.command.MajorCreateCommand;
import com.icnet.capstonehub.application.port.in.mapper.MajorResultMapper;
import com.icnet.capstonehub.application.port.in.result.MajorResult;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.domain.Major;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MajorService implements MajorUseCase {
    private final MajorPort majorPort;

    @Override
    public List<MajorResult> getAll() {
        return majorPort.getAll().stream().map(MajorResultMapper::toResult).toList();
    }

    @Override
    public MajorResult createMajor(MajorCreateCommand command) {
        Major domain = Major.builder().name(command.name()).build();
        return MajorResultMapper.toResult(majorPort.save(domain));
    }
}
