package data;

import data.enumerate.NatureTerrain;

/**
 * Classe Case
 */

public class Case{

    /**
     * Classe Case représentée par :
     * 		Ligne
     *		Colonne
     * 		Nature du terrain
     */

	private int ligne;
	private int colonne;
	// private Coordonnee coordonnee;
	private NatureTerrain nature;
    private int positionAbsolue;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */
    
	/* Affichage */
	public String toString() {
		return this.getNature()+" y : "+this.ligne + "  x : "+this.colonne ;
	}

	/* Constructeur */
	public Case(int ligne, int colonne, NatureTerrain nature, int positionAbsolue) {
		this.setLigne(ligne);
		this.setColonne(colonne);
		this.setNature(nature);
        this.positionAbsolue = positionAbsolue;
	}

	/* Accesseurs */
	public int getLigne() {
		return this.ligne;
	}
	public int getColonne() {
		return this.colonne;
	}
	public NatureTerrain getNature() {
		return this.nature;
	}
    public int getPositionAbsolue() {
        return this.positionAbsolue;
    }

	/* Mutateurs */
	public void setLigne(int nl) {
		this.ligne = nl;
	}
	public void setColonne(int nc) {
		this.colonne = nc;
	}
	public void setNature(NatureTerrain nt) {
		this.nature = nt;
	}
    public void setPositionAbsolue(int posAbs) {
        this.positionAbsolue = posAbs;
    }

    /* Egalité entre deux case */
	public boolean equals(Case cas){
		return (this.getLigne() == cas.getLigne() && this.getColonne() == cas.getColonne());
	}

}
