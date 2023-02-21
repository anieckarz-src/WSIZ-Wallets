package com.identity.services;

import com.identity.exceptions.UserNotFoundException;
import com.identity.models.UserDto;
import com.identity.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void update(String email, String firstName, String lastName){

        var user = userRepository.findByEmail(email).get();

        if(user == null){
            throw new UserNotFoundException(email);
        }

        user.update(firstName, lastName);

        userRepository.save(user);
    }

    public void delete(String email){

        var user = userRepository.findByEmail(email).get();

        if(user == null){
            throw new UserNotFoundException(email);
        }

        userRepository.delete(user);
    }

    public UserDto getUser(String email){
        var user = userRepository.findByEmail(email).get();

        if(user == null){
            throw new UserNotFoundException(email);
        }

        return new UserDto(user.getId(), user.getEmail(), user.getFirstname(), user.getLastname());
    }

}
