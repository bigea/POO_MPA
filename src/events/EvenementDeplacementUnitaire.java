package events;

import data.Case;
import data.robot.Robot;
import gui.Simulateur;

/**
 * Classe EvenementDeplacementUnitaire
 */

public class EvenementDeplacementUnitaire extends Evenement {
	/**
	 * Classe EvenementDeplacementUnitaire :
	 * 		hérite du modèle Evenement et effectue le déplacement du robot d'un case
	 */
	
	private Robot robot;
	private Case destination;
	
	/*********************************************
	 * 
	 * METHODES DE BASE
	 */
	
	/* Constructeur */
	public EvenementDeplacementUnitaire(int date, Simulateur sim, Robot rbt, Case dest) {
		super(date, sim);
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
	
	/*********************************************
	 * 
	 * EXECUTION
	 */
	
	/* Exécution de l'évènement
	 * 		Evènement unitaire, c'est le seul qui modifie effectivement la position du robot
	 * 		après être forcément créé par le robot lui-même lors du calcul du plus court chemin
	 */
	public void execute() {
		this.getRobot().setPosition(this.getCase());
		EvenementMessage event = new EvenementMessage(this.getDate(), this.getSimulateur(), "Le robot est bien arrivé");
		event.execute();
	}
}