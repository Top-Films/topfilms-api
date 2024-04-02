package io.topfilms.api.controllers;

import io.topfilms.api.entities.User;
//import io.topfilms.api.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController {

//    @Autowired
//    private UserRepository userRepository;

    @QueryMapping
    public List<User> users() {
//        return userRepository.findAll();
        return null;
    }

    @QueryMapping
    public Optional<User> userById(UUID id) {
//        return userRepository.findById(id);
        return null;
    }

}
