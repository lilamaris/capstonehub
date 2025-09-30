package com.icnet.capstonehub.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "major")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MajorEntity extends AuditableEntity {
    @Id @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;
}