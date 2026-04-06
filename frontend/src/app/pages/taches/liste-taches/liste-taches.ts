import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { TacheService } from '../../../services/tache.service';
import { TacheResponseDto } from '../../../models/tache.model';

@Component({
  selector: 'app-liste-taches',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './liste-taches.html'
})
export class ListeTaches implements OnInit {

  listeTaches: TacheResponseDto[] = [];
  messageErreur: string = '';
  projetId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private tacheService: TacheService,
    private detecteurChangements: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.projetId = Number(this.route.snapshot.paramMap.get('projetId'));
    this.chargerLesTaches();
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
}