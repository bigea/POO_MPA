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
	protected int volume;
	protected int vitesse;
	private Carte carte;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	public Robot(Case pos, Carte carte, NatureRobot nature) {
		this.setPosition(pos);
		this.carte = carte;
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
	public int getVolume() {
		return this.volume;
	}
	public Carte getCarte() {
		return this.carte;
	}

	/* Mutateurs */
	public void setPosition(Case cas) {
		this.position = cas;
	}
	public void setVolume(int vol) {
		this.volume = vol;
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
		Chemin chemin = plusCourt(dest, sim.getDateSimulation());
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
    if (this.possibleRemplissage(this.getCase())) {
      this.remplirReservoir();
    } else {
      int date = sim.getDateSimulation();
      Chemin chemin = new Chemin(this, date);
      /* Chemin pour récupérer de l'eau */
      chemin.getEau();
      /* Ajout au simulateur du chemin */
      this.ajoutSimulateurDeplacement(sim, chemin);
      /* Date de fin du déplacement */
      date = chemin.getDates().get(chemin.getNbCase());
      /* Ajout du temps de remplissage */
      date = date + this.getTempsRemplissage();
      /* Ajout au simulateur du remplissage */
      this.ajoutSimulateurRemplissage(sim, date);
    }
  }

	/*Remplie le réservoir du robot. Private car on passe par ordreRemplissage*/
	private abstract void remplirReservoir();

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
			sim.ajouteEvenement(new EvenementDeplacementUnitaire(date, sim, this, deplacement));
		}
	}

	/* Ajout au simulateur d'un remplissage effectif */
	public void ajoutSimulateurRemplissage(Simulateur sim, int date) {
		// sim.ajouteEvenement(new EvenementRemplissageSurPlace(date, sim, this));
    sim.ajouteEvenement(new Remplissage(date, sim, this));
	}

}
