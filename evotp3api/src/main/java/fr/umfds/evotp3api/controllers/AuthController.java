package fr.umfds.evotp3api.controllers;

import fr.umfds.evotp3api.dto.Credentials;
import fr.umfds.evotp3api.models.User;
import fr.umfds.evotp3api.repositories.UserRepository;
import fr.umfds.evotp3api.services.JWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/auth")
@RestController
public class AuthController {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JWTService jwtService, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> register(@RequestBody User registerUser) {
        registerUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        return ResponseEntity.ok(jwtService.issue(userRepository.saveAndFlush(registerUser)));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> authenticate(@RequestBody Credentials credentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getEmail(),
                        credentials.getPassword()
                )
        );
        var user = userRepository.findByEmail(credentials.getEmail()).orElseThrow();
        return ResponseEntity.ok(jwtService.issue(user));
    }
}