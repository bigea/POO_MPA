package data.robot;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.enumerate.NatureRobot;
import data.enumerate.NatureTerrain;

/**
 * Classe Pattes
 */

public class Pattes extends Robot {

	/**
	 * Classe Pattes (Robot Terrestre)
	 */

	/*********************************************
	 *
	 * METHODES DE BASE
	 */
	/* réservoir infini */
	private static final int INFINI = 300000;

	public Pattes(Case pos) {
		super(pos, NatureRobot.PATTES);
		this.setCapacite(INFINI);
		this.setVitesse(30);
		this.setTempsRemplissage(0);
		this.setTempsVidageComplet(INFINI);
		this.vitesseVidage =  10;
		/*Aucune vitesse de remplissage car le robot a un repertoire infini*/
	}
	public void setVitesse(int vitesse) {
		if (this.getPosition().getNature() == NatureTerrain.ROCHE) {
			this.vitesse = 10;
		} else {
			this.vitesse = vitesse;
		}
	}
	public double getVitesse(NatureTerrain nt) {
		switch(nt) {
			case ROCHE:
				return 10;
			default:
				return this.vitesse;
		}
	}

	private void setCapacite(int capacite){
		this.capacite = capacite;
	}
	public int getCapacite(){
		return this.capacite;
	}

	public int getTempsRemplissage() {
		return this.tempsRemplissage;
	}
	private void setTempsRemplissage(int temps){
		this.tempsRemplissage = temps;
	}

	public int getTempsVidageComplet() {
		return this.tempsVidage;
	}
	private void setTempsVidageComplet(int temps){
		this.tempsVidage = temps;
	}

	public double getVitesseVidage(){
		return this.vitesseVidage;
	}





	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" PATTES "+this.getVitesse(this.getPosition().getNature());
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
	 * METHODES DE REMPLISSAGE : ne se remplit jamais
	 */

	@Override
	public void remplirReservoir() {
		this.setVolume(INFINI);
	}
	@Override
	public boolean possibleRemplissage(Case cas) {
		return false;
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

	/* Déplacement possible partout sauf eau */
	@Override
	public boolean possibleDeplacement(Case voisin) {
		NatureTerrain nature = voisin.getNature();
		switch(nature) {
			case EAU:
				return false;
			default:
				return true;
		}
	}
}
