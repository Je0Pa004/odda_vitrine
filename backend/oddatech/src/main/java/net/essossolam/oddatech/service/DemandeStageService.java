package net.essossolam.oddatech.service;

import net.essossolam.oddatech.dto.DemandeStageRequestDTO;
import net.essossolam.oddatech.dto.DemandeStageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface DemandeStageService {
    DemandeStageResponseDTO postuler(DemandeStageRequestDTO request, MultipartFile cvFichier,
            MultipartFile lettreRecommandationFichier);

    DemandeStageResponseDTO findByTrackingId(UUID trackingId);

    List<DemandeStageResponseDTO> findAll();

    DemandeStageResponseDTO updateStatus(UUID trackingId, String status);

    void delete(UUID trackingId);
}
