package io.topfilms.api.services.user.impl;

import io.topfilms.api.entities.User;
import io.topfilms.api.exceptions.TopFilmsException;
import io.topfilms.api.models.UserInput;
import io.topfilms.api.repositories.UserRepository;
import io.topfilms.api.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl() {}

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(UUID id) {
        LOG.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication()));
        return userRepository.findById(id);
    }

    @Override
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

}
