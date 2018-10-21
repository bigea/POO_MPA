package data.robot;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.enumerate.Direction;
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
	
	public Roues(Case pos, Carte carte) {
		super(pos, carte);
		this.setVolume(5000);
		this.setVitesse(80);
	}

	@Override
	public void setVitesse(int vitesse) {
		if (this.getPosition().getNature() != NatureTerrain.TERRAIN_LIBRE && this.getPosition().getNature() != NatureTerrain.HABITAT) {
			this.vitesse = 0;
		} else {
			this.vitesse = vitesse;
		}
	}
	@Override
	public double getVitesse(NatureTerrain nt) {
		return this.vitesse;
	}
	@Override
	protected int getTempsRemplissage() {
		return 600;
	}
	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" ROUES "+this.getVitesse(this.getPosition().getNature());
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
		this.setVolume(5000);
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
