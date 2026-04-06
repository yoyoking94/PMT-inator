import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProjetRequestDto, ProjetResponseDto } from '../models/projet.model';

@Injectable({
  providedIn: 'root'
})
export class ProjetService {

  private readonly urlBase = 'http://localhost:8080/projets';

  constructor(private httpClient: HttpClient) { }

  creerUnProjet(projetRequestDto: ProjetRequestDto, administrateurId: number): Observable<ProjetResponseDto> {
    return this.httpClient.post<ProjetResponseDto>(
      `${this.urlBase}?administrateurId=${administrateurId}`,
      projetRequestDto
    );
  }

  recupererTousLesProjets(): Observable<ProjetResponseDto[]> {
    return this.httpClient.get<ProjetResponseDto[]>(this.urlBase);
  }

  recupererUnProjet(projetId: number): Observable<ProjetResponseDto> {
    return this.httpClient.get<ProjetResponseDto>(`${this.urlBase}/${projetId}`);
  }

  recupererLesProjetsDeUnUtilisateur(administrateurId: number): Observable<ProjetResponseDto[]> {
    return this.httpClient.get<ProjetResponseDto[]>(
      `${this.urlBase}/utilisateur/${administrateurId}`
    );
  }

  mettreAJourUnProjet(projetId: number, projetRequestDto: ProjetRequestDto): Observable<ProjetResponseDto> {
    return this.httpClient.put<ProjetResponseDto>(
      `${this.urlBase}/${projetId}`,
      projetRequestDto
    );
  }
}