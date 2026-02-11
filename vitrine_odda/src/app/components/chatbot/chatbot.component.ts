import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatService } from '../../services/chat.service';

@Component({
  selector: 'app-chatbot',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chatbot.component.html',
  styleUrl: './chatbot.component.css'
})
export class ChatbotComponent {
  private chatService = inject(ChatService);

  isOpen = false;
  isLoading = false;
  userInput = '';
  messages: { role: 'user' | 'ai', text: string }[] = [
    { role: 'ai', text: 'Bonjour ! Je suis l\'assistant intelligent d\'Odda Technology. Comment puis-je vous aider aujourd\'hui ?' }
  ];

  toggleChat() {
    this.isOpen = !this.isOpen;
  }

  async sendMessage() {
    if (!this.userInput.trim() || this.isLoading) return;

    const userMsg = this.userInput;
    this.messages.push({ role: 'user', text: userMsg });
    this.userInput = '';
    this.isLoading = true;

    const aiResponse = await this.chatService.getResponse(userMsg);
    this.messages.push({ role: 'ai', text: aiResponse });
    this.isLoading = false;
  }
}
