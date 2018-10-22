package data.robot;

import java.util.List;

import chemin.Chemin;
import data.Carte;
import data.Case;
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
	protected Case position;
	protected int volume;
	protected int vitesse;
	private Carte carte;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	public Robot(Case pos, Carte carte) {
		this.setPosition(pos);
		this.carte = carte;
	}

	/* Affichage */
	public abstract String toString();

	/* Accesseurs */
	public Case getPosition() {
		return this.position;
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
		ajoutSimulateurDeplacement(sim, chemin);
	}

	/* Déplacement possible selon la nature du drone */
	public abstract boolean possibleDeplacement(Case voisin);

	/* Calcul du plus court chemin : spécifique selon le type de robot*/
	public abstract Chemin plusCourt(Case dest, int date);


	/*********************************************
	 *
	 * METHODES DE REMPLISSAGE
	 */

	/* Remplissage mais instanciation d'évènements pour ce faire (déplacement puis sur place) */
	public void remplirReservoir(Simulateur sim) {
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

	/* Possibilité de se remplir sur la position donnée */
	public abstract boolean possibleRemplissage(Case cas);

	/* Remplissage effectif */
	public abstract void remplirEffectif();


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
		sim.ajouteEvenement(new EvenementRemplissageSurPlace(date, sim, this));
	}

}
