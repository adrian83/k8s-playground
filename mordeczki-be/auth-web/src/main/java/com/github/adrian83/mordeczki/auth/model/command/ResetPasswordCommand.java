package com.github.adrian83.mordeczki.auth.model.command;

import lombok.Data;

@Data
public class ResetPasswordCommand {
	private String email;
	private String oldPassword;
	private String newPassword;
}
