package events;

import data.Case;
import data.enumerate.Direction;
import data.robot.Robot;
import gui.Simulateur;

/**
 * Classe EvenementDeplacementDirection
 */

public class EvenementDeplacementDirection extends EvenementDeplacement {
	/**
	 * Classe EvenementDeplacementDirection :
	 * 		hérite du modèle Evenement et effectue le déplacement du robot d'une case vers une direction
	 */
	
	private Direction direct;
	
	/* Constructeur */
	public EvenementDeplacementDirection(int date, Simulateur sim, Robot rbt, Direction direct) {
		super(date, sim, rbt);
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

	@Override
	public void execute() {
		/* On récupère la case voisine dans la direction donnée */
		Case voisin = this.getRobot().getCarte().getVoisin(this.getRobot().getPosition(), this.getDirection());
		/* Le robot gère le déplacement vers cette case */
		this.getRobot().deplacementCase(voisin, this.getSimulateur());
	}
}