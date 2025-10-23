package com.icnet.capstonehub.adapter.out.persistence.repository;

import com.icnet.capstonehub.adapter.out.persistence.entity.AcademicUnitEntity;
import org.springframework.data.jpa.repository.EntityGraph;
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
    @EntityGraph(attributePaths = {"edition", "timeline"})
    @Query("""
            SELECT a
            FROM AcademicUnitEntity a
            WHERE a.createdBy = :userId""")
    List<AcademicUnitEntity> findAllByUserId(
            @Param("userId") UUID userId
    );

    @EntityGraph(attributePaths = {"edition"})
    @Query("""
            SELECT a
            FROM AcademicUnitEntity a
            JOIN FETCH a.timeline t
            WHERE t.sharedId = :timelineSharedId""")
    List<AcademicUnitEntity> findTimeline(
            @Param("timelineSharedId") UUID timelineSharedId
    );

    @Query("""
            SELECT a
            FROM AcademicUnitEntity a
            JOIN FETCH a.timeline t
            JOIN FETCH a.edition e
            WHERE t.sharedId = :timelineSharedId
                AND t.validTo = :maxTime
                AND e.txTo = :maxTime""")
    Optional<AcademicUnitEntity> findHeadOfTimeline(
            @Param("timelineSharedId") UUID timelineSharedId,
            @Param("maxTime") LocalDateTime maxTime
    );

    @EntityGraph(attributePaths = {"edition"})
    @Query("""
            SELECT a
            FROM AcademicUnitEntity a
            JOIN FETCH a.timeline t
            JOIN FETCH a.edition e
            WHERE t.sharedId = :timelineSharedId
                AND e.txFrom <= :txAt
                AND e.txTo > :txAt""")
    List<AcademicUnitEntity> findSnapshot(
            @Param("timelineSharedId") UUID timelineSharedId,
            @Param("txAt") LocalDateTime txAt
    );
}