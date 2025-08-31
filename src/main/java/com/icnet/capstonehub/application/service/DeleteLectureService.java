package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.DeleteLectureUseCase;
import com.icnet.capstonehub.application.port.out.LecturePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteLectureService implements DeleteLectureUseCase {
    private final LecturePort lecturePort;

    @Override
    public void byId(Long id) {
        lecturePort.delete(id);
    }
}
