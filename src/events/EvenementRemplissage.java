package events;

import data.robot.Robot;
import gui.Simulateur;

/**
 * Classe EvenementRemplissage
 */

public class EvenementRemplissage extends Evenement {
	/**
	 * Classe EvenementRemplissage :
	 * 		hérite du modèle Evenement et effectue le remplissage du robot
	 */
	
	private Robot robot;
	
	/*********************************************
	 * 
	 * METHODES DE BASE
	 */
	
	/* Constructeur */
	public EvenementRemplissage(int date, Simulateur sim, Robot rbt) {
		super(date, sim);
		this.setRobot(rbt);
	}
	
	/* Mutateurs */
	public void setRobot(Robot rbt) {
		this.robot = rbt;
	}
	
	/* Accesseur */
	public Robot getRobot() {
		return this.robot;
	}
	
	/*********************************************
	 * 
	 * EXECUTION
	 */
	
	/* Exécution de l'évènement */
	public void execute() {
		/* Le robot gère le remplissage */
		this.getRobot().remplirReservoir();
	}
}