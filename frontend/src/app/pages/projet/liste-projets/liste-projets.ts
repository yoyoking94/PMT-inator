import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProjetService } from '../../../services/projet.service';
import { AuthentificationService } from '../../../services/authentification.service';
import { ProjetResponseDto, ProjetRequestDto } from '../../../models/projet.model';

@Component({
  selector: 'app-liste-projets',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './liste-projets.html'
})
export class ListeProjets implements OnInit {

  listeMesProjets: ProjetResponseDto[] = [];
  listeTousProjets: ProjetResponseDto[] = [];
  afficherFormulaireCreation: boolean = false;
  messageErreur: string = '';
  chargementEnCours: boolean = false;

  nouveauProjet: ProjetRequestDto = {
    nom: '',
    description: '',
    dateDeDebut: ''
  };

  constructor(
    private projetService: ProjetService,
    private authentificationService: AuthentificationService,
    private routeur: Router,
    private detecteurChangements: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.chargerMesProjets();
    this.chargerTousLesProjets();
  }

  chargerMesProjets(): void {
    const utilisateurConnecte = this.authentificationService.recupererUtilisateur();
    if (!utilisateurConnecte) {
      this.routeur.navigate(['/connexion']);
      return;
    }

    this.projetService.recupererLesProjetsDeUnUtilisateur(utilisateurConnecte.id).subscribe({
      next: (projets) => {
        this.listeMesProjets = projets;
        this.detecteurChangements.detectChanges();
      },
      error: () => {
        this.messageErreur = 'Impossible de charger vos projets.';
        this.detecteurChangements.detectChanges();
      }
    });
  }

  chargerTousLesProjets(): void {
    this.projetService.recupererTousLesProjets().subscribe({
      next: (projets) => {
        this.listeTousProjets = projets;
        this.detecteurChangements.detectChanges();
      },
      error: () => {
        this.messageErreur = 'Impossible de charger tous les projets.';
        this.detecteurChangements.detectChanges();
      }
    });
  }

  creerUnProjet(): void {
    const utilisateurConnecte = this.authentificationService.recupererUtilisateur();
    if (!utilisateurConnecte) return;

    this.chargementEnCours = true;
    this.messageErreur = '';

    this.projetService.creerUnProjet(this.nouveauProjet, utilisateurConnecte.id).subscribe({
      next: (projetCree) => {
        this.listeMesProjets = [...this.listeMesProjets, projetCree];
        this.listeTousProjets = [...this.listeTousProjets, projetCree];
        this.nouveauProjet = { nom: '', description: '', dateDeDebut: '' };
        this.chargementEnCours = false;
        this.afficherFormulaireCreation = false;
        this.detecteurChangements.detectChanges();
      },
      error: () => {
        this.messageErreur = 'Impossible de créer le projet.';
        this.chargementEnCours = false;
        this.detecteurChangements.detectChanges();
      }
    });
  }

  deconnecter(): void {
    this.authentificationService.deconnecter();
    this.routeur.navigate(['/connexion']);
  }
}