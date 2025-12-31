package com.easybid.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.easybid.user.dto.CreateUserDTO;
import com.easybid.user.dto.UpdateUserDTO;
import com.easybid.user.dto.UserResponseDTO;
import com.easybid.auth.PasswordHasher;
import com.easybid.exceptions.DataConflictException;
import com.easybid.exceptions.ResourceNotFoundException;
import com.easybid.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordHasher passwordHasher;

  public UserServiceImpl(final UserRepository userRepository, final PasswordHasher passwordHasher) {
    this.userRepository = userRepository;
    this.passwordHasher = passwordHasher;
  }

  @Override
  public UserEntity createUser(final CreateUserDTO createUserDTO) {
    if (userRepository.findByEmailAndDeletedAtIsNull(createUserDTO.getEmail()).isPresent()) {
      throw new DataConflictException("Email already exists: " + createUserDTO.getEmail());
    }
    final UserEntity userEntity = new UserEntity();
    userEntity.setName(createUserDTO.getName());
    String hashPassword = this.passwordHasher.hash(createUserDTO.getPassword());
    userEntity.setHashPassword(hashPassword);
    userEntity.setAddress(createUserDTO.getAddress());
    userEntity.setEmail(createUserDTO.getEmail());
    userEntity.setPhoneNumber(createUserDTO.getPhoneNumber());
    userEntity.setRole(createUserDTO.getRole());
    userEntity.setBio(createUserDTO.getBio());
    this.userRepository.save(userEntity);
    return this.findUserById(userEntity.getId());
  }

  @Override
  public UserResponseDTO updateUser(final UUID userId, final UpdateUserDTO updateUserDTO) {
    final UserEntity user = this.findUserById(userId);
    if (userRepository.findByEmailAndDeletedAtIsNull(updateUserDTO.getEmail()).isPresent()
        && !user.getEmail().equals(updateUserDTO.getEmail())) {
      throw new DataConflictException("Email already exists: " + updateUserDTO.getEmail());
    }
    System.out.println("befor: " + user.getHashPassword());
    UserMapper.updateEntityFromDTO(user, updateUserDTO, this.passwordHasher);
    this.userRepository.save(user);
    System.out.println("after : " + user.getHashPassword());
    return UserMapper.toUserResponseDTO(user);
  }

  @Override
  public UserResponseDTO getUserById(final UUID userId) {
    return UserMapper.toUserResponseDTO(findUserById(userId));
  }

  @Override
  public List<UserResponseDTO> getAllUsers() {
    final List<UserEntity> users = this.userRepository.findByDeletedAtIsNull();
    final List<UserResponseDTO> responseDTOs = users.stream()
        .map(UserMapper::toUserResponseDTO)
        .collect(Collectors.toList());
    return responseDTOs;
  }

  @Override
  public void deleteUser(final UUID userId) {
    final UserEntity user = this.findUserById(userId);
    user.setDeletedAt(LocalDateTime.now());
    this.userRepository.save(user);
  }

  @Override
  public UserEntity findUserById(final UUID userId) {
    final UserEntity user = this.userRepository.findByIdAndDeletedAtIsNull(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found for ID: " + userId));
    return user;
  }

  @Override
  public UserEntity findUserByEmail(String email) {
    final UserEntity user = this.userRepository.findByEmailAndDeletedAtIsNull(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found for ID: " + email));
    return user;
  }

}
