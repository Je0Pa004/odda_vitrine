import { Component, EventEmitter, Output, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnnonceService } from '../../services/annonce.service';
import { environment } from '../../../environments/environment';

interface Announcement {
  id?: string;
  title: string;
  date: string;
  category: 'info' | 'event' | 'update' | 'promo';
  description: string;
  imageUrl?: string;
  link?: string;
}

@Component({
  selector: 'app-announcements',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './announcements.component.html',
  styleUrl: './announcements.component.css'
})
export class AnnouncementsComponent implements OnInit {
  @Output() navChange = new EventEmitter<string>();

  private annonceService = inject(AnnonceService);
  announcements: Announcement[] = [];
  isLoading = true;
  errorMessage: string | null = null;

  // Detail View State
  selectedAnnouncement: Announcement | null = null;

  ngOnInit() {
    this.loadAnnouncements();
  }

  loadAnnouncements() {
    this.isLoading = true;
    this.errorMessage = null;
    this.annonceService.getAll().subscribe({
      next: (data: any[]) => {
        const fileBase = environment.apiBase.replace('/v1', '') + '/uploads/';
        // Map backend data (AnnonceDTO) vers notre interface d'affichage
        this.announcements = data.map(item => ({
          id: item.trackingId,
          title: item.titre || item.title || '',
          date: item.dateCreation ? this.formatDate(item.dateCreation) : new Date().toLocaleDateString('fr-FR'),
          category: this.mapTypeAnnonceToCategory(item.typeAnnonce),
          description: item.description || '',
          imageUrl: item.imagePath ? fileBase + item.imagePath : undefined,
          link: '#'
        }));
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur chargement annonces:', err);
        this.errorMessage = 'Impossible de charger les annonces.';
        this.isLoading = false;
      }
    });
  }

  private mapTypeAnnonceToCategory(typeAnnonce: string | null | undefined): 'info' | 'event' | 'update' | 'promo' {
    if (!typeAnnonce) return 'info';
    switch (typeAnnonce) {
      case 'EMPLOI':
        return 'info';
      case 'EVENEMENT':
        return 'event';
      case 'FORMATION':
        return 'update';
      default:
        return 'promo';
    }
  }

  private formatDate(dateString: string): string {
    try {
      const date = new Date(dateString);
      return date.toLocaleDateString('fr-FR', { year: 'numeric', month: 'long', day: 'numeric' });
    } catch {
      return dateString;
    }
  }

  onNavClick(id: string) {
    this.navChange.emit(id);
  }

  getCategoryColor(category: string): string {
    const colors: { [key: string]: string } = {
      'info': 'text-blue-500',
      'event': 'text-purple-500',
      'update': 'text-green-500',
      'promo': 'text-[#ff7a1a]'
    };
    return colors[category] || 'text-slate-500';
  }

  getCategoryLabel(category: string): string {
    const labels: { [key: string]: string } = {
      'info': 'Information',
      'event': 'Événement',
      'update': 'Mise à jour',
      'promo': 'Promotion'
    };
    return labels[category] || category;
  }

  openDetails(announcement: Announcement) {
    this.selectedAnnouncement = announcement;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  closeDetails() {
    this.selectedAnnouncement = null;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  getSuggestedAnnouncements(): Announcement[] {
    if (!this.selectedAnnouncement) return [];
    return this.announcements
      .filter(a => a.id !== this.selectedAnnouncement?.id)
      .slice(0, 3);
  }
}
