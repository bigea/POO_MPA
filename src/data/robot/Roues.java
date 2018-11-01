package data.robot;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.enumerate.Direction;
import data.enumerate.NatureRobot;
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

	public Roues(Case pos) {
		super(pos, NatureRobot.ROUES);
		this.setCapacite(5000);
		this.setVitesse(80);
		this.setTempsRemplissage(10*60);
		this.setTempsVidageComplet(50*5);
		this.vitesseRemplissage =  (float)this.capacite/(float)this.getTempsRemplissage();
		this.vitesseVidage =  (float)this.capacite/(float)this.getTempsVidageComplet();
	}


	public void setVitesse(int vitesse) {
		if (this.getPosition().getNature() != NatureTerrain.TERRAIN_LIBRE && this.getPosition().getNature() != NatureTerrain.HABITAT) {
			this.vitesse = 0;
		} else {
			this.vitesse = vitesse;
		}
	}
	public double getVitesse(NatureTerrain nt) {
		return this.vitesse;
	}

	public void setCapacite(int capacite){
		this.capacite = capacite;
	}
	public int getCapacite(){
		return this.capacite;
	}

	public int getTempsRemplissage() {
		return this.tempsRemplissage;
	}
	protected void setTempsRemplissage(int temps){
		this.tempsRemplissage = temps;
	}

	public int getTempsVidageComplet() {
		return this.tempsVidage;
	}
	protected void setTempsVidageComplet(int temps){
		this.tempsVidage = temps;
	}

	public double getVitesseRemplissage(){
		return this.vitesseRemplissage;
	}
	public void setVitesseRemplissage(int tempsRemplissage, int capacite) {
		this.vitesseRemplissage = (float)capacite/(float)tempsRemplissage;
	}

	public double getVitesseVidage(){
		return this.vitesseVidage;
	}
	public void setVitesseVidage(int tempsVidage, int capacite) {
		this.vitesseVidage = (float)capacite/(float)tempsVidage;
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
	public boolean possibleRemplissage(Case cas, Carte carte) {
		Direction direction = Direction.SUD;
		Case voisin = carte.voisin(cas, direction);
		if(voisin.getNature() == NatureTerrain.EAU) {
			return true;
		}
		direction = Direction.NORD;
		voisin = carte.voisin(cas, direction);
		if(voisin.getNature() == NatureTerrain.EAU) {
			return true;
		}
		direction = Direction.EST;
		voisin = carte.voisin(cas, direction);
		if(voisin.getNature() == NatureTerrain.EAU) {
			return true;
		}
		direction = Direction.OUEST;
		voisin = carte.voisin(cas, direction);
		if(voisin.getNature() == NatureTerrain.EAU) {
			return true;
		}
		return false;
	}

	/* Remplissage effectif */
	@Override
	public void remplirReservoir() {
		this.setCapacite(5000);
	}

	/*********************************************
	 *
	 * METHODES DE DEPLACEMENT
	 */

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
