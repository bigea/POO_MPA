package data.robot;

import data.Carte;
import data.Case;
import data.enumerate.NatureTerrain;
import gui.Simulateur;

/**
 * Classe Pattes
 */

public class Pattes extends Robot {
	
	/**
	 * Classe Pattes (Robot Terrestre)
	 */
	
	public Pattes(Case pos, Carte carte) {
		super(pos, carte);
		this.setVolume(200000);
		this.setVitesse(30);
	}
	
	@Override
	public void setVitesse(int vitesse) {
		if (this.getPosition().getNature() == NatureTerrain.ROCHE) {
			this.vitesse = 10;
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
		this.setVolume(200000);
	}

	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" PATTES "+this.getVitesse(this.getPosition().getNature());
	}

	@Override
	public void deplacementCase(Case cas, Simulateur sim) {
		// TODO Auto-generated method stub
		
	}
}
