package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.UserUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateUserCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateUserCommand;
import com.icnet.capstonehub.application.port.in.mapper.UserResultMapper;
import com.icnet.capstonehub.application.port.in.result.UserResult;
import com.icnet.capstonehub.application.port.out.UserPort;
import com.icnet.capstonehub.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserUseCase {
    private final UserPort userPort;

    @Override
    public UserResult createUser(CreateUserCommand command) {
        User user = User.builder()
                .email(command.email())
                .name(command.name())
                .role(User.Role.STUDENT)
                .build();
        return UserResultMapper.toResult(userPort.save(user));
    }

    @Override
    public UserResult updateUser(UpdateUserCommand command) {
        return null;
    }

    @Override
    public List<UserResult> getAll() {
        return List.of();
    }
}
