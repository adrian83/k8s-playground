package com.github.adrian83.mordeczki.migration.web.health.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Status {
	private String message;
}
