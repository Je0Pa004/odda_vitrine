package net.essossolam.oddatech.service.serviceimpl;

import net.essossolam.oddatech.dto.DemandeStageRequestDTO;
import net.essossolam.oddatech.dto.DemandeStageResponseDTO;
import net.essossolam.oddatech.entity.enumPack.StatutCandidature;
import net.essossolam.oddatech.entity.DemandeStage;
import net.essossolam.oddatech.entity.Annonce;
import net.essossolam.oddatech.exception.RessourceNonTrouveeException;
import net.essossolam.oddatech.mapper.DemandeStageMapper;
import net.essossolam.oddatech.dao.DemandeStageDao;
import net.essossolam.oddatech.dao.AnnonceDao;
import net.essossolam.oddatech.service.DemandeStageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class DemandeStageServiceImpl implements DemandeStageService {

    private final DemandeStageDao demandeStageDao;
    private final AnnonceDao annonceEmploiDao;
    private final DemandeStageMapper mapper;

    @Value("${app.file.upload-dir:./uploads}")
    private String uploadDir;

    public DemandeStageServiceImpl(DemandeStageDao demandeStageDao,
            AnnonceDao annonceEmploiDao,
            DemandeStageMapper mapper) {
        this.demandeStageDao = demandeStageDao;
        this.annonceEmploiDao = annonceEmploiDao;
        this.mapper = mapper;
    }

    public DemandeStageResponseDTO postuler(DemandeStageRequestDTO request, MultipartFile cvFichier,
            MultipartFile lettreRecommandationFichier) {
        Annonce offreEmploi = annonceEmploiDao.findByTrackingId(request.offreEmploiTrackingId())
                .orElseThrow(() -> new RessourceNonTrouveeException(
                        "Annonce non trouvée avec le trackingId: " + request.offreEmploiTrackingId()));

        DemandeStage entity = mapper.toEntity(request);
        entity.setOffreEmploi(offreEmploi);
        entity.setStatut(StatutCandidature.EN_ATTENTE);

        if (cvFichier != null && !cvFichier.isEmpty()) {
            entity.setCvCheminFichier(saveFile(cvFichier));
        }

        if (lettreRecommandationFichier != null && !lettreRecommandationFichier.isEmpty()) {
            entity.setLettreRecommandationCheminFichier(saveFile(lettreRecommandationFichier));
        }

        DemandeStage saved = demandeStageDao.save(entity);
        return mapper.toDto(saved);
    }

    private String saveFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Files.copy(file.getInputStream(), path.resolve(fileName));
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier", e);
        }
    }

    @Transactional(readOnly = true)
    public DemandeStageResponseDTO findByTrackingId(UUID trackingId) {
        return demandeStageDao.findByTrackingId(trackingId)
                .map(mapper::toDto)
                .orElseThrow(() -> new RessourceNonTrouveeException(
                        "Candidature non trouvée avec le trackingId: " + trackingId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DemandeStageResponseDTO> findAll() {
        return demandeStageDao.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DemandeStageResponseDTO updateStatus(UUID trackingId, String status) {
        DemandeStage entity = demandeStageDao.findByTrackingId(trackingId)
                .orElseThrow(() -> new RessourceNonTrouveeException(
                        "Candidature non trouvée avec le trackingId: " + trackingId));

        if ("VALIDE".equalsIgnoreCase(status) || "ACCEPTEE".equalsIgnoreCase(status)
                || "Approved".equalsIgnoreCase(status)) {
            entity.setStatut(StatutCandidature.ACCEPTEE);
        } else if ("REFUSE".equalsIgnoreCase(status) || "REJETEE".equalsIgnoreCase(status)
                || "Rejected".equalsIgnoreCase(status)) {
            entity.setStatut(StatutCandidature.REJETEE);
        } else {
            entity.setStatut(StatutCandidature.EN_ATTENTE);
        }

        DemandeStage updated = demandeStageDao.save(entity);
        return mapper.toDto(updated);
    }

    @Override
    public void delete(UUID trackingId) {
        DemandeStage entity = demandeStageDao.findByTrackingId(trackingId)
                .orElseThrow(() -> new RessourceNonTrouveeException(
                        "Candidature non trouvée avec le trackingId: " + trackingId));
        demandeStageDao.delete(entity);
    }
}
