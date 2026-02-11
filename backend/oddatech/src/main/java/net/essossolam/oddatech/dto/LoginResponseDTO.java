package net.essossolam.oddatech.dto;

import java.util.UUID;

public record LoginResponseDTO(UUID trackingId, String email, String message) {
}
