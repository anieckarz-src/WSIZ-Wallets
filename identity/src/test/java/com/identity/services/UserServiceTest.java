package com.identity.services;

import com.identity.requests.RegisterRequest;
import com.identity.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private  UserService underTest;
    @Autowired
    private  AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void update() {
        //given
        var email = "email@gmail.com";
        var firstName = "fUpdated";
        var lastName = "lUpdated";
        seedUser(email);

        //then
        underTest.update(email, firstName, lastName);
        var user = userRepository.findByEmail(email);

        //then
        assertThat(user.get().getFirstname()).isEqualTo(firstName);
    }

    @Test
    void delete() {
        //given
        var email = "email@gmail.com";
        seedUser(email);

        //then
        underTest.delete(email);
        var user = userRepository.findByEmail(email);

        //then
        assertThat(!user.isPresent());
    }

    @Test
    void getUser() {
        //given
        var email = "email@gmail.com";
        seedUser(email);

        //then
        underTest.getUser(email);
        var user = userRepository.findByEmail(email);

        //then
        assertThat(user.get().getEmail()).isEqualTo(email);
    }

    private void seedUser(String email){
        var request = new RegisterRequest("fname","lname", email,"123456");
        authenticationService.register(request);
    }
}