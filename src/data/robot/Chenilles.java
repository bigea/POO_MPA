package data.robot;

import java.util.List;

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
		this.vitesseRemplissage =  (float)this.capacite/(float)this.getTempsRemplissage();
		this.vitesseVidage =  (float)this.capacite/(float)this.getTempsVidageComplet();
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

	public void setCapacite(int capacite){
		this.capacite = capacite;
	}
	public int getCapacite(){
		return this.capacite;
	}

	public int getTempsRemplissage() {
		return this.tempsRemplissage;
	}
	public void setTempsRemplissage(int temps){
		this.tempsRemplissage = temps;
	}

	public int getTempsVidageComplet() {
		return this.tempsVidage;
	}
	public void setTempsVidageComplet(int temps){
		this.tempsVidage = temps;
	}

	public double getVitesseRemplissage(){
		return this.vitesseRemplissage;
	}
	public void setVitesseRemplissage(int tempsRemplissage, int capacite) {
		this.vitesseRemplissage = (float)capacite/(float)tempsRemplissage;
	}

	public double getVitesseVidage(){
		return this.vitesseVidage;
	}
	public void setVitesseVidage(int tempsVidage, int capacite) {
		this.vitesseVidage = (float)capacite/(float)tempsVidage;
	}


	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" CHENILLES "+this.getVitesse(this.getPosition().getNature());
	}


	/*********************************************
	 *
	 * METHODES D'INTERVENTION
	 */

	// public void deverserEau(int vol) {
	// 	this.setCapacite(this.getCapacite() - vol);
	// }

	/*********************************************
	 *
	 * METHODES DE REMPLISSAGE
	 */

	/* Possibilité de remplir sur la case donnée */
	public boolean possibleRemplissage(Case cas, Carte carte) {
		Direction direction = Direction.SUD;
		Case voisin = carte.voisin(cas, direction);
		if(voisin.getNature() == NatureTerrain.EAU) {
			return true;
		}
		direction = Direction.NORD;
		voisin = carte.voisin(cas, direction);
		if(voisin.getNature() == NatureTerrain.EAU) {
			return true;
		}
		direction = Direction.EST;
		voisin = carte.voisin(cas, direction);
		if(voisin.getNature() == NatureTerrain.EAU) {
			return true;
		}
		direction = Direction.OUEST;
		voisin = carte.voisin(cas, direction);
		if(voisin.getNature() == NatureTerrain.EAU) {
			return true;
		}
		return false;
	}

	/* Remplissage effectif */
	public void remplirReservoir() {
		this.setCapacite(2000);
	}


	/*********************************************
	 *
	 * METHODES DE DEPLACEMENT
	 */

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
