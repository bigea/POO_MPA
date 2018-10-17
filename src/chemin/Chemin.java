package chemin;

import java.util.ArrayList;
import java.util.List;

import data.Case;
import data.robot.Robot;

/**
 * Classe Chemin
 */
public class Chemin {
	/**
	* Classe Chemin :
	* 		Séquence de cases et de dates
	*/
	
	private Robot rbt;
	private int dateSimulation;
	private int nbCase;
	private List<Case> chemin;
	private List<Integer> dates;
	
	public Chemin(Robot rbt, int dateSimulation) {
		this.setRobot(rbt);
		this.setDateSimulation(dateSimulation);
		this.setNbCase(0);
		this.setChemin();
		this.setDates();
	}
	
	/* Mutateurs - Accesseurs */
	public Robot getRobot() {
		return rbt;
	}
	public void setRobot(Robot rbt) {
		this.rbt = rbt;
	}
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
	public int getDateSimulation() {
		return dateSimulation;
	}
	public void setDateSimulation(int dateSimulaion) {
		this.dateSimulation = dateSimulaion;
	}
	
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
		this.chemin.add(cas);
		this.dates.add(date);
		this.nbCase += 1;
	}
}
