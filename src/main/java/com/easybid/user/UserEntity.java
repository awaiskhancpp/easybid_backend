package com.easybid.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.easybid.bid.BidEntity;
import com.easybid.common.BaseEntity;
import com.easybid.enums.UsersRole;
import com.easybid.item.ItemEntity;
import com.easybid.notification.NotificationEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema()
@Table(name = "users")
public class UserEntity extends BaseEntity implements UserDetails {

  @Column(nullable = false, length = 255)
  private String name;

  @Column(nullable = false, length = 255, unique = true)
  @Email
  private String email;

  @Column(nullable = false, length = 255)
  private String hashPassword;

  @Column(nullable = true, length = 255)
  private String address;

  @Column(nullable = true, length = 255)
  private String phoneNumber;

  @Column(nullable = true, length = 255)
  private String bio;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UsersRole role;

  @OneToMany(mappedBy = "user")
  @JsonManagedReference("user-item")
  private List<ItemEntity> items;

  @OneToMany(mappedBy = "user")
  @JsonManagedReference("user-bid")
  private List<BidEntity> bids;

  @OneToMany(mappedBy = "user")
  @JsonManagedReference("user-notifications")
  private List<NotificationEntity> notifications;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return this.getHashPassword();
  }

  @Override
  public String getUsername() {
    return this.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
