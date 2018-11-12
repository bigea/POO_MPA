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
	public Remplissage(long date, Simulateur sim, Robot rbt) {
		super(date, sim, rbt);
	}

	@Override
	public String toString() {
		String chaine = new String();
		chaine += "Remplissage (" + this.getDate() + ", " + this.getRobot().getNature() + ")";
		return chaine;
	}

	/*********************************************
	 *
	 * EXECUTION
	 */

	/* Exécution de l'évènement */
	public void execute() {
		this.getRobot().remplirReservoir();
	}
}
