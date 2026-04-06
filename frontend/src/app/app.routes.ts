import { Routes } from '@angular/router';
import { authentificationGuard } from './guards/authentification-guard';
import { Connexion } from './pages/auth/connexion/connexion';
import { DetailProjet } from './pages/projet/detail-projet/detail-projet';
import { DetailTache } from './pages/taches/detail-tache/detail-tache';
import { FormulaireTache } from './pages/taches/formulaire-tache/formulaire-tache';
import { Inscription } from './pages/auth/inscription/inscription';
import { ListeProjets } from './pages/projet/liste-projets/liste-projets';
import { ListeTaches } from './pages/taches/liste-taches/liste-taches';

export const routes: Routes = [
    { path: '', redirectTo: 'connexion', pathMatch: 'full' },

    // Routes publiques — accessibles sans être connecté
    { path: 'inscription', component: Inscription },
    { path: 'connexion', component: Connexion },

    // Routes protégées — l'utilisateur doit être connecté
    {
        path: 'projets',
        canActivate: [authentificationGuard],
        component: ListeProjets
    },
    {
        path: 'projets/:projetId',
        canActivate: [authentificationGuard],
        component: DetailProjet
    },
    {
        path: 'projets/:projetId/taches',
        canActivate: [authentificationGuard],
        component: ListeTaches
    },
    {
        path: 'projets/:projetId/taches/nouvelle',
        canActivate: [authentificationGuard],
        component: FormulaireTache
    },
    {
        path: 'projets/:projetId/taches/:tacheId',
        canActivate: [authentificationGuard],
        component: DetailTache
    },
    {
        path: 'projets/:projetId/taches/:tacheId/modifier',
        canActivate: [authentificationGuard],
        component: FormulaireTache
    },

    // Toute URL inconnue redirige vers connexion
    { path: '**', redirectTo: 'connexion' }
];
