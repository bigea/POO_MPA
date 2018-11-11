package data.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.DonneesSimulation;
import data.Incendie;
import data.enumerate.NatureRobot;
import data.enumerate.NatureTerrain;
import events.*;
import gui2.Simulateur;
import strategie.*;
import data.enumerate.Direction;

/**
 * Classe Robot
 */

public abstract class Robot {

    /**
     * Classe Robot
     * 		Avec hérachie (sous-classes)
     */
	private static final long INFINI = 2147483647;

    /* [PHILEMON]
    * Attention, quelques attributs inutiles (genre vitesseVidage)
    * Faudra faire le ménage
    */


	/* Attributs */
	private NatureRobot nature;
	protected Case position;
	protected int capacite; //en litre
	protected double vitesse; //en km/h
	protected long tempsRemplissageComplet; //temps en seconde
	protected long tempsVidageComplet; //temps en seconde
    protected long tempsVidageUnitaire;
    protected int volumeVidageUnitaire;
	protected double vitesseRemplissage; //en l/s
	protected double vitesseVidage; //en l/s
	protected long dateDisponibilite;
    protected int capaciteMaximale;
    protected Chef chef;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	public Robot(Case pos, NatureRobot nature, long dateDisponibilite) {
		this.setPosition(pos);
		this.setDateDisponibilite(dateDisponibilite);
		this.nature = nature;
        this.chef = null;
	}

	/* Affichage */
	public abstract String toString();

    /* égalité de robots */
    @Override
    public boolean equals(Object o) {
        Robot robot = (Robot)o;
        return this.position.equals(robot.getPosition()) && this.nature==robot.getNature() && this.capacite==robot.getCapacite() && this.dateDisponibilite==robot.getDateDisponibilite();
    }

	/* Accesseurs */
	public Case getPosition() {
		return this.position;
	}
	public NatureRobot getNature() {
		return this.nature;
	}

	/* Mutateurs */
	public void setPosition(Case cas) {
		this.position = cas;
	}

	public abstract void setVitesse(double vitesse);
	public double getVitesse(NatureTerrain nt) {
		return this.vitesse;
	}

	public void setCapacite(int capacite){
		this.capacite = capacite;
	}
	public int getCapacite(){
		return this.capacite;
	}

    public int getCapaciteMaximale() {
        return this.capaciteMaximale;
    }
    public void setCapaciteMaximale(int capaciteMaximale) {
        this.capaciteMaximale = capaciteMaximale;
    }

	public long getTempsRemplissageComplet() {
        return this.tempsRemplissageComplet;

        /* [PHILEMON]
        * Problème dans la version commentée :
        * en réalité, this.capacite n'est modifié qu'au moment où  l'événement Remplissage
        * a lieu, et donc ici et pour tous les calculs, this.capacite est encore
        * égal à this.capaciteMaximale. Ce qui pose problème pour savoir le temps de
        * remplissage ... On voudrait savoir quelle quantité d'eau il a perdu
        * Faut peut-être rajouter un paramètre capaciteFictive ou un truc du genre ...
        */

		// if(this.capacite == 0){
		// 	return this.tempsRemplissageComplet;
		// }
		// else{
		// 	return (long) (((double)(this.capaciteMaximale - this.capacite)) / this.vitesseRemplissage);
		// }
	}
	public void setTempsRemplissageComplet(long temps){
		this.tempsRemplissageComplet = temps;
	}

	public long getTempsVidageComplet() {
		return this.tempsVidageComplet;
	}
	public void setTempsVidageComplet(long temps){
		this.tempsVidageComplet = temps;
	}

    public long getTempsVidageUnitaire() {
        return this.tempsVidageUnitaire;
    }
    public void setTempsVidageUnitaire(long temps){
        this.tempsVidageUnitaire = temps;
    }

    public int getVolumeVidageUnitaire() {
        return this.volumeVidageUnitaire;
    }
    public void setVolumeVidageUnitaire(int volume){
        this.volumeVidageUnitaire = volume;
    }

	public double getVitesseRemplissage(){
		return this.vitesseRemplissage;
	}
	public void setVitesseRemplissage(long tempsRemplissageComplet, int capacite) {
		this.vitesseRemplissage = (double)capacite/(double)tempsRemplissageComplet;
	}

	public double getVitesseVidage(){
		return this.vitesseVidage;
	}
	public void setVitesseVidage(long tempsVidage, int capacite) {
		this.vitesseVidage = (double)capacite/(double)tempsVidage;
	}

	public long getDateDisponibilite() {
		return this.dateDisponibilite;
	}
	public void setDateDisponibilite(long date){
		this.dateDisponibilite = date;
	}

    public void setChef(Chef chef) {
        this.chef = chef;
    }
    public Chef getChef() {
        return this.chef;
    }

	public boolean estDisponible(long date){
		if(date < this.getDateDisponibilite()){
			return false;
		}
		else{
			return true;
		}
	}
	/*********************************************
	 *
	 * METHODES DE DEPLACEMENT
	 */

	/* Déplacement du robot vers une case et ajout des évènements au simulateur */
	public Chemin deplacementCase(Case dest, Simulateur sim, long date) {
        if (this.possibleDeplacement(dest)) {
            /* Calcul du plus court chemin */
    		Chemin chemin = this.plusCourt(dest, date, sim.getDonnees().getCarte());
    		this.ajoutSimulateurDeplacement(sim,chemin);
            this.setPosition(dest);     // En réalité le robot n'est pas affiché à la position dest et ne doit pas l'être.
            // Mais il ne le sera jamais car il y a toujours un evenement qui change sa position avant
            // On a besoin de faire ça pour les calculs du plus court chemin qui suivront
    		return chemin;
        } else {
            System.out.println("Deplacement impossible pour ce robot");
            return new Chemin();
        }
	}

	/* Déplacement possible selon la nature du robot */
	public abstract boolean possibleDeplacement(Case voisin);

    /* Calcul du temps de déplacement
	 * 		Dépend de la vitesse du robot et de la nature du terrain,
	 * 		donc de la nature du terrain sur la moitié de la première
	 * 		case et la moitié de la seconde case
	 */
	public long calculTemps(Case src, Case voisin, Carte carte) {
		/* Vitesse sur la case src en m/s*/
		double vitesseSrc = (this.getVitesse(src.getNature()))/3.6;
		/* Vitesse sur la case dest en m/s */
		double vitesseVoisin = (this.getVitesse(voisin.getNature()))/3.6;
		/* Taille de la case, on prend comme distance la moitié */
		int tailleCase = carte.getTailleCases();
		int distance = tailleCase/2;
		/* Calcul du temps sur les deux terrains */
		double tempsSrc = distance/vitesseSrc;
		double tempsVoisin = distance/vitesseVoisin;
		/* On renvoie le temps, arrondi au supérieur */
		return (long) Math.round(tempsSrc+tempsVoisin);
	}


/*FONCTION UTILE AU CALCUL DE PLUS COURT CHEMIN********************************************/

	/* calcul du plus court chemin */
	public Chemin plusCourt(Case dest, long date, Carte carte) {
		Chemin chemin = Dijkstra(dest, date, carte);
		return chemin;
	}
	/* Initialisation du graphe (poids infini) */
	protected void Initialisation(Carte carte, Case src, List<Case> noeuds, long[][] poids){
		for(int l=0; l<carte.getNbLignes(); l++) {
			for(int c=0; c<carte.getNbColonnes(); c++) {
				poids[l][c] = INFINI;
				noeuds.add(carte.getCase(l, c));
			}
		}
		poids[src.getColonne()][src.getLigne()] = 0;
	}
	/*Trouve le noeud de poids minimum parmis tous les noeuds*/
	protected Case TrouveMin(List<Case> noeuds, long[][] poids){
		long min = INFINI;
        Case noeudChoisi = null;
		for(Case noeud : noeuds){
			if(poids[noeud.getColonne()][noeud.getLigne()] < min){
				min = poids[noeud.getColonne()][noeud.getLigne()];
				noeudChoisi = noeud;
			}
		}
		return noeudChoisi;
	}
	/* Met à jour la distance entre le voisin et le noeud */
	protected void majPoids(Case[][] predecesseurs, long[][] poids, Case src, Case voisin, Carte carte) {
		long temps = this.calculTemps(src, voisin, carte);
		long poidsSrc = poids[src.getColonne()][src.getLigne()];
		long poidsVoisin = poids[voisin.getColonne()][voisin.getLigne()];
		if(poidsVoisin > poidsSrc+temps) {
			poids[voisin.getColonne()][voisin.getLigne()] = poidsSrc+temps;
			predecesseurs[voisin.getColonne()][voisin.getLigne()] = src;
		}
	}
	/*Algorithme qui nous permet de trouver le plus court chemin,*/
	protected Chemin Dijkstra(Case dest, long date, Carte carte){
        /* Sauvegarde de la date de début */
        long dateDebut = date;
		/*création du tableau des predecesseurs*/
		Case[][] predecesseurs = new Case[carte.getNbColonnes()][carte.getNbLignes()];
		/*Données nécessaires à l'algorithme*/
		Case src = this.getPosition();



        // [PHILEMON] Faudrait peut etre avoir un paramètre du genre positionVirtuelle
        // car par exemple si dans TestEvenementBis on lui dit de faire deplacementCase puis
        // ordreIntervention, il faut que this.getPosition() ait changé pour le calcul du plus courtChemin
        // pour l'intervention ...




		/* Ensemble des cases */
		ArrayList<Case> noeuds = new ArrayList<Case>();
		/* Ensemble des poids */
		long[][] poids = new long[carte.getNbColonnes()][carte.getNbLignes()];
		/* Initialisation du graphe (poids infini) */
		Initialisation(carte, src, noeuds, poids);

		Case noeud = src;
		/* Tant que l'on a pas parcouru toutes les cases ou atteint la dest */
		while(!noeuds.isEmpty()) {
			// on récupère la case dont la distance est minimale
			noeud = TrouveMin(noeuds, poids);
			// on supprime ce noeud
			noeuds.remove(noeud);
			if(noeud.equals(dest)){ //On s'arrête dès qu'on a atteint la destination pour éviter des calculs inutiles
				break;
			}
			// on s'occupe des noeuds restants
			// on met à jour les distances pour tous les voisins du minimum
			Case voisin = carte.voisin(noeud, Direction.SUD);
			if( (voisin != null) && (noeuds.contains(voisin)&&(this.possibleDeplacement(voisin)))){
				majPoids(predecesseurs, poids, noeud, voisin, carte);
			}
			voisin = carte.voisin(noeud, Direction.NORD);
			if((voisin != null) && (noeuds.contains(voisin)&&(this.possibleDeplacement(voisin)))){
				majPoids(predecesseurs, poids, noeud, voisin, carte);
			}
			voisin = carte.voisin(noeud, Direction.EST);
			if((voisin != null) && (noeuds.contains(voisin)&&(this.possibleDeplacement(voisin)))){
				majPoids(predecesseurs, poids, noeud, voisin, carte);
			}
			voisin = carte.voisin(noeud, Direction.OUEST);
			if((voisin != null) && (noeuds.contains(voisin)&&(this.possibleDeplacement(voisin)))){
				majPoids(predecesseurs, poids, noeud, voisin, carte);
			}
		}
		/*On récupère le plus court chemin à partir du tableau des prédécesseurs*/
		Chemin chemin = recupPlusCourtChemin(predecesseurs, poids, dest, src, carte, dateDebut);
		return chemin;
	}

	/*A partir du tableau des predecesseurs on récupère toutes les cases qui formeront le plus court chemin*/
	protected Chemin recupPlusCourtChemin(Case[][] predecesseurs, long[][] poids, Case caseFinale, Case caseInitiale, Carte carte, long date){
		Chemin chemin = new Chemin();
		Case cas = caseFinale;
        long dateDebut = date;
		ArrayList<Case> listeCases = new ArrayList<Case>();
		while(cas != caseInitiale){
			listeCases.add(cas);
			cas = predecesseurs[cas.getColonne()][cas.getLigne()];
		}
		for(int i = listeCases.size()-1; i >=0; i--){
			chemin.ajoutCase(listeCases.get(i), date, this, carte);
			date = dateDebut + poids[listeCases.get(i).getColonne()][listeCases.get(i).getLigne()];
		}
		return chemin;
	}


    /*********************************************
     *
     * METHODE D'INTERVENTION
     */

    public void ordreIntervention(Simulateur sim, Incendie incendie) {
        long date = sim.getDateSimulation();
        if (this.getPosition() != incendie.getPosition()) {
            if (this.possibleDeplacement(incendie.getPosition())) {
                /* On se déplace jusqu'à l'incendie */
                Chemin chemin = this.deplacementCase(incendie.getPosition(), sim, date);
                /* Date de fin du déplacement */
                date = date + chemin.tempsChemin(this, sim.getDonnees().getCarte());

            } else {
                System.out.println("Intervention impossible pour ce robot");
            }
        }
        /* Ajout au simulateur des interventions unitaires */
        int sauvegardeCapacite = this.getCapacite();
        int sauvegardeLitrePourEteindre = incendie.getLitrePourEteindre();
        long tempsVidageUnitaire = this.getTempsVidageUnitaire();
        int volumeVidageUnitaire = this.getVolumeVidageUnitaire();
        while (incendie.getLitrePourEteindre()>0 && this.getCapacite()>0) {
            this.ajoutSimulateurIntervention(sim, date, tempsVidageUnitaire, incendie);
            this.deverserEau(volumeVidageUnitaire);
            incendie.setLitrePourEteindre(incendie.getLitrePourEteindre() - volumeVidageUnitaire);
            date = date + tempsVidageUnitaire;
        }
        boolean fautRemplir = (this.getCapacite()<=0);
        this.setCapacite(sauvegardeCapacite);
        incendie.setLitrePourEteindre(sauvegardeLitrePourEteindre);
        /* Si reservoir vide, va se remplir */
        if (fautRemplir) {
            this.ordreRemplissage(sim, date);
        }
    }

    /* Le robot intervient sur le feu */
    public void intervenir(Simulateur sim, Incendie incendie) {
        /* On déverse l'eau selon la taille de l'incendie et du réservoir */
		int volInc = incendie.getLitrePourEteindre(); /*on récupère la quantité d'eau nécessaire pour éteindre l'incendie*/
        if (volInc > 0) {
            int volRbt = this.getVolumeVidageUnitaire(); /*on récupère la quantité d'eau contenue dans le robot*/

    		int volEncoreNecessaire = volInc - volRbt;
    		/* Si eau dans réservoir robot insuffisant */
    		if(volEncoreNecessaire > 0) {
    			this.deverserEau(volRbt);
    			incendie.setLitrePourEteindre(volEncoreNecessaire);
    		}
    		/* Si eau dans réservoir robot suffisante */
    		else {
    			this.deverserEau(volRbt);
    			incendie.setLitrePourEteindre(0);
                /* On supprime l'incendie qu'on vient d'éteindre */
                sim.getDonnees().supprimerIncendie(incendie);

                // [PHILEMON]
                // Remarque : on pourrait faire en sorte qu'on attende this.getTempsVidageUnitaire() avant de supprimer l'incendie
                // car par exemple pour le cas du drone quand il se vide ca supprime direct l'incendie alors que normalementt,
                // l'incendie est éteint après trente secondes
                // Pour implémenter ça on pourrait par exemple créer un evenement DesaffichageIncendie à une date donnée.
                // Faut choisir si on le fait ou pas.

    		}
        } else {
            this.supprimeSimulateurEvenements(sim, sim.getDateSimulation());
        }
        HashMap<Incendie,Robot> affectations = this.chef.getAffectations();
        affectations.remove(incendie);
        this.chef.setAffectations(affectations);

    }

    /* Déverser l'eau */
    public void deverserEau(int vol) {
		this.setCapacite(this.getCapacite() - vol);
	}

	/*********************************************
	 *
	 * METHODES DE REMPLISSAGE
	 */

	/*Remplie le réservoir du robot. Private car on passe par ordreRemplissage*/
	public abstract void remplirReservoir();

	/* Possibilité de se remplir sur la position donnée */
	public abstract boolean possibleRemplissage(Case cas, Carte carte);

	/*ordre de remplissage donné au robot*/ /*fonction qui remplacera remplir Reservoir*/
	/*Cette fonction appelera remplirResevoir une fois le robot arrivé sur la zone d'eau*/
	public  void ordreRemplissage(Simulateur sim, long date) {
	    if (!this.possibleRemplissage(this.getPosition(), sim.getDonnees().getCarte())) {
            /* On recupere la case eau la plus proche en temps */
	        Case destinationEau = this.choisirCaseEau(sim);
	        /* On se déplace jusqu'à cette case */
	        Chemin chemin = this.deplacementCase(destinationEau, sim, date);
	        /* Date de fin du déplacement */
	        date = date + chemin.tempsChemin(this, sim.getDonnees().getCarte());
	    }
	    /* Ajout au simulateur du remplissage */
	    this.ajoutSimulateurRemplissage(sim, date, this.getTempsRemplissageComplet());
	}

	/* Renvoie la case d'eau pour laquelle le trajet vers cele-ci est le plus rapide */
	public Case choisirCaseEau(Simulateur sim) {
		long minTemps = INFINI;
		Case caseEauChoisie = this.position;
		DonneesSimulation donnees = sim.getDonnees();
	    Carte carte = donnees.getCarte();
		long date = this.getDateDisponibilite();
		Case[] eaux = donnees.getEaux();
		int nbEaux = donnees.getNbEaux();
		/* Pour chaque case d'eau on va calculer le plus court chemin pour aller à côté de la case et le temps que notre robot met pour le faire */
		for (int i=0; i<nbEaux; i++) {
			Case caseEau = eaux[i];
            Case caseProcheEau;
            Chemin chemin;
            long temps;
            for (Direction dir : Direction.values()) {
                if (carte.voisinExiste(caseEau, dir) && this.possibleDeplacement(carte.voisin(caseEau, dir))) {
                    caseProcheEau = carte.voisin(caseEau, dir);
                    chemin = this.plusCourt(caseProcheEau, date, carte);
                    temps = chemin.tempsChemin(this, carte);
        			if (temps < minTemps) {
        				minTemps=temps;
        				caseEauChoisie = caseProcheEau;
        			}
                }
            }
		}
		return caseEauChoisie;
	}

	/*********************************************
	 *
	 * METHODE D'AJOUT AU SIMULATEUR
	 */

	/* Ajout au simulateur d'un déplacement vers une case */
	public void ajoutSimulateurDeplacement(Simulateur sim, Chemin chemin) {
		/* On récupère les caractéristiques du chemin */
		int nbCase = chemin.getNbCase();
		List<Case> cases = chemin.getChemin();
		List<Long> dates = chemin.getDates();
		/* Ajout des évènements au simulateur */
		for(int i = 0; i<nbCase; i++) {
			long date = dates.get(i);
			Case deplacement = cases.get(i);
            long duree = 0;
            if (i+1<nbCase) {
                duree = dates.get(i+1) - date;
            } else {
                duree = dates.get(0) + chemin.tempsChemin(this, sim.getDonnees().getCarte()) - date;
            }
            System.out.println("[" + this.getNature() + "]-" + "DeplacementUnitaire, date : " + date + ", case (" + deplacement.getLigne() + ";" + deplacement.getColonne() +")");
			this.setDateDisponibilite(this.getDateDisponibilite()+duree);
			sim.ajouteEvenement(new DeplacementUnitaire(date, sim, this, deplacement));
		}
	}

	/* Ajout au simulateur d'un remplissage */
	public void ajoutSimulateurRemplissage(Simulateur sim, long date, long duree) {
		this.setDateDisponibilite(this.getDateDisponibilite()+duree);
        System.out.println("[" + this.getNature() + "]-" + "Remplissage, date : " + date + " de durée " + duree +"; Et la date de dispo est " + this.getDateDisponibilite());
        sim.ajouteEvenement(new Remplissage(date, sim, this));
	}

    /* Ajout au simulateur d'une intervention */
    public void ajoutSimulateurIntervention(Simulateur sim, long date, long duree, Incendie incendie) {
        System.out.println("[" + this.getNature() + "]-" + "Intervention, date : " + date + ", incendie " + incendie);
        this.setDateDisponibilite(this.getDateDisponibilite()+duree);
        sim.ajouteEvenement(new Intervention(date, sim, this, incendie));
    }

    /* Enleve au simulateur les prochains evenements liés à ce robot commencant à partir de date */
    public void supprimeSimulateurEvenements(Simulateur sim, long date) {
        sim.supprimeEvenements(this, date);
        this.setDateDisponibilite(date);
    }

}
