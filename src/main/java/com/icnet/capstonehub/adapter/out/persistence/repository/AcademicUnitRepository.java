package com.icnet.capstonehub.adapter.out.persistence.repository;

import com.icnet.capstonehub.adapter.out.persistence.entity.AcademicUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AcademicUnitRepository extends JpaRepository<AcademicUnitEntity, UUID> {
    @Query("""
            SELECT a
            FROM AcademicUnitEntity a
            LEFT JOIN FETCH a.edition v
            LEFT JOIN FETCH a.timeline l
            WHERE l.scope = com.icnet.capstonehub.domain.model.Timeline.Scope.AFFILIATION
                AND l.sharedId = :timelineSharedId
                AND v.sharedId = :editionSharedId
                AND v.txFrom <= :txAt
                AND (v.txTo IS NULL OR v.txTo > :txAt)""")
    Optional<AcademicUnitEntity> findSnapshotOfRecord(
            @Param("timelineSharedId") UUID timelineSharedId,
            @Param("editionSharedId") UUID editionSharedId,
            @Param("txAt") LocalDateTime txAt
    );

    @Query("""
            SELECT a
            FROM AcademicUnitEntity a
            LEFT JOIN FETCH a.edition v
            LEFT JOIN FETCH a.timeline l
            WHERE l.scope = com.icnet.capstonehub.domain.model.Timeline.Scope.AFFILIATION
                AND l.sharedId = :timelineSharedId
                AND l.validFrom <= :validAt
                AND (l.validTo IS NULL OR l.validTo > :validAt)
                AND v.txFrom <= :txAt
                AND (v.txTo IS NULL OR v.txTo > :txAt)""")
    Optional<AcademicUnitEntity> findSnapshotOfRecord(
            @Param("timelineSharedId") UUID timelineSharedId,
            @Param("validAt") LocalDateTime validAt,
            @Param("txAt") LocalDateTime txAt
    );

    @Query("""
            SELECT a
            FROM AcademicUnitEntity a
            LEFT JOIN FETCH a.edition v
            LEFT JOIN FETCH a.timeline l
            WHERE l.scope = com.icnet.capstonehub.domain.model.Timeline.Scope.AFFILIATION
                AND l.sharedId = :timelineSharedId
                AND v.sharedId = :editionSharedId""")
    List<AcademicUnitEntity> findEditionOfRecord(
            @Param("timelineSharedId") UUID timelineSharedId,
            @Param("editionSharedId") UUID editionSharedId
    );

    @Query("""
            SELECT a
            FROM AcademicUnitEntity a
            LEFT JOIN FETCH a.edition v
            LEFT JOIN FETCH a.timeline l
            WHERE l.scope = com.icnet.capstonehub.domain.model.Timeline.Scope.AFFILIATION
                AND l.sharedId = :timelineSharedId
                AND l.validFrom <= :validAt
                AND (l.validTo IS NULL OR l.validTo > :validAt)""")
    List<AcademicUnitEntity> findEditionOfRecord(
            @Param("timelineSharedId") UUID timelineSharedId,
            @Param("validAt") LocalDateTime validAt
    );

    @Query("""
            SELECT a
            FROM AcademicUnitEntity a
            LEFT JOIN FETCH a.edition v
            LEFT JOIN FETCH a.timeline l
            WHERE l.scope = com.icnet.capstonehub.domain.model.Timeline.Scope.AFFILIATION
                AND l.sharedId = :timelineSharedId
                AND v.txFrom <= :txAt
                AND (v.txTo IS NULL OR v.txTo > :txAt)""")
    List<AcademicUnitEntity> findTimelineOfSnapshot(
            @Param("timelineSharedId") UUID timelineSharedId,
            @Param("txAt") LocalDateTime txAt
    );
}