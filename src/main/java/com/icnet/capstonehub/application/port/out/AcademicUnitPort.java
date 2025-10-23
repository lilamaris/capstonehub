package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.AcademicUnit;
import com.icnet.capstonehub.domain.model.Timeline;
import com.icnet.capstonehub.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AcademicUnitPort {
    Optional<AcademicUnit> getById(AcademicUnit.Id id);
    List<AcademicUnit> getAllByUserId(User.Id userId);
    List<AcademicUnit> getTimeline(Timeline.SharedId sharedId);
    Optional<AcademicUnit> getHeadOfTimeline(Timeline.SharedId sharedId);
    List<AcademicUnit> getSnapshot(Timeline.SharedId sharedId, LocalDateTime txAt);
    AcademicUnit save(AcademicUnit domain);
}
