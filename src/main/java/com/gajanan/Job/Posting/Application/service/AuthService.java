package com.gajanan.Job.Posting.Application.service;

import com.gajanan.Job.Posting.Application.model.dto.UserDTO;
import com.gajanan.Job.Posting.Application.model.entity.User;
import com.gajanan.Job.Posting.Application.repository.UserRepository;
import com.gajanan.Job.Posting.Application.util.AuthenticationRequest;
import com.gajanan.Job.Posting.Application.util.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(UserDTO request) {
        var user=new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());


        var savedUser=userRepository.save(user);
        String jwtToken=jwtService.generateToken(savedUser);
        return AuthenticationResponse.builder().jwt(jwtToken).build();

    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        String jwtToken=jwtService.generateToken(user);

        return AuthenticationResponse.builder().jwt(jwtToken).build();
    }
}
