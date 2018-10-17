package data.robot;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.enumerate.NatureTerrain;
import gui.Simulateur;

/**
 * Classe Roues (Robot Terrestre)
 */

public class Roues extends Robot {

	/**
	 * Classe Roues (Robot Terrestre)
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
		// TODO Auto-generated method stub
		return this.vitesse;
	}

	@Override
	public void deverserEau(int vol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remplirReservoir() {
		this.setVolume(5000);
	}


	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" ROUES "+this.getVitesse(this.getPosition().getNature());
	}

	@Override
	public void deplacementCase(Case cas, Simulateur sim) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Chemin plusCourt(Case dest, int date) {
		// TODO Auto-generated method stub
		return null;
	}

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
