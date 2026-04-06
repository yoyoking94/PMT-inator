INSERT IGNORE INTO utilisateur (username, email, password, date_de_creation) VALUES
('optimus_prime', 'optimus.prime@autobot.com', 'matrix123', NOW()),
('bumblebee', 'bumblebee@autobot.com', 'camaro2026', NOW()),
('ironhide', 'ironhide@autobot.com', 'weapon4ever', NOW()),
('ratchet', 'ratchet@autobot.com', 'medic1234', NOW()),
('jazz', 'jazz@autobot.com', 'groove99', NOW()),
('wheeljack', 'wheeljack@autobot.com', 'inventor42', NOW()),
('perceptor', 'perceptor@autobot.com', 'science77', NOW()),
('prowl', 'prowl@autobot.com', 'logic2026', NOW()),
('sideswipe', 'sideswipe@autobot.com', 'speed456', NOW()),
('sunstreaker', 'sunstreaker@autobot.com', 'gold789', NOW()),
('grimlock', 'grimlock@autobot.com', 'dinobot1', NOW()),
('slag', 'slag@autobot.com', 'dinofire2', NOW()),
('sludge', 'sludge@autobot.com', 'dino3333', NOW()),
('swoop', 'swoop@autobot.com', 'dinofly4', NOW()),
('blaster', 'blaster@autobot.com', 'music2026', NOW()),
('megatron', 'megatron@decepticon.com', 'conquer99', NOW()),
('starscream', 'starscream@decepticon.com', 'betray2026', NOW()),
('soundwave', 'soundwave@decepticon.com', 'superior1', NOW()),
('shockwave', 'shockwave@decepticon.com', 'logic666', NOW()),
('thundercracker', 'thundercracker@decepticon.com', 'thunder77', NOW()),
('skywarp', 'skywarp@decepticon.com', 'warp2026', NOW()),
('ravage', 'ravage@decepticon.com', 'cassette1', NOW()),
('laserbeak', 'laserbeak@decepticon.com', 'spy1234', NOW()),
('rumble', 'rumble@decepticon.com', 'rumble55', NOW()),
('frenzy', 'frenzy@decepticon.com', 'chaos999', NOW()),
('devastator', 'devastator@decepticon.com', 'combine1', NOW()),
('constructicon', 'constructicon@decepticon.com', 'build2026', NOW()),
('galvatron', 'galvatron@decepticon.com', 'power999', NOW()),
('unicron', 'unicron@chaos.com', 'devour2026', NOW()),
('rodimus_prime', 'rodimus.prime@autobot.com', 'hotrod77', NOW());


INSERT IGNORE INTO projets (nom, description, date_de_debut, date_de_creation, administrateur_id) VALUES
('Operation Cybertron', 'Reconquête de la planète Cybertron', '2026-01-01', NOW(), 1),
('Project Allspark', 'Localisation et sécurisation de l''Allspark', '2026-01-05', NOW(), 1),
('Shield Defense', 'Mise en place d''un bouclier énergétique autour de la Terre', '2026-01-10', NOW(), 2),
('Energon Hunt', 'Recherche de nouvelles sources d''energon', '2026-01-12', NOW(), 3),
('Ark Restoration', 'Restauration du vaisseau Ark', '2026-01-15', NOW(), 4),
('Autobot Academy', 'Formation des nouvelles recrues Autobots', '2026-01-18', NOW(), 5),
('Dinobot Training', 'Programme d''entraînement des Dinobots', '2026-01-20', NOW(), 11),
('Earth Alliance', 'Alliance avec les forces militaires terrestres', '2026-01-22', NOW(), 8),
('Space Bridge Repair', 'Réparation du pont spatial inter-galactique', '2026-01-25', NOW(), 6),
('Matrix Quest', 'Quête de la Matrice du Leadership', '2026-01-28', NOW(), 1),
('Decepticon Surveillance', 'Surveillance des mouvements Decepticons', '2026-02-01', NOW(), 9),
('Omega Supreme Defense', 'Activation du système de défense Omega Supreme', '2026-02-03', NOW(), 7),
('Iacon Rebuild', 'Reconstruction de la cité d''Iacon', '2026-02-05', NOW(), 4),
('Energon Stockpile', 'Constitution de réserves d''energon pour l''hiver', '2026-02-08', NOW(), 15),
('Signal Intercept', 'Interception des communications Decepticons', '2026-02-10', NOW(), 14),
('Earth Conquest', 'Plan de conquête de la planète Terre', '2026-02-01', NOW(), 16),
('Energon Extraction', 'Extraction massive d''energon des ressources terrestres', '2026-02-05', NOW(), 16),
('Space Bridge Control', 'Prise de contrôle du pont spatial', '2026-02-08', NOW(), 18),
('Spy Network', 'Mise en place d''un réseau d''espionnage mondial', '2026-02-10', NOW(), 22),
('Dark Matter Weapon', 'Développement d''une arme à matière noire', '2026-02-12', NOW(), 17),
('Autobot Elimination', 'Plan d''élimination des Autobots', '2026-02-15', NOW(), 19),
('Cybertron Domination', 'Domination totale de Cybertron', '2026-02-18', NOW(), 28),
('Unicron Protocol', 'Activation du protocole Unicron', '2026-02-20', NOW(), 29),
('Combiner Project', 'Fusion des Constructicons en Devastator', '2026-02-22', NOW(), 26),
('Mind Control Device', 'Création d''un dispositif de contrôle mental', '2026-02-25', NOW(), 18),
('Galactic Storm', 'Tempête galactique pour affaiblir les Autobots', '2026-03-01', NOW(), 28),
('New Cybertron', 'Construction d''un nouveau Cybertron artificiel', '2026-03-05', NOW(), 16),
('Chaos Bringer', 'Réveil du dévoreur de mondes Unicron', '2026-03-08', NOW(), 29),
('Hot Rod Initiative', 'Programme de développement de la nouvelle génération', '2026-03-10', NOW(), 30),
('Peace Treaty', 'Négociation d''un traité de paix entre Autobots et Decepticons', '2026-03-15', NOW(), 1);


INSERT IGNORE INTO membre_projet (utilisateur_id, projet_id, role, date_de_creation) VALUES
-- Projet 1 : Operation Cybertron (admin = 1)
(1, 1, 'ADMINISTRATEUR', NOW()),
(2, 1, 'MEMBRE', NOW()),
(3, 1, 'MEMBRE', NOW()),
(4, 1, 'OBSERVATEUR', NOW()),

-- Projet 2 : Project Allspark (admin = 1)
(1, 2, 'ADMINISTRATEUR', NOW()),
(5, 2, 'MEMBRE', NOW()),
(6, 2, 'MEMBRE', NOW()),
(7, 2, 'OBSERVATEUR', NOW()),

-- Projet 3 : Shield Defense (admin = 2)
(2, 3, 'ADMINISTRATEUR', NOW()),
(8, 3, 'MEMBRE', NOW()),
(9, 3, 'MEMBRE', NOW()),
(10, 3, 'OBSERVATEUR', NOW()),

-- Projet 4 : Energon Hunt (admin = 3)
(3, 4, 'ADMINISTRATEUR', NOW()),
(11, 4, 'MEMBRE', NOW()),
(12, 4, 'MEMBRE', NOW()),

-- Projet 5 : Ark Restoration (admin = 4)
(4, 5, 'ADMINISTRATEUR', NOW()),
(13, 5, 'MEMBRE', NOW()),
(14, 5, 'MEMBRE', NOW()),
(15, 5, 'OBSERVATEUR', NOW()),

-- Projet 6 : Autobot Academy (admin = 5)
(5, 6, 'ADMINISTRATEUR', NOW()),
(2, 6, 'MEMBRE', NOW()),
(3, 6, 'OBSERVATEUR', NOW()),

-- Projet 7 : Dinobot Training (admin = 11)
(11, 7, 'ADMINISTRATEUR', NOW()),
(12, 7, 'MEMBRE', NOW()),
(13, 7, 'OBSERVATEUR', NOW()),

-- Projet 8 : Earth Alliance (admin = 8)
(8, 8, 'ADMINISTRATEUR', NOW()),
(9, 8, 'MEMBRE', NOW()),
(10, 8, 'OBSERVATEUR', NOW()),

-- Projet 9 : Space Bridge Repair (admin = 6)
(6, 9, 'ADMINISTRATEUR', NOW()),
(7, 9, 'MEMBRE', NOW()),
(8, 9, 'OBSERVATEUR', NOW()),

-- Projet 10 : Matrix Quest (admin = 1)
(1, 10, 'ADMINISTRATEUR', NOW()),
(2, 10, 'MEMBRE', NOW()),
(3, 10, 'OBSERVATEUR', NOW()),

-- Projet 11 : Decepticon Surveillance (admin = 9)
(9, 11, 'ADMINISTRATEUR', NOW()),
(10, 11, 'MEMBRE', NOW()),

-- Projet 12 : Omega Supreme Defense (admin = 7)
(7, 12, 'ADMINISTRATEUR', NOW()),
(8, 12, 'MEMBRE', NOW()),

-- Projet 13 : Iacon Rebuild (admin = 4)
(4, 13, 'ADMINISTRATEUR', NOW()),
(5, 13, 'MEMBRE', NOW()),

-- Projet 14 : Energon Stockpile (admin = 15)
(15, 14, 'ADMINISTRATEUR', NOW()),
(14, 14, 'MEMBRE', NOW()),

-- Projet 15 : Signal Intercept (admin = 14)
(14, 15, 'ADMINISTRATEUR', NOW()),
(15, 15, 'MEMBRE', NOW()),

-- Projet 16 : Earth Conquest (admin = 16)
(16, 16, 'ADMINISTRATEUR', NOW()),
(17, 16, 'MEMBRE', NOW()),
(18, 16, 'OBSERVATEUR', NOW()),

-- Projet 17 : Energon Extraction (admin = 16)
(16, 17, 'ADMINISTRATEUR', NOW()),
(19, 17, 'MEMBRE', NOW()),
(20, 17, 'OBSERVATEUR', NOW()),

-- Projet 18 : Space Bridge Control (admin = 18)
(18, 18, 'ADMINISTRATEUR', NOW()),
(20, 18, 'MEMBRE', NOW()),
(21, 18, 'OBSERVATEUR', NOW()),

-- Projet 19 : Spy Network (admin = 22)
(22, 19, 'ADMINISTRATEUR', NOW()),
(23, 19, 'MEMBRE', NOW()),
(24, 19, 'OBSERVATEUR', NOW()),

-- Projet 20 : Dark Matter Weapon (admin = 17)
(17, 20, 'ADMINISTRATEUR', NOW()),
(24, 20, 'MEMBRE', NOW()),
(25, 20, 'OBSERVATEUR', NOW()),

-- Projet 21 : Autobot Elimination (admin = 19)
(19, 21, 'ADMINISTRATEUR', NOW()),
(16, 21, 'MEMBRE', NOW()),

-- Projet 22 : Cybertron Domination (admin = 28)
(28, 22, 'ADMINISTRATEUR', NOW()),
(29, 22, 'MEMBRE', NOW()),

-- Projet 23 : Unicron Protocol (admin = 29)
(29, 23, 'ADMINISTRATEUR', NOW()),
(28, 23, 'MEMBRE', NOW()),

-- Projet 24 : Combiner Project (admin = 26)
(26, 24, 'ADMINISTRATEUR', NOW()),
(27, 24, 'MEMBRE', NOW()),

-- Projet 25 : Mind Control Device (admin = 18)
(18, 25, 'ADMINISTRATEUR', NOW()),
(19, 25, 'MEMBRE', NOW()),

-- Projet 26 : Galactic Storm (admin = 28)
(28, 26, 'ADMINISTRATEUR', NOW()),
(29, 26, 'MEMBRE', NOW()),

-- Projet 27 : New Cybertron (admin = 16)
(16, 27, 'ADMINISTRATEUR', NOW()),
(17, 27, 'MEMBRE', NOW()),

-- Projet 28 : Chaos Bringer (admin = 29)
(29, 28, 'ADMINISTRATEUR', NOW()),
(28, 28, 'MEMBRE', NOW()),

-- Projet 29 : Hot Rod Initiative (admin = 30)
(30, 29, 'ADMINISTRATEUR', NOW()),
(1, 29, 'MEMBRE', NOW()),

-- Projet 30 : Peace Treaty (admin = 1)
(1, 30, 'ADMINISTRATEUR', NOW()),
(30, 30, 'MEMBRE', NOW());


INSERT IGNORE INTO taches (nom, description, date_echeant, priorite, date_de_fin, date_de_creation, projet_id, membre_assigne_id) VALUES
('Cartographier Cybertron', 'Établir une carte complète de Cybertron', '2026-02-01', 'HAUTE', NULL, NOW(), 1, 2),
('Sécuriser l''Allspark', 'Construire un conteneur sécurisé pour l''Allspark', '2026-02-05', 'HAUTE', '2026-02-04', NOW(), 2, 5),
('Installer le bouclier', 'Déployer les générateurs du bouclier terrestre', '2026-02-10', 'HAUTE', NULL, NOW(), 3, 8),
('Scanner les sources energon', 'Scanner la surface terrestre pour trouver de l''energon', '2026-02-12', 'MOYENNE', NULL, NOW(), 4, 11),
('Réparer le réacteur Ark', 'Remplacer les composants défaillants du réacteur', '2026-02-15', 'HAUTE', '2026-02-14', NOW(), 5, 4),
('Former les recrues', 'Session de formation au combat rapproché', '2026-02-18', 'MOYENNE', NULL, NOW(), 6, 5),
('Entraîner Grimlock', 'Programme d''entraînement intensif pour Grimlock', '2026-02-20', 'MOYENNE', NULL, NOW(), 7, 11),
('Contacter l''armée terrestre', 'Établir un canal de communication sécurisé', '2026-02-22', 'BASSE', '2026-02-20', NOW(), 8, 8),
('Calibrer le pont spatial', 'Recalibrer les fréquences du pont spatial', '2026-02-25', 'HAUTE', NULL, NOW(), 9, 6),
('Localiser la Matrice', 'Analyser les anciennes cartes de Cybertron', '2026-02-28', 'HAUTE', NULL, NOW(), 10, 1),
('Surveiller Megatron', 'Suivre les déplacements de Megatron', '2026-03-01', 'HAUTE', NULL, NOW(), 11, 9),
('Activer Omega Supreme', 'Lancer la séquence d''activation d''Omega Supreme', '2026-03-03', 'HAUTE', NULL, NOW(), 12, 7),
('Reconstruire Iacon', 'Poser les fondations de la nouvelle cité d''Iacon', '2026-03-05', 'MOYENNE', NULL, NOW(), 13, 4),
('Stocker l''energon', 'Transférer les cubes d''energon dans les entrepôts', '2026-03-08', 'BASSE', NULL, NOW(), 14, 15),
('Décrypter les signaux', 'Analyser les transmissions Decepticons interceptées', '2026-03-10', 'HAUTE', NULL, NOW(), 15, 14),
('Envahir une ville', 'Planifier l''invasion de la première ville terrestre', '2026-03-01', 'HAUTE', NULL, NOW(), 16, 17),
('Extraire l''energon', 'Lancer les foreuses géantes sur les gisements', '2026-03-05', 'HAUTE', '2026-03-04', NOW(), 17, 19),
('Pirater le pont spatial', 'Prendre le contrôle du système informatique', '2026-03-08', 'HAUTE', NULL, NOW(), 18, 20),
('Déployer les espions', 'Infiltrer les bases Autobots', '2026-03-10', 'MOYENNE', NULL, NOW(), 19, 22),
('Construire l''arme', 'Assembler les composants de l''arme à matière noire', '2026-03-12', 'HAUTE', NULL, NOW(), 20, 24),
('Localiser les Autobots', 'Identifier toutes les bases Autobots sur Terre', '2026-03-15', 'HAUTE', NULL, NOW(), 21, 16),
('Dominer Cybertron', 'Lancer l''offensive finale sur Cybertron', '2026-03-18', 'HAUTE', NULL, NOW(), 22, 28),
('Activer Unicron', 'Initialiser la séquence de réveil d''Unicron', '2026-03-20', 'HAUTE', NULL, NOW(), 23, 29),
('Fusionner les Constructicons', 'Synchroniser les matrices de fusion', '2026-03-22', 'MOYENNE', NULL, NOW(), 24, 26),
('Tester le dispositif', 'Premier test du dispositif de contrôle mental', '2026-03-25', 'HAUTE', NULL, NOW(), 25, 18),
('Déclencher la tempête', 'Activer les générateurs de tempête galactique', '2026-04-01', 'HAUTE', NULL, NOW(), 26, 28),
('Poser les fondations', 'Commencer la construction du nouveau Cybertron', '2026-04-05', 'MOYENNE', NULL, NOW(), 27, 16),
('Réveiller Unicron', 'Lancer le rituel de réveil du Chaos Bringer', '2026-04-08', 'HAUTE', NULL, NOW(), 28, 29),
('Recruter Hot Rod', 'Intégrer Hot Rod dans la nouvelle génération', '2026-04-10', 'BASSE', NULL, NOW(), 29, 30),
('Organiser les négociations', 'Préparer le lieu de la réunion de paix', '2026-04-15', 'MOYENNE', NULL, NOW(), 30, 1);


INSERT IGNORE INTO historique_tache (champ_modifie, ancienne_valeur, nouvelle_valeur, date_de_modification, taches_id, modifie_par) VALUES
('priorite', 'MOYENNE', 'HAUTE', NOW(), 1, 1),
('description', 'Carte partielle de Cybertron', 'Carte complète de Cybertron', NOW(), 1, 2),
('date_echeant', '2026-02-10', '2026-02-01', NOW(), 1, 1),
('priorite', 'BASSE', 'HAUTE', NOW(), 2, 5),
('date_de_fin', 'NULL', '2026-02-04', NOW(), 2, 5),
('description', 'Conteneur basique', 'Conteneur sécurisé renforcé', NOW(), 3, 8),
('priorite', 'BASSE', 'HAUTE', NOW(), 3, 8),
('date_echeant', '2026-02-20', '2026-02-12', NOW(), 4, 11),
('priorite', 'HAUTE', 'MOYENNE', NOW(), 5, 4),
('date_de_fin', 'NULL', '2026-02-14', NOW(), 5, 4),
('description', 'Formation basique', 'Session de formation au combat rapproché', NOW(), 6, 5),
('priorite', 'BASSE', 'MOYENNE', NOW(), 6, 5),
('nom', 'Entrainer Grimlock', 'Entraîner Grimlock', NOW(), 7, 11),
('priorite', 'HAUTE', 'MOYENNE', NOW(), 7, 11),
('date_de_fin', 'NULL', '2026-02-20', NOW(), 8, 8),
('description', 'Canal basique', 'Canal de communication sécurisé', NOW(), 8, 8),
('priorite', 'MOYENNE', 'HAUTE', NOW(), 9, 6),
('date_echeant', '2026-03-01', '2026-02-25', NOW(), 9, 6),
('priorite', 'MOYENNE', 'HAUTE', NOW(), 10, 1),
('description', 'Recherche basique', 'Analyser les anciennes cartes de Cybertron', NOW(), 10, 1),
('priorite', 'MOYENNE', 'HAUTE', NOW(), 16, 17),
('date_echeant', '2026-03-10', '2026-03-01', NOW(), 16, 16),
('description', 'Extraction simple', 'Lancer les foreuses géantes sur les gisements', NOW(), 17, 19),
('date_de_fin', 'NULL', '2026-03-04', NOW(), 17, 19),
('priorite', 'MOYENNE', 'HAUTE', NOW(), 18, 20),
('description', 'Piratage basique', 'Prendre le contrôle du système informatique', NOW(), 18, 18),
('priorite', 'BASSE', 'MOYENNE', NOW(), 19, 22),
('date_echeant', '2026-03-15', '2026-03-10', NOW(), 19, 22),
('priorite', 'MOYENNE', 'HAUTE', NOW(), 20, 24),
('description', 'Assemblage partiel', 'Assembler les composants de l''arme à matière noire', NOW(), 20, 24);