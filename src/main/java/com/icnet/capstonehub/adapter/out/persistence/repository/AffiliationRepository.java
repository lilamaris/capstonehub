package com.icnet.capstonehub.adapter.out.persistence.repository;

import com.icnet.capstonehub.adapter.out.persistence.entity.AffiliationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AffiliationRepository extends JpaRepository<AffiliationEntity, UUID> {
    @Query(value = """
            SELECT a
            FROM AffiliationEntity a
            INNER JOIN a.version v
            WHERE v.lineageId = :lineageId
            ORDER By v.version_no ASC
            """, nativeQuery = true)
    List<AffiliationEntity> findByLineageId(UUID lineageId);

    @Query(value = """
            SELECT a
            FROM AffiliationEntity a
            INNER JOIN a.version v
            WHERE v.lineageId = :lineageId
                AND v.validFrom <= :current
                AND (v.validTo IS NULL OR v.validTo > :current)
            """, nativeQuery = true)
    Optional<AffiliationEntity> findCurrent(UUID lineageId, LocalDate current);

    @Query(value = """
            SELECT a
            FROM affiliationEntity a
            INNER JOIN a.version v
            WHERE v.lineageId = :lineageId
                AND v.validTo IS NULL""", nativeQuery = true)
    Optional<AffiliationEntity> findCurrent(UUID lineageId);
}