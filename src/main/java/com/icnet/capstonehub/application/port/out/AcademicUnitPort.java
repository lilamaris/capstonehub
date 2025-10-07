package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.AcademicUnit;
import com.icnet.capstonehub.domain.model.Edition;
import com.icnet.capstonehub.domain.model.Timeline;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AcademicUnitPort {
    Optional<AcademicUnit> getSnapshotOfRecord(Timeline.SharedId lineageSharedId, Edition.SharedId versionSharedId, LocalDateTime txAt);
    Optional<AcademicUnit> getSnapshotOfRecord(Timeline.SharedId lineageSharedId, LocalDateTime validAt, LocalDateTime txAt);
    List<AcademicUnit> getTimelineOfSnapshot(Timeline.SharedId lineageSharedId, LocalDateTime txAt);
    List<AcademicUnit> getEditionOfRecord(Timeline.SharedId lineageSharedId, Edition.SharedId versionSharedId);
    List<AcademicUnit> getEditionOfRecord(Timeline.SharedId lineageSharedId, LocalDateTime validAt);
    AcademicUnit save(AcademicUnit domain);
}
