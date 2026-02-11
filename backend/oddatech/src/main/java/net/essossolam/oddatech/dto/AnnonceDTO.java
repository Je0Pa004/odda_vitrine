package net.essossolam.oddatech.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import net.essossolam.oddatech.entity.enumPack.TypeAnnonce;
import net.essossolam.oddatech.entity.enumPack.TypeContrat;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Schema(description = "DTO pour une annonce (emploi, formation, etc.)")
public record AnnonceDTO(
                @Schema(description = "Identifiant unique de suivi", example = "123e4567-e89b-12d3-a456-426614174000") UUID trackingId,

                @NotBlank(message = "Le titre de l'annonce est obligatoire") @Schema(description = "Titre de l'annonce", example = "Développeur Fullstack Java/Angular") String titre,

                @Schema(description = "Description détaillée de l'offre") String description,

                @NotNull(message = "Le type d'annonce est obligatoire") @Schema(description = "Type d'annonce") TypeAnnonce typeAnnonce,

                @Schema(description = "Type de contrat (optionnel)") TypeContrat typeContrat,

                @Schema(description = "Indique si l'annonce est toujours active") boolean estActif,

                @Schema(description = "Chemin de l'image de l'annonce") String imagePath,

                @Schema(description = "Date de création de l'annonce") LocalDateTime dateCreation) {
}
