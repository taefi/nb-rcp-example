package com.example.model;

import jakarta.validation.constraints.*;
import org.jspecify.annotations.NonNull;

public record Person(
		Integer id, 
		@NotBlank String firstName, 
		@NotBlank String lastName, 
		@NotBlank @Email String email, 
		@Positive @Min(18) Integer age,
		@NotNull City hqCity,
		@NonNull Boolean partTime) {
}
