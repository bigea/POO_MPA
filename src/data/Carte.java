package data;

import data.Case;
import data.enumerate.Direction;

/**
 * Classe Carte
 */

public class Carte {

    /**
     * Classe Carte (matrice mxn) représentée par :
     * 		- Matrice de Case
     * 		- Taille Côté des cases
     *
     * Méthodes pour accéder à une case en fonction de ses coordonnées,
     * ou pour trouver le voisin d'une case dans une direction donnée
     */

	private int tailleCases;
	private int nbLignes;
	private int nbColonnes;
	private Case[][] matrice;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */
	
	/* Affichage */
	public String toString() {
		return this.getNbLignes()+" "+this.getNbColonnes()+" "+this.getTailleCases();
	}

	/* Constructeur */
	public Carte(int tc, int nbl, int nbc) {
		this.setTailleCases(tc);
		this.setNbLignes(nbl);
		this.setNbColonnes(nbc);
		this.setMatrice();
	}

	/* Mutateurs */
	public void setNbLignes(int nbl) {
		this.nbLignes = nbl;
	}
	public void setNbColonnes(int nbc) {
		this.nbColonnes = nbc;
	}
	public void setTailleCases(int tc) {
		this.tailleCases = tc;
	}
	public void setMatrice() {
		this.matrice = new Case[this.getNbLignes()][this.getNbColonnes()];
	}

	/* Accesseurs */
	public int getNbLignes() {
		return this.nbLignes;
	}
	public int getNbColonnes() {
		return this.nbColonnes;
	}
	public int getTailleCases() {
		return this.tailleCases;
	}

	/* Accéder à une case */
	public Case getCase(int lig, int col) {
		return this.matrice[lig][col];
	}

	/* Instancier une case dans la matrice */
	public void setCase(Case cas) {
		matrice[cas.getLigne()][cas.getColonne()] = cas;
	}
	
	/*********************************************
	 *
	 * METHODES SUR LES VOISINS
	 */
	
	/* Existence du voisin */
	public boolean voisinExiste(Case src, Direction dir) {
	    int src_ligne = src.getLigne();
	    int src_colonne = src.getColonne();
	    boolean existe = false;
	    switch(dir){
	      case NORD:
	        existe = (src_ligne - 1 >= 0);
	        break;
	      case SUD:
	        existe = (src_ligne + 1 < this.nbLignes);
	        break;
	      case EST:
	        existe = (src_colonne + 1 < this.nbColonnes);
	        break;
	      case OUEST:
	        existe = (src_colonne - 1 >= 0);
	        break;
	    }
	    return existe;
	}

	/* Renvoie le voisin de la case src selon une direction */
	public Case voisin(Case src, Direction dir) {
    if (this.voisinExiste(src, dir)){
	      int src_ligne = src.getLigne();
	      int src_colonne = src.getColonne();
	      Case voisin = src;
	      switch(dir){
	        case NORD:
	          voisin = this.matrice[src_ligne - 1][src_colonne];
	          break;
	        case SUD:
	          voisin = this.matrice[src_ligne + 1][src_colonne];
	          break;
	        case EST:
	          voisin = this.matrice[src_ligne][src_colonne + 1];
	          break;
	        case OUEST:
	          voisin = this.matrice[src_ligne][src_colonne - 1];
	          break;
	      }
	  		return voisin;
	    } else {
	      return null;
	    }
	}
}
