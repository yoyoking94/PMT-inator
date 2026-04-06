import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { UtilisateurService } from '../../../services/utilisateur.service';
import { AuthentificationService } from '../../../services/authentification.service';

@Component({
  selector: 'app-connexion',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './connexion.html'
})
export class Connexion {

  email: string = '';
  motDePasse: string = '';
  messageErreur: string = '';
  chargementEnCours: boolean = false;

  constructor(
    private utilisateurService: UtilisateurService,
    private authentificationService: AuthentificationService,
    private routeur: Router
  ) { }

  connecter(): void {
    this.messageErreur = '';
    this.chargementEnCours = true;

    this.utilisateurService.connecterUnUtilisateur(this.email, this.motDePasse).subscribe({
      next: (utilisateur) => {
        this.authentificationService.sauvegarderUtilisateur(utilisateur);
        this.routeur.navigate(['/projets']);
      },
      error: () => {
        this.messageErreur = 'Email ou mot de passe incorrect.';
        this.chargementEnCours = false;
      }
    });
  }
}