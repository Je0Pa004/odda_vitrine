package net.essossolam.oddatech.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.essossolam.oddatech.entity.enumPack.StatutCandidature;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "demandes_stage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class DemandeStage extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;


    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telephone;


    @Column(nullable = false)
    private String ecole;

    @Column(nullable = false)
    private String niveau;

    @Column(nullable = false)
    private String cvCheminFichier;

    private String lettreRecommandationCheminFichier;

    @Column(columnDefinition = "TEXT")
    private String motivation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatutCandidature statut = StatutCandidature.EN_ATTENTE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_emploi_id", nullable = false)
    private Annonce offreEmploi;

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEcole() {
        return this.ecole;
    }

    public void setEcole(String ecole) {
        this.ecole = ecole;
    }

    public String getNiveau() {
        return this.niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getCvCheminFichier() {
        return this.cvCheminFichier;
    }

    public void setCvCheminFichier(String cvCheminFichier) {
        this.cvCheminFichier = cvCheminFichier;
    }

    public String getLettreRecommandationCheminFichier() {
        return this.lettreRecommandationCheminFichier;
    }

    public void setLettreRecommandationCheminFichier(String lettreRecommandationCheminFichier) {
        this.lettreRecommandationCheminFichier = lettreRecommandationCheminFichier;
    }

    public String getMotivation() {
        return this.motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public StatutCandidature getStatut() {
        return this.statut;
    }

    public void setStatut(StatutCandidature statut) {
        this.statut = statut;
    }

    public Annonce getOffreEmploi() {
        return this.offreEmploi;
    }

    public void setOffreEmploi(Annonce offreEmploi) {
        this.offreEmploi = offreEmploi;
    }
}
