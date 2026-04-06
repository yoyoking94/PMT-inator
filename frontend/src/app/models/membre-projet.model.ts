import { ProjetResponseDto } from './projet.model';
import { UtilisateurResponseDto } from './utilisateur.model';

export type Role = 'ADMINISTRATEUR' | 'MEMBRE' | 'OBSERVATEUR';

export interface MembreProjetResponseDto {
    id: number;
    utilisateur: UtilisateurResponseDto;
    projet: ProjetResponseDto;
    role: Role;
    dateDeCreation: string;
}

export interface MembreProjetRequestDto {
    email: string;
    role: Role;
}