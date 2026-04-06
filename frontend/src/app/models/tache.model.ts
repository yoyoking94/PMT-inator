import { ProjetResponseDto } from './projet.model';
import { UtilisateurResponseDto } from './utilisateur.model';

export type Priorite = 'BASSE' | 'MOYENNE' | 'HAUTE';

export interface TacheResponseDto {
    id: number;
    nom: string;
    description: string;
    dateEcheant: string;
    priorite: Priorite;
    dateDeFin: string | null;
    dateDeCreation: string;
    projet: ProjetResponseDto;
    membreAssigne: UtilisateurResponseDto;
}

export interface TacheRequestDto {
    nom: string;
    description: string;
    dateEcheant: string;
    priorite: Priorite;
    modificateurId?: number;
}