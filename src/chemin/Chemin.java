package chemin;

import data.Carte;
import data.Case;
import data.robot.Robot;

/**
 * Classe Chemin
 */
public abstract class Chemin {
	/**
	* Classe Chemin :
	* 		Séquence de cases et de date qui définissent
	* 		le plus court chemin pour aller à une case
	* 		pour un robot donné
	*/
	
	private Robot rbt;
	private int dateSimulation;
	private int nbCase;
	private Case[] chemin;
	private int[] dates;
	
	public Chemin(Robot rbt, int dateSimulation) {
		this.setRobot(rbt);
		this.setDateSimulation(dateSimulation);
		this.setNbCase(0);
		this.setChemin(0);
		this.setDates(0);
	}
	
	/* Mutateurs - Accesseurs */
	public Robot getRobot() {
		return rbt;
	}
	public void setRobot(Robot rbt) {
		this.rbt = rbt;
	}
	public Case[] getChemin() {
		return chemin;
	}
	public void setChemin(int nbCase) {
		this.chemin = new Case[nbCase];
	}
	public int[] getDates() {
		return dates;
	}
	public void setDates(int nbCase) {
		this.dates = new int[nbCase];
	}
	public int getNbCase() {
		return nbCase;
	}
	public void setNbCase(int nbCase) {
		this.nbCase = nbCase;
	}
	public int getDateSimulation() {
		return dateSimulation;
	}
	public void setDateSimulation(int dateSimulaion) {
		this.dateSimulation = dateSimulaion;
	}

	/* Calcul du plus court chemin : héritage */
	public abstract void plusCourt();
	
	/* Calcul du temps de déplacement
	 * 		Dépend de la vitesse du robot et de la nature du terrain,
	 * 		donc de la nature du terrain sur la moitié de la première
	 * 		case et la moitié de la seconde case
	 */
	public int calculTemps(Case src, Case dest) {
		/* Vitesse sur la case src */
		double vitesse_src = this.getRobot().getVitesse(src.getNature());
		/* Vitesse sur la case dest */
		double vitesse_dest = this.getRobot().getVitesse(dest.getNature());
		/* Taille de la case, on prend comme distance la moitié */
		int taille_case = this.getRobot().getCarte().getTailleCases();
		int distance = taille_case/2;
		/* Calcul du temps sur les deux terrains */
		double temps_src = distance/vitesse_src;
		double temps_dest = distance/vitesse_dest;
		/* On renvoie le temps, arrondi au supérieur */
		return (int) Math.round(temps_src+temps_dest);
	}
	
	/* Ajout d'une case au chemin */
	public void ajoutCase(Case cas, int date) {
		int nbCase = this.getNbCase();
		Case[] ancienChemin = this.chemin;
		int[] ancienDates = this.dates;
		this.setChemin(nbCase+1);
		this.setDates(nbCase+1); 
		this.setNbCase(nbCase+1);
		for(int i = 0; i<nbCase; i++) {
			this.chemin[i] = ancienChemin[i];
			this.dates[i] = ancienDates[i];
		}
		this.chemin[nbCase] = cas;
		this.dates[nbCase] = date;
	}
	
}
