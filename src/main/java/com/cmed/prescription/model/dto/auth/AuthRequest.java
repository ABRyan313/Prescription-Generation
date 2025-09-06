package com.cmed.prescription.model.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(@NotBlank(message = "Username is required") String username,
                          @NotBlank(message = "Password is required") String password) {
}
