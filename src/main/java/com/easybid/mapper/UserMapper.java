package com.easybid.mapper;

import com.easybid.auth.PasswordHasher;
import com.easybid.user.UserEntity;
import com.easybid.user.dto.UpdateUserDTO;
import com.easybid.user.dto.UserResponseDTO;

public class UserMapper {

  public static UserResponseDTO toUserResponseDTO(final UserEntity userEntity) {
    final UserResponseDTO userResponseDTO = new UserResponseDTO();
    userResponseDTO.setId(userEntity.getId());
    userResponseDTO.setName(userEntity.getName());
    userResponseDTO.setEmail(userEntity.getEmail());
    userResponseDTO.setPhoneNumber(userEntity.getPhoneNumber());
    userResponseDTO.setRole(userEntity.getRole());
    userResponseDTO.setAddress(userEntity.getAddress());
    userResponseDTO.setBio(userEntity.getBio());
    userResponseDTO.setCreatedAt(userEntity.getCreatedAt());
    userResponseDTO.setUpdatedAt(userEntity.getUpdatedAt());
    userResponseDTO.setDeletedAt(userEntity.getDeletedAt());
    return userResponseDTO;
  }

  public static void updateEntityFromDTO(UserEntity userEntity, UpdateUserDTO dto, PasswordHasher passwordHasher) {
    if (dto.getName() != null && !dto.getName().isBlank()) {
      userEntity.setName(dto.getName());
    }
    if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
      userEntity.setEmail(dto.getEmail());
    }
    if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
      userEntity.setHashPassword(passwordHasher.hash(dto.getPassword()));
    }
    if (dto.getAddress() != null && !dto.getAddress().isBlank()) {
      userEntity.setAddress(dto.getAddress());
    }
    if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isBlank()) {
      userEntity.setPhoneNumber(dto.getPhoneNumber());
    }
    if (dto.getBio() != null && !dto.getBio().isBlank()) {
      userEntity.setBio(dto.getBio());
    }
    if (dto.getRole() != null) {
      userEntity.setRole(dto.getRole());
    }
  }

}
