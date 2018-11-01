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
	 protected static final int capaciteMaximale = 2000;
	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	public Chenilles(Case pos) {
		/* Valeurs par défault*/
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
	@Override
	public double getVitesse(NatureTerrain nt) {
		switch(nt) {
			case FORET:
				return this.vitesse/2;
			default:
				return this.vitesse;
		}
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
