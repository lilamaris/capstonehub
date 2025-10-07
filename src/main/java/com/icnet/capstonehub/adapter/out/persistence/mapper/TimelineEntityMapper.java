package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.TimelineEntity;
import com.icnet.capstonehub.domain.model.Timeline;
import com.icnet.capstonehub.domain.model.Period;

import java.util.Optional;
import java.util.UUID;

public class TimelineEntityMapper {
    public static TimelineEntity toEntity(Timeline domain) {
        UUID id = Optional.ofNullable(domain.id())
                .map(Timeline.Id::value)
                .orElse(null);

        return TimelineEntity.builder()
                .id(id)
                .sharedId(domain.sharedId().value())
                .scope(domain.scope())
                .validFrom(domain.validPeriod().from())
                .validTo(domain.validPeriod().to())
                .build();
    }

    public static Timeline toDomain(TimelineEntity entity) {
        Timeline.Id id = new Timeline.Id(entity.getId());
        Timeline.SharedId sharedId = new Timeline.SharedId(entity.getSharedId());
        Period validPeriod = new Period(entity.getValidFrom(), entity.getValidTo());
        return Timeline.builder()
                .id(id)
                .sharedId(sharedId)
                .scope(entity.getScope())
                .validPeriod(validPeriod)
                .build();
    }
}
