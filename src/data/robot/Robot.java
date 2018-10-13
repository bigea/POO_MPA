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

	/* Méthodes abstraites */

	/* Mutateurs*/
	public abstract void setPosition(Case pos);
	public abstract void setVolume(int vol);

	/* Obtenir la vitesse */
	public abstract double getVitesse(NatureTerrain nt);

	/* Déverser l'eau */
	public abstract void deverserEau(int vol);

	/* Remplir réservoir */
	public abstract void remplirReservoir();
}
