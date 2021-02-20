package com.github.adrian83.mordeczki.auth.model.command;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "user")
public class User {

  @Id
  @Column(name = "email")
  private String email;

  @Column(name = "password_hash")
  private String passwordHash;
}
