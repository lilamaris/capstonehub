package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.EditionEntity;
import com.icnet.capstonehub.domain.model.Edition;
import com.icnet.capstonehub.domain.model.Period;

import java.util.Optional;
import java.util.UUID;

public class EditionEntityMapper {
    public static EditionEntity toEntity(Edition domain) {
        UUID id = Optional.ofNullable(domain.id())
                .map(Edition.Id::value)
                .orElse(null);

        return EditionEntity.builder()
                .id(id)
                .sharedId(domain.sharedId().value())
                .editionNo(domain.editionNo())
                .editionDescription(domain.editionDescription())
                .txFrom(domain.txPeriod().from())
                .txTo(domain.txPeriod().to())
                .build();
    }

    public static Edition toDomain(EditionEntity entity) {
        Edition.Id id = new Edition.Id(entity.getId());
        Edition.SharedId sharedId = new Edition.SharedId(entity.getSharedId());
        Period txPeriod = new Period(entity.getTxFrom(), entity.getTxTo());
        return Edition.builder()
                .id(id)
                .sharedId(sharedId)
                .editionNo(entity.getEditionNo())
                .editionDescription(entity.getEditionDescription())
                .txPeriod(txPeriod)
                .build();
    }
}
