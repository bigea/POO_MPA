package data.robot;

import java.util.List;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.enumerate.NatureRobot;
import data.enumerate.NatureTerrain;
import events.EvenementDeplacementUnitaire;
import events.EvenementRemplissageSurPlace;
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
	protected int capacite;
	protected int vitesse;
  protected int vitesseRemplissage;
	// private Carte carte;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	public Robot(Case pos, NatureRobot nature) {
		this.setPosition(pos);
		// this.carte = carte;
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

	/* Obtenir la vitesse */
	public abstract double getVitesse(NatureTerrain nt);

	/* Calcul du temps de remplissage */
	protected abstract int getTempsRemplissage();


	/*********************************************
	 *
	 * METHODES DE DEPLACEMENT
	 */

	/* Déplacement du robot vers une case et ajout des évènements au simulateur */
	public void deplacementCase(Case dest, Simulateur sim) {
		/* Calcul du plus court dans chemin */
		Chemin chemin = new Chemin(this, sim.getDateSimulation());
    chemin.plusCourt(dest);
		ajoutSimulateurDeplacement(sim,chemin);
	}

	/* Méthode de déplacement du robot vers une case voisine (ne peut être appelée seulement si la case dest est vosine) */
	// public void deplacementVoisin(Case dest, Simulateur sim) {
	// 	int date = sim.getDateSimulation();
	// 	Chemin chemin = new Chemin(this, date);
	// 	date = date+chemin.calculTemps(this.getPosition(), dest);
	// 	chemin.ajoutCase(dest, date);
	// 	ajoutSimulateurDeplacement(sim, chemin);
	// }

	/* Déplacement possible selon la nature du robot */
	public abstract boolean possibleDeplacement(Case voisin);

	/* Calcul du plus court chemin : spécifique selon le type de robot*/
	public abstract Chemin plusCourt(Case dest, int date);


	/*********************************************
	 *
	 * METHODES DE REMPLISSAGE
	 */

	/* Remplissage mais instanciation d'évènements pour ce faire (déplacement puis sur place) */
	// public void remplirReservoir(Simulateur sim) {
	// 	int date = sim.getDateSimulation();
	// 	Chemin chemin = new Chemin(this, date);
	// 	/* Chemin pour récupérer de l'eau */
	// 	chemin.getEau();
	// 	/* Ajout au simulateur du chemin */
	// 	this.ajoutSimulateurDeplacement(sim, chemin);
	// 	/* Date de fin du déplacement */
	// 	date = chemin.getDates().get(chemin.getNbCase());
	// 	/* Ajout du temps de remplissage */
	// 	date = date + this.getTempsRemplissage();
	// 	/* Ajout au simulateur du remplissage */
	// 	this.ajoutSimulateurRemplissage(sim, date);
	// }

	/* Possibilité de se remplir sur la position donnée */
	public abstract boolean possibleRemplissage(Case cas);

	/* Remplissage effectif */
	// public abstract void remplirEffectif();

	/*ordre de remplissage donné au robot*/ /*fonction qui remplacera remplir Reservoir*/
	/*Cette fonction appelera remplirResevoir une fois le robot arrivé sur la zone d'eau*/
	public  void ordreRemplissage(Simulateur sim) {
    int date = sim.getDateSimulation();
    if (this.possibleRemplissage(this.getCase())) {
      this.ajoutSimulateurRemplissage(sim, date + this.getTempsRemplissage());
    } else {
      Case destinationEau = this.choisirCaseEau(sim);
      Carte carte = sim.getDonnees().getCarte();
      Chemin chemin = this.plusCourt(destinationEau, date, carte);
      /* Chemin pour récupérer de l'eau */
      // chemin.getEau();
      /* Ajout au simulateur du chemin */
      this.ajoutSimulateurDeplacement(sim, chemin);
      /* Date de fin du déplacement */
      date = chemin.getDates().get(chemin.getNbDate()-1);
      /* Ajout du temps de remplissage */
      date = date + this.getTempsRemplissage();
      /* Ajout au simulateur du remplissage */
      this.ajoutSimulateurRemplissage(sim, date);
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
			int temps = chemin.tempsChemin(this);
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

  /* Calcule le plus court chemin vers une case donnée */
  public abstract Chemin plusCourt(Case dest, int date, Carte carte);

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
			sim.ajouteEvenement(new DeplacementUnitaire(date, sim, this, deplacement));
		}
	}

	/* Ajout au simulateur d'un remplissage effectif */
	public void ajoutSimulateurRemplissage(Simulateur sim, int date) {
		// sim.ajouteEvenement(new EvenementRemplissageSurPlace(date, sim, this));
    sim.ajouteEvenement(new Remplissage(date, sim, this));
	}

}
