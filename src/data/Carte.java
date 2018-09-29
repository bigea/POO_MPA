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
	
	/** Attributs */
	private int tailleCases;
	private int nbLignes;
	private int nbColonnes;
	private Case[][] matrice;
	
	/** Constructeur */
	public Carte(int nbl, int nbc) {
		this.nbLignes = nbl;
		this.nbColonnes = nbc;
		this.matrice = new Case[nbl][nbc];
	}
	
	/** Accesseurs */
	public int getNbLignes() {
		return this.nbLignes;
	}
	public int getNbColonnes() {
		return this.nbColonnes;
	}
	public int getTailleCases() {
		return this.tailleCases;
	}
	
	/** Accéder à une case */
	public Case getCase(int lig, int col) {
		return this.matrice[lig][col];
	}
	
	/** Existence du voisin */
	public boolean voisinExiste(Case src, Direction dir) {
		// TODO
		return false;
	}
	
	/** Accéder au voisin */
	public Case getVoisin(Case src, Direction dir) {
		// TODO
		return null;
	}
}
