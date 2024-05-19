package io.topfilms.api.services.user;

import io.topfilms.api.entities.User;
import io.topfilms.api.models.user.UserInput;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(UUID id);

    User createUser(UserInput userInput);

    Boolean deleteUser(UUID id);

}
