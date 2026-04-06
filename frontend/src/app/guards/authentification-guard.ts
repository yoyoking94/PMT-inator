import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthentificationService } from '../services/authentification.service';

export const authentificationGuard: CanActivateFn = () => {
  const serviceAuthentification = inject(AuthentificationService);
  const routeur = inject(Router);

  if (serviceAuthentification.estConnecte()) {
    return true;
  }

  // Redirige vers la page de connexion si l'utilisateur n'est pas connecté
  return routeur.createUrlTree(['/connexion']);
};