package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.DeleteMajorUseCase;
import com.icnet.capstonehub.application.port.out.MajorPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteMajorService implements DeleteMajorUseCase {
    private final MajorPort majorPort;

    @Override
    public void byId(Long id) {
        majorPort.delete(id);
    }
}
