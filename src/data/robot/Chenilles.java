package data.robot;

import java.util.zip.DataFormatException;

import data.Case;
import data.enumerate.NatureTerrain;

/**
 * Classe Chenilles
 */


public class Chenilles extends Robot {

	/**
	 * Classe Chenilles (Robot Terrestre)
	 */

	public Chenilles(Case pos) {
		super(pos);
		this.setVolume(2000);
		this.setVitesse(vitesse);
	}

	@Override
	public void setPosition(Case pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getVitesse(NatureTerrain nt) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deverserEau(int vol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remplirReservoir() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVitesse(int vitesse) {
		if(vitesse > 80) {
			this.vitesse = 80;
		}
		if (this.getPosition().getNature() == NatureTerrain.FORET) {
			this.vitesse = vitesse/2;
		}
		this.vitesse = vitesse;		
	}
}
