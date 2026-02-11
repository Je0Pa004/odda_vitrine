import { Component, signal, effect, PLATFORM_ID, inject } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { HeroComponent } from './components/hero/hero.component';
import { ServicesComponent } from './components/services/services.component';
import { RealizationsComponent } from './components/realizations/realizations.component';
import { WhyUsComponent } from './components/why-us/why-us.component';
import { AboutComponent } from './components/about/about.component';
import { ReferencesComponent } from './components/references/references.component';
import { AnnouncementsComponent } from './components/announcements/announcements.component';
import { InternshipComponent } from './components/internship/internship.component';
import { ContactComponent } from './components/contact/contact.component';
import { ChatbotComponent } from './components/chatbot/chatbot.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoginComponent } from './components/login/login.component';
import { OrganizationComponent } from './components/organization/organization.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    HeaderComponent,
    HeroComponent,
    ServicesComponent,
    RealizationsComponent,
    WhyUsComponent,
    AboutComponent,
    ReferencesComponent,
    AnnouncementsComponent,
    InternshipComponent,
    ContactComponent,
    ChatbotComponent,
    FooterComponent,
    LoginComponent,
    OrganizationComponent,
    AdminDashboardComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  private platformId = inject(PLATFORM_ID);

  // Initial status should be hydration-safe but aware of browser state
  activeSection = signal<string>(
    (typeof window !== 'undefined' && localStorage.getItem('activeSection')) || 'hero'
  );

  constructor() {
    // Load after construction to ensure browser availability
    if (isPlatformBrowser(this.platformId)) {
      // Logic handled in signal initialization to avoid flash
      document.body.classList.add('is-ready');
    }

    effect(() => {
      const currentSection = this.activeSection();
      if (isPlatformBrowser(this.platformId)) {
        localStorage.setItem('activeSection', currentSection);
        // Special case for admin dashboard to ensure it's at the top
        if (currentSection === 'admin-dashboard') {
          window.scrollTo(0, 0);
        }
      }
    });
  }

  setSection(section: string) {
    this.activeSection.set(section);
  }
}
