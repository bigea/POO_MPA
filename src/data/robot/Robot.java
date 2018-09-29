package data.robot;

import data.Case;
import data.enumerate.NatureTerrain;

/**
 * Classe Robot
 */

public abstract class Robot {

    /**
     * Classe Robot
     * 		Avec hérachie (sous-classes)
     */
	
	/* Attributs */
	private Case position;
	private int volume;
	
	/* Constructeur */
	public Robot(Case pos, int vol) {
		this.position = pos;
		this.volume = vol;
	}
	
	/* Accesseurs */
	public Case getPosition() {
		return this.position;
	}
	public int getVolume() {
		return this.volume;
	}
	
	/* Mutateurs */
	public void setPosition(Case pos) {
		this.position = pos;
	}
	public void setVolume(int vol) {
		this.volume = vol;
	}
	
	/* Obtenir la vitesse */
	public double getVitesse(NatureTerrain nt) {
		// TODO
	}
	
	/* Déverser l'eau */
	public void deverserEau(int vol) {
		// TODO
	}
	
	/* Remplir réservoir */
	public void remplirReservoir() {
		// TODO
	}
}