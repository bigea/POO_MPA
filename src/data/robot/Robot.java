package data.robot;

import java.util.List;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.DonneesSimulation;
import data.enumerate.NatureRobot;
import data.enumerate.NatureTerrain;
import events.EvenementDeplacementUnitaire;
import events.EvenementRemplissageSurPlace;
import events.*;
import gui2.Simulateur;

/**
 * Classe Robot
 */

public abstract class Robot {

    /**
     * Classe Robot
     * 		Avec hérachie (sous-classes)
     */

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
	public Chemin deplacementCase(Case dest, Simulateur sim, int date) {
		/* Calcul du plus court dans chemin */
        // date = sim.getDateSimulation(); // Valeur par défaut si pas donné en paramètre
		Chemin chemin = this.plusCourt(dest, date, sim.getDonnees().getCarte());
		this.ajoutSimulateurDeplacement(sim,chemin);
        return chemin;
	}

	/* Méthode de déplacement du robot vers une case voisine (ne peut être appelée seulement si la case dest est vosine) */
	// public void deplacementVoisin(Case dest, Simulateur sim) {
	// 	int date = sim.getDateSimulation();
	// 	Chemin chemin = new Chemin(this, date);
	// 	date = date+chemin.calculTemps(this.getPosition(), dest);
	// 	chemin.ajoutCaseQueue(dest, date);
	// 	ajoutSimulateurDeplacement(sim, chemin);
	// }

	/* Déplacement possible selon la nature du robot */
	public abstract boolean possibleDeplacement(Case voisin);

	/* Calcul du plus court chemin : spécifique selon le type de robot*/
	protected abstract Chemin plusCourt(Case dest, int date, Carte carte);

    /* Calcul du temps de déplacement
	 * 		Dépend de la vitesse du robot et de la nature du terrain,
	 * 		donc de la nature du terrain sur la moitié de la première
	 * 		case et la moitié de la seconde case
	 */
	public int calculTemps(Case src, Case voisin, Carte carte) {
		/* Vitesse sur la case src en m/s*/
		double vitesse_src = (this.getVitesse(src.getNature()))/3.6;
		/* Vitesse sur la case dest en m/s */
		double vitesse_voisin = (this.getVitesse(voisin.getNature()))/3.6;
		/* Taille de la case, on prend comme distance la moitié */
		int taille_case = carte.getTailleCases();
		int distance = taille_case/2;
		/* Calcul du temps sur les deux terrains */
		double temps_src = distance/vitesse_src;
		double temps_voisin = distance/vitesse_voisin;
		/* On renvoie le temps, arrondi au supérieur */
		return (int) Math.round(temps_src+temps_voisin);
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
        int date = sim.getDateSimulation();
        if (this.possibleRemplissage(this.getPosition(), sim.getDonnees().getCarte())) {
            this.ajoutSimulateurRemplissage(sim, date, this.getTempsRemplissage());
        } else {
            /* On recupere la case eau la plus proche en temps */
            Case destinationEau = this.choisirCaseEau(sim);
            /* On se déplace jusqu'à cette case */
            Chemin chemin = this.deplacementCase(destinationEau, sim, sim.getDateSimulation());
            // Carte carte = sim.getDonnees().getCarte();
            // Chemin chemin = this.plusCourt(destinationEau, date, carte);
            /* Ajout au simulateur du chemin */
            // this.ajoutSimulateurDeplacement(sim, chemin);
            /* Date de fin du déplacement */
            date = date + chemin.tempsChemin(this, sim.getDonnees().getCarte());
            // /* Ajout du temps de remplissage */
            // date = date + this.getTempsRemplissage();
            /* Ajout au simulateur du remplissage */
            this.ajoutSimulateurRemplissage(sim, date, this.getTempsRemplissage()); // A revoir pourquoi donner la date de fin de remplissage [PHILEMON]
        }
    }

  /* Renvoie la case d'eau pour laquelle le trajet vers cele-ci est le plus rapide */
  public Case choisirCaseEau(Simulateur sim) {
		int minTemps = 0;
		Case caseEauChoisie = this.position;
		DonneesSimulation donnees = sim.getDonnees();
        Carte carte = donnees.getCarte();
		int date = sim.getDateSimulation();
		Case[] eaux = donnees.getEaux();
		int nbEaux = donnees.getNbEaux();
		/* Pour chaque case d'eau on va calculer le plus court chemin et le temps que notre robot met pour le faire */
		for (int i=0; i<nbEaux; i++) {
			Case caseEau = eaux[i];
			Chemin chemin = this.plusCourt(caseEau, date, carte);
			int temps = chemin.tempsChemin(this, carte);
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

	/* Déverser l'eau */
	public abstract void deverserEau(int vol);


	/*********************************************
	 *
	 * METHODE D'AJOUT AU SIMULATEUR
	 */

	/* Ajout au simulateur d'un déplacement vers une case */
	public void ajoutSimulateurDeplacement(Simulateur sim, Chemin chemin) {
		/* On récupère les caractéristiques du chemin */
		int nbCase = chemin.getNbCase();
		List<Case> cases = chemin.getChemin();
		List<Integer> dates = chemin.getDates();
		/* Ajout des évènements au simulateur */
		for(int i = 0; i<nbCase; i++) {
			int date = dates.get(i);
			Case deplacement = cases.get(i);
            int duree = 0;
            if (i+1<nbCase) {
                duree = dates.get(i+1) - date;
            } else {
                duree = dates.get(0) + chemin.tempsChemin(this, sim.getDonnees().getCarte()) - date;
            }
			sim.ajouteEvenement(new DeplacementUnitaire(date, sim, this, duree, deplacement));
		}
	}

	/* Ajout au simulateur d'un remplissage effectif */
	public void ajoutSimulateurRemplissage(Simulateur sim, int date, int duree) {
		// sim.ajouteEvenement(new EvenementRemplissageSurPlace(date, sim, this));
        sim.ajouteEvenement(new Remplissage(date, sim, this, duree));
	}

}
