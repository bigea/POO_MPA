package data.robot;

import data.Case;
import data.enumerate.NatureTerrain;

/**
 * Classe Robot Drone
 */

public class Drone extends Robot {

    /**
     * Classe Drone
     * 		Hiérarchie des classes avec Drone => Robot
     */
	
	public Drone(Case pos) {
		super(pos);
		this.setVolume(10000);
		this.setVitesse(100);
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
		if(vitesse > 150) {
			this.vitesse = 150;
		}
		this.vitesse = vitesse;		
	}
}