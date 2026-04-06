import { TestBed } from '@angular/core/testing';

import { MembreProjetService } from './membre-projet.service';

describe('MembreProjetService', () => {
  let service: MembreProjetService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MembreProjetService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
