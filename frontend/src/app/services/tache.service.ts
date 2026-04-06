import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TacheRequestDto, TacheResponseDto } from '../models/tache.model';

@Injectable({
  providedIn: 'root'
})
export class TacheService {

  private readonly urlBase = 'http://localhost:8080/projets';

  constructor(private httpClient: HttpClient) { }

  creerUneTache(projetId: number, membreAssigneId: number, createurId: number, tacheRequestDto: TacheRequestDto): Observable<TacheResponseDto> {
    return this.httpClient.post<TacheResponseDto>(
      `${this.urlBase}/${projetId}/taches?membreAssigneId=${membreAssigneId}&createurId=${createurId}`,
      tacheRequestDto
    );
  }

  recupererLesTachesDeUnProjet(projetId: number): Observable<TacheResponseDto[]> {
    return this.httpClient.get<TacheResponseDto[]>(
      `${this.urlBase}/${projetId}/taches`
    );
  }

  recupererUneTache(projetId: number, tacheId: number): Observable<TacheResponseDto> {
    return this.httpClient.get<TacheResponseDto>(
      `${this.urlBase}/${projetId}/taches/${tacheId}`
    );
  }

  mettreAJourUneTache(
    projetId: number,
    tacheId: number,
    tacheRequestDto: TacheRequestDto
  ): Observable<TacheResponseDto> {
    return this.httpClient.put<TacheResponseDto>(
      `${this.urlBase}/${projetId}/taches/${tacheId}`,
      tacheRequestDto
    );
  }
}