import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ListeTaches } from './liste-taches';
import { TacheService } from '../../../services/tache.service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

describe('ListeTaches', () => {
  let component: ListeTaches;
  let fixture: ComponentFixture<ListeTaches>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListeTaches],
      providers: [
        {
          provide: TacheService,
          useValue: {
            recupererLesTachesDeUnProjet: () => of([])
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

    fixture = TestBed.createComponent(ListeTaches);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});