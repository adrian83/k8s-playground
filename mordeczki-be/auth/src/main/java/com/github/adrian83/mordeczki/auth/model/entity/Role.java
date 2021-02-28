package com.github.adrian83.mordeczki.auth.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Table(name = "auth_role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_role_id_seq_gen")
  @SequenceGenerator(name = "auth_role_id_seq_gen", sequenceName = "auth_role_id_seq", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;
}
