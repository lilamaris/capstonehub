package com.icnet.capstonehub.adapter.out.persistence.repository;

import com.icnet.capstonehub.adapter.out.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    @Query("""
            SELECT a
            FROM AccountEntity a
            LEFT JOIN FETCH a.connectedUser u
            WHERE u.email = :email
            """)
    List<AccountEntity> findAccountByUserEmail(@Param("email") String email);

    @Query("""
            SELECT a
            FROM AccountEntity a
            LEFT JOIN FETCH a.connectedUser u
            WHERE u.email = :email
                AND a.provider = com.icnet.capstonehub.domain.model.Account.Type.CREDENTIAL""")
    Optional<AccountEntity> findCredentialByUserEmail(@Param("email") String email);
}
