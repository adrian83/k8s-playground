package com.github.adrian83.mordeczki.auth.model.command;

import lombok.Data;

@Data
public class RegisterCommand {
	private String email;
	private String password;
}
