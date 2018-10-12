package events;

import data.Case;
import data.robot.Robot;

/**
 * Classe EvenementDeplacement
 */

public class EvenementDeplacement extends Evenement {
	/**
	 * Classe EvenementDeplacement :
	 * 		hérite du modèle Evenement et effectue le déplacement du robot
	 */
	
	private Robot robot;
	private Case destination;
	
	/* Constructeur */
	public EvenementDeplacement(Date date, Robot rbt, Case dest) {
		super(date);
		this.setRobot(rbt);
		this.setCase(dest);
	}
	
	/* Mutateurs */
	public void setRobot(Robot rbt) {
		this.robot = rbt;
	}
	public void setCase(Case dest) {
		this.destination = dest;
	}
	
	/* Accesseur */
	public Robot getRobot() {
		return this.robot;
	}
	public Case getCase() {
		return this.destination;
	}
	
	
	/* Exécution de l'évènement */
	public void execute() {
		/* On modifie la position du robot */
		this.getRobot().setPosition(this.getCase());
		/* On prévient que le robot est bien arrivé */
		EvenementMessage event = new EvenementMessage(this.getDate(), "Robot bien arrivé");;
		event.execute();
	}
}