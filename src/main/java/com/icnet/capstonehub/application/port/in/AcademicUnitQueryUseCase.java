package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.result.AcademicUnitResult;
import com.icnet.capstonehub.domain.model.Timeline;
import com.icnet.capstonehub.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface AcademicUnitQueryUseCase {
    List<AcademicUnitResult.Query> getOwned(User.Id userId);
    List<AcademicUnitResult.Query> getTimeline(Timeline.SharedId sharedId);
    List<AcademicUnitResult.Query> getSnapshot(Timeline.SharedId sharedId, LocalDateTime txAt);
}
