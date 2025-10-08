package com.icnet.capstonehub.domain.model;

import lombok.With;

public record CourseOffer (
        @With Timeline timeline,
        @With Edition edition,
        Course course,
        Integer credit,
        Integer semester
) {}
