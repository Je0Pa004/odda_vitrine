import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {
  @Output() navChange = new EventEmitter<string>();

  formData = {
    name: '',
    email: '',
    message: ''
  };
  isSubmitting = false;

  onSubmit() {
    this.isSubmitting = true;
    setTimeout(() => {
      this.isSubmitting = false;
      alert('Message envoyé avec succès !');
      this.formData = { name: '', email: '', message: '' };
    }, 2000);
  }

  onNavClick(id: string) {
    this.navChange.emit(id);
  }
}
