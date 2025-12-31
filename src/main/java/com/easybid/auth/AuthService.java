package com.easybid.auth;

import com.easybid.auth.dto.AuthRequestDTO;
import com.easybid.auth.dto.AuthResponseDTO;
import com.easybid.user.dto.CreateUserDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
  AuthResponseDTO login(AuthRequestDTO authRequestDTO);

  AuthResponseDTO register(CreateUserDTO userDTO);

  AuthResponseDTO refreshToken(HttpServletRequest request);

}
