package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.DeleteCollegeUseCase;
import com.icnet.capstonehub.application.port.out.CollegePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteCollegeService implements DeleteCollegeUseCase {
    private final CollegePort collegePort;

    @Override
    public void byId(Long id) {
        collegePort.delete(id);
    }
}
