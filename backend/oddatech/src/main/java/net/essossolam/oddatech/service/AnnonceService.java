package net.essossolam.oddatech.service;

import net.essossolam.oddatech.dto.AnnonceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AnnonceService {
    AnnonceDTO create(AnnonceDTO dto, MultipartFile image);

    List<AnnonceDTO> findAll();

    AnnonceDTO findByTrackingId(UUID trackingId);

    AnnonceDTO update(UUID trackingId, AnnonceDTO dto, MultipartFile image);

    void delete(UUID trackingId);
}
