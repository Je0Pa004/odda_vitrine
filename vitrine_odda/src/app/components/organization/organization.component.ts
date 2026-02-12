import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-organization',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './organization.component.html',
  styles: `
    :host {
      display: block;
    }
    .org-container {
      @apply pt-[160px] pb-32 bg-slate-50 min-h-screen font-sans;
    }
    .org-card {
      @apply bg-[#005a64] text-white p-6 rounded-lg shadow-xl text-center w-64 mx-auto relative z-10 border border-white/20;
    }
    .org-name {
      @apply text-lg font-black uppercase tracking-tight mb-1;
    }
    .org-role {
      @apply text-sm font-bold text-white/80 uppercase tracking-widest;
    }
    .org-avatar {
      @apply w-20 h-20 bg-orange-500 rounded-full mx-auto mb-4 border-4 border-white shadow-lg flex items-center justify-center overflow-hidden;
    }
    .line-vertical {
      @apply w-1 bg-[#005a64] mx-auto;
    }
    .line-horizontal {
      @apply h-1 bg-[#005a64];
    }
    .branch-container {
      @apply flex justify-center gap-12 mt-12 relative;
    }
    .branch-line-v {
       @apply w-1 bg-[#005a64] h-8 mx-auto;
    }
    .member-grid-card {
      @apply bg-white rounded-2xl overflow-hidden shadow-sm border border-slate-100 transition-all duration-500 hover:shadow-xl hover:-translate-y-2;
    }
    .member-img-container {
      @apply aspect-[3/4] bg-slate-100 overflow-hidden relative;
    }
    .member-info {
      @apply p-4 bg-white border-t border-slate-50 flex flex-col justify-center min-h-[100px];
    }
  `
})
export class OrganizationComponent {
  @Output() navChange = new EventEmitter<string>();

  teamMembers = [
    {
      name: 'M. BILANTE Addo',
      role: 'Président Directeur Général',
      image: 'https://images.unsplash.com/photo-1560250097-0b93528c311a?auto=format&fit=crop&q=80&w=800'
    },
    {
      name: 'M. BILANTE Tchapo Alaza',
      role: 'Directeur des Systèmes d\'Information (DSI)',
      image: 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&q=80&w=800'
    },
    {
      name: 'M. TCHAMDJA Badi',
      role: 'Responsable des Ressources Humaines',
      image: 'https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?auto=format&fit=crop&q=80&w=800'
    },
    {
      name: 'Mlle ASSIH Constantine',
      role: 'Secrétaire de Direction',
      image: 'https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&q=80&w=800'
    },
    {
      name: 'M. TAKO Jos',
      role: 'Ingénieur Informaticien',
      image: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&q=80&w=800'
    }
  ];

  onNavClick(id: string) {
    this.navChange.emit(id);
  }
}
