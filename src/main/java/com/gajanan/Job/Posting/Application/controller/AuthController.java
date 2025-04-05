package com.gajanan.Job.Posting.Application.controller;

import com.gajanan.Job.Posting.Application.model.dto.UserDTO;
import com.gajanan.Job.Posting.Application.service.AuthService;
import com.gajanan.Job.Posting.Application.util.AuthenticationRequest;
import com.gajanan.Job.Posting.Application.util.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserDTO request
            ){
        AuthenticationResponse authResponse = authService.register(request);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ){
        AuthenticationResponse authResponse=authService.login(request);
        return ResponseEntity.ok(authResponse);
    }
}
