package net.essossolam.oddatech.dao;

import net.essossolam.oddatech.entity.DemandeStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DemandeStageDao extends JpaRepository<DemandeStage, Long> {
    Optional<DemandeStage> findByTrackingId(UUID trackingId);
}
