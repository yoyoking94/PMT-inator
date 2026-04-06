import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DetailProjet } from './detail-projet';
import { ProjetService } from '../../../services/projet.service';
import { MembreProjetService } from '../../../services/membre-projet.service';
import { TacheService } from '../../../services/tache.service';
import { AuthentificationService } from '../../../services/authentification.service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

describe('DetailProjet', () => {
  let component: DetailProjet;
  let fixture: ComponentFixture<DetailProjet>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailProjet],
      providers: [
        {
          provide: ProjetService,
          useValue: {
            recupererUnProjet: () => of(null)
          }
        },
        {
          provide: MembreProjetService,
          useValue: {
            recupererLesMembresDeUnProjet: () => of([]),
            inviterUnMembre: () => of(null)
          }
        },
        {
          provide: TacheService,
          useValue: {
            recupererLesTachesDeUnProjet: () => of([])
          }
        },
        {
          provide: AuthentificationService,
          useValue: {
            recupererUtilisateur: () => null
          }
        },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: () => '1' } }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(DetailProjet);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});