import { Component, EventEmitter, Output, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DemandeStageService } from '../../services/demande-stage.service';
import { DemandeStageRequest } from '../../models/demande-stage.model';
import { AnnonceService } from '../../services/annonce.service';

interface InternshipForm {
  requestType: 'internship' | 'training';
  lastName: string;
  firstName: string;
  email: string;
  phone: string;
  school: string;
  level: string;
  motivation: string;
  offreEmploiTrackingId?: string;
  cv: File | null;
  recommendationLetter: File | null;
}

@Component({
  selector: 'app-internship',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './internship.component.html',
  styleUrl: './internship.component.css'
})
export class InternshipComponent implements OnInit {
  @Output() navChange = new EventEmitter<string>();

  private demandeService = inject(DemandeStageService);
  private annonceService = inject(AnnonceService);

  formData: InternshipForm = {
    requestType: 'internship',
    lastName: '',
    firstName: '',
    email: '',
    phone: '',
    school: '',
    level: '',
    motivation: '',
    cv: null,
    recommendationLetter: null,
    offreEmploiTrackingId: ''
  };

  announcements: any[] = [];
  levels = [
    'Licence 1',
    'Licence 2',
    'Licence 3',
    'Master 1',
    'Master 2',
    'Doctorat',
    'BTS'
  ];

  isSubmitting = false;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  ngOnInit() {
    this.loadAnnouncements();
  }

  loadAnnouncements() {
    this.annonceService.getAll().subscribe({
      next: (data: any[]) => {
        this.announcements = data;
      },
      error: (err) => console.error('Erreur chargement annonces:', err)
    });
  }

  onFileChange(event: any, field: 'cv' | 'recommendationLetter') {
    const file = event.target.files[0];
    if (file) {
      this.formData[field] = file;
    }
  }

  onSubmit() {
    // Validation minimale côté front : uniquement vérifier la présence du CV.
    // Les autres champs seront validés côté backend et renverront un message explicite si manquants.
    if (!this.formData.cv) {
      this.errorMessage = 'Veuillez joindre votre CV (format PDF).';
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = null;
    this.successMessage = null;

    const request: DemandeStageRequest = {
      nom: this.formData.lastName,
      prenom: this.formData.firstName,
      email: this.formData.email,
      telephone: this.formData.phone,
      ecole: this.formData.school,
      niveau: this.formData.level,
      motivation: this.formData.motivation,
      offreEmploiTrackingId: this.formData.offreEmploiTrackingId || '550e8400-e29b-41d4-a716-446655440000'
    };

    this.demandeService.apply(request, this.formData.cv, this.formData.recommendationLetter || undefined).subscribe({
      next: (resp) => {
        this.isSubmitting = false;
        this.successMessage = 'Félicitations ! Votre dossier de stage a été transmis avec succès à l\'équipe RH d\'Odda Technology.';
        this.resetForm();
        setTimeout(() => this.successMessage = null, 5000);
      },
      error: (err) => {
        this.isSubmitting = false;
        // Si le backend renvoie des erreurs de validation, on les affiche
        if (err.error && err.error.errors) {
          const errors = err.error.errors;
          const messages = Object.values(errors as any).join(' / ');
          this.errorMessage = `Erreur de validation : ${messages}`;
        } else if (err.error && err.error.message) {
          this.errorMessage = err.error.message;
        } else {
          this.errorMessage = 'Une erreur s\'est produite lors de l\'envoi de votre candidature. Veuillez réessayer.';
        }
        console.error('Erreur envoi candidature:', err);
      }
    });
  }

  resetForm() {
    this.formData = {
      requestType: 'internship',
      lastName: '',
      firstName: '',
      email: '',
      phone: '',
      school: '',
      level: '',
      motivation: '',
      cv: null,
      recommendationLetter: null,
      offreEmploiTrackingId: ''
    };
  }

  onNavClick(id: string) {
    this.navChange.emit(id);
  }
}
