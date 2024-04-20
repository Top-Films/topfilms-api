package io.topfilms.api.controllers;

import io.topfilms.api.entities.User;
import io.topfilms.api.models.UserInput;
import io.topfilms.api.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @QueryMapping
    public List<User> users() {
        return userService.findAll();
    }

    @QueryMapping
    public Optional<User> userById(@Argument UUID id) {
        return userService.findById(id);
    }

    @MutationMapping
    public User createUser(@Argument UserInput input) {
        return userService.createUser(input);
    }

    @MutationMapping
    public Boolean deleteUser(@Argument UUID id) {
        return userService.deleteUser(id);
    }

}
