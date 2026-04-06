import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { TacheService } from '../../../services/tache.service';
import { HistoriqueTacheService } from '../../../services/historique-tache.service';
import { TacheResponseDto } from '../../../models/tache.model';
import { HistoriqueTacheResponseDto } from '../../../models/historique-tache.model';

@Component({
  selector: 'app-detail-tache',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './detail-tache.html'
})
export class DetailTache implements OnInit {

  tache: TacheResponseDto | null = null;
  listeHistoriques: HistoriqueTacheResponseDto[] = [];
  messageErreur: string = '';
  projetId: number = 0;
  tacheId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private tacheService: TacheService,
    private historiqueTacheService: HistoriqueTacheService,
    private detecteurChangements: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(parametres => {
      this.projetId = Number(parametres.get('projetId'));
      this.tacheId = Number(parametres.get('tacheId'));
      this.chargerLaTache();
      this.chargerLesHistoriques();
    });
  }

  chargerLaTache(): void {
    this.tacheService.recupererUneTache(this.projetId, this.tacheId).subscribe({
      next: (tache) => {
        this.tache = tache;
        this.detecteurChangements.detectChanges();
      },
      error: () => {
        this.messageErreur = 'Impossible de charger la tâche.';
        this.detecteurChangements.detectChanges();
      }
    });
  }

  chargerLesHistoriques(): void {
    this.historiqueTacheService.recupererLesHistoriquesDeUneTache(this.tacheId).subscribe({
      next: (historiques) => {
        this.listeHistoriques = historiques;
        this.detecteurChangements.detectChanges();
      },
      error: () => {
        this.messageErreur = 'Impossible de charger l\'historique.';
        this.detecteurChangements.detectChanges();
      }
    });
  }
}