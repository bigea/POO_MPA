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

	public Drone(Case pos) {
		super(pos, NatureRobot.DRONE);
		this.setCapacite(10000);
		this.setVitesse(100);
		this.setTempsRemplissage(30*60);
		this.setTempsVidageComplet(30);
		this.vitesseRemplissage =  (float)this.capacite/(float)this.getTempsRemplissage();
		/*Le drone n'a pas de vitesse de vidage car son intervention unitaire vide la totalité de son réservoir*/
	}

	public void setVitesse(int vitesse) {
		if(vitesse > 150) {
			this.vitesse = 150;
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
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" DRONE "+this.getVitesse(this.getPosition().getNature());
	}


	/*********************************************
	 *
	 * METHODES D'INTERVENTION
	 */

	public void deverserEau(int vol) {
		// TODO Auto-generated method stub

	}


	/*********************************************
	 *
	 * METHODES DE REMPLISSAGE
	 */

	/* Possibilité de remplir sur la case donnée */
	public boolean possibleRemplissage(Case cas, Carte carte) {
		if(cas.getNature() == NatureTerrain.EAU) {
			return true;
		}
		return false;
	}

	/* Remplissage effectif */
	public void remplirReservoir() {
		this.setCapacite(10000);
	}


	/*********************************************
	 *
	 * METHODES DE DEPLACEMENT
	 */

	/* Calcul du plus court chemin */
	protected Chemin plusCourt(Case dest, int date, Carte carte) {
		Case src = this.getPosition();
		int x_src = src.getColonne();
		int y_src = src.getLigne();
		int x_dest = dest.getColonne();
		int y_dest = dest.getLigne();
		Chemin chemin = new Chemin();
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
			Case voisin = carte.voisin(src, direction);
			chemin.ajoutCaseQueue(voisin, date, this, carte);
			/* On réactualise la case qui est virtuellement la position du robot */
			x_src = voisin.getColonne();
			y_src = voisin.getLigne();
		}
		return chemin;
	}

	/* Déplacement possible du drone partout */
	public boolean possibleDeplacement(Case voisin) {
		return true;
	}
}
