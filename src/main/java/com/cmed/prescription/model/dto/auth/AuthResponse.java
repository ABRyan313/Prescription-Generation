package com.cmed.prescription.model.dto.auth;

public record AuthResponse(String token, String username, Long expiresAt) {
}
