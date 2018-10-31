package events;

import data.Case;
import data.enumerate.Direction;
import data.robot.Robot;
import gui2.Simulateur;

/**
 * Classe EvenementDeplacementDirection
 */

public class EvenementDeplacementDirection extends Evenement {
	/**
	 * Classe EvenementDeplacementDirection :
	 * 		hérite du modèle Evenement et effectue le déplacement du robot d'une case vers une direction
	 */

	private Direction direct;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	public EvenementDeplacementDirection(int date, Simulateur sim, Robot rbt, int duree, Direction direct) {
		super(date, sim, rbt, duree);
		this.setDirection(direct);
	}

	/* Mutateurs */
	public void setDirection(Direction direct) {
		this.direct = direct;
	}

	/* Accesseur */
	public Direction getDirection() {
		return this.direct;
	}

	/*********************************************
	 *
	 * EXECUTION
	 */

	@Override
	public void execute() throws Exception {
	// 	/* On récupère la case voisine dans la direction donnée */
	// 	Case voisin = this.getSimulateur().getDonnees().getCarte().getVoisin(this.getRobot().getPosition(), this.getDirection());
	// 	if(voisin == null) {
	// 		throw new Exception("Déplacement impossible");
	// 	}
	// 	/* Le robot gère le déplacement vers cette case */
	// 	this.getRobot().deplacementCase(voisin, this.getSimulateur(), this.getDate());
	}
}
