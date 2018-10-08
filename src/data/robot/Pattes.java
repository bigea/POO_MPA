package data.robot;

import data.Case;
import data.enumerate.NatureTerrain;

/**
 * Classe Pattes
 */

public class Pattes extends Robot {
	
	/**
	 * Classe Pattes (Robot Terrestre)
	 */
	
	public Pattes(Case pos) {
		super(pos);
		this.setVolume(20000);
		this.setVitesse(30);
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
		if (this.getPosition().getNature() == NatureTerrain.ROCHE) {
			this.vitesse = 10;
		}
		this.vitesse = vitesse;
	}
}
