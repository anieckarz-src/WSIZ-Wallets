package com.identity.services;

import com.identity.exceptions.InvalidDataException;
import com.identity.requests.AuthenticationRequest;
import com.identity.models.AuthenticationDto;
import com.identity.requests.RegisterRequest;
import com.identity.user.Role;
import com.identity.user.User;
import com.identity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationDto register(RegisterRequest request) {

    if(!Extension.validateEmail(request.getEmail())){
      throw new InvalidDataException("email is invalid");
    }

    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();
    repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationDto.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationDto authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationDto.builder()
        .token(jwtToken)
        .build();
  }
}
