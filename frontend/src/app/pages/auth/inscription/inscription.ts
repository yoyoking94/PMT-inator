import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { UtilisateurService } from '../../../services/utilisateur.service';
import { UtilisateurRequestDto } from '../../../models/utilisateur.model';

@Component({
  selector: 'app-inscription',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './inscription.html'
})
export class Inscription {

  utilisateurRequestDto: UtilisateurRequestDto = {
    username: '',
    email: '',
    password: ''
  };

  messageErreur: string = '';
  chargementEnCours: boolean = false;

  constructor(
    private utilisateurService: UtilisateurService,
    private routeur: Router
  ) { }

  inscrire(): void {
    this.messageErreur = '';
    this.chargementEnCours = true;

    this.utilisateurService.inscrireUnUtilisateur(this.utilisateurRequestDto).subscribe({
      next: () => {
        this.routeur.navigate(['/connexion']);
      },
      error: () => {
        this.messageErreur = 'Une erreur est survenue lors de l\'inscription.';
        this.chargementEnCours = false;
      }
    });
  }
}