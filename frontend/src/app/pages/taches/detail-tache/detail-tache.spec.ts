import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailTache } from './detail-tache';

describe('DetailTache', () => {
  let component: DetailTache;
  let fixture: ComponentFixture<DetailTache>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailTache],
    }).compileComponents();

    fixture = TestBed.createComponent(DetailTache);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
