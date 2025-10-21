package com.icnet.capstonehub.adapter.out.persistence.entity;

import com.icnet.capstonehub.domain.model.Timeline;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "timeline")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TimelineEntity extends AuditableEntity {
    @Id @GeneratedValue
    private UUID id;

    @Column(name = "shared_id", nullable = false, updatable = false)
    private UUID sharedId;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false, updatable = false)
    private Timeline.Scope scope;

    @Column(name = "valid_from", nullable = false)
    private LocalDateTime validFrom;

    @Column(name = "valid_to")
    private LocalDateTime validTo;
}
