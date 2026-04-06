import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { TacheService } from '../../../services/tache.service';
import { MembreProjetService } from '../../../services/membre-projet.service';
import { AuthentificationService } from '../../../services/authentification.service';
import { HistoriqueTacheService } from '../../../services/historique-tache.service';
import { TacheRequestDto, TacheResponseDto, Priorite } from '../../../models/tache.model';
import { MembreProjetResponseDto } from '../../../models/membre-projet.model';

@Component({
  selector: 'app-formulaire-tache',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './formulaire-tache.html'
})
export class FormulaireTache implements OnInit {

  projetId: number = 0;
  tacheId: number | null = null;
  estEnModeModification: boolean = false;

  tacheExistante: TacheResponseDto | null = null;
  listeMembres: MembreProjetResponseDto[] = [];
  membreAssigneIdSelectionne: number = 0;

  messageErreur: string = '';
  chargementEnCours: boolean = false;

  listePriorities: Priorite[] = ['BASSE', 'MOYENNE', 'HAUTE'];

  tacheRequestDto: TacheRequestDto = {
    nom: '',
    description: '',
    dateEcheant: '',
    priorite: 'MOYENNE'
  };

  constructor(
    private route: ActivatedRoute,
    private routeur: Router,
    private tacheService: TacheService,
    private membreProjetService: MembreProjetService,
    private authentificationService: AuthentificationService,
    private historiqueTacheService: HistoriqueTacheService,
    private detecteurChangements: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.projetId = Number(this.route.snapshot.paramMap.get('projetId'));
    const tacheIdParam = this.route.snapshot.paramMap.get('tacheId');

    if (tacheIdParam) {
      this.tacheId = Number(tacheIdParam);
      this.estEnModeModification = true;
      this.chargerLaTache();
    }

    this.chargerLesMembres();
  }

  chargerLaTache(): void {
    this.tacheService.recupererUneTache(this.projetId, this.tacheId!).subscribe({
      next: (tache) => {
        this.tacheExistante = tache;
        this.tacheRequestDto = {
          nom: tache.nom,
          description: tache.description,
          dateEcheant: tache.dateEcheant,
          priorite: tache.priorite
        };
        this.membreAssigneIdSelectionne = tache.membreAssigne.id;
        this.detecteurChangements.detectChanges();
      },
      error: () => {
        this.messageErreur = 'Impossible de charger la tâche.';
        this.detecteurChangements.detectChanges();
      }
    });
  }

  chargerLesMembres(): void {
    this.membreProjetService.recupererLesMembresDeUnProjet(this.projetId).subscribe({
      next: (membres) => {
        this.listeMembres = membres;
        this.detecteurChangements.detectChanges();
      },
      error: () => {
        this.messageErreur = 'Impossible de charger les membres.';
        this.detecteurChangements.detectChanges();
      }
    });
  }

  enregistrer(): void {
    this.messageErreur = '';
    this.chargementEnCours = true;
    this.detecteurChangements.detectChanges();

    if (this.estEnModeModification) {
      this.modifierLaTache();
    } else {
      this.creerLaTache();
    }
  }

  private creerLaTache(): void {
    const utilisateurConnecte = this.authentificationService.recupererUtilisateur();
    if (!utilisateurConnecte) return;

    this.tacheService.creerUneTache(
      this.projetId,
      this.membreAssigneIdSelectionne,
      utilisateurConnecte.id,  // ← createurId
      this.tacheRequestDto
    ).subscribe({
      next: () => {
        this.chargementEnCours = false;
        this.routeur.navigate(['/projets', this.projetId]);
      },
      error: () => {
        this.messageErreur = 'Impossible de créer la tâche.';
        this.chargementEnCours = false;
        this.detecteurChangements.detectChanges();
      }
    });
  }

  private modifierLaTache(): void {
    const utilisateurConnecte = this.authentificationService.recupererUtilisateur();
    if (!utilisateurConnecte) return;

    // On ajoute l'id du modificateur avant d'envoyer la requête
    this.tacheRequestDto.modificateurId = utilisateurConnecte.id;

    this.tacheService.mettreAJourUneTache(
      this.projetId,
      this.tacheId!,
      this.tacheRequestDto
    ).subscribe({
      next: () => {
        if (utilisateurConnecte && this.tacheExistante) {
          this.enregistrerLesModificationsDansHistorique(utilisateurConnecte.id);
        } else {
          this.chargementEnCours = false;
          this.detecteurChangements.detectChanges();
          this.naviguerVersDetailTache();
        }
      },
      error: () => {
        this.messageErreur = 'Impossible de modifier la tâche. Vérifiez vos droits.';
        this.chargementEnCours = false;
        this.detecteurChangements.detectChanges();
      }
    });
  }

  private enregistrerLesModificationsDansHistorique(utilisateurId: number): void {
    const champsModifies = this.detecterLesChampsModifies();

    if (champsModifies.length === 0) {
      this.chargementEnCours = false;
      this.detecteurChangements.detectChanges();
      this.naviguerVersDetailTache();
      return;
    }

    // On attend que tous les appels d'historique soient terminés avant de naviguer
    let nombreAppelsTermines = 0;
    const nombreAppelsTotal = champsModifies.length;

    champsModifies.forEach(champ => {
      this.historiqueTacheService.enregistrerUneModification(this.tacheId!, {
        tacheId: this.tacheId!,
        modifieParId: utilisateurId,
        champModifie: champ.nom,
        ancienneValeur: champ.ancienneValeur,
        nouvelleValeur: champ.nouvelleValeur
      }).subscribe({
        next: () => {
          nombreAppelsTermines++;
          if (nombreAppelsTermines === nombreAppelsTotal) {
            this.chargementEnCours = false;
            this.detecteurChangements.detectChanges();
            this.naviguerVersDetailTache();
          }
        },
        error: () => {
          nombreAppelsTermines++;
          if (nombreAppelsTermines === nombreAppelsTotal) {
            this.chargementEnCours = false;
            this.detecteurChangements.detectChanges();
            this.naviguerVersDetailTache();
          }
        }
      });
    });
  }

  // Navigation vers la liste puis vers le détail pour forcer le rechargement
  private naviguerVersDetailTache(): void {
    this.routeur.navigate(['/projets', this.projetId, 'taches']).then(() => {
      this.routeur.navigate(['/projets', this.projetId, 'taches', this.tacheId]);
    });
  }

  private detecterLesChampsModifies(): { nom: string; ancienneValeur: string; nouvelleValeur: string }[] {
    const champsModifies = [];
    const tache = this.tacheExistante!;

    if (tache.nom !== this.tacheRequestDto.nom) {
      champsModifies.push({ nom: 'nom', ancienneValeur: tache.nom, nouvelleValeur: this.tacheRequestDto.nom });
    }
    if (tache.priorite !== this.tacheRequestDto.priorite) {
      champsModifies.push({ nom: 'priorite', ancienneValeur: tache.priorite, nouvelleValeur: this.tacheRequestDto.priorite });
    }
    if (tache.dateEcheant !== this.tacheRequestDto.dateEcheant) {
      champsModifies.push({ nom: 'dateEcheant', ancienneValeur: tache.dateEcheant, nouvelleValeur: this.tacheRequestDto.dateEcheant });
    }

    return champsModifies;
  }
}