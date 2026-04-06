export interface UtilisateurResponseDto {
    id: number;
    username: string;
    email: string;
    dateDeCreation: string;
}

export interface UtilisateurRequestDto {
    username: string;
    email: string;
    password: string;
}