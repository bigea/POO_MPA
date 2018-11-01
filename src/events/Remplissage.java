package events;

import data.robot.Robot;
import gui2.Simulateur;

/**
 * Classe EvenementRemplissage
 */

public class Remplissage extends Evenement {
	/**
	 * Classe EvenementRemplissage :
	 * 		hérite du modèle Evenement et effectue le remplissage du robot (nécessite déplacement peut-être)
	 */

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	public Remplissage(long date, Simulateur sim, Robot rbt, long duree) {
		super(date, sim, rbt, duree);
	}

	/*********************************************
	 *
	 * EXECUTION
	 */

	/* Exécution de l'évènement */
	public void execute() {
		/* Le robot gère le remplissage (déplacement) */
		this.getRobot().remplirReservoir();
	}
}
