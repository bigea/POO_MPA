package events;

import data.Case;
import data.robot.Robot;
import gui2.Simulateur;

/**
 * Classe EvenementDeplacementCase
 */

public class EvenementDeplacementCase extends Evenement{
	/**
	 * Classe EvenementDeplacementCase :
	 * 		hérite du modèle Evenement et effectue le déplacement du robot
	 * 		Jusqu'à une case quelconque
	 */

	private Case destination;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	public EvenementDeplacementCase(int date, Simulateur sim, Robot rbt, int duree, Case dest) {
		super(date, sim, rbt, duree);
		this.setCase(dest);
	}

	/* Mutateurs */
	public void setCase(Case dest) {
		this.destination = dest;
	}

	/* Accesseur */
	public Case getCase() {
		return this.destination;
	}

	/*********************************************
	 *
	 * EXECUTION
	 */

	/* Exécution de l'évènement */
	public void execute() {
		/* Le robot va gérer le déplacement vers la case */
		this.getRobot().deplacementCase(this.getCase(), this.getSimulateur(), this.getDate());
	}
}
