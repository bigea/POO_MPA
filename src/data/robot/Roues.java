package data.robot;

import data.Carte;
import data.Case;
import data.enumerate.Direction;
import data.enumerate.NatureRobot;
import data.enumerate.NatureTerrain;

/**
 * Classe Roues (Robot Terrestre)
 */

public class Roues extends Robot {

	/**
	 * Classe Roues (Robot Terrestre)
	 */

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	public Roues(Case pos) {
		super(pos, NatureRobot.ROUES, 0);
		this.setCapacite(5000);
		this.setCapaciteMaximale(5000);
		this.setVitesse(80);
		this.setTempsRemplissageComplet(10*60);
		this.setTempsVidageComplet(50*5);
		this.setTempsVidageUnitaire(5);
		this.setVolumeVidageUnitaire(100);
		this.vitesseRemplissage =  (double)this.capacite/(double)this.getTempsRemplissageComplet();
		this.vitesseVidage =  (double)this.capacite/(double)this.getTempsVidageComplet();
	}


	public void setVitesse(double vitesse) {
		if (this.getPosition().getNature() != NatureTerrain.TERRAIN_LIBRE && this.getPosition().getNature() != NatureTerrain.HABITAT) {
			this.vitesse = 0;
		} else {
			this.vitesse = vitesse;
		}
	}


	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" ROUES "+this.getVitesse(this.getPosition().getNature());
	}


	/*********************************************
	 *
	 * METHODES D'INTERVENTION
	 */

	// public void deverserEau(int vol) {
	// 	// TODO Auto-generated method stub
	// }

	/*********************************************
	 *
	 * METHODES DE REMPLISSAGE
	 */

	/* Possibilité de remplir sur la case donnée */
	@Override
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
	@Override
	public void remplirReservoir() {
		this.setCapacite(5000);
	}

	/*********************************************
	 *
	 * METHODES DE DEPLACEMENT
	 */

	/* Indique si le déplacement est possible dans la case voisine */
	@Override
	public boolean possibleDeplacement(Case voisin) {
		NatureTerrain nature = voisin.getNature();
		switch(nature) {
			case HABITAT:
				return true;
			case TERRAIN_LIBRE:
				return true;
			default:
				return false;
		}
	}
}
