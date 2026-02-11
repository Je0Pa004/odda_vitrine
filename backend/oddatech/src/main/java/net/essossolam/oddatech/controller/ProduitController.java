package net.essossolam.oddatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.essossolam.oddatech.dto.ProduitDTO;
import net.essossolam.oddatech.entity.enumPack.Categorie;
import net.essossolam.oddatech.service.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/produits")
@Tag(name = "Gestion des Produits", description = "Endpoints pour la gestion des produits")
public class ProduitController {

    private final ProduitService productService;

    public ProduitController(ProduitService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau produit")
    public ResponseEntity<ProduitDTO> createProduct(@Valid @RequestBody ProduitDTO productRecord) {
        return new ResponseEntity<>(productService.createProduct(productRecord), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les produits")
    public ResponseEntity<List<ProduitDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{trackingId}")
    @Operation(summary = "Récupérer un produit par son trackingId")
    public ResponseEntity<ProduitDTO> getProductByTrackingId(@PathVariable UUID trackingId) {
        return ResponseEntity.ok(productService.getProductByTrackingId(trackingId));
    }

    @GetMapping("/categorie/{categorie}")
    @Operation(summary = "Récupérer les produits par catégorie")
    public ResponseEntity<List<ProduitDTO>> getProductsByCategory(@PathVariable Categorie categorie) {
        return ResponseEntity.ok(productService.getProductsByCategory(categorie));
    }

    @DeleteMapping("/{trackingId}")
    @Operation(summary = "Supprimer un produit")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID trackingId) {
        productService.deleteProduct(trackingId);
        return ResponseEntity.noContent().build();
    }
}
