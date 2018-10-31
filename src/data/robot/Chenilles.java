package data.robot;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.enumerate.Direction;
import data.enumerate.NatureRobot;
import data.enumerate.NatureTerrain;

/**
 * Classe Chenilles
 */


public class Chenilles extends Robot {

	/**
	 * Classe Chenilles (Robot Terrestre)
	 */

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	public Chenilles(Case pos) {
		super(pos, NatureRobot.CHENILLES);
		this.setCapacite(2000);
		this.setVitesse(60);
		this.setTempsRemplissage(5*60);
		this.setTempsVidageComplet(20*8);
		this.vitesseRemplissage =  (float)this.capacite/(float)this.getTempsRemplissage;
		this.vitesseVidage =  (float)this.capacite/(float)this.getTempsVidageComplet;
	}

	public void setVitesse(int vitesse) {
		if(vitesse > 80) {
			this.vitesse = 80;
		} else if (this.getPosition().getNature() == NatureTerrain.FORET) {
			this.vitesse = vitesse/2;
		} else {
			this.vitesse = vitesse;
		}
	}
	public double getVitesse(NatureTerrain nt) {
		switch(nt) {
			case FORET:
				return this.vitesse/2;
			default:
				return this.vitesse;
		}
	}

	private void setCapacite(int capacite){
		this.capacite = capacite;
	}
	public int getCapacite(){
		return this.capacite;
	}

	public int getTempsRemplissage() {
		return this.tempsRemplissage;
	}
	private void setTempsRemplissage(int temps){
		this.tempsRemplissage = temps;
	}

	public int getTempsVidageComplet() {
		return this.tempsVidage;
	}
	private void setTempsVidageComplet(int temps){
		this.tempsVidage = temps;
	}

	public double getVitesseRemplissage(){
		return this.vitesseRemplissage;
	}

	public double getVitesseVidage(){
		return this.vitesseVidage;
	}


	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" CHENILLES "+this.getVitesse(this.getPosition().getNature());
	}


	/*********************************************
	 *
	 * METHODES D'INTERVENTION
	 */

	public void deverserEau(int vol) {
		// TODO Auto-generated method stub
	}

	/*********************************************
	 *
	 * METHODES DE REMPLISSAGE
	 */

	/* Possibilité de remplir sur la case donnée */
	public boolean possibleRemplissage(Case cas) {
		Direction direction = Direction.SUD;
		Case voisin = this.getCarte().getVoisin(cas, direction);
		if(voisin.getNature() == NatureTerrain.EAU) {
			return true;
		}
		direction = Direction.NORD;
		voisin = this.getCarte().getVoisin(cas, direction);
		if(voisin.getNature() == NatureTerrain.EAU) {
			return true;
		}
		direction = Direction.EST;
		voisin = this.getCarte().getVoisin(cas, direction);
		if(voisin.getNature() == NatureTerrain.EAU) {
			return true;
		}
		direction = Direction.OUEST;
		voisin = this.getCarte().getVoisin(cas, direction);
		if(voisin.getNature() == NatureTerrain.EAU) {
			return true;
		}
		return false;
	}

	/* Remplissage effectif */
	public void remplirResevoir() {
		this.setVolume(2000);
	}


	/*********************************************
	 *
	 * METHODES DE DEPLACEMENT
	 */

	/* calcul du plus court chemin */
	public Chemin plusCourt(Case dest, int date, Carte carte) {
		Chemin chemin = new Chemin(this, date);
		chemin = this.plusCourtChemin(dest, date, carte);
		return chemin;
	}

	/*FONCTION UTILE AU CALCUL DE PLUS COURT CHEMIN*/
	private Chemin plusCourtChemin(int dest, int date, Carte carte){
		/*Données nécessaires à l'algorithme*/
		int nbColonnes = carte.getNbColonnes();
		int nbLignes = carte.getNbLignes();
		Case src = this.getPosition();
		int x_src = src.getColonne();
		int y_src = src.getLigne();



	}

	private Initialisation(){

	}

	public void plusCourt(Case dest int date) {
		/* Données nécessaires à l'algorithme */
		int date = this.getDateSimulation();
		Robot rbt = this.getRobot();
		Carte carte = rbt.getCarte();
		int nbColonnes = carte.getNbColonnes();
		int nbLignes = carte.getNbLignes();
		Case src = this.getRobot().getPosition();
		int x_src = src.getLigne();
		int y_src = src.getColonne();

		/* Ensemble des poids */
		int[][] poids = new int[nbLignes][nbColonnes];
		/* Ensemble des cases */
		List<Case> noeuds = new ArrayList<Case>();

		/* Initialisation du graphe (poids infini) */
		for(int l=0; l<nbLignes; l++) {
			for(int c=0; c<nbColonnes; c++) {
				poids[l][c] = INFINI;
				noeuds.add(carte.getCase(l, c));
			}
		}
		poids[x_src][y_src] = 0;

		Case noeud = src;
		/* Tant que l'on a pas parcouru toutes les cases ou atteint la dest */
		while(!noeuds.isEmpty() || src != dest) {
			// on récupère la case dont la distance est minimale
			noeud = minimum(noeuds,poids);
			date += poids[noeud.getLigne()][noeud.getColonne()];
			// si c'est la case src : on ne la prend pas en compte dans le chemin
			if(noeud!=src) {
				// on l'ajoute au chemin
				this.ajoutCase(noeud, date);
			}
			// on supprime ce noeud
			noeuds.remove(noeud);
			// on s'occupe des noeuds restants
			// on met à jour les distances pour tous les voisins du minimum
			Case voisin = carte.getVoisin(noeud, Direction.SUD);
			if(noeuds.contains(voisin)&&(rbt.possibleDeplacement(voisin))){
				poids = majPoids(poids, noeud, voisin);
			}
			voisin = carte.getVoisin(noeud, Direction.NORD);
			if(noeuds.contains(voisin)&&(rbt.possibleDeplacement(voisin))){
				poids = majPoids(poids, noeud, voisin);
			}
			voisin = carte.getVoisin(noeud, Direction.EST);
			if(noeuds.contains(voisin)&&(rbt.possibleDeplacement(voisin))){
				poids = majPoids(poids, noeud, voisin);
			}
			voisin = carte.getVoisin(noeud, Direction.OUEST);
			if(noeuds.contains(voisin)&&(rbt.possibleDeplacement(voisin))){
				poids = majPoids(poids, noeud, voisin);
			}
		}
	}

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


	/* Indique si le déplacement est possible pour ce robot */
	public boolean possibleDeplacement(Case voisin) {
		NatureTerrain nature = voisin.getNature();
		switch(nature) {
			case EAU:
				return false;
			case ROCHE:
				return false;
			default:
				return true;
		}
	}

}
