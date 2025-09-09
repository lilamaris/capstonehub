package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.LineageEntity;
import com.icnet.capstonehub.domain.Lineage;
import com.icnet.capstonehub.domain.Period;

import java.util.Optional;
import java.util.UUID;

public class LineageEntityMapper {
    public static LineageEntity toEntity(Lineage domain) {
        UUID id = Optional.ofNullable(domain.id())
                .map(UUID.class::cast)
                .orElse(null);

        return LineageEntity.builder()
                .id(id)
                .lineageId(domain.lineageId().value())
                .scope(domain.scope())
                .validFrom(domain.validPeriod().from())
                .validTo(domain.validPeriod().to())
                .build();
    }

    public static Lineage toDomain(LineageEntity entity) {
        Lineage.Id id = new Lineage.Id(entity.getId());
        Lineage.LineageId lineageId = new Lineage.LineageId(entity.getLineageId());
        Period validPeriod = new Period(entity.getValidFrom(), entity.getValidTo());
        return Lineage.builder()
                .id(id)
                .lineageId(lineageId)
                .validPeriod(validPeriod)
                .build();
    }
}
