package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.Timeline;
import com.icnet.capstonehub.domain.model.User;

import java.util.List;

public interface TimelinePort {
    Timeline save(Timeline domain);
    List<Timeline> getAll();
    List<Timeline> getByUserId(User.Id userId);
}
