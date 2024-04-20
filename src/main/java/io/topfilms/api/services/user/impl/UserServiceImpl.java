package io.topfilms.api.services.user.impl;

import io.topfilms.api.entities.User;
import io.topfilms.api.exceptions.TopFilmsException;
import io.topfilms.api.models.UserInput;
import io.topfilms.api.repositories.UserRepository;
import io.topfilms.api.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl() {}

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    @PreAuthorize("#userInput.getId().toString() == principal.subject")
    public User createUser(UserInput userInput) {
        if (userRepository.existsById(userInput.getId())) {
            throw new TopFilmsException("User already exists. Please use your original login method.", ErrorType.BAD_REQUEST);
        }

        if (userRepository.existsByUsername(userInput.getUsername())) {
            throw new TopFilmsException("Username '" + userInput.getUsername() + "' is taken. Please pick a different one.", ErrorType.BAD_REQUEST);
        }

        return userRepository.save(new User(
                userInput.getId(),
                userInput.getUsername(),
                userInput.getFirstName(),
                userInput.getLastName()
        ));
    }

    @Override
    @PreAuthorize("#id.toString() == principal.subject")
    public Boolean deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            return false;
        }

        userRepository.deleteById(id);
        return true;
    }

}
