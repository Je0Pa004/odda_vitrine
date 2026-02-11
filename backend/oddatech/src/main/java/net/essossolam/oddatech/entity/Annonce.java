package net.essossolam.oddatech.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.essossolam.oddatech.entity.enumPack.TypeAnnonce;
import net.essossolam.oddatech.entity.enumPack.TypeContrat;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "annonces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Annonce extends BaseEntity {

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeAnnonce typeAnnonce;

    @Enumerated(EnumType.STRING)
    private TypeContrat typeContrat;

    @Builder.Default
    private boolean estActif = true;

    private String imagePath;

    @OneToMany(mappedBy = "offreEmploi", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DemandeStage> candidatures = new ArrayList<>();

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeAnnonce getTypeAnnonce() {
        return this.typeAnnonce;
    }

    public void setTypeAnnonce(TypeAnnonce typeAnnonce) {
        this.typeAnnonce = typeAnnonce;
    }

    public TypeContrat getTypeContrat() {
        return this.typeContrat;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public boolean isEstActif() {
        return this.estActif;
    }

    public void setEstActif(boolean estActif) {
        this.estActif = estActif;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
