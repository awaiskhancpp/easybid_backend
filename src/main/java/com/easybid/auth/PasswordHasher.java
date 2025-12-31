package com.easybid.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordHasher {

  private final PasswordEncoder passwordEncoder;

  public PasswordHasher(final PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public String hash(final String plainPassword) {
    return passwordEncoder.encode(plainPassword);
  }

  public boolean matches(final String plainPassword, final String hashedPassword) {
    return passwordEncoder.matches(plainPassword, hashedPassword);
  }
}
