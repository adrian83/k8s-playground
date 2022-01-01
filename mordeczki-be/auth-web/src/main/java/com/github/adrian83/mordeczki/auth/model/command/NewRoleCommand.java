package com.github.adrian83.mordeczki.auth.model.command;

import javax.validation.constraints.NotEmpty;

public record NewRoleCommand(@NotEmpty String name) {}
