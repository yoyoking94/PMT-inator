import { TestBed } from '@angular/core/testing';

import { HistoriqueTacheService } from './historique-tache.service';

describe('HistoriqueTacheService', () => {
  let service: HistoriqueTacheService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HistoriqueTacheService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
