import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Inscription } from './inscription';
import { UtilisateurService } from '../../../services/utilisateur.service';
import { provideRouter } from '@angular/router';

describe('Inscription', () => {
  let component: Inscription;
  let fixture: ComponentFixture<Inscription>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Inscription],
      providers: [
        provideRouter([]),
        { provide: UtilisateurService, useValue: {} }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(Inscription);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});