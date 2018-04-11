package io.allstate.example.demo.controller;

import io.allstate.example.demo.domain.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/users")
public class UsersController {

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all users", response = User.class, responseContainer = "List")
    public Iterable<User> getAllUsers() {
        User user1 = User.builder()
                .firstName("Tom")
                .lastName("Franklin")
                .birthYear("2")
                .build();

        User user2 = User.builder()
                .firstName("Karen")
                .lastName("Franklin")
                .birthYear("1986")
                .build();

        return Arrays.asList(user1, user2);
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public User postUser(@Valid @RequestBody User user) {
        return User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthYear(user.getBirthYear())
                .build();
    }
}
