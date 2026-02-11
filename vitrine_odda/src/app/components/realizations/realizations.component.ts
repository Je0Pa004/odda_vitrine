import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-realizations',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './realizations.component.html',
  styleUrl: './realizations.component.css'
})
export class RealizationsComponent {
  @Output() navChange = new EventEmitter<string>();

  onNavClick(id: string) {
    this.navChange.emit(id);
  }
}
