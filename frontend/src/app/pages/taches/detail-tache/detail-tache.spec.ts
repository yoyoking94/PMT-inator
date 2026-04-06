import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DetailTache } from './detail-tache';
import { TacheService } from '../../../services/tache.service';
import { HistoriqueTacheService } from '../../../services/historique-tache.service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

describe('DetailTache', () => {
  let component: DetailTache;
  let fixture: ComponentFixture<DetailTache>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailTache],
      providers: [
        {
          provide: TacheService,
          useValue: {
            recupererUneTache: () => of(null)
          }
        },
        {
          provide: HistoriqueTacheService,
          useValue: {
            recupererLesHistoriquesDeUneTache: () => of([])
          }
        },
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of({ get: (cle: string) => '1' })
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(DetailTache);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});