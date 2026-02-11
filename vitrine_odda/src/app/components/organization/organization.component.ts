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
  `
})
export class OrganizationComponent {
  @Output() navChange = new EventEmitter<string>();

  onNavClick(id: string) {
    this.navChange.emit(id);
  }
}
