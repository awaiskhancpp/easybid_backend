package com.easybid.mapper;

import com.easybid.auth.dto.AuthResponseDTO;
import com.easybid.user.UserEntity;

public class AuthMapper {

  public static AuthResponseDTO toAuthResponseDTO(UserEntity userEntity, String refreshToken, String accessToken) {
    AuthResponseDTO authResponseDTO = new AuthResponseDTO();
    authResponseDTO.setUser(UserMapper.toUserResponseDTO(userEntity));
    authResponseDTO.setRefreshToken(refreshToken);
    authResponseDTO.setAccessToken(accessToken);
    return authResponseDTO;
  }
}
