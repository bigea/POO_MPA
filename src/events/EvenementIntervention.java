package events;

import data.Incendie;
import data.robot.Robot;
import gui.Simulateur;

/**
 * Classe EvenementIntervention
 */

public class EvenementIntervention extends Evenement {
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
	public EvenementIntervention(int date, Simulateur sim, Robot rbt, Incendie inc) {
		super(date, sim, rbt);
		this.setIncendie(inc);
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
		/* On déverse l'eau selon la taille de l'incendie et du réservoir */
		int vol_inc = this.getIncendie().getLitre();
		int vol_rbt = this.getRobot().getVolume();
		
		int vol_restant = vol_inc-vol_rbt;
		/* Si robot insuffisant */
		if(vol_restant > 0) {
			this.getRobot().deverserEau(vol_rbt);
			this.getIncendie().setVolume(vol_restant);
		} 
		/* Si robot suffisant */
		else {
			this.getRobot().deverserEau(vol_inc);
			this.getIncendie().setVolume(0);
		}
	}
}