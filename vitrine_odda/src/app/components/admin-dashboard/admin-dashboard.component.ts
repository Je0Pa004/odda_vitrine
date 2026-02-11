import { Component, EventEmitter, Output, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AnnonceService } from '../../services/annonce.service';
import { DemandeStageService } from '../../services/demande-stage.service';
import { environment } from '../../../environments/environment';

interface Announcement {
  id?: string;
  title: string;
  category: 'info' | 'event' | 'update' | 'promo';
  description: string;
  imageUrl?: string;
  date: string;
}

interface Request {
  id?: string;
  type: 'Internship' | 'Training';
  name: string;
  firstName: string;
  email: string;
  telephone?: string;
  university?: string;
  level?: string;
  cv?: string;
  letter?: string;
  motivation?: string;
  status: 'Pending' | 'Approved' | 'Rejected';
  date: string;
}

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  @Output() navChange = new EventEmitter<string>();

  private annonceService = inject(AnnonceService);
  private demandeService = inject(DemandeStageService);

  activeTab: 'overview' | 'announcements' | 'requests' = 'overview';

  // KPIs
  kpis = {
    totalAnnouncements: 0,
    totalRequests: 0,
    pendingRequests: 0,
    approvedRequests: 0
  };

  // Announcements Data
  announcements: Announcement[] = [];

  newAnnouncement: Partial<Announcement> = {
    title: '',
    category: 'info',
    description: ''
  };

  // Requests Data
  requests: Request[] = [];

  // Modal states
  showDetailsModal = false;
  showEditModal = false;
  showDeleteModal = false;

  selectedItem: any = null;
  editItem: any = null;
  itemToDelete: any = null;
  modalType: 'announcement' | 'request' = 'announcement';

  selectedFile: File | null = null;

  ngOnInit() {
    this.loadAnnouncements();
    this.loadRequests();
  }

  loadAnnouncements() {
    this.annonceService.getAll().subscribe({
      next: (data: any[]) => {
        const fileBase = environment.apiBase.replace('/v1', '') + '/uploads/';
        this.announcements = data.map(item => ({
          id: item.trackingId,
          title: item.titre || item.title || '',
          category: this.mapTypeAnnonceToCategory(item.typeAnnonce),
          description: item.description || '',
          imageUrl: item.imagePath ? fileBase + item.imagePath : undefined,
          date: item.dateCreation ? this.formatDate(item.dateCreation) : new Date().toISOString().split('T')[0]
        }));
        console.log('Annonces chargées avec images:', this.announcements);
        this.calculateKpis();
      },
      error: (err) => console.error('Erreur chargement annonces:', err)
    });
  }

  loadRequests() {
    this.demandeService.getAll().subscribe({
      next: (data: any[]) => {
        const fileBase = environment.apiBase.replace('/v1', '') + '/uploads/';

        this.requests = data.map(item => ({
          id: item.trackingId,
          type: (item.niveau?.toLowerCase().includes('formation') ? 'Training' : 'Internship') as any,
          name: item.nom || '',
          firstName: item.prenom || '',
          email: item.email || '',
          telephone: item.telephone || '',
          university: item.ecole || '',
          level: item.niveau || '',
          cv: item.cvCheminFichier ? fileBase + item.cvCheminFichier : '',
          letter: item.lettreRecommandationCheminFichier ? fileBase + item.lettreRecommandationCheminFichier : '',
          motivation: item.motivation || '',
          status: this.mapStatut(item.statut),
          date: item.dateCreation ? this.formatDate(item.dateCreation) : new Date().toISOString().split('T')[0]
        }));
        this.calculateKpis();
      },
      error: (err) => console.error('Erreur chargement demandes:', err)
    });
  }

  calculateKpis() {
    this.kpis.totalAnnouncements = this.announcements.length;
    this.kpis.totalRequests = this.requests.length;
    this.kpis.pendingRequests = this.requests.filter(r => r.status === 'Pending').length;
    this.kpis.approvedRequests = this.requests.filter(r => r.status === 'Approved').length;
  }

  formatDate(dateString: string): string {
    try {
      const date = new Date(dateString);
      return date.toLocaleDateString('fr-FR');
    } catch {
      return dateString;
    }
  }

  mapTypeAnnonceToCategory(typeAnnonce: string | null | undefined): 'info' | 'event' | 'update' | 'promo' {
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

  mapCategoryToTypeAnnonce(category: 'info' | 'event' | 'update' | 'promo'): string {
    switch (category) {
      case 'info':
        return 'EMPLOI';
      case 'event':
        return 'EVENEMENT';
      case 'update':
        return 'FORMATION';
      case 'promo':
      default:
        return 'AUTRE';
    }
  }

  onNavClick(id: string) {
    this.navChange.emit(id);
  }

  setActiveTab(tab: 'announcements' | 'requests') {
    this.activeTab = tab as any;
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onEditFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  addAnnouncement() {
    if (!this.newAnnouncement.title || !this.newAnnouncement.description) {
      alert('Veuillez remplir au moins le titre et la description.');
      return;
    }

    const formData = new FormData();
    const annonceDto = {
      titre: this.newAnnouncement.title,
      description: this.newAnnouncement.description,
      typeAnnonce: this.mapCategoryToTypeAnnonce(this.newAnnouncement.category as any),
      estActif: true
    };

    formData.append('annonce', new Blob([JSON.stringify(annonceDto)], { type: 'application/json' }));
    if (this.selectedFile) {
      formData.append('image', this.selectedFile);
    }

    this.annonceService.create(formData).subscribe({
      next: (created: any) => {
        const fileBase = environment.apiBase.replace('/v1', '') + '/uploads/';
        this.announcements.unshift({
          id: created.trackingId,
          title: created.titre,
          category: this.mapTypeAnnonceToCategory(created.typeAnnonce),
          description: created.description,
          imageUrl: created.imagePath ? fileBase + created.imagePath : undefined,
          date: created.dateCreation ? this.formatDate(created.dateCreation) : new Date().toISOString().split('T')[0]
        });
        this.newAnnouncement = { title: '', category: 'info', description: '' };
        this.selectedFile = null;
        this.calculateKpis();
        alert('Annonce publiée avec succès !');
      },
      error: (err) => {
        console.error('Erreur création annonce:', err);
        alert('Une erreur est survenue lors de la création de l\'annonce.');
      }
    });
  }

  saveEdit() {
    if (this.modalType === 'announcement') {
      const formData = new FormData();
      const annonceDto = {
        titre: this.editItem.title,
        description: this.editItem.description,
        typeAnnonce: this.mapCategoryToTypeAnnonce(this.editItem.category),
        estActif: true
      };

      formData.append('annonce', new Blob([JSON.stringify(annonceDto)], { type: 'application/json' }));
      if (this.selectedFile) {
        formData.append('image', this.selectedFile);
      }

      this.annonceService.update(this.editItem.id, formData).subscribe({
        next: (updated: any) => {
          const fileBase = environment.apiBase.replace('/v1', '') + '/uploads/';
          const idx = this.announcements.findIndex(a => a.id === this.editItem.id);
          if (idx !== -1) {
            this.announcements[idx] = {
              id: updated.trackingId,
              title: updated.titre,
              category: this.mapTypeAnnonceToCategory(updated.typeAnnonce),
              description: updated.description,
              imageUrl: updated.imagePath ? fileBase + updated.imagePath : undefined,
              date: updated.dateCreation ? this.formatDate(updated.dateCreation) : this.editItem.date
            };
          }
          this.showEditModal = false;
          this.selectedFile = null;
          alert('Annonce modifiée avec succès !');
        },
        error: (err) => {
          console.error('Erreur modification annonce:', err);
          alert('Une erreur est survenue lors de la modification.');
        }
      });
    }
  }

  updateRequestStatus(id: string | undefined, status: 'Approved' | 'Rejected') {
    if (!id) return;

    this.demandeService.updateStatus(id, status).subscribe({
      next: (updated) => {
        const idx = this.requests.findIndex(r => r.id === id);
        if (idx !== -1) {
          const mappedStatus = this.mapStatut(updated.statut);
          this.requests[idx].status = mappedStatus;
          this.calculateKpis();
          alert(`Demande ${status === 'Approved' ? 'approuvée' : 'rejetée'} avec succès !`);
        }
      },
      error: (err) => {
        console.error('Erreur mise à jour statut:', err);
        alert('Une erreur est survenue lors de la mise à jour du statut.');
      }
    });
  }

  closeAllModals() {
    this.showDetailsModal = false;
    this.showEditModal = false;
    this.showDeleteModal = false;
  }

  closeDetailsModal() {
    this.showDetailsModal = false;
  }

  viewAnnouncement(item: Announcement) {
    this.selectedItem = item;
    this.modalType = 'announcement';
    this.showDetailsModal = true;
  }

  viewRequestDetails(item: Request) {
    this.selectedItem = item;
    this.modalType = 'request';
    this.showDetailsModal = true;
  }

  openEditModal(item: any, type: 'announcement' | 'request') {
    this.editItem = JSON.parse(JSON.stringify(item));
    this.modalType = type;
    this.showEditModal = true;
  }

  confirmDelete(item: any, type: 'announcement' | 'request') {
    this.itemToDelete = item;
    this.modalType = type;
    this.showDeleteModal = true;
  }

  executeDelete() {
    if (this.modalType === 'announcement') {
      this.annonceService.delete(this.itemToDelete.id).subscribe({
        next: () => {
          this.announcements = this.announcements.filter(a => a.id !== this.itemToDelete.id);
          this.calculateKpis();
          this.closeDeleteModal();
        },
        error: (err) => {
          console.error('Erreur suppression:', err);
          alert('Erreur lors de la suppression.');
          this.closeDeleteModal();
        }
      });
    } else {
      this.demandeService.delete(this.itemToDelete.id).subscribe({
        next: () => {
          this.requests = this.requests.filter(r => r.id !== this.itemToDelete.id);
          this.calculateKpis();
          this.closeDeleteModal();
        },
        error: (err) => {
          console.error('Erreur suppression:', err);
          alert('Erreur lors de la suppression.');
          this.closeDeleteModal();
        }
      });
    }
  }

  closeDeleteModal() {
    this.showDeleteModal = false;
    this.itemToDelete = null;
  }

  mapStatut(statut: any): 'Pending' | 'Approved' | 'Rejected' {
    if (!statut) return 'Pending';
    switch (statut) {
      case 'ACCEPTEE': return 'Approved';
      case 'REJETEE': return 'Rejected';
      default: return 'Pending';
    }
  }
}
