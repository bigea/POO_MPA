package data.robot;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.enumerate.Direction;
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
	
	public Chenilles(Case pos, Carte carte) {
		super(pos, carte);
		this.setVolume(2000);
		this.setVitesse(60);
	}
	@Override
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
	protected int getTempsRemplissage() {
		return 300;
	}
	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" CHENILLES "+this.getVitesse(this.getPosition().getNature());
	}

	
	/*********************************************
	 * 
	 * METHODES D'INTERVENTION
	 */
	
	@Override
	public void deverserEau(int vol) {
		// TODO Auto-generated method stub
	}

	/*********************************************
	 * 
	 * METHODES DE REMPLISSAGE
	 */
	
	/* Possibilité de remplir sur la case donnée */
	@Override
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
	@Override
	public void remplirEffectif() {
		this.setVolume(2000);
	}

	
	/*********************************************
	 * 
	 * METHODES DE DEPLACEMENT
	 */

	/* Calcul du plus court chemin */
	@Override
	public Chemin plusCourt(Case dest, int date) {
		Chemin chemin = new Chemin(this, date);
		chemin.plusCourt(dest);
		return chemin;
	}
	
	/* Indique si le déplacement est possible pour ce robot */
	@Override
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
