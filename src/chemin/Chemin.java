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

	// private int dateSimulation;
	private int nbCase;
	private List<Case> chemin;
	private int nbDate;
	private List<Integer> dates;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	public Chemin() {
		this.setNbCase(0);
		this.setChemin();
		this.setNbDate(0);
		this.setDates();
	}

	/* Mutateurs - Accesseurs */
	// public Robot getRobot() {
	// 	return rbt;
	// }
	// public void setRobot(Robot rbt) {
	// 	this.rbt = rbt;
	// }
	public List<Case> getChemin() {
		return chemin;
	}
	public void setChemin() {
		this.chemin = new ArrayList<Case>();
	}
	public List<Integer> getDates() {
		return dates;
	}
	public void setDates() {
		this.dates = new ArrayList<Integer>();
	}
	public int getNbCase() {
		return nbCase;
	}
	public void setNbCase(int nbCase) {
		this.nbCase = nbCase;
	}
	public int getNbDate() {
		return nbDate;
	}
	public void setNbDate(int nbDate) {
		this.nbDate = nbDate;
	}
	// public int getDateSimulation() {
	// 	return dateSimulation;
	// }
	// public void setDateSimulation(int dateSimulaion) {
	// 	this.dateSimulation = dateSimulaion;
	// }
	/* Ajout d'une case au chemin */
	public void ajoutCase(Case cas, int date, Robot robot) { 	// parametre robot car on en a besoin dans calculTemps
		if (nbDate>0 && nbCase>0) {
			int derniereDate = this.dates.get(nbDate-1);
			Case derniereCase = this.chemin.get(nbCase-1);
			this.dates.add(derniereDate + this.calculTemps(derniereCase, cas, robot));
		} else {
			this.dates.add(date);
		}
		this.chemin.add(cas);
		this.nbCase += 1;
		this.nbDate += 1;
	}

	public int tempsChemin(Robot robot) {
		int temps = this.dates.get(chemin.getNbDates()-1) - this.dates.get(0);
		Case avantDerniereCase = this.chemin.get(this.nbCase-2);
		Case derniereCase = this.chemin.get(this.nbCase-1);
		temps = temps + calculTemps(avantDerniereCase, derniereCase, robot);
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
	// 	int date = this.getDateSimulation();
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

	/* Met à jour la distance entre le voisin et le noeud */
	private int[][] majPoids(int[][] poids, Case src, Case voisin) {
		int temps = this.calculTemps(src, voisin);
		int d_src = poids[src.getLigne()][src.getColonne()];
		int d_voisin = poids[voisin.getLigne()][voisin.getColonne()];
		if(d_voisin > d_src+temps) {
			poids[voisin.getLigne()][voisin.getColonne()] = d_src+temps;
		}
		return poids;
	}

	/* renvoie la case dont le poids est minimal parmis toutes les cases restantes */
	private Case minimum(List<Case> noeuds, int[][] poids) {
		int mini = INFINI;
		Case position = noeuds.get(0);
		for(int i=0;i<noeuds.size();i++) {
			Case noeud = noeuds.get(i);
			int poid = poids[noeud.getLigne()][noeud.getColonne()];
			if( poid < mini) {
				mini = poid;
				position = noeud;
			}
		}
		return position;
	}

	/* Calcul du temps de déplacement
	 * 		Dépend de la vitesse du robot et de la nature du terrain,
	 * 		donc de la nature du terrain sur la moitié de la première
	 * 		case et la moitié de la seconde case
	 */
	private int calculTemps(Case src, Case voisin, Robot robot) {
		/* Vitesse sur la case src en m/s*/
		double vitesse_src = (robot.getVitesse(src.getNature()))/3.6;
		/* Vitesse sur la case dest en m/s */
		double vitesse_voisin = (robot.getVitesse(voisin.getNature()))/3.6;
		/* Taille de la case, on prend comme distance la moitié */
		int taille_case = robot.getCarte().getTailleCases();
		int distance = taille_case/2;
		/* Calcul du temps sur les deux terrains */
		double temps_src = distance/vitesse_src;
		double temps_voisin = distance/vitesse_voisin;
		/* On renvoie le temps, arrondi au supérieur */
		return (int) Math.round(temps_src+temps_dest);
	}


	/*********************************************
	 *
	 * REMPLISSAGE
	 * 		- gérer le déplacement vers la case eau la plus proche
	 * 		- selon le type du robot
	 * 		- génère le chemin correspondant
	 */

	// public void getEau() {
	// 	//TODO
	// }
}
