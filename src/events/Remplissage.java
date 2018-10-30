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
	public Remplissage(int date, Simulateur sim, Robot rbt) {
		super(date, sim, rbt);
	}

	/*********************************************
	 *
	 * EXECUTION
	 */

	/* Exécution de l'évènement */
	public void execute() {
		/* Le robot gère le remplissage (déplacement) */
		this.getRobot().ordreRemplissage(this.getSimulateur());
	}
}
