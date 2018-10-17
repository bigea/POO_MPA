package data.robot;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.enumerate.NatureTerrain;
import gui.Simulateur;

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
	
	
	/*********************************************
	 * 
	 * METHODES D'ACTION
	 */
	
	/* Déplacement du robot vers une case
	 * 		Spécifique pour chaque robot
	 */
	public abstract void deplacementCase(Case cas, Simulateur sim);
	
	/* Déplacement possible selon la nature du drone */
	public abstract boolean possibleDeplacement(Case voisin);
	
	/* Calcul du plus court chemin */
	public abstract Chemin plusCourt(Case dest, int date);
	
	/* Obtenir la vitesse */
	public abstract double getVitesse(NatureTerrain nt);

	/* Déverser l'eau */
	public abstract void deverserEau(int vol);

	/* Remplir réservoir */
	public abstract void remplirReservoir();

}
