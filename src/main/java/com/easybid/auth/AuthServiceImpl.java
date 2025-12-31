package com.easybid.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.easybid.auth.dto.AuthRequestDTO;
import com.easybid.auth.dto.AuthResponseDTO;
import com.easybid.auth.jwt.JwtService;
import com.easybid.exceptions.InvalidTokenException;
import com.easybid.exceptions.UnauthorizedException;
import com.easybid.mapper.AuthMapper;
import com.easybid.token.TokenRepository;
import com.easybid.user.UserService;
import com.easybid.user.dto.CreateUserDTO;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserService userService;
  private final PasswordHasher passwordHasher;
  private final TokenRepository tokenRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthServiceImpl(
      final UserService userService,
      final PasswordHasher passwordHasher,
      final TokenRepository tokenRepository,
      final JwtService jwtService,
      final AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.passwordHasher = passwordHasher;
    this.tokenRepository = tokenRepository;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public AuthResponseDTO login(final AuthRequestDTO authRequestDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authRequestDTO.getEmail(),
            authRequestDTO.getPassword()));
    final var user = this.userService.findUserByEmail(authRequestDTO.getEmail());
    final var accessToken = this.jwtService.generateToken(user);
    final var refreshToken = this.jwtService.generateRefreshToken(user);
    return AuthMapper.toAuthResponseDTO(user, refreshToken, accessToken);
  }

  @Override
  public AuthResponseDTO register(final CreateUserDTO userDTO) {
    final var user = this.userService.createUser(userDTO);
    final var accessToken = this.jwtService.generateToken(user);
    final var refreshToken = this.jwtService.generateRefreshToken(user);
    return AuthMapper.toAuthResponseDTO(user, refreshToken, accessToken);
  }

  @Override
  public AuthResponseDTO refreshToken(HttpServletRequest request) {
    var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new UnauthorizedException("Authorization token is missing or invalid.");
    }
    var token = authHeader.substring(7);
    var userEmail = this.jwtService.extractUsername(token);
    var user = this.userService.findUserByEmail(userEmail);
    if (!jwtService.isTokenValid(token, user)) {
      throw new InvalidTokenException("Token is invalid or Experied.");
    }
    var refreshToken = this.jwtService.generateRefreshToken(user);
    var accessToken = this.jwtService.generateToken(user);
    return AuthMapper.toAuthResponseDTO(user, refreshToken, accessToken);
  }

}
