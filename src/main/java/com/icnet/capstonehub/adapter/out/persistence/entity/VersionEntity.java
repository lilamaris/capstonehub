package com.icnet.capstonehub.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "version")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VersionEntity {
    @Id @GeneratedValue
    private UUID id;

    @Column(name = "version_no", nullable = false, updatable = false)
    private Integer versionNo;

    @Column(name = "version_description")
    private String versionDescription;

    @Column(name = "tx_from", nullable = false)
    private LocalDate txFrom;

    @Column(name ="tx_to")
    private LocalDate txTo;
}
