package com.icnet.capstonehub.unit.application;

import com.icnet.capstonehub.application.port.in.command.SignupCredentialCommand;
import com.icnet.capstonehub.application.service.AccountService;
import com.icnet.capstonehub.unit.util.FakeAccountPort;
import com.icnet.capstonehub.unit.util.FakeUserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationTest {
    @Mock
    PasswordEncoder encoder;

    AccountService svc;

    @BeforeEach
    void setup() {
        var userPort = new FakeUserPort(UUID::randomUUID);
        var accountPort = new FakeAccountPort(UUID::randomUUID);

        svc = new AccountService(userPort, accountPort);
    }

    @Test
    @DisplayName("When user sign up, new credential account is created and linked to user")
    void should_create_credential_and_link() {
        var email = "kim@ex.co";
        var raw_pw = "asdf1234";
        when(encoder.encode(raw_pw)).thenReturn("hashed");

        var command = SignupCredentialCommand.builder()
                .name("Kim")
                .email(email)
                .passwordHash(encoder.encode(raw_pw))
                .build();

        var res = svc.signupCredential(command);

        assertThat(res.email()).isEqualTo(email);
        assertThat(res.name()).isEqualTo("Kim");

        var credentials = svc.getCredentialByUserEmail(email);

        assertThat(credentials.passwordHash()).isEqualTo(encoder.encode(raw_pw));
        assertThat(credentials.user().id()).isEqualTo(res.id());
    }

    @Test
    @DisplayName("When user signup with email that already created, throw exception")
    void duplicated_user() {
        var u1_email = "kim@ex.com";
        var pw = "raw_pw";

        when(encoder.encode("raw_pw")).thenReturn("hashed");

        var u1_command = SignupCredentialCommand.builder()
                .name("kim")
                .email(u1_email)
                .passwordHash(encoder.encode(pw))
                .build();

        var u1_res = svc.signupCredential(u1_command);

        var u2_command = SignupCredentialCommand.builder()
                .name("JJAAAAA")
                .email(u1_email)
                .passwordHash(encoder.encode(pw))
                .build();

        assertThatThrownBy(
                () -> svc.signupCredential(u2_command)
        ).isInstanceOf(IllegalStateException.class);
    }
}
