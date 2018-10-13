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
	protected Case position;
	protected int volume;
	protected int vitesse;

	/* Constructeur */
	public Robot(Case pos) {
		this.setPosition(pos);
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

	/* Déverser l'eau */
	public abstract void deverserEau(int vol);

	/* Remplir réservoir */
	public abstract void remplirReservoir();
}
