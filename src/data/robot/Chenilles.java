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
		/* Valeurs par défault*/
		super(pos, NatureRobot.CHENILLES, 0);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		this.setCapacite(2000);
		this.setCapaciteMaximale(2000);
		this.setVitesse(60);
		this.setTempsRemplissageComplet(300);
		this.setTempsVidageComplet(160);
		this.setTempsVidageUnitaire(8);
		this.setVolumeVidageUnitaire(100);
		this.vitesseRemplissage =  (double)this.capacite/(double)this.getTempsRemplissageComplet();
		this.vitesseVidage =  (double)this.capacite/(double)this.getTempsVidageComplet();
		System.out.println("dans constructeur, temps remplissage de chenilles est " + this.getTempsRemplissageComplet());
	}

	public void setVitesse(double vitesse) {
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

	// public void deverserEau(int vol) {
	// 	this.setCapacite(this.getCapacite() - vol);
	// }

	/*********************************************
	 *
	 * METHODES DE REMPLISSAGE
	 */

	/* Possibilité de remplir sur la case donnée */
	public boolean possibleRemplissage(Case cas, Carte carte) {
		Case voisin;
		Direction direction;
		if (cas.getLigne() < carte.getNbLignes()-1) {
			direction = Direction.SUD;
			voisin = carte.voisin(cas, direction);
			if(voisin.getNature() == NatureTerrain.EAU) {
				return true;
			}
		}
		if (cas.getLigne() > 0) {
			direction = Direction.NORD;
			voisin = carte.voisin(cas, direction);
			if(voisin.getNature() == NatureTerrain.EAU) {
				return true;
			}
		}
		if (cas.getColonne() < carte.getNbColonnes()-1) {
			direction = Direction.EST;
			voisin = carte.voisin(cas, direction);
			if(voisin.getNature() == NatureTerrain.EAU) {
				return true;
			}
		}
		if (cas.getColonne() > 0) {
			direction = Direction.OUEST;
			voisin = carte.voisin(cas, direction);
			if(voisin.getNature() == NatureTerrain.EAU) {
				return true;
			}
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
