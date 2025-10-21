package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.Edition;
import com.icnet.capstonehub.domain.model.User;

import java.util.List;

public interface EditionPort {
    Edition save(Edition domain);
    List<Edition> getAll();
    List<Edition> getByUserId(User.Id userId);
}
