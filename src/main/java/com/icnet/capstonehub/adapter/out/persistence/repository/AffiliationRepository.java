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
                AND l.lineageId = :lineageId
                AND l.validFrom <= :now
                AND (l.validTo IS NULL OR l.validTo > :now)
                AND v.txTo IS NULL
            """)
    Optional<AffiliationEntity> findLineageHead(
            @Param("lineageId") UUID lineageId,
            @Param("now") LocalDate now
    );

    @Query("""
            SELECT a
            FROM AffiliationEntity a
            LEFT JOIN FETCH a.version v
            LEFT JOIN FETCH a.lineage l
            WHERE l.scope = com.icnet.capstonehub.domain.Lineage.Scope.AFFILIATION
                AND l.lineageId = :lineageId
                AND v.txFrom <= :txAt
                AND (v.txTo IS NULL OR v.txTo > :txAt)
            """)
    List<AffiliationEntity> findLineageSnapshotAtTx(
            @Param("lineageId") UUID lineageId,
            @Param("txAt") LocalDate txAt
    );

    @Query("""
            SELECT a
            FROM AffiliationEntity a
            LEFT JOIN FETCH a.version v
            LEFT JOIN FETCH a.lineage l
            WHERE l.scope = com.icnet.capstonehub.domain.Lineage.Scope.AFFILIATION
                AND l.lineageId = :lineageId
                AND l.validFrom <= :on
                AND (l.validTo IS NULL OR l.validTo > :on)
                AND v.txFrom <= :txAt
                AND (v.txTo IS NULL OR v.txTo > :txAt)
            """)
    List<AffiliationEntity> findLineageSnapshotAtTxOnDate(
            @Param("lineageId") UUID lineageId,
            @Param("txAt") LocalDate txAt,
            @Param("on") LocalDate on
    );
}