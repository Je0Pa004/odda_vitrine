package net.essossolam.oddatech.dao;

import net.essossolam.oddatech.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AnnonceDao extends JpaRepository<Annonce, Long> {
    Optional<Annonce> findByTrackingId(UUID trackingId);
}
