package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateUserCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateUserCommand;
import com.icnet.capstonehub.application.port.in.result.UserResult;

import java.util.List;

public interface UserUseCase {
    List<UserResult> getAll();
    UserResult getByEmail(String email);
    UserResult createUser(CreateUserCommand command);
    UserResult updateUser(UpdateUserCommand command);
}
