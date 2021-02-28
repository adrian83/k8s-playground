package com.github.adrian83.mordeczki.auth.model.command;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRequest {
  private String email;
  private List<String> roles;
}
