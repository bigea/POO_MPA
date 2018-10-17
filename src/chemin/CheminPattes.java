package chemin;

import data.Carte;
import data.Case;
import data.enumerate.Direction;
import data.robot.Robot;

/**
 * Classe CheminPattes
 */
public class CheminPattes extends Chemin {

	/**
	* Classe CheminRoues : pour le robot à pattes
	* 		Séquence de cases et de date qui définissent
	* 		le plus court chemin pour aller à une case
	* 		pour un robot donné
	*/

	public CheminPattes(Robot rbt, int dateSimulation) {
		super(rbt, dateSimulation);
	}
	
	@Override
	/* Calcul du plus court chemin : cas du drone */
	public void plusCourt(Case dest) {
		Robot rbt = this.getRobot();
		Carte carte = rbt.getCarte();
		Case src = rbt.getPosition();
		int x_src = src.getLigne();
		int y_src = src.getColonne();
		int x_dest = dest.getLigne();
		int y_dest = dest.getColonne();
		int date = this.getDateSimulation();
		/* Tant qu'on a pas atteint la destination */
		while((x_src != x_dest)&&(y_src != y_dest)) {
			Direction direction = null;
			/* 	On choisit simplement la direction
			 * 		qui nous rapproche le plus de la dest
			 * 		en "ligne droite" sans se soucier de la nature du terrain
			 */
			//TODO
			/* On ajoute le déplacement élémentaire dans le simulateur
			 * 		La date dépend de la durée du déplacement donc de la vitesse du robot
			 * 		Temps calculé dans calculTemps()
			 * 		Provisoirement +1 à chaque déplacement
			 */
			Case deplacement = carte.getVoisin(src, direction);
			date += this.calculTemps(src, deplacement);
			this.ajoutCase(deplacement, date);
			/* On réactualise la case qui est virtuellement la position du robot */
			src = deplacement;
			x_src = src.getLigne();
			y_src = src.getColonne();
		}		
	}
}
