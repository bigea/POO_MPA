package data.robot;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.enumerate.Direction;
import data.enumerate.NatureRobot;
import data.enumerate.NatureTerrain;

/**
 * Classe Robot Drone
 */

public class Drone extends Robot {

    /**
     * Classe Drone
     * 		Hiérarchie des classes avec Drone => Robot
     */

	/*********************************************
	 *
	 * METHODES DE BASE
	 */
	public Drone(Case pos, Carte carte) {
		super(pos, carte, NatureRobot.DRONE);
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
		return this.vitesse;
	}
	@Override
	protected int getTempsRemplissage() {
		return 1800;
	}
	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" DRONE "+this.getVitesse(this.getPosition().getNature());
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
	public boolean possibleRemplissage(Case cas) {
		if(cas.getNature() == NatureTerrain.EAU) {
			return true;
		}
		return false;
	}

	/* Remplissage effectif */
	@Override
	public void remplirReservoir() {
		this.setVolume(10000);
	}


	/*********************************************
	 *
	 * METHODES DE DEPLACEMENT
	 */

	@Override
	/* Calcul du plus court chemin */
	public Chemin plusCourt(Case dest, int date) {
		Carte carte = this.getCarte();
		Case src = this.getPosition();
		int x_src = src.getLigne();
		int y_src = src.getColonne();
		int x_dest = dest.getLigne();
		int y_dest = dest.getColonne();
		Chemin chemin = new Chemin(this, date);
		/* Tant qu'on a pas atteint la destination */
		Direction direction = null;
		while((x_src != x_dest)||(y_src != y_dest)) {
			/* 	On choisit simplement la direction
			 * 		qui nous rapproche le plus de la dest
			 * 		en "ligne droite" sans se soucier de la nature du terrain
			 */
			if(y_src < y_dest) {
				direction = Direction.SUD;
			} else if (y_src > y_dest) {
				direction = Direction.NORD;
			} else {
				if(x_src < x_dest) {
					direction = Direction.EST;
				} else if (x_src > x_dest) {
					direction = Direction.OUEST;
				}
			}
			/* On ajoute le déplacement élémentaire dans le simulateur
			 * 		La date dépend de la durée du déplacement donc de la vitesse du robot
			 * 		Temps calculé dans calculTemps() par le Chemin
			 */
			Case deplacement = carte.getVoisin(src, direction);
			date += chemin.calculTemps(src, deplacement);
			chemin.ajoutCase(deplacement, date);
			/* On réactualise la case qui est virtuellement la position du robot */
			src = deplacement;
			x_src = src.getColonne();
			y_src = src.getLigne();
		}
		return chemin;
	}

	/* Déplacement possible du drone partout */
	@Override
	public boolean possibleDeplacement(Case voisin) {
		return true;
	}
}
