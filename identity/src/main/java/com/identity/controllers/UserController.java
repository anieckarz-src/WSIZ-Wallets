package com.identity.controllers;

import com.identity.models.UserDto;
import com.identity.requests.UpdateUserRequest;
import com.identity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getPrincipal(Principal principal){
        var user = userService.getUser(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<UserDto> deletePrincipal(Principal principal){
        userService.getUser(principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<UserDto> updatePrincipal(@RequestBody UpdateUserRequest request, Principal principal){
        userService.update(principal.getName(), request.getFirstName(), request.getLastName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
