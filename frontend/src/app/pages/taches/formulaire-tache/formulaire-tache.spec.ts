import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormulaireTache } from './formulaire-tache';

describe('FormulaireTache', () => {
  let component: FormulaireTache;
  let fixture: ComponentFixture<FormulaireTache>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormulaireTache],
    }).compileComponents();

    fixture = TestBed.createComponent(FormulaireTache);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
