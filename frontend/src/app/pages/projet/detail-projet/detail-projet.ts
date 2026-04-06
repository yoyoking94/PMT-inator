import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProjetService } from '../../../services/projet.service';
import { MembreProjetService } from '../../../services/membre-projet.service';
import { TacheService } from '../../../services/tache.service';
import { AuthentificationService } from '../../../services/authentification.service';
import { ProjetResponseDto } from '../../../models/projet.model';
import { MembreProjetResponseDto, MembreProjetRequestDto, Role } from '../../../models/membre-projet.model';
import { TacheResponseDto } from '../../../models/tache.model';

@Component({
  selector: 'app-detail-projet',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './detail-projet.html'
})
export class DetailProjet implements OnInit {

  projet: ProjetResponseDto | null = null;
  listeMembres: MembreProjetResponseDto[] = [];
  listeTaches: TacheResponseDto[] = [];
  tacheSelectionnee: TacheResponseDto | null = null;
  afficherFormulaireInvitation: boolean = false;
  messageErreur: string = '';
  messageSucces: string = '';
  projetId: number = 0;
  roleUtilisateurConnecte: Role | null = null;

  invitationRequestDto: MembreProjetRequestDto = {
    email: '',
    role: 'MEMBRE'
  };

  listeRoles: Role[] = ['ADMINISTRATEUR', 'MEMBRE', 'OBSERVATEUR'];

  constructor(
    private route: ActivatedRoute,
    private projetService: ProjetService,
    private membreProjetService: MembreProjetService,
    private tacheService: TacheService,
    private authentificationService: AuthentificationService,
    private detecteurChangements: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.projetId = Number(this.route.snapshot.paramMap.get('projetId'));
    this.chargerLeProjet();
    this.chargerLesMembres();
    this.chargerLesTaches();
  }

  chargerLeProjet(): void {
    this.projetService.recupererUnProjet(this.projetId).subscribe({
      next: (projet) => {
        this.projet = projet;
        this.detecteurChangements.detectChanges();
      },
      error: () => {
        this.messageErreur = 'Impossible de charger le projet.';
        this.detecteurChangements.detectChanges();
      }
    });
  }

  chargerLesMembres(): void {
    this.membreProjetService.recupererLesMembresDeUnProjet(this.projetId).subscribe({
      next: (membres) => {
        this.listeMembres = membres;
        this.determinerLeRoleDeLUtilisateurConnecte();
        this.detecteurChangements.detectChanges();
      },
      error: () => {
        this.messageErreur = 'Impossible de charger les membres.';
        this.detecteurChangements.detectChanges();
      }
    });
  }

  chargerLesTaches(): void {
    this.tacheService.recupererLesTachesDeUnProjet(this.projetId).subscribe({
      next: (taches) => {
        this.listeTaches = taches;
        this.detecteurChangements.detectChanges();
      },
      error: () => {
        this.messageErreur = 'Impossible de charger les tâches.';
        this.detecteurChangements.detectChanges();
      }
    });
  }

  // On cherche l'utilisateur connecté dans la liste des membres pour connaître son rôle
  determinerLeRoleDeLUtilisateurConnecte(): void {
    const utilisateurConnecte = this.authentificationService.recupererUtilisateur();
    if (!utilisateurConnecte) return;

    const membreTrouve = this.listeMembres.find(
      membre => membre.utilisateur.id === utilisateurConnecte.id
    );

    this.roleUtilisateurConnecte = membreTrouve ? membreTrouve.role : null;
    this.detecteurChangements.detectChanges();
  }

  // Retourne true si l'utilisateur peut gérer les membres (admin uniquement)
  peutGererLesMembres(): boolean {
    return this.roleUtilisateurConnecte === 'ADMINISTRATEUR';
  }

  // Retourne true si l'utilisateur peut créer/modifier des tâches (admin ou membre)
  peutGererLesTaches(): boolean {
    return this.roleUtilisateurConnecte === 'ADMINISTRATEUR'
      || this.roleUtilisateurConnecte === 'MEMBRE';
  }

  selectionnerUneTache(tache: TacheResponseDto): void {
    if (this.tacheSelectionnee?.id === tache.id) {
      this.tacheSelectionnee = null;
    } else {
      this.tacheSelectionnee = tache;
    }
    this.detecteurChangements.detectChanges();
  }

  inviterUnMembre(): void {
    this.messageErreur = '';
    this.messageSucces = '';

    const utilisateurConnecte = this.authentificationService.recupererUtilisateur();
    if (!utilisateurConnecte) return;

    this.membreProjetService.inviterUnMembre(
      this.projetId,
      utilisateurConnecte.id,
      this.invitationRequestDto
    ).subscribe({
      next: (membre) => {
        this.listeMembres = [...this.listeMembres, membre];
        this.messageSucces = 'Membre invité avec succès.';
        this.afficherFormulaireInvitation = false;
        this.invitationRequestDto = { email: '', role: 'MEMBRE' };
        this.detecteurChangements.detectChanges();
      },
      error: () => {
        this.messageErreur = 'Impossible d\'inviter ce membre. Vérifiez vos droits.';
        this.detecteurChangements.detectChanges();
      }
    });
  }
}