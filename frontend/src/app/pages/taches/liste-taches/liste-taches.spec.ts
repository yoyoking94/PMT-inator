import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeTaches } from './liste-taches';

describe('ListeTaches', () => {
  let component: ListeTaches;
  let fixture: ComponentFixture<ListeTaches>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListeTaches],
    }).compileComponents();

    fixture = TestBed.createComponent(ListeTaches);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
