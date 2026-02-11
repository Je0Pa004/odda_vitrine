package net.essossolam.oddatech.mapper;

import net.essossolam.oddatech.dto.AnnonceDTO;
import net.essossolam.oddatech.entity.Annonce;
import org.springframework.stereotype.Component;

@Component
public class AnnonceMapper {

    public AnnonceDTO toDto(Annonce entity) {
        if (entity == null)
            return null;
        return new AnnonceDTO(
                entity.getTrackingId(),
                entity.getTitre(),
                entity.getDescription(),
                entity.getTypeAnnonce(),
                entity.getTypeContrat(),
                entity.isEstActif(),
                entity.getImagePath(),
                entity.getDateCreation());
    }

    public Annonce toEntity(AnnonceDTO dto) {
        if (dto == null)
            return null;
        Annonce entity = new Annonce();
        entity.setTrackingId(dto.trackingId());
        entity.setTitre(dto.titre());
        entity.setDescription(dto.description());
        entity.setTypeAnnonce(dto.typeAnnonce());
        entity.setTypeContrat(dto.typeContrat());
        entity.setEstActif(dto.estActif());
        entity.setImagePath(dto.imagePath());
        return entity;
    }
}
