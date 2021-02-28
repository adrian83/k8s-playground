package com.github.adrian83.mordeczki.auth.model.command;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewRoleCommand {
	@NotEmpty
	private String name;
}
