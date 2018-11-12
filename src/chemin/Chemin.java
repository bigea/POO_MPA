package chemin;

import java.util.ArrayList;
import java.util.List;

import data.Carte;
import data.Case;
import data.robot.Robot;

/**
 * Classe Chemin
 */
public class Chemin {
	private int nbCase;
	private ArrayList<Case> chemin;
	// private int nbDate;
	private ArrayList<Long> dates;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	public Chemin() {
		this.setNbCase(0);
		// this.setNbDate(0);
		this.setChemin();
		this.setDates();
	}

	/* Mutateurs - Accesseurs */
	public ArrayList<Case> getChemin() {
		return this.chemin;
	}
	public void setChemin() {
		this.chemin = new ArrayList<Case>();
	}
	public ArrayList<Long> getDates() {
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
	// public int getNbDate() {
	// 	return this.nbDate;
	// }
	// public void setNbDate(int nbDate) {
	// 	this.nbDate = nbDate;
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
		this.chemin.add(cas);
		this.nbCase += 1;
		// this.nbDate += 1;
	}

	public void ajoutDate(long date){
		this.dates.add(date);
		// this.nbDate += 1;
	}

	/* Renvoie la durée d'un chemin */
	public long tempsChemin(Robot robot, Carte carte) {
		long temps = this.dates.get(this.getNbCase()-1) - this.dates.get(0);
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
}
