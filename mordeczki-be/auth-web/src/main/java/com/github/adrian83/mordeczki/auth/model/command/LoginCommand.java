package com.github.adrian83.mordeczki.auth.model.command;

import lombok.Data;

@Data
public class LoginCommand {
	private String email;
	private String password;
}
