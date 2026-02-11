package net.essossolam.oddatech.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.essossolam.oddatech.entity.enumPack.Categorie;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO représentant un produit")
public record ProduitDTO(
        @Schema(description = "Identifiant unique de suivi")
        UUID trackingId,
        
        @NotBlank(message = "Le nom du produit est obligatoire")
        @Schema(description = "Nom du produit", example = "Clé Windows 11 Pro")
        String nom,
        
        @Schema(description = "Description du produit")
        String description,
        
        @NotNull(message = "Le prix est obligatoire")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
        @Schema(description = "Prix du produit", example = "25.99")
        BigDecimal prix,
        
        @NotNull(message = "La catégorie est obligatoire")
        @Schema(description = "Catégorie du produit")
        Categorie categorie,
        
        @Schema(description = "Indique s'il s'agit d'une clé officielle")
        boolean estCleOfficielle,
        
        @Schema(description = "Date de création")
        LocalDateTime dateCreation,
        
        @Schema(description = "Date de dernière mise à jour")
        LocalDateTime dateMiseAJour
) {
}
