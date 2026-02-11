package net.essossolam.oddatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.essossolam.oddatech.dto.DemandeStageRequestDTO;
import net.essossolam.oddatech.dto.DemandeStageResponseDTO;
import net.essossolam.oddatech.dto.AnnonceDTO;
import net.essossolam.oddatech.service.DemandeStageService;
import net.essossolam.oddatech.service.AnnonceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/hr")
@Tag(name = "Gestion RH", description = "API pour les annonces et les candidatures")
public class RHController {

    private static final Logger log = LoggerFactory.getLogger(RHController.class);

    private final AnnonceService jobService;
    private final DemandeStageService applicationService;

    public RHController(AnnonceService jobService, DemandeStageService applicationService) {
        this.jobService = jobService;
        this.applicationService = applicationService;
    }

    @PostMapping(value = "/annonces", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Créer une nouvelle annonce")
    public ResponseEntity<AnnonceDTO> createJob(
            @RequestPart("annonce") @Valid AnnonceDTO jobAdvertRecord,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        log.info("Requête POST /annonces reçue pour: {}", jobAdvertRecord.titre());
        return new ResponseEntity<>(jobService.create(jobAdvertRecord, image), HttpStatus.CREATED);
    }

    @GetMapping("/annonces")
    @Operation(summary = "Récupérer toutes les annonces")
    public ResponseEntity<List<AnnonceDTO>> getAllJobs() {
        return ResponseEntity.ok(jobService.findAll());
    }

    @GetMapping("/annonces/{trackingId}")
    @Operation(summary = "Récupérer une annonce par son trackingId")
    public ResponseEntity<AnnonceDTO> getJobByTrackingId(@PathVariable UUID trackingId) {
        return ResponseEntity.ok(jobService.findByTrackingId(trackingId));
    }

    @PutMapping(value = "/annonces/{trackingId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Modifier une annonce existante")
    public ResponseEntity<AnnonceDTO> updateJob(
            @PathVariable UUID trackingId,
            @RequestPart("annonce") @Valid AnnonceDTO jobAdvertRecord,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ResponseEntity.ok(jobService.update(trackingId, jobAdvertRecord, image));
    }

    @DeleteMapping("/annonces/{trackingId}")
    @Operation(summary = "Supprimer une annonce")
    public ResponseEntity<Void> deleteJob(@PathVariable UUID trackingId) {
        jobService.delete(trackingId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/applications", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Postuler à une offre d'emploi")
    public ResponseEntity<DemandeStageResponseDTO> apply(
            @RequestPart("application") @Valid DemandeStageRequestDTO applicationRequest,
            @RequestPart("cv") MultipartFile cvFichier,
            @RequestPart(value = "lettreRecommandation", required = false) MultipartFile lettreRecommandationFichier) {
        return new ResponseEntity<>(
                applicationService.postuler(applicationRequest, cvFichier, lettreRecommandationFichier),
                HttpStatus.CREATED);
    }

    @GetMapping("/applications")
    @Operation(summary = "Récupérer toutes les candidatures")
    public ResponseEntity<List<DemandeStageResponseDTO>> getAllApplications() {
        return ResponseEntity.ok(applicationService.findAll());
    }

    @GetMapping("/applications/{trackingId}")
    @Operation(summary = "Récupérer une candidature par son trackingId")
    public ResponseEntity<DemandeStageResponseDTO> getApplicationByTrackingId(@PathVariable UUID trackingId) {
        return ResponseEntity.ok(applicationService.findByTrackingId(trackingId));
    }

    @PutMapping("/applications/{trackingId}/status")
    @Operation(summary = "Mettre à jour le statut d'une candidature")
    public ResponseEntity<DemandeStageResponseDTO> updateApplicationStatus(
            @PathVariable UUID trackingId,
            @RequestParam String status) {
        return ResponseEntity.ok(applicationService.updateStatus(trackingId, status));
    }

    @DeleteMapping("/applications/{trackingId}")
    @Operation(summary = "Supprimer une candidature")
    public ResponseEntity<Void> deleteApplication(@PathVariable UUID trackingId) {
        applicationService.delete(trackingId);
        return ResponseEntity.noContent().build();
    }
}
