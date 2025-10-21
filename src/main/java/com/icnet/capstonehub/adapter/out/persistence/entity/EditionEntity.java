package com.icnet.capstonehub.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "edition")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EditionEntity extends AuditableEntity{
    @Id @GeneratedValue
    private UUID id;

    @Column(name = "shared_id", nullable = false, updatable = false)
    private UUID sharedId;

    @Column(name = "edition_no", nullable = false, updatable = false)
    private Integer editionNo;

    @Column(name = "edition_description")
    private String editionDescription;

    @Column(name = "tx_from", nullable = false)
    private LocalDateTime txFrom;

    @Column(name ="tx_to")
    private LocalDateTime txTo;
}
