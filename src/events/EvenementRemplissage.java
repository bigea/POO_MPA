package events;

import data.robot.Robot;

/**
 * Classe EvenementRemplissage
 */

public class EvenementRemplissage extends Evenement {
	/**
	 * Classe EvenementRemplissage :
	 * 		hérite du modèle Evenement et effectue le remplissage du robot
	 */
	
	private Robot robot;
	
	/* Constructeur */
	public EvenementRemplissage(int date, Robot rbt) {
		super(date);
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
	
	
	/* Exécution de l'évènement */
	public void execute() {
		/* On remplie le robot */
		this.getRobot().remplirReservoir();
	}
}