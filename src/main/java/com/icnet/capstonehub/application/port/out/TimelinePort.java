package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.Timeline;
import com.icnet.capstonehub.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface TimelinePort {
    Timeline save(Timeline domain);
    Optional<Timeline> get(Timeline.Id id);
    List<Timeline> getAll();
    List<Timeline> getByUserId(User.Id userId);
}
