package net.essossolam.oddatech.dao;

import net.essossolam.oddatech.entity.enumPack.Categorie;
import net.essossolam.oddatech.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProduitDao extends JpaRepository<Produit, Long> {
    Optional<Produit> findByTrackingId(UUID trackingId);
    List<Produit> findByCategorie(Categorie categorie);
}
