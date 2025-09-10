package com.icnet.capstonehub.adapter.out.persistence.repository;

import com.icnet.capstonehub.adapter.out.persistence.entity.AffiliationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AffiliationRepository extends JpaRepository<AffiliationEntity, UUID> {
    @Query("""
            SELECT a
            FROM AffiliationEntity a
            LEFT JOIN FETCH a.version v
            LEFT JOIN FETCH a.lineage l
            WHERE l.scope = com.icnet.capstonehub.domain.Lineage.Scope.AFFILIATION
                AND l.sharedId = :lineageSharedId
                AND v.sharedId = :versionSharedId
                AND v.txFrom <= :txAt
                AND (v.txTo IS NULL OR v.txTo > :txAt)""")
    Optional<AffiliationEntity> findSnapshotOfRecord(
            @Param("lineageSharedId") UUID lineageSharedId,
            @Param("versionSharedId") UUID versionSharedId,
            @Param("txAt") LocalDate txAt
    );

    @Query("""
            SELECT a
            FROM AffiliationEntity a
            LEFT JOIN FETCH a.version v
            LEFT JOIN FETCH a.lineage l
            WHERE l.scope = com.icnet.capstonehub.domain.Lineage.Scope.AFFILIATION
                AND l.sharedId = :lineageSharedId
                AND l.validFrom <= :validAt
                AND (l.validTo IS NULL OR l.validTo > :validAt)
                AND v.txFrom <= :txAt
                AND (v.txTo IS NULL OR v.txTo > :txAt)""")
    Optional<AffiliationEntity> findSnapshotOfRecord(
            @Param("lineageSharedId") UUID lineageSharedId,
            @Param("validAt") LocalDate validAt,
            @Param("txAt") LocalDate txAt
    );

    @Query("""
            SELECT a
            FROM AffiliationEntity a
            LEFT JOIN FETCH a.version v
            LEFT JOIN FETCH a.lineage l
            WHERE l.scope = com.icnet.capstonehub.domain.Lineage.Scope.AFFILIATION
                AND l.sharedId = :lineageSharedId
                AND v.sharedId = :versionSharedId""")
    List<AffiliationEntity> findVersionOfRecord(
            @Param("lineageSharedId") UUID lineageSharedId,
            @Param("versionSharedId") UUID versionSharedId
    );

    @Query("""
            SELECT a
            FROM AffiliationEntity a
            LEFT JOIN FETCH a.version v
            LEFT JOIN FETCH a.lineage l
            WHERE l.scope = com.icnet.capstonehub.domain.Lineage.Scope.AFFILIATION
                AND l.sharedId = :lineageSharedId
                AND l.validFrom <= :validAt
                AND (l.validTo IS NULL OR l.validTo > :validAt)""")
    List<AffiliationEntity> findVersionOfRecord(
            @Param("lineageSharedId") UUID lineageSharedId,
            @Param("validAt") LocalDate validAt
    );

    @Query("""
            SELECT a
            FROM AffiliationEntity a
            LEFT JOIN FETCH a.version v
            LEFT JOIN FETCH a.lineage l
            WHERE l.scope = com.icnet.capstonehub.domain.Lineage.Scope.AFFILIATION
                AND l.sharedId = :lineageSharedId
                AND v.txFrom <= :txAt
                AND (v.txTo IS NULL OR v.txTo > :txAt)""")
    List<AffiliationEntity> findLineageOfSnapshot(
            @Param("lineageSharedId") UUID lineageSharedId,
            @Param("txAt") LocalDate txAt
    );
}