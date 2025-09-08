package com.icnet.capstonehub.adapter.out.persistence.entity;

import com.icnet.capstonehub.domain.Version;
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

    @Column(name = "lineage_id", nullable = false, updatable = false)
    private UUID lineageId;

    @Enumerated(EnumType.STRING)
    @Column(name = "lineage_scope")
    private Version.LineageScope lineageScope;

    @Column(name = "version_no", nullable = false, updatable = false)
    private Integer versionNo;

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "version_description")
    private String versionDescription;
}
