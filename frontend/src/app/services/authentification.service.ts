import { Injectable } from '@angular/core';
import { UtilisateurResponseDto } from '../models/utilisateur.model';

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {

  private readonly cleUtilisateur = 'utilisateurConnecte';

  sauvegarderUtilisateur(utilisateur: UtilisateurResponseDto): void {
    sessionStorage.setItem(this.cleUtilisateur, JSON.stringify(utilisateur));
  }

  recupererUtilisateur(): UtilisateurResponseDto | null {
    const donnees = sessionStorage.getItem(this.cleUtilisateur);
    if (donnees) {
      return JSON.parse(donnees) as UtilisateurResponseDto;
    }
    return null;
  }

  estConnecte(): boolean {
    return this.recupererUtilisateur() !== null;
  }

  deconnecter(): void {
    sessionStorage.removeItem(this.cleUtilisateur);
  }
}