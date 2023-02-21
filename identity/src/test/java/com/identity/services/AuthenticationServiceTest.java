package com.identity.services;

import com.identity.requests.AuthenticationRequest;
import com.identity.requests.RegisterRequest;
import com.identity.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    private  AuthenticationService underTest;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void register() {
        //given
        var email = "email@gmail.com";
        var request = new RegisterRequest("fname","lname", email,"123456");

        //when
        underTest.register(request);
        var user = userRepository.findByEmail(email);

        //then
        assertThat(email).isEqualTo(user.get().getEmail());
    }

    @Test
    void authenticate() {
        //given
        var email = "email@gmail.com";
        var password = "pass";

        var registerRequest = new RegisterRequest("fname","lname", email,password);
        underTest.register(registerRequest);
        var request = new AuthenticationRequest(email, password);

        //when
        var token = underTest.authenticate(request);

        //then
        assertThat(token).isNotNull();
    }
}