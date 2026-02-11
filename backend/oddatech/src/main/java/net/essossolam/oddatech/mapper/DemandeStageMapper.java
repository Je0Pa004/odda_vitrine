package net.essossolam.oddatech.mapper;

import net.essossolam.oddatech.dto.DemandeStageRequestDTO;
import net.essossolam.oddatech.dto.DemandeStageResponseDTO;
import net.essossolam.oddatech.entity.DemandeStage;
import org.springframework.stereotype.Component;

@Component
public class DemandeStageMapper {

    public DemandeStageResponseDTO toDto(DemandeStage entity) {
        if (entity == null) return null;
        return new DemandeStageResponseDTO(
                entity.getTrackingId(),
                entity.getNom(),
                entity.getPrenom(),
                entity.getEmail(),
                entity.getTelephone(),
                entity.getEcole(),
                entity.getNiveau(),
                entity.getCvCheminFichier(),
                entity.getLettreRecommandationCheminFichier(),
                entity.getMotivation(),
                entity.getStatut(),
                entity.getOffreEmploi() != null ? entity.getOffreEmploi().getTrackingId() : null,
                entity.getDateCreation()
        );
    }

    public DemandeStage toEntity(DemandeStageRequestDTO dto) {
        if (dto == null) return null;
        DemandeStage entity = new DemandeStage();
        entity.setNom(dto.nom());
        entity.setPrenom(dto.prenom());
        entity.setEmail(dto.email());
        entity.setTelephone(dto.telephone());
        entity.setEcole(dto.ecole());
        entity.setNiveau(dto.niveau());
        entity.setMotivation(dto.motivation());
        // Les chemins de fichiers seront gérés dans le service après l'upload
        return entity;
    }
}
