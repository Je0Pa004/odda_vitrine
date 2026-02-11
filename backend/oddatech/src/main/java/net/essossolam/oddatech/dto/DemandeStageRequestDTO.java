package net.essossolam.oddatech.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "DTO de requête pour postuler à un stage/emploi")
public record DemandeStageRequestDTO(
        @NotBlank(message = "Le nom est obligatoire")
        @Schema(description = "Nom du candidat", example = "Salami")
        String nom,
        
        @NotBlank(message = "Le prénom est obligatoire")
        @Schema(description = "Prénom du candidat", example = "Essossolam")
        String prenom,

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "L'email doit être valide")
        @Schema(description = "Adresse email du candidat", example = "essossolam@example.com")
        String email,
        
        @NotBlank(message = "Le téléphone est obligatoire")
        @Schema(description = "Numéro de téléphone du candidat", example = "+228 90 00 00 00")
        String telephone,

        @NotBlank(message = "L'école est obligatoire")
        @Schema(description = "École ou Université actuelle", example = "Université de Lomé")
        String ecole,
        
        @NotBlank(message = "Le niveau d'études est obligatoire")
        @Schema(description = "Niveau d'études actuel", example = "Master 2")
        String niveau,

        @Schema(description = "Chemin du fichier CV (géré par le serveur)", hidden = true)
        String cvCheminFichier,
        
        @Schema(description = "Chemin de la lettre de recommandation (géré par le serveur)", hidden = true)
        String lettreRecommandationCheminFichier,

        @Schema(description = "Lettre de motivation ou message", example = "Je suis très motivé par votre entreprise...")
        String motivation,

        @NotNull(message = "L'ID de l'offre d'emploi est obligatoire")
        @Schema(description = "ID de suivi de l'offre d'emploi à laquelle on postule")
        UUID offreEmploiTrackingId
) {
}
