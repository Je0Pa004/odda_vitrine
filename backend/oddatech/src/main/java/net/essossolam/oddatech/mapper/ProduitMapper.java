package net.essossolam.oddatech.mapper;

import net.essossolam.oddatech.dto.ProduitDTO;
import net.essossolam.oddatech.entity.Produit;
import org.springframework.stereotype.Component;

@Component
public class   ProduitMapper {

    public ProduitDTO toRecord(Produit product) {
        if (product == null) return null;
        return new ProduitDTO(
                product.getTrackingId(),
                product.getNom(),
                product.getDescription(),
                product.getPrix(),
                product.getCategorie(),
                product.isEstCleOfficielle(),
                product.getDateCreation(),
                product.getDateMiseAJour()
        );
    }

    public Produit toEntity(ProduitDTO record) {
        if (record == null) return null;
        Produit product = new Produit();
        product.setNom(record.nom());
        product.setDescription(record.description());
        product.setPrix(record.prix());
        product.setCategorie(record.categorie());
        product.setEstCleOfficielle(record.estCleOfficielle());
        return product;
    }
}
