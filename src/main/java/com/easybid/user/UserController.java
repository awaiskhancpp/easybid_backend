package com.easybid.user;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easybid.user.dto.UpdateUserDTO;
import com.easybid.user.dto.UserResponseDTO;

@RestController
@RequestMapping("api/v1")
public class UserController {
  private final UserService userService;

  public UserController(final UserService userService) {
    this.userService = userService;
  }

  // @PostMapping("/users")
  // public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Validated
  // final CreateUserDTO createUserDTO) {
  // UserResponseDTO userResponseDTO = this.userService.createUser(createUserDTO);
  // return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
  // }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
    List<UserResponseDTO> userResponseDTO = this.userService.getAllUsers();
    return ResponseEntity.ok(userResponseDTO);
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable final UUID userId) {
    UserResponseDTO userResponseDto = this.userService.getUserById(userId);
    return ResponseEntity.ok(userResponseDto);
  }

  @PutMapping("/users/{userId}")
  public ResponseEntity<UserResponseDTO> updateUser(
      @PathVariable final UUID userId,
      @RequestBody @Validated final UpdateUserDTO updateUserDTO) {
    UserResponseDTO userResponseDto = this.userService.updateUser(userId, updateUserDTO);
    return ResponseEntity.ok(userResponseDto);
  }

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<String> deleteUser(@PathVariable final UUID userId) {
    this.userService.deleteUser(userId);
    return ResponseEntity.ok("User Delete Successfuly");
  }

}
