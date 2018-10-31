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
	private int litrePourEteindre;

	/* Affichage */
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" "+this.getLitrePourEteindre();
	}

	/* Constructeur */
	public Incendie(Case pos, int litrePourEteindre) {
		this.setPosition(pos);
		this.setVolume(litrePourEteindre);
	};

	/* Accesseurs */
	public Case getPosition() {
		return this.position;
	}
	public int getLitrePourEteindre() {
		return this.litrePourEteindre;
	}

	/* Mutateurs */
	public void setPosition(Case pos) {
		this.position = pos;
	}
	public void setVolume(int ltr) {
		this.litrePourEteindre = ltr;
	}
}
