import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HistoriqueTacheRequestDto, HistoriqueTacheResponseDto } from '../models/historique-tache.model';

@Injectable({
  providedIn: 'root'
})
export class HistoriqueTacheService {

  private readonly urlBase = 'http://localhost:8080/taches';

  constructor(private httpClient: HttpClient) { }

  enregistrerUneModification(
    tacheId: number,
    historiqueTacheRequestDto: HistoriqueTacheRequestDto
  ): Observable<HistoriqueTacheResponseDto> {
    return this.httpClient.post<HistoriqueTacheResponseDto>(
      `${this.urlBase}/${tacheId}/historiques`,
      historiqueTacheRequestDto
    );
  }

  recupererLesHistoriquesDeUneTache(tacheId: number): Observable<HistoriqueTacheResponseDto[]> {
    return this.httpClient.get<HistoriqueTacheResponseDto[]>(
      `${this.urlBase}/${tacheId}/historiques`
    );
  }
}