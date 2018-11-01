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
	 protected static final int capaciteMaximale = INFINI;
	/*********************************************
	 *
	 * METHODES DE BASE
	 */
	/* réservoir infini */
	private static final int INFINI = 300000;

	public Pattes(Case pos) {
		/* Valeurs par défault*/
		super(pos, NatureRobot.PATTES);
		this.setCapacite(INFINI);
		this.setVitesse(30);
		this.setTempsRemplissage(0);
		this.setTempsVidageComplet(INFINI);
		this.vitesseVidage =  10;
	}
	public void setVitesse(int vitesse) {
		if (this.getPosition().getNature() == NatureTerrain.ROCHE) {
			this.vitesse = 10;
		} else {
			this.vitesse = vitesse;
		}
	}
	@Override
	public double getVitesse(NatureTerrain nt) {
		switch(nt) {
			case ROCHE:
				return 10;
			default:
				return this.vitesse;
		}
	}
	@Override
    public void setVitesseRemplissage(int tempsRemplissage, int capacite) {
		this.vitesseRemplissage = INFINI;
	}
	@Override
	public int getTempsRemplissage() {
		return 0;
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
	public  void ordreRemplissage(Simulateur sim) {

	}
	public void remplirReservoir() {
		this.setCapacite(INFINI);
	}

	public boolean possibleRemplissage(Case cas, Carte carte) {
		return false;
	}


	/*********************************************
	 *
	 * METHODES DE DEPLACEMENT
	 */

	/* Déplacement possible partout sauf eau */
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
