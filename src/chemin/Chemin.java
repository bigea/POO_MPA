package chemin;

import java.util.ArrayList;
import java.util.List;

import data.Carte;
import data.Case;
import data.enumerate.Direction;
import data.robot.Robot;

/**
 * Classe Chemin
 */
public class Chemin {
	/**
	* Classe Chemin :
	* 		Séquence de cases et de dates
	*/


	/* Valeur infini */
	private static final int INFINI = 10000;

	// private long dateSimulation;
	private int nbCase;
	private List<Case> chemin;
	private int nbDate;
	private List<Long> dates;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	public Chemin() {
		this.setNbCase(0);
		this.setNbDate(0);
		this.setChemin();
		this.setNbDate(0);
		this.setDates();
	}

	/* Mutateurs - Accesseurs */
	public List<Case> getChemin() {
		return this.chemin;
	}
	public void setChemin() {
		this.chemin = new ArrayList<Case>();
	}
	public List<Long> getDates() {
		return this.dates;
	}
	public void setDates() { //stupide car ce n'est pas un setter
		this.dates = new ArrayList<Long>();
	}
	public int getNbCase() {
		return this.nbCase;
	}
	public void setNbCase(int nbCase) {
		this.nbCase = nbCase;
	}
	public int getNbDate() {
		return this.nbDate;
	}
	public void setNbDate(int nbDate) {
		this.nbDate = nbDate;
	}
	// public int getDateSimulation() {
	// 	return dateSimulation;
	// }
	// public void setDateSimulation(long dateSimulaion) {
	// 	this.dateSimulation = dateSimulaion;
	// }


 	/* Ajout d'une case à la fin du chemin (en queue) */
	// public void ajoutCase(Case cas, long date, Robot rbt, Carte carte) {
	// 	if(this.nbDate > 0 && this.nbCase > 0){
	// 		int derniereDate = this.dates.get(this.nbDate-1);
	// 		Case derniereCase = this.chemin.get(this.nbCase-1);
	// 		this.dates.add(derniereDate + rbt.calculTemps(derniereCase, cas, carte)); // à changer
	// 	}
	// 	else{
	// 		this.dates.add(date);
	// 	}
    //     this.chemin.add(cas);
    //     this.nbCase += 1;
	// 	this.nbDate += 1;
    // }

	@Override
	public String toString() {
		String chaine = new String();
		chaine += "[";
		for (int i=0; i<this.getNbCase(); i++) {
			Case cas = this.getChemin().get(i);
			chaine += " (" + cas.getLigne() + "," + cas.getColonne() + ") ";
		}
		chaine += "]";
		return chaine;
	}

	public StringBuffer toStringDate(){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<this.dates.size(); i++){
			sb.append(" " + this.dates.get(i) + "FIN");
		}
		return sb;
	}

	public void ajoutCase(Case cas, long date, Robot rbt, Carte carte) {
		this.dates.add(date);
		// System.out.println(this.toStringDate());
		this.chemin.add(cas);
		this.nbCase += 1;
		this.nbDate += 1;
	}

	public long tempsChemin(Robot robot, Carte carte) {
		long temps = this.dates.get(this.getNbDate()-1) - this.dates.get(0);
		Case avantDerniereCase;
		if (this.getNbCase() > 1) {
			avantDerniereCase = this.chemin.get(this.nbCase-2);
		} else {
			avantDerniereCase = robot.getPosition();
		}
		Case derniereCase = this.chemin.get(this.nbCase-1);
		temps = temps + robot.calculTemps(avantDerniereCase, derniereCase, carte);
		return temps;
	}

	/*********************************************
	 *
	 * PLUS COURT CHEMIN
	 */

	/* Plus court chemin : algorithme de Dijkstra
	 * 		On pourra changer la manière de faire
	 * 		en mémorisant les poids pour chaque case
	 * 		=> vers un nouveau type d'objet ?
	 */
	// public void plusCourt(Case dest) {
	// 	/* Données nécessaires à l'algorithme */
	// 	long date = this.getDateSimulation();
	// 	Robot rbt = this.getRobot();
	// 	Carte carte = rbt.getCarte();
	// 	int nbColonnes = carte.getNbColonnes();
	// 	int nbLignes = carte.getNbLignes();
	// 	Case src = this.getRobot().getPosition();
	// 	int x_src = src.getLigne();
	// 	int y_src = src.getColonne();
	//
	// 	/* Ensemble des poids */
	// 	int[][] poids = new int[nbLignes][nbColonnes];
	// 	/* Ensemble des cases */
	// 	List<Case> noeuds = new ArrayList<Case>();
	//
	// 	/* Initialisation du graphe (poids infini) */
	// 	for(int l=0; l<nbLignes; l++) {
	// 		for(int c=0; c<nbColonnes; c++) {
	// 			poids[l][c] = INFINI;
	// 			noeuds.add(carte.getCase(l, c));
	// 		}
	// 	}
	// 	poids[x_src][y_src] = 0;
	//
	// 	Case noeud = src;
	// 	/* Tant que l'on a pas parcouru toutes les cases ou atteint la dest */
	// 	while(!noeuds.isEmpty() || src != dest) {
	// 		// on récupère la case dont la distance est minimale
	// 		noeud = minimum(noeuds,poids);
	// 		date += poids[noeud.getLigne()][noeud.getColonne()];
	// 		// si c'est la case src : on ne la prend pas en compte dans le chemin
	// 		if(noeud!=src) {
	// 			// on l'ajoute au chemin
	// 			this.ajoutCase(noeud, date);
	// 		}
	// 		// on supprime ce noeud
	// 		noeuds.remove(noeud);
	// 		// on s'occupe des noeuds restants
	// 		// on met à jour les distances pour tous les voisins du minimum
	// 		Case voisin = carte.getVoisin(noeud, Direction.SUD);
	// 		if(noeuds.contains(voisin)&&(rbt.possibleDeplacement(voisin))){
	// 			poids = majPoids(poids, noeud, voisin);
	// 		}
	// 		voisin = carte.getVoisin(noeud, Direction.NORD);
	// 		if(noeuds.contains(voisin)&&(rbt.possibleDeplacement(voisin))){
	// 			poids = majPoids(poids, noeud, voisin);
	// 		}
	// 		voisin = carte.getVoisin(noeud, Direction.EST);
	// 		if(noeuds.contains(voisin)&&(rbt.possibleDeplacement(voisin))){
	// 			poids = majPoids(poids, noeud, voisin);
	// 		}
	// 		voisin = carte.getVoisin(noeud, Direction.OUEST);
	// 		if(noeuds.contains(voisin)&&(rbt.possibleDeplacement(voisin))){
	// 			poids = majPoids(poids, noeud, voisin);
	// 		}
	// 	}
	// }
	//
	// /* Met à jour la distance entre le voisin et le noeud */
	// private int[][] majPoids(int[][] poids, Case src, Case voisin) {
	// 	int temps = this.calculTemps(src, voisin);
	// 	int d_src = poids[src.getLigne()][src.getColonne()];
	// 	int d_voisin = poids[voisin.getLigne()][voisin.getColonne()];
	// 	if(d_voisin > d_src+temps) {
	// 		poids[voisin.getLigne()][voisin.getColonne()] = d_src+temps;
	// 	}
	// 	return poids;
	// }
	//
	// /* renvoie la case dont le poids est minimal parmis toutes les cases restantes */
	// private Case minimum(List<Case> noeuds, int[][] poids) {
	// 	int mini = INFINI;
	// 	Case position = noeuds.get(0);
	// 	for(int i=0;i<noeuds.size();i++) {
	// 		Case noeud = noeuds.get(i);
	// 		int poid = poids[noeud.getLigne()][noeud.getColonne()];
	// 		if( poid < mini) {
	// 			mini = poid;
	// 			position = noeud;
	// 		}
	// 	}
	// 	return position;
	// }

	 /*
	 * REMPLISSAGE
	 * 		- gérer le déplacement vers la case eau la plus proche
	 * 		- selon le type du robot
	 * 		- génère le chemin correspondant
	 */

}
