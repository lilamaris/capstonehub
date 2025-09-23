package com.icnet.capstonehub.adapter.out.persistence.entity;

import com.icnet.capstonehub.domain.model.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "account")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountEntity {
    @Id @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private Account.Type provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "password_hash")
    private String passwordHash;

    @Setter
    @Column(name = "connected_at")
    private LocalDateTime connectedAt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity connectedUser;
}
