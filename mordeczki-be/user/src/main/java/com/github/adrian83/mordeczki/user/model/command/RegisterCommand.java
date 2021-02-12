package com.github.adrian83.mordeczki.user.model.command;

import lombok.Data;

@Data
public class RegisterCommand {
	
	private String email;
	private String password;
	
}
