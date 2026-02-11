package net.essossolam.oddatech.service.serviceimpl;

import jakarta.annotation.PostConstruct;
import net.essossolam.oddatech.dto.AnnonceDTO;
import net.essossolam.oddatech.entity.Annonce;
import net.essossolam.oddatech.exception.RessourceNonTrouveeException;
import net.essossolam.oddatech.mapper.AnnonceMapper;
import net.essossolam.oddatech.dao.AnnonceDao;
import net.essossolam.oddatech.service.AnnonceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AnnonceServiceImpl implements AnnonceService {

    private final AnnonceDao jobAdvertisementDao;
    private final AnnonceMapper mapper;

    @Value("${app.file.upload-dir:./uploads}")
    private String uploadDir;

    private static final Logger log = LoggerFactory.getLogger(AnnonceServiceImpl.class);

    @PostConstruct
    public void init() {
        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info("Répertoire d'upload créé: {}", path.toAbsolutePath());
            } else {
                log.info("Répertoire d'upload existant: {}", path.toAbsolutePath());
            }
        } catch (IOException e) {
            log.error("Impossible de créer le répertoire d'upload", e);
        }
    }

    public AnnonceServiceImpl(AnnonceDao jobAdvertisementDao, AnnonceMapper mapper) {
        this.jobAdvertisementDao = jobAdvertisementDao;
        this.mapper = mapper;
    }

    public AnnonceDTO create(AnnonceDTO dto, MultipartFile image) {
        log.info("Tentative de création d'annonce: {}", dto.titre());
        try {
            Annonce entity = mapper.toEntity(dto);
            if (image != null && !image.isEmpty()) {
                log.info("Image reçue: {}, taille: {} bytes", image.getOriginalFilename(), image.getSize());
                entity.setImagePath(saveFile(image));
            } else {
                log.info("Aucune image reçue pour l'annonce");
            }
            Annonce saved = jobAdvertisementDao.save(entity);
            log.info("Annonce créée avec succès, ID: {}", saved.getTrackingId());
            return mapper.toDto(saved);
        } catch (Exception e) {
            log.error("Erreur lors de la création de l'annonce: ", e);
            throw e;
        }
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
    public List<AnnonceDTO> findAll() {
        return jobAdvertisementDao.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnnonceDTO findByTrackingId(UUID trackingId) {
        return jobAdvertisementDao.findByTrackingId(trackingId)
                .map(mapper::toDto)
                .orElseThrow(() -> new RessourceNonTrouveeException(
                        "Annonce non trouvée avec le trackingId: " + trackingId));
    }

    public AnnonceDTO update(UUID trackingId, AnnonceDTO dto, MultipartFile image) {
        Annonce entity = jobAdvertisementDao.findByTrackingId(trackingId)
                .orElseThrow(() -> new RessourceNonTrouveeException(
                        "Annonce non trouvée avec le trackingId: " + trackingId));

        entity.setTitre(dto.titre());
        entity.setDescription(dto.description());
        entity.setTypeAnnonce(dto.typeAnnonce());

        if (image != null && !image.isEmpty()) {
            entity.setImagePath(saveFile(image));
        }

        Annonce updated = jobAdvertisementDao.save(entity);
        return mapper.toDto(updated);
    }

    public void delete(UUID trackingId) {
        Annonce entity = jobAdvertisementDao.findByTrackingId(trackingId)
                .orElseThrow(() -> new RessourceNonTrouveeException(
                        "Annonce non trouvée avec le trackingId: " + trackingId));

        // Supprimer le fichier image associé s'il existe
        if (entity.getImagePath() != null && !entity.getImagePath().isEmpty()) {
            try {
                Path imagePath = Paths.get(uploadDir).resolve(entity.getImagePath());
                Files.deleteIfExists(imagePath);
                log.info("Fichier image supprimé avec succès: {}", imagePath);
            } catch (IOException e) {
                log.warn("Impossible de supprimer le fichier image: {}. Erreur: {}", entity.getImagePath(),
                        e.getMessage());
            }
        }

        jobAdvertisementDao.delete(entity);
    }
}
