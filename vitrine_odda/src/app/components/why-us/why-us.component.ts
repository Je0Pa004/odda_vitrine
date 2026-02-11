import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-why-us',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './why-us.component.html',
  styleUrl: './why-us.component.css'
})
export class WhyUsComponent {
  @Output() navChange = new EventEmitter<string>();

  onNavClick(id: string) {
    this.navChange.emit(id);
  }
}
