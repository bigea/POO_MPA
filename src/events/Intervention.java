package events;

import data.Incendie;
import data.robot.Robot;
import gui2.Simulateur;

/**
 * Classe EvenementIntervention
 */

public class Intervention extends Evenement {
	/**
	 * Classe EvenementIntervention :
	 * 		hérite du modèle Evenement et effectue l'extinction d'un incendie par un robot
	 */

	private Incendie incendie;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	public Intervention(int date, Simulateur sim, Robot rbt, int duree Incendie inc) {
		super(date, sim, rbt, duree);
		this.setIncendie(inc);
	}

	/* Mutateurs */
	public void setIncendie(Incendie inc) {
		this.incendie = inc;
	}

	/* Accesseur */
	public Incendie getIncendie() {
		return this.incendie;
	}


	/*********************************************
	 *
	 * EXECUTION
	 */

	/* Exécution de l'évènement */
	public void execute() {
		/* On déverse l'eau selon la taille de l'incendie et du réservoir */
		int vol_inc = this.getIncendie().getLitre(); /*on récupère la quantité d'eau nécessaire pour éteindre l'incendie*/
		int vol_rbt = this.getRobot().getVolume(); /*on récupère la quantité d'eau contenue dans le robot*/

		int vol_encore_necessaire = vol_inc-vol_rbt;
		/* Si eau dans réservoir robot insuffisant */
		if(vol_encore_necessaire > 0) {
			this.getRobot().deverserEau(vol_rbt);
			this.getIncendie().setVolume(vol_restant);
		}
		/* Si eau dans réservoir robot suffisante */
		else {
			this.getRobot().deverserEau(vol_inc);
			this.getIncendie().setVolume(0);
		}
	}
}
