package net.essossolam.oddatech.service.serviceimpl;

import net.essossolam.oddatech.dto.ProduitDTO;
import net.essossolam.oddatech.entity.enumPack.Categorie;
import net.essossolam.oddatech.entity.Produit;
import net.essossolam.oddatech.exception.RessourceNonTrouveeException;
import net.essossolam.oddatech.mapper.ProduitMapper;
import net.essossolam.oddatech.dao.ProduitDao;
import net.essossolam.oddatech.service.ProduitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitDao productDao;
    private final ProduitMapper productMapper;

    public ProduitServiceImpl(ProduitDao productDao, ProduitMapper productMapper) {
        this.productDao = productDao;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProduitDTO createProduct(ProduitDTO productRecord) {
        Produit product = productMapper.toEntity(productRecord);
        Produit savedProduct = productDao.save(product);
        return productMapper.toRecord(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProduitDTO> getAllProducts() {
        return productDao.findAll().stream()
                .map(productMapper::toRecord)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProduitDTO getProductByTrackingId(UUID trackingId) {
        return productDao.findByTrackingId(trackingId)
                .map(productMapper::toRecord)
                .orElseThrow(
                        () -> new RessourceNonTrouveeException("Produit non trouvé avec le trackingId: " + trackingId));
    }

    @Transactional(readOnly = true)
    public List<ProduitDTO> getProductsByCategory(Categorie categorie) {
        return productDao.findByCategorie(categorie).stream()
                .map(productMapper::toRecord)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteProduct(UUID trackingId) {
        Produit product = productDao.findByTrackingId(trackingId)
                .orElseThrow(
                        () -> new RessourceNonTrouveeException("Produit non trouvé avec le trackingId: " + trackingId));
        productDao.delete(product);
    }
}
