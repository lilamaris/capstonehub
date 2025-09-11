package com.icnet.capstonehub.adapter.out.persistence.entity;

import com.icnet.capstonehub.domain.model.Lineage;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "lineage")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LineageEntity {
    @Id @GeneratedValue
    private UUID id;

    @Column(name = "shared_id", nullable = false, updatable = false)
    private UUID sharedId;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false, updatable = false)
    private Lineage.Scope scope;

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;
}
