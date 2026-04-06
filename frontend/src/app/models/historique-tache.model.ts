import { TacheResponseDto } from './tache.model';
import { UtilisateurResponseDto } from './utilisateur.model';

export interface HistoriqueTacheResponseDto {
    id: number;
    tache: TacheResponseDto;
    modifiePar: UtilisateurResponseDto;
    champModifie: string;
    ancienneValeur: string;
    nouvelleValeur: string;
    dateDeModification: string;
}

export interface HistoriqueTacheRequestDto {
    tacheId: number;
    modifieParId: number;
    champModifie: string;
    ancienneValeur: string;
    nouvelleValeur: string;
}