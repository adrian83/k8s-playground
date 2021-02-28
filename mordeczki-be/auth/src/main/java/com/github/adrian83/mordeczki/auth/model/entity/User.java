package com.github.adrian83.mordeczki.auth.model.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_user")
public class User {

  @Id
  @Column(name = "email")
  private String email;

  @Column(name = "password_hash")
  private String passwordHash;

  @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
  @JoinTable(
      name = "auth_user_role",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id")})
  Set<Role> roles;
}
