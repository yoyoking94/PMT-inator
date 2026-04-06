import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UtilisateurRequestDto, UtilisateurResponseDto } from '../models/utilisateur.model';

@Injectable({
  providedIn: 'root'
})
export class UtilisateurService {

  private readonly urlBaseAuth = 'http://localhost:8080/auth';
  private readonly urlBase = 'http://localhost:8080/utilisateurs';

  constructor(private httpClient: HttpClient) { }

  inscrireUnUtilisateur(utilisateurRequestDto: UtilisateurRequestDto): Observable<UtilisateurResponseDto> {
    return this.httpClient.post<UtilisateurResponseDto>(
      `${this.urlBaseAuth}/inscription`,
      utilisateurRequestDto
    );
  }

  connecterUnUtilisateur(email: string, motDePasse: string): Observable<UtilisateurResponseDto> {
    const donnees: UtilisateurRequestDto = {
      username: '',
      email: email,
      password: motDePasse
    };
    return this.httpClient.post<UtilisateurResponseDto>(
      `${this.urlBaseAuth}/connexion`,
      donnees
    );
  }

  recupererTousLesUtilisateurs(): Observable<UtilisateurResponseDto[]> {
    return this.httpClient.get<UtilisateurResponseDto[]>(this.urlBase);
  }

  recupererUnUtilisateur(identifiant: number): Observable<UtilisateurResponseDto> {
    return this.httpClient.get<UtilisateurResponseDto>(`${this.urlBase}/${identifiant}`);
  }
}