package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.FindAllMajorUseCase;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.domain.Major;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
class FindAllMajorService implements FindAllMajorUseCase {
    private final MajorPort majorPort;

    @Override
    public List<MajorResponse> find() {
        List<Major> found = majorPort.findAll();

        return found.stream()
                .map(major -> MajorResponse.builder()
                        .id(major.id().value())
                        .name(major.name())
                        .effectiveStartDate(major.effectiveStartDate())
                        .effectiveEndDate(major.effectiveEndDate())
                        .build()).toList();
    }
}
