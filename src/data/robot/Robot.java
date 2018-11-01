package data.robot;

import java.util.ArrayList;
import java.util.List;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.DonneesSimulation;
import data.Incendie;
import data.enumerate.NatureRobot;
import data.enumerate.NatureTerrain;
import events.EvenementDeplacementUnitaire;
import events.EvenementRemplissageSurPlace;
import events.*;
import gui2.Simulateur;
import data.enumerate.Direction;

/**
 * Classe Robot
 */

public abstract class Robot {

    /**
     * Classe Robot
     * 		Avec hérachie (sous-classes)
     */
	private static final long INFINI = 32000;
	/* Attributs */
	private NatureRobot nature;
	protected Case position;
	protected int capacite; //en litre
	protected int vitesse; //en km/h
	protected int tempsRemplissage; //temps en seconde
	protected int tempsVidage; //temps en seconde
	protected double vitesseRemplissage; //en l/s
	protected double vitesseVidage; //en l/s

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	public Robot(Case pos, NatureRobot nature) {
		this.setPosition(pos);
		this.nature = nature;
	}

	/* Affichage */
	public abstract String toString();

	/* Accesseurs */
	public Case getPosition() {
		return this.position;
	}
	public NatureRobot getNature() {
		return this.nature;
	}
	public int getCapacite() {
		return this.capacite;
	}

	// public Carte getCarte() {
	// 	return this.carte;
	// }

	/* Mutateurs */
	public void setPosition(Case cas) {
		this.position = cas;
	}
	public void setCapacite(int vol) {
		this.capacite = vol;
	}

	public abstract void setVitesse(int vitesse);
	public abstract double getVitesse(NatureTerrain nt);

	public abstract int getTempsRemplissage();
	protected abstract void setTempsRemplissage(int temps);

	public abstract int getTempsVidageComplet();
	protected abstract void setTempsVidageComplet(int temps);

	public abstract double getVitesseRemplissage();
	protected abstract void setVitesseRemplissage(int tempsRemplissage, int capacite);

	public abstract double getVitesseVidage();
	protected abstract void setVitesseVidage(int tempsVidage, int capacite);


	/*********************************************
	 *
	 * METHODES DE DEPLACEMENT
	 */

	/* Déplacement du robot vers une case et ajout des évènements au simulateur */
	public Chemin deplacementCase(Case dest, Simulateur sim, long date) {
		/* Calcul du plus court dans chemin */
        // date = sim.getDateSimulation(); // Valeur par défaut si pas donné en paramètre
		Chemin chemin = this.plusCourt(dest, date, sim.getDonnees().getCarte());
		// System.out.println("HELLO");
		// System.out.println("chemin : " + chemin.toStringDate());
		this.ajoutSimulateurDeplacement(sim,chemin);
        return chemin;
	}

	/* Méthode de déplacement du robot vers une case voisine (ne peut être appelée seulement si la case dest est vosine) */
	// public void deplacementVoisin(Case dest, Simulateur sim) {
	// 	long date = sim.getDateSimulation();
	// 	Chemin chemin = new Chemin(this, date);
	// 	date = date+chemin.calculTemps(this.getPosition(), dest);
	// 	chemin.ajoutCaseQueue(dest, date);
	// 	ajoutSimulateurDeplacement(sim, chemin);
	// }

	/* Déplacement possible selon la nature du robot */
	public abstract boolean possibleDeplacement(Case voisin);

    /* Calcul du temps de déplacement
	 * 		Dépend de la vitesse du robot et de la nature du terrain,
	 * 		donc de la nature du terrain sur la moitié de la première
	 * 		case et la moitié de la seconde case
	 */
	public int calculTemps(Case src, Case voisin, Carte carte) {
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
		return (int) Math.round(tempsSrc+tempsVoisin);
	}

	/*********************************************
	 *
	 * METHODES DE REMPLISSAGE
	 */

	/* Possibilité de se remplir sur la position donnée */
	public abstract boolean possibleRemplissage(Case cas, Carte carte);

	/*ordre de remplissage donné au robot*/ /*fonction qui remplacera remplir Reservoir*/
	/*Cette fonction appelera remplirResevoir une fois le robot arrivé sur la zone d'eau*/
	public  void ordreRemplissage(Simulateur sim) {
        long date = sim.getDateSimulation(); // Gerer
        if (this.possibleRemplissage(this.getPosition(), sim.getDonnees().getCarte())) {
            this.ajoutSimulateurRemplissage(sim, date, this.getTempsRemplissage());
        } else {
            /* On recupere la case eau la plus proche en temps */
            Case destinationEau = this.choisirCaseEau(sim);
            /* On se déplace jusqu'à cette case */
            Chemin chemin = this.deplacementCase(destinationEau, sim, date);
            /* Date de fin du déplacement */
            date = date + chemin.tempsChemin(this, sim.getDonnees().getCarte());
            /* Ajout au simulateur du remplissage */
            this.ajoutSimulateurRemplissage(sim, date, this.getTempsRemplissage());
        }
    }

    /* Renvoie la case d'eau pour laquelle le trajet vers cele-ci est le plus rapide */
    public Case choisirCaseEau(Simulateur sim) {
		long minTemps = 0;
		Case caseEauChoisie = this.position;
		DonneesSimulation donnees = sim.getDonnees();
        Carte carte = donnees.getCarte();
		long date = sim.getDateSimulation();
		Case[] eaux = donnees.getEaux();
		int nbEaux = donnees.getNbEaux();
		/* Pour chaque case d'eau on va calculer le plus court chemin et le temps que notre robot met pour le faire */
		for (int i=0; i<nbEaux; i++) {
			Case caseEau = eaux[i];
			Chemin chemin = this.plusCourt(caseEau, date, carte);
			long temps = chemin.tempsChemin(this, carte);
			if (i==0) {		// Initialisation de minTemps et de caseEauChoisie en i==0
				minTemps = temps;
				caseEauChoisie = caseEau;
			}
			if (temps < minTemps) {
				minTemps=temps;
				caseEauChoisie = caseEau;
			}
		}
		return caseEauChoisie;
    }

    /*Remplie le réservoir du robot. Private car on passe par ordreRemplissage*/
    public abstract void remplirReservoir();

    /*********************************************
     *
     * METHODE D'INTERVENTION
     */

    public void ordreIntervention(Simulateur sim, Incendie incendie) {
        long date = sim.getDateSimulation();
        if (this.getPosition() == incendie.getPosition()) {
            this.ajoutSimulateurIntervention(sim, date, sim.INCRE, incendie);
        } else {
            /* On se déplace jusqu'à l'incendie */
            Chemin chemin = this.deplacementCase(incendie.getPosition(), sim, date);
            /* Date de fin du déplacement */
            date = date + chemin.tempsChemin(this, sim.getDonnees().getCarte());
            /* Ajout au simulateur de l'intervention */
            this.ajoutSimulateurIntervention(sim, date, this.getTempsVidageComplet(), incendie);
        }
        // Peut-être après on peut rajouter que si reservoir vide, va se remplir
    }

    /* Le robot intervient sur le feu */
    public void intervenir(Simulateur sim, Incendie incendie) {
        /* On déverse l'eau selon la taille de l'incendie et du réservoir */
		int volInc = incendie.getLitrePourEteindre(); /*on récupère la quantité d'eau nécessaire pour éteindre l'incendie*/
		int volRbt = this.getCapacite(); /*on récupère la quantité d'eau contenue dans le robot*/

		int volEncoreNecessaire = volInc - volRbt;
		/* Si eau dans réservoir robot insuffisant */
		if(volEncoreNecessaire > 0) {
			this.deverserEau(volRbt);
			incendie.setVolume(volEncoreNecessaire);
		}
		/* Si eau dans réservoir robot suffisante */
		else {
			this.deverserEau(volInc);
			incendie.setVolume(0);
			Incendie[] incendies = sim.getDonnees().getIncendies();
			int nbIncendie = sim.getDonnees().getNbIncendies();
			int posIncendie = 0;
			/* On trouve l'indice de l'incendie qu'on vient d'éteindre dans la liste de tous les incendies */
			for (int i=0; i<nbIncendie; i++) {
				if (incendies[i] == incendie) {
					posIncendie = i;
					i=nbIncendie;
				}
			}
			/* On va recréer une nouvelle liste d'incendies en copiant tous ceux qu'on a sauf celui qu'on vient d'éteindre */
			Incendie[] newIncendies = new Incendie[nbIncendie-1];
			int jBis = 0;
			for (int j=0; j<nbIncendie-1; j++) {
				if (j!=posIncendie) {
					newIncendies[j] = incendies[jBis];
				} else {
					newIncendies[j] = incendies[jBis+1];
					jBis++;
				}
				jBis++;
			}
			sim.getDonnees().setNbIncendies(nbIncendie-1);
			sim.getDonnees().setIncendies(newIncendies);
		}
    }

    /* Déverser l'eau */
    // public abstract void deverserEau(int vol);
    public void deverserEau(int vol) {
		this.setCapacite(this.getCapacite() - vol);
	}


/*FONCTION UTILE AU CALCUL DE PLUS COURT CHEMIN********************************************/

	/* calcul du plus court chemin */
	protected Chemin plusCourt(Case dest, long date, Carte carte) {
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
		int temps = this.calculTemps(src, voisin, carte);
		long poidsSrc = poids[src.getColonne()][src.getLigne()];
		long poidsVoisin = poids[voisin.getColonne()][voisin.getLigne()];
		if(poidsVoisin > poidsSrc+temps) {
			poids[voisin.getColonne()][voisin.getLigne()] = poidsSrc+temps;
			predecesseurs[voisin.getColonne()][voisin.getLigne()] = src;
		}
	}

	protected Chemin Dijkstra(Case dest, long date, Carte carte){
        /* Sauvegarde de la date de début */
        long dateDebut = date;
		/*création du tableau des predecesseurs*/
		Case[][] predecesseurs = new Case[carte.getNbColonnes()][carte.getNbLignes()];
		/*Données nécessaires à l'algorithme*/
		Case src = this.getPosition();
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

	protected Chemin recupPlusCourtChemin(Case[][] predecesseurs, long[][] poids, Case caseFinale, Case caseInitiale, Carte carte, long date){
		Chemin chemin = new Chemin();
		Case cas = caseFinale;
		ArrayList<Case> listeCases = new ArrayList<Case>();
		while(cas != caseInitiale){
			listeCases.add(cas);
			cas = predecesseurs[cas.getColonne()][cas.getLigne()];
		}
		for(int i = listeCases.size()-1; i >=0; i--){
			chemin.ajoutCase(listeCases.get(i), date, this, carte);
            System.out.println("poids : " + poids[listeCases.get(i).getColonne()][listeCases.get(i).getLigne()]);
			date = poids[listeCases.get(i).getColonne()][listeCases.get(i).getLigne()];
		}
		return chemin;
	}

/**********************************************************************************/

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
            System.out.println("On ajoute un DeplacementUnitaire à la date : " + date);
			sim.ajouteEvenement(new DeplacementUnitaire(date, sim, this, duree, deplacement));
		}
	}

	/* Ajout au simulateur d'un remplissage */
	public void ajoutSimulateurRemplissage(Simulateur sim, long date, long duree) {
		// sim.ajouteEvenement(new EvenementRemplissageSurPlace(date, sim, this));
        sim.ajouteEvenement(new Remplissage(date, sim, this, duree));
	}

    /* Ajout au simulateur d'une intervention */
    public void ajoutSimulateurIntervention(Simulateur sim, long date, long duree, Incendie incendie) {
        sim.ajouteEvenement(new Intervention(date, sim, this, duree, incendie));
    }

}
