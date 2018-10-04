package data.robot;

import data.Case;
import data.enumerate.NatureTerrain;

/**
 * Classe Roues (Robot Terrestre)
 */

public class Roues extends Robot {

	/**
	 * Classe Roues (Robot Terrestre)
	 */
	
	public Roues(Case pos) {
		super(pos);
		this.setVolume(5000);
		this.setVitesse(80);
	}

	@Override
	public void setPosition(Case pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVolume(int vol) {
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
		this.vitesse = vitesse;		
	}
}
