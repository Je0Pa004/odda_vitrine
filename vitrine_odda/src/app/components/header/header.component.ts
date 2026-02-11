import { Component, EventEmitter, Input, Output, HostListener, PLATFORM_ID, inject } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  @Input() activeSection = 'hero';
  @Output() navChange = new EventEmitter<string>();

  isScrolled = false;
  isMobileMenuOpen = false;
  private platformId = inject(PLATFORM_ID);

  navItems = [
    { id: 'hero', label: 'ACCUEIL' },
    { id: 'realizations', label: 'PROJETS RÉALISÉS' },
    { id: 'services', label: 'SERVICES' },
    { id: 'about', label: 'À PROPOS' },
    { id: 'references', label: 'RÉFÉRENCES' },
    { id: 'internship', label: 'DEMANDE' },
    { id: 'organization', label: 'EQUIPE' },
    { id: 'announcements', label: 'ANNONCES' },
    { id: 'contact', label: 'CONTACT' }
  ];

  @HostListener('window:scroll', [])
  onWindowScroll() {
    if (isPlatformBrowser(this.platformId)) {
      this.isScrolled = window.scrollY > 50;
    }
  }

  onNavClick(id: string) {
    this.navChange.emit(id);
  }
}
