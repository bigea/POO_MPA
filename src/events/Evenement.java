package events;

import data.robot.Robot;
import gui2.Simulateur;

/**
 * Classe abstraite Evenement
 */

public abstract class Evenement {
	/**
	 * Classe abstraite Evenement :
	 * 		héritée de sous-classes qui représenteront des évènements réels avec leurs propres propriétés
	 */

	private int date;
	private Simulateur sim;
	private Robot robot;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	public Evenement(int date, Simulateur sim, Robot robot) {
		this.setDate(date);
		this.setRobot(robot);
		this.sim = sim;
	}

	/* Mutateurs */
	public void setDate(int date) {
		this.date = date;
	}
	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	/* Accesseur */
	public int getDate() {
		return this.date;
	}
	public Robot getRobot() {
		return robot;
	}
	public Simulateur getSimulateur() {
		return this.sim;
	}

	/*********************************************
	 *
	 * EXECUTION
	 */

	/* Exécution de l'évènement */
	public abstract void execute() throws Exception;
}
