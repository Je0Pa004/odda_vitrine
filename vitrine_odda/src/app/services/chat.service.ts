import { Injectable, PLATFORM_ID, inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { GoogleGenerativeAI } from '@google/generative-ai';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private genAI: GoogleGenerativeAI | null = null;
  private model: any;
  private platformId = inject(PLATFORM_ID);

  constructor() {
    // Only initialize in browser environment
    if (isPlatformBrowser(this.platformId)) {
      const apiKey = (window as any).GEMINI_API_KEY || '';
      this.genAI = new GoogleGenerativeAI(apiKey);
      this.model = this.genAI.getGenerativeModel({
        model: "gemini-1.5-flash",
        systemInstruction: "Tu es l'assistant d'Odda Technology. Tu parles français. Tu es professionnel. Tu connais les horaires (7h-18h) et les services : 1. Développement sur mesure (Spring Boot, Angular, Java), 2. Vente de matériels informatiques, 3. Maintenance & Réseaux. Localisation : Route CEET Agoè Cacaveli, Face CEG cacaveli, Lomé. Produit phare : School Manager SM-WEB 2.0.",
      });
    }
  }


  async getResponse(prompt: string) {
    if (!isPlatformBrowser(this.platformId)) {
      return "Le chat est disponible uniquement dans le navigateur.";
    }
    if (!(window as any).GEMINI_API_KEY) {
      return "Clé API Gemini manquante. Veuillez configurer GEMINI_API_KEY.";
    }
    try {
      const result = await this.model.generateContent(prompt);
      const response = await result.response;
      return response.text();
    } catch (error) {
      console.error("Gemini Error:", error);
      return "Désolé, je rencontre une difficulté technique. Veuillez réessayer plus tard ou nous contacter directement.";
    }
  }
}
