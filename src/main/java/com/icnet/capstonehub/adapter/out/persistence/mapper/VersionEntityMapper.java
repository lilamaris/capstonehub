package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.VersionEntity;
import com.icnet.capstonehub.domain.model.Period;
import com.icnet.capstonehub.domain.model.Version;

import java.util.Optional;
import java.util.UUID;

public class VersionEntityMapper {
    public static VersionEntity toEntity(Version domain) {
        UUID id = Optional.ofNullable(domain.id())
                .map(Version.Id::value)
                .orElse(null);

        return VersionEntity.builder()
                .id(id)
                .sharedId(domain.sharedId().value())
                .versionNo(domain.versionNo())
                .versionDescription(domain.versionDescription())
                .txFrom(domain.txPeriod().from())
                .txTo(domain.txPeriod().to())
                .build();
    }

    public static Version toDomain(VersionEntity entity) {
        Version.Id id = new Version.Id(entity.getId());
        Version.SharedId sharedId = new Version.SharedId(entity.getSharedId());
        Period txPeriod = new Period(entity.getTxFrom(), entity.getTxTo());
        return Version.builder()
                .id(id)
                .sharedId(sharedId)
                .versionNo(entity.getVersionNo())
                .versionDescription(entity.getVersionDescription())
                .txPeriod(txPeriod)
                .build();
    }
}
