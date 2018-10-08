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
	public void setVitesse(int vitesse) {
		if(vitesse > 150) {
			this.vitesse = 150;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" DRONE "+this.getVitesse(this.getPosition().getNature());

	}

}