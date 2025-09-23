package com.icnet.capstonehub.unit.domain;

import com.icnet.capstonehub.domain.model.Account;
import com.icnet.capstonehub.domain.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class AccountTest {
    @Test
    public void should_create_account() {
        var now = LocalDateTime.now();
        var userId = new User.Id(UUID.randomUUID());
        var user = User.builder()
                .id(userId)
                .build();

        var providerId = UUID.randomUUID().toString();
        var credential = Account.withCredential("This is Hashed Password", user, now);
        var provider = Account.withProvider(Account.Type.GITHUB, providerId, user, now);

        assertThat(credential).isInstanceOf(Account.class);
        assertThat(credential.provider()).isEqualTo(Account.Type.CREDENTIAL);
        assertThat(credential.connectedAt()).isEqualTo(now);

        assertThat(provider).isInstanceOf(Account.class);
        assertThat(provider.provider()).isEqualTo(Account.Type.GITHUB);
        assertThat(provider.connectedAt()).isEqualTo(now);
    }

    @Test
    public void should_throw() {
        assertThatThrownBy(
                () -> {
                    var manual_credential = Account.builder()
                            .provider(Account.Type.CREDENTIAL)
                            .build();
                }
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
