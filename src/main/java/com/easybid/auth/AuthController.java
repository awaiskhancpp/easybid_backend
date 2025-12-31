package com.easybid.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easybid.auth.dto.AuthRequestDTO;
import com.easybid.auth.dto.AuthResponseDTO;
import com.easybid.user.dto.CreateUserDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(final AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody final AuthRequestDTO request) {
    return ResponseEntity.ok(this.authService.login(request));
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponseDTO> register(@RequestBody final CreateUserDTO createUserDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.authService.register(createUserDTO));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<AuthResponseDTO> refresh(final HttpServletRequest request) {
    return ResponseEntity.ok(this.authService.refreshToken(request));
  }

}
