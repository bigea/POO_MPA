package events;

import data.robot.Robot;
import gui.Simulateur;

/**
 * Classe EvenementDeplacement (héritage de plusieurs types de déplacement)
 */

public abstract class EvenementDeplacement extends Evenement {
	/**
	 * Classe EvenementDeplacement :
	 * 		hérite du modèle Evenement et effectue le déplacement du robot
	 * 		plusieurs types de paramètres : un déplacement unitaire (voisin ou direction) et un déplacement jusqu'à une case quelconque
	 */
	
	private Robot robot;
	
	/* Constructeur */
	public EvenementDeplacement(int date, Simulateur sim, Robot rbt) {
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
	
	/* Exécution de l'évènement */
	public abstract void execute() throws Exception;
}