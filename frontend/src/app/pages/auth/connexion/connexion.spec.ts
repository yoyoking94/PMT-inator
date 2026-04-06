import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Connexion } from './connexion';
import { UtilisateurService } from '../../../services/utilisateur.service';
import { AuthentificationService } from '../../../services/authentification.service';
import { provideRouter } from '@angular/router';

describe('Connexion', () => {
  let component: Connexion;
  let fixture: ComponentFixture<Connexion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Connexion],
      providers: [
        provideRouter([]),
        { provide: UtilisateurService, useValue: {} },
        { provide: AuthentificationService, useValue: {} }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(Connexion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});