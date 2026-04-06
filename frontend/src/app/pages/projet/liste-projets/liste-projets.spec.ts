import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ListeProjets } from './liste-projets';
import { ProjetService } from '../../../services/projet.service';
import { AuthentificationService } from '../../../services/authentification.service';
import { provideRouter } from '@angular/router';
import { of } from 'rxjs';

describe('ListeProjets', () => {
  let component: ListeProjets;
  let fixture: ComponentFixture<ListeProjets>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListeProjets],
      providers: [
        provideRouter([]),
        {
          provide: ProjetService,
          useValue: {
            recupererLesProjetsDeUnUtilisateur: () => of([]),
            recupererTousLesProjets: () => of([]),
            creerUnProjet: () => of(null)
          }
        },
        {
          provide: AuthentificationService,
          useValue: {
            recupererUtilisateur: () => ({ id: 1, username: 'test' }),
            deconnecter: () => { }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ListeProjets);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});