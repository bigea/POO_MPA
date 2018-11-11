package data.robot;

import data.Carte;
import data.Case;
import data.enumerate.NatureRobot;
import data.enumerate.NatureTerrain;
import gui2.Simulateur;

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
		/* Valeurs par défault*/
		super(pos, NatureRobot.PATTES, 0);
		this.setCapacite(INFINI);
		this.setCapaciteMaximale(INFINI);
		this.setVitesse(30);
		this.setTempsRemplissageComplet(0);
		this.setTempsVidageComplet(INFINI);
		this.setTempsVidageUnitaire(1);
		this.setVolumeVidageUnitaire(10);
		this.vitesseVidage = 10;
	}
	public void setVitesse(double vitesse) {
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
    public void setVitesseRemplissage(long tempsRemplissageComplet, int capacite) {
		this.vitesseRemplissage = INFINI;
	}
	@Override
	public long getTempsRemplissageComplet() {
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

	// public void deverserEau(int vol) {
	// 	// TODO Auto-generated method stub
	//
	// }


	/*********************************************
	 *
	 * METHODES DE REMPLISSAGE : ne se remplit jamais
	 */
	 @Override
	public  void ordreRemplissage(Simulateur sim, long date) {
		return;
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
