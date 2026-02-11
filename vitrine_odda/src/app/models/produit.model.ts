export interface Produit {
  trackingId?: string; // UUID
  nom: string;
  description?: string;
  prix: number;
  categorie: string; // use backend enum values
  estCleOfficielle: boolean;
  dateCreation?: string;
  dateMiseAJour?: string;
}
