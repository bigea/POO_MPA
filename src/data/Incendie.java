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

	/* Attributs */
	private Case position;
	private int litre;
	
	/* Affichage */
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" "+this.getLitre();
	}

	/* Constructeur */
	public Incendie(Case pos, int litre) {
		this.setPosition(pos);
		this.setVolume(litre);
	};
	
	/* Accesseurs */
	public Case getPosition() {
		return this.position;
	}
	public int getLitre() {
		return this.litre;
	}

	/* Mutateurs */
	public void setPosition(Case pos) {
		this.position = pos;
	}
	public void setVolume(int ltr) {
		this.litre = ltr;
	}
}
