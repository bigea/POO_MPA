package data.robot;

import data.Carte;
import data.Case;
import data.enumerate.NatureTerrain;
import gui.Simulateur;

/**
 * Classe Chenilles
 */


public class Chenilles extends Robot {

	/**
	 * Classe Chenilles (Robot Terrestre)
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
		// TODO Auto-generated method stub
		return this.vitesse;
	}

	@Override
	public void deverserEau(int vol) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remplirReservoir() {
		this.setVolume(2000);
	}

	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" CHENILLES "+this.getVitesse(this.getPosition().getNature());

	}

	@Override
	/* Déplacement du robot vers une case 
	 * 		Calcul du plus court chemin en prenant en compte la nature du robot et du terrain
	 * 		Ajout des évènements au simulateur
	 */
	public void deplacementCase(Case dest, Simulateur sim) {
		//TODO
		
	}
}
