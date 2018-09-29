package data;

/**
 * Classe Robot
 */

public class Incendie {

    /**
     * Classe Incendie représentée par :
     * Position (une Case)
     * Nombre de litres d'eau nécessaires pour l'éteindre
     */
	
	/** Attributs */
	private Case position;
	private int litre;
	
	/** Accesseurs */
	public Case getPosition() {
		return this.position;
	}
	public int getLitre() {
		return this.litre;
	}
	
	/** Mutateurs */
	public void setPosition(Case pos) {
		this.position = pos;
	}
	public void setColonne(int ltr) {
		this.litre = ltr;
	}
}