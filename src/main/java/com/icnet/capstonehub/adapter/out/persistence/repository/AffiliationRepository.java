package com.icnet.capstonehub.adapter.out.persistence.repository;

import com.icnet.capstonehub.adapter.out.persistence.entity.join.AffiliationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AffiliationRepository extends JpaRepository<AffiliationEntity, Long> {
    void deleteByMajorId(Long majorId);
}
