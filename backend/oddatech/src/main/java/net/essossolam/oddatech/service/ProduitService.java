package net.essossolam.oddatech.service;

import net.essossolam.oddatech.dto.ProduitDTO;
import net.essossolam.oddatech.entity.enumPack.Categorie;

import java.util.List;
import java.util.UUID;

public interface ProduitService {
    ProduitDTO createProduct(ProduitDTO productRecord);
    List<ProduitDTO> getAllProducts();
    ProduitDTO getProductByTrackingId(UUID trackingId);
    List<ProduitDTO> getProductsByCategory(Categorie categorie);
    void deleteProduct(UUID trackingId);
}
