package data.robot;

import data.Case;
import data.enumerate.NatureTerrain;

/**
 * Classe Robot
 */

public abstract class Robot {

    /**
     * Classe Robot
     */
	
	/* Attributs */
	private Case position;
	private int volume;
	private NatureRobot nature;
	
	/* Accesseurs */
	public Case getPosition() {
		return this.position;
	}
	public int getVolume() {
		return this.volume;
	}
	public NatureRobot getNature() {
		return this.nature;
	}
	
	/* Mutateurs */
	public void setPosition(Case pos) {
		this.position = pos;
	}
	public void setVolume(int vol) {
		this.volume = vol;
	}
	public void setNature(NatureRobot nr) {
		this.nature = nr;
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