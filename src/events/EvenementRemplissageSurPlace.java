package events;

import data.robot.Robot;
import gui.Simulateur;

/**
 * Classe EvenementRemplissageSurPlace
 */

public class EvenementRemplissageSurPlace extends Evenement {
	/**
	 * Classe EvenementRemplissageSurPlace :
	 * 		hérite du modèle Evenement et effectue le remplissage effectif du robot (pas besoin de déplacement)
	 */
		
	/*********************************************
	 * 
	 * METHODES DE BASE
	 */
	
	/* Constructeur */
	public EvenementRemplissageSurPlace(int date, Simulateur sim, Robot rbt) {
		super(date, sim, rbt);
		this.setRobot(rbt);
	}
	
	/*********************************************
	 * 
	 * EXECUTION
	 */
	
	/* Exécution de l'évènement */
	public void execute() {
		/* Le robot se remplit */
		this.getRobot().remplirEffectif();
	}
}