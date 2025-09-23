package com.icnet.capstonehub.adapter.out.persistence.entity;

import com.icnet.capstonehub.domain.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "capstoneUser")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {
    @Id @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private User.Role role;

    @OneToMany(mappedBy = "connectedUser", fetch = FetchType.LAZY)
    private List<AccountEntity> connectedAccount;

    public void addAccount(AccountEntity account, LocalDateTime connectedAt) {
        connectedAccount.add(account);
        account.setConnectedUser(this);
        account.setConnectedAt(connectedAt);
    }

    public void removeAccount(AccountEntity account) {
        connectedAccount.remove(account);
        account.setConnectedUser(null);
    }
}
