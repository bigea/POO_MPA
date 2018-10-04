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
	protected int vitesse;
	
	/* Constructeur */
	public Robot(Case pos) {
		this.setPosition(pos);
	}
	
	
	public void setVolume(int vol) {
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
	
	/* Position */
	public abstract void setPosition(Case pos);

	/* Instancer la vitesse*/
	public abstract void setVitesse(int vitesse);
	
	/* Obtenir la vitesse */
	public abstract double getVitesse(NatureTerrain nt);
	
	/* Déverser l'eau */
	public abstract void deverserEau(int vol);
	
	/* Remplir réservoir */
	public abstract void remplirReservoir();

	/* Insérer une vitesse */

}