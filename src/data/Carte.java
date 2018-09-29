package data;

import data.Case;
import data.enumerate.Direction;

/**
 * Classe Carte
 */

public class Carte {

    /**
     * Classe Carte (matrice mxn) représentée par :
     * 		Matrice de Case
     * 		Taille Côté des cases
     * 
     * Méthodes pour accéder à une case en fonction de ses coordonnées,
     * ou por trouver le voisin d'une case dans une direction donnée
     */
	
	/** Taille des Cases */
	private int tailleCases;
	
	/** Nombre de lignes et colonnes */
	private int NbLignes;
	private int NbColonnes;
	
	/** Matrice */
	private Case[][] Matrice;
	
	/** Accesseurs */
	public int getNbLignes() {
		return this.NbLignes;
	}
	public int getNbColonnes() {
		return this.NbColonnes;
	}
	public int getTailleCases() {
		return this.tailleCases;
	}
	
	/** Mutateurs */
	public void setNbLignes(int nbl) {
		this.NbLignes = nbl;
	}
	public void setNbColonnes(int nbc) {
		this.NbColonnes = nbc;
	}
	public void setMatrice(int nbl, int nbc) {
		this.Matrice = new Case[nbl][nbc];
	}
	public void setTailleCases(int tc) {
		this.tailleCases = tc;
	}
	
	/** Accéder à une case */
	public Case getCase(int lig, int col) {
		if((lig <= this.NbLignes) && (col <= this.NbColonnes)) {
			return this.Matrice[lig][col];
		}
	}
	
	/** Existence du voisin */
	public boolean voisinExiste(Case src, Direction dir) {
		/*A COMPLETER*/
		return (Boolean) null;
	}
	
	/** Accéder au voisin */
	public Case getVoisin(Case src, Direction dir) {
		/*A COMPLETER */
		return null;
	}
}
