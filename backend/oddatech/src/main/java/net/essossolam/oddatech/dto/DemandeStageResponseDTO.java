package net.essossolam.oddatech.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import net.essossolam.oddatech.entity.enumPack.StatutCandidature;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO de réponse pour une candidature")
public record DemandeStageResponseDTO(
        @Schema(description = "Identifiant unique de suivi")
        UUID trackingId,
        @Schema(description = "Nom du candidat")
        String nom,
        @Schema(description = "Prénom du candidat")
        String prenom,
        @Schema(description = "Email du candidat")
        String email,
        @Schema(description = "Téléphone du candidat")
        String telephone,
        @Schema(description = "École du candidat")
        String ecole,
        @Schema(description = "Niveau du candidat")
        String niveau,
        @Schema(description = "Nom du fichier CV enregistré")
        String cvCheminFichier,
        @Schema(description = "Nom du fichier lettre de recommandation enregistré")
        String lettreRecommandationCheminFichier,
        @Schema(description = "Motivation du candidat")
        String motivation,
        @Schema(description = "Statut actuel de la candidature")
        StatutCandidature statut,
        @Schema(description = "ID de l'offre d'emploi associée")
        UUID offreEmploiTrackingId,
        @Schema(description = "Date de soumission")
        LocalDateTime dateCreation
) {
}
