package data;

/**
 * Classe Robot
 */

public class Incendie {

    /**
     * Classe Incendie représentée par :
     * 		Position (une Case)
     * 		Nombre de litres d'eau nécessaires pour l'éteindre
     */

	private Case position;
	private int litrePourEteindre;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */
	
	/* Affichage */
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" "+this.getLitrePourEteindre();
	}

	/* Constructeur */
	public Incendie(Case pos, int litrePourEteindre) {
		this.setPosition(pos);
		this.setLitrePourEteindre(litrePourEteindre);
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
	public void setLitrePourEteindre(int ltr) {
		this.litrePourEteindre = ltr;
	}

	/* Egalité entre deux incendies */
    @Override
    public boolean equals(Object o) {
        Incendie inc = (Incendie)o;
        return this.position.equals(inc.getPosition());
    }
    
    /* Pour une table de hachage */
    @Override
    public int hashCode() {
        return this.position.getPositionAbsolue();
    }
}
