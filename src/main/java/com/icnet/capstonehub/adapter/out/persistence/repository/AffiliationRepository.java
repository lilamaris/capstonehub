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
            INNER JOIN a.version v
            WHERE v.lineageId = :lineageId
            ORDER By v.versionNo ASC
            """)
    List<AffiliationEntity> findByLineageId(@Param("lineageId") UUID lineageId);

    @Query("""
            SELECT a
            FROM AffiliationEntity a
            INNER JOIN a.version v
            WHERE v.lineageId = :lineageId
                AND v.validFrom <= :current
                AND (v.validTo IS NULL OR v.validTo > :current)
            """)
    Optional<AffiliationEntity> findCurrent(
            @Param("lineageId") UUID lineageId,
            @Param("current") LocalDate current
    );

    @Query("""
            SELECT a
            FROM AffiliationEntity a
            INNER JOIN a.version v
            WHERE v.lineageId = :lineageId
                AND v.validTo IS NULL""")
    Optional<AffiliationEntity> findCurrent(@Param("lineageId") UUID lineageId);
}