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
	public Intervention(long date, Simulateur sim, Robot rbt, Incendie inc) {
		super(date, sim, rbt);
		this.setIncendie(inc);
	}

	@Override
	public String toString() {
		String chaine = new String();
		chaine += "Intervention (" + this.getDate() + ", " + this.getRobot().getNature() + ", " + this.getIncendie() + ")";
		return chaine;
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
		this.getRobot().intervenir(this.getSimulateur(), this.getIncendie());
	}
}
