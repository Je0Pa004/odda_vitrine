import { Component, EventEmitter, Output, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styles: ``
})
export class LoginComponent {
  @Output() navChange = new EventEmitter<string>();

  private auth = inject(AuthService);

  loginData = {
    email: '',
    password: ''
  };

  showPassword = false;
  isSubmitting = false;
  errorMessage: string | null = null;

  onSubmit() {
    this.isSubmitting = true;
    this.errorMessage = null;
    this.auth.login(this.loginData.email, this.loginData.password).subscribe({
      next: (resp) => {
        this.isSubmitting = false;
        // Successful login: navigate to admin dashboard
        this.onNavClick('admin-dashboard');
      },
      error: (err) => {
        this.isSubmitting = false;
        this.errorMessage = 'Email ou mot de passe invalide.';
        console.error('Login failed', err);
      }
    });
  }

  onNavClick(id: string) {
    this.navChange.emit(id);
  }
}
