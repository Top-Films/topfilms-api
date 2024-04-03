package io.topfilms.api.services.user.impl;

import io.topfilms.api.entities.User;
import io.topfilms.api.models.UserInput;
import io.topfilms.api.repositories.UserRepository;
import io.topfilms.api.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public User createUser(UserInput userInput) {
        return userRepository.save(new User(
                userInput.getId(),
                userInput.getUsername(),
                userInput.getFirstName(),
                userInput.getLastName()
        ));
    }

}
