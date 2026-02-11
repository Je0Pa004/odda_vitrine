export interface DemandeStageRequest {
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  ecole: string;
  niveau: string;
  motivation?: string;
  offreEmploiTrackingId: string; // UUID
}

export interface DemandeStageResponse {
  trackingId?: string;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  ecole: string;
  niveau: string;
  cvCheminFichier?: string;
  lettreRecommandationCheminFichier?: string;
  motivation?: string;
  statut?: string;
  offreEmploiTrackingId?: string;
  dateCreation?: string;
}
