import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormulaireTache } from './formulaire-tache';
import { TacheService } from '../../../services/tache.service';
import { MembreProjetService } from '../../../services/membre-projet.service';
import { AuthentificationService } from '../../../services/authentification.service';
import { HistoriqueTacheService } from '../../../services/historique-tache.service';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

describe('FormulaireTache', () => {
  let component: FormulaireTache;
  let fixture: ComponentFixture<FormulaireTache>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormulaireTache],
      providers: [
        {
          provide: TacheService,
          useValue: {
            recupererUneTache: () => of(null),
            creerUneTache: () => of(null),
            mettreAJourUneTache: () => of(null)
          }
        },
        {
          provide: MembreProjetService,
          useValue: {
            recupererLesMembresDeUnProjet: () => of([])
          }
        },
        {
          provide: AuthentificationService,
          useValue: {
            recupererUtilisateur: () => null
          }
        },
        {
          provide: HistoriqueTacheService,
          useValue: {
            enregistrerUneModification: () => of(null)
          }
        },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: (cle: string) => null } }
          }
        },
        { provide: Router, useValue: { navigate: () => Promise.resolve(true) } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(FormulaireTache);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});