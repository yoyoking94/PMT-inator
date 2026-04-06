import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MembreProjetRequestDto, MembreProjetResponseDto } from '../models/membre-projet.model';

@Injectable({
  providedIn: 'root'
})
export class MembreProjetService {

  private readonly urlBase = 'http://localhost:8080/projets';

  constructor(private httpClient: HttpClient) { }

  inviterUnMembre(projetId: number, administrateurId: number, membreProjetRequestDto: MembreProjetRequestDto): Observable<MembreProjetResponseDto> {
    return this.httpClient.post<MembreProjetResponseDto>(
      `${this.urlBase}/${projetId}/membres?administrateurId=${administrateurId}`,
      membreProjetRequestDto
    );
  }

  recupererLesMembresDeUnProjet(projetId: number): Observable<MembreProjetResponseDto[]> {
    return this.httpClient.get<MembreProjetResponseDto[]>(
      `${this.urlBase}/${projetId}/membres`
    );
  }
}