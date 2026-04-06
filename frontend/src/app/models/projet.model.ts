import { UtilisateurResponseDto } from './utilisateur.model';

export interface ProjetResponseDto {
    id: number;
    nom: string;
    description: string;
    dateDeDebut: string;
    dateDeCreation: string;
    administrateur: UtilisateurResponseDto;
}

export interface ProjetRequestDto {
    nom: string;
    description: string;
    dateDeDebut: string;
}