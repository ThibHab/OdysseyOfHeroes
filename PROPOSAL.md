- [Elements principaux :](#elements-principaux-)
  - [Carte](#carte)
  - [2 joueurs :](#2-joueurs-)
  - [2 types d’ennemis :](#2-types-dennemis-)
- [Déroulement du jeu :](#déroulement-du-jeu-)
- [Phase de jeu classique: jeu d’aventure](#phase-de-jeu-classique-jeu-daventure)
  - [Objectif](#objectif)
  - [Jeu](#jeu)
  - [## Originalité](#originalité)
- [Mondes Intérieurs : Énigmes et coopération](#mondes-intérieurs--énigmes-et-coopération)
  - [## Labyrinthe](#labyrinthe)
  - [## (OPTIONNEL) Donjon1 : jeu de caméra](#optionnel-donjon1--jeu-de-caméra)
  - [## (OPTIONNEL) Marchand ou Magasin](#optionnel-marchand-ou-magasin)
  - [## Jeu et Originalité](#jeu-et-originalité)
- [Salle et Combat de boss](#salle-et-combat-de-boss)
  - [Objectif](#objectif-1)
  - [Jeu et Originalité](#jeu-et-originalité-1)
- [Extensions](#extensions)

# Elements principaux :
=====================

  
- [ ] check: Jeu coopératif sur une planète torique avec une vue du dessus
- [ ] 2D vue du dessus avec un seul écran pour les 2 joueurs.

## Carte
-----

La carte est composée des zones suivantes :

- [ ]  un village dans lequel les joueurs débutent   
- [ ]  une forêt bloquée au début (accès détaillé plus loin dans le contrat)
    
Le reste de la carte s’étend sur une prairie dans laquelle se trouve 
- [ ] le labyrinthe 
- [ ] le donjon du boss final (qui sont des mondes fermés, détaillés plus bas).
    

Des ennemis apparaissent dans la prairie et la forêt (ainsi que le donjon et le labyrinthe évidemment)

## 2 joueurs

- [ ] un guerrier, arme = 
  - [ ] épée, enlève des points de vie à l’entité en face lors de l’appui sur une touche (touche à définir)
    
- [ ]  un mage, armes = 
  - [ ] boule de magie = projectile infligeant des dégâts lorsqu’il touche un ennemi (portée maximale à définir)  
  - [ ] boule de feu : même chose que boule de magie mais 
  - [ ] peut mettre feu à des torches lorsqu’elles sont touchées)
    

  
## 2 types d’ennemis

- [ ]  Un en mélée (=corps à corps) : peut infliger des dégâts à l’entité située devant lui.
    
- [ ]  Un à distance : lance des projectiles infligeant des dégats lorsqu’ils touchent une entité (sur une certaine portée à définir)
    

## Les PNJ
- [ ] Mineur
- [ ] Hermite

# Déroulement du jeu 

## Début du jeu dans le village, 
- [ ] on parle à un pnj mineur qui a une bombe *(séquence avec phylactère  :speech_balloon:)*
- `HORS CONTRAT` Il la vend uniquement aux joueurs expérimentés (niveau 5 par ex).  
 
## Tuer des monstres 
- [ ] fait gagner de l’expérience (ils peuvent également donner un peu d’argent). 
- [ ] L’expérience est partagée entre les joueurs.

  
## Visiter le labyrinthe 
- [ ] pour gagner de l’argent BEAUCOUP plus vite
- `HORS CONTRAT` nécessite un peu d’argent pour rentrer (on peut donc potentiellement être perdant)
    

## Couper des buissons
- [ ] situés un peu partout dans la map 
- [ ] peuvent alors générer un peu d’argent 
- [ ] quelques fois faire apparaître un ennemi.

  
## `HORS CONTRAT` Recommandé :  
Refaire plusieurs fois le labyrinthe pour gagner de l’argent et améliorer ses armes -> un autre pnj (forgeron) permet d’améliorer les armes (plus de dégats infligés, et contre de l’argent


## Achèter la bombe au pnj mineur

- [ ] nécessite argent pour acheter la bombe

* `HORS CONTRAT` nécessite lvl 5
* `HORS CONTRAT` Le pnj ne vends plus une bombe, mais une clé pour entrer dans le  
donjon. Puis aller dans le donjon (avec le jeu de caméra détaillé plus bas)

- [ ] nécessite une clé pour entrer dans le donjon : la porte s’ouvre quand la clé est utilisée
- [ ] `AJOUT` :exclamation: la clef est cachée dans le décor   

 
## Fôret avec un pnj hermite

- [ ] visualisation des personnages dans la forêt
    `À PRÉCISER`

- [ ] besoin de bombe pour entrer :question:_[MP> dans une forêt?]_
- [ ] :question:_[MP>il faut / on peut]_ exploser le rocher à l’entrée pour entrer )
    
- [ ] l’hermite apprend aux joueurs le pouvoir du feu *(séquence avec philactère  :speech_balloon:)*

- [ ] les boules de magie du mage seront maintenant des boules de feu

- [ ] le guerrier est maintenant capable d’éclairer autour de lui lorsqu’il est plongé dans le noir  
    
- `HORS CONTRAT` le pnj hermite parle de la salle du boss et prévient du danger
    

## Salle du boss

#### travail graphique
- [ ] Joueurs dans le noir
- [ ] le guerrier éclaire légèrement autour de lui
- [ ] le mage ne se voit plus du tout s’il s’éloigne du guerrier.
    
- [ ] les 2 joueurs doivent avancer ensemble pour que le mage lance des boules de feu sur des flambeaux
    `PAS CLAIR, À PRÉCISER`
`

- [ ] Une fois tous les flambeaux allumés par le mage, le dragon (boss final) se réveille : combat final.
    

* `HORS CONTRAT` Une fois rentrés, les joueurs ne sortent plus de la salle sans avoir tué le boss.
    

# Phase de jeu classique: jeu d’aventure

## Objectif

*   Déplacements dans un monde torique, débloquer les accès à des salles ou des  
    zones au fur et à mesure de l’aventure (détaillé plus bas)
    
*   Trouver le boss final puis le tuer
    

## Jeu

###  Accomplir des quêtes données par des PNJ
- [ ] :exclamation: `AJOUT` le phylactere est une action de l'automate du PNJ.
    Chaque POP déclenche sa prochaine phrase.
- [ ] “Trouver l'ermite dans la forêt”
- [ ] “Entrer dans le labyrinthe”
- [ ] “Couper 20 buissons” 
→ donnent des récompenses.
    
### Combattre des ennemis dans la map principale , ainsi que dans le labyrinthe

- [ ] peuvent donner des pièces lorsqu’on les tue 
- [ ] implémenter via l'action egg de l’automate

### Débloquer des objets
    
- [ ] une bombe
- [ ] utilisable à l’infini (Quelle action? quelle Touche?)
* à partir du moment où elle est débloquée mais qui ne peut détruire que certaines pierres pour accéder à la zone de la forêt en début de jeu.
        
- [ ] une torche
- [ ] débloquée après avoir parlé à l’ermite dans la forêt, mais qui n’est pas un objet manipulable ni visible, elle donne simplement un effet de lumière qui éclaire le guerrier, lui permettant de voir dans une petite zone autour de lui lorqsu’il est dans le noir.
- [ ] :exclamation: `AJOUT`s'active/disparaît automatiquement (si une pièce sombre ou la nuit)
        
* `DÉJÀ DIT`  L’expérience est partagée entre les joueurs. Cela signifie donc que les deux joueurs ont tout le temps le même niveau et que lorsqu’ils gagnent de l’expérience, ils en gagnent la même quantité. Ce système fonctionne comme s’il n’y avait qu’un seul joueur, il permet de ne pas déséquilibrer le jeu entre les deux joueurs.
    
### Débloquer des améliorations des armes

à chaque niveau gagné (par les deux joueurs puisque les niveaux sont communs)
* qui augmentent les dégâts des joueurs, 
- [ ] augmente (plus rarement) la portée du mage 
* (tous les X niveaux sa portée augmente afin de ne pas trop l'avantager non plus).
    


## Originalité

### Jeu 100% collaboratif
les joueurs ne peuvent pas avancer sans s’entraider, il y a besoin  
des deux joueurs pour combattre les ennemis, pour résoudre les énigmes (détaillé plus bas),  
et être 2 permet de récupérer plus d’argent dans le labyrinthe.

  
### Map semi-aléatoire
certains éléments de la map sont générés aléatoirement par un  
algorithme: 
- [ ] le fond de map est fixe
- [ ] ainsi que l’emplacement des donjons et du village. 
- [ ] Les  villageois sont placés manuellement dans le village (PAR QUI :question:)

- [ ] Les ennemis sont placés aléatoirement sur la carte.
- [ ] Les arbres (beaucoup  dans la forêt, moins dans la plaine) et les buissons sont placés aléatoirement.
- [ ] génération aléatoire de zones de forêts 

  
### Travail graphique 
- [ ] Transparence des éléments du décor lorsque le joueur passe derrière 
  - [ ] arbres
  - [ ] maisons

- [ ] Possibilité de sauvegarder les éléments importants de la partie en cours 
   - [ ] le niveau des joueurs, 
   - [ ] leurs items, 
   - [ ] les zones débloquées.

- [ ] :exclamation: `À PRÉCISER` Combien de sauvegardes possibles ?
- [ ] :exclamation: `À PRÉCISER` Comment restaurer une sauvegarde ?
  

### Système de zoom qui s’adapte à la distance séparant les joueurs. 

- [ ] La caméra dézoom **AUTOMATIQUEMENT* en laissant une zone tampon pour que les joueurs voient ce qui les attend. 
- [ ] jusqu'à  dézoom  maximal 
* la zone tampon permet aux joueurs de se rendre compte qu’il vont etre bloqués s´ils continuent de s’éloigner l’un de l’autre. 
- [ ] La caméra suit (=?EST PLACÉE AU?) le centre des deux joueurs.


# Mondes Intérieurs : Énigmes et coopération

## Labyrinthe

- [ ] Gagner des pièces en récupérant des coffres dans un labyrinthe complexe 
- [ ] le tout en temps limité
- [ ] Labyritnhe généré aléatoirement peuplé d’ennemis en mêlée 
    

## `HORS CONTRAT` Donjon1 : jeu de caméra

*   Salle avec une caméra sans zoom se déplaçant selon le centre des deux joueurs
    
*   En entrant dans la salle : les joueurs sont chacun d’un côté de la salle (séparés par un mur), voient chacun de leur côté une case rouge sur laquelle ils doivent aller, la case devient alors verte.
    
*   Il ne se passe rien : il faut faire en sorte de déplacer la caméra pour découvrir une dernière case rouge : lorsqu’elle devient verte, un coffre s’ouvre offrant aux joueurs une bombe.
    

## `HORS CONTRAT` Marchand ou Magasin

*   PNJ marchand qui propose des potions de vie ou d'attaque et des améliorations de leurs armes en échange de pièces  
    
OU BIEN
    
*   Magasin dans lequel les joueurs peuvent acheter la même chose que ci-dessus
    

## Jeu et Originalité

*   Jeu de caméra pour résoudre une énigme.
    
* Labyrinthe généré aléatoirement.
    

# Salle et Combat de boss

Le donjon du boss final contient une seule grande pièce avec le dragon endormi au centre  
de la salle et des flambeaux sur les côtés.

## Objectif

*   Salle plongée dans le noir, les joueurs doivent allumer les flambeaux en coopérant. 
- [ ] Le guerrier doit éclairer l’autre joueur pour qu’ils se guident 
- [ ] et que le mage puisse allumer au fur et à mesure les flambeaux en se déplaçant dans le donjon
- [ ] tout cela afin de réveiller le dragon endormi.
    
*   Le boss se réveille 
- [ ] il a un comportement différent *(plusieurs automates)* 
   selon la phase dans laquelle il se trouve (selon ses points de vie), il devient plus agressif lorsqu’il perd des points de  
    vie.
    

## Jeu et Originalité

*   Jeu en coopération avec la visibilité des joueurs (lumière) afin de réveiller le boss.
    
*   Changement d'automate selon la phase actuelle du boss.
    

# `HORS CONTRAT` Extensions

* Minimap : Plan de la map entière dans un coin de l’écran sur laquelle les positions des 2 joueurs sont indiquées
    
*  Plusieurs classes : les joueurs peuvent choisir leur personnage en début de partie, chaque personnage ayant des caractéristiques différentes.
