package events;

import data.Incendie;
import data.robot.Robot;

/**
 * Classe EvenementIntervention
 */

public class EvenementIntervention extends Evenement {
	/**
	 * Classe EvenementIntervention :
	 * 		hérite du modèle Evenement et effectue l'extinction d'un incendie par un robot
	 */
	
	private Robot robot;
	private Incendie incendie;
	
	/* Constructeur */
	public EvenementIntervention(Date date, Robot rbt, Incendie inc) {
		super(date);
		this.setRobot(rbt);
		this.setIncendie(inc);
	}
	
	/* Mutateurs */
	public void setRobot(Robot rbt) {
		this.robot = rbt;
	}
	public void setIncendie(Incendie inc) {
		this.incendie = inc;
	}
	
	/* Accesseur */
	public Robot getRobot() {
		return this.robot;
	}
	public Incendie getIncendie() {
		return this.incendie;
	}
	
	
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
			EvenementMessage event = new EvenementMessage(this.getDate(), "Intervention terminée - Incendie non éteint");
			event.execute();
		} 
		/* Si robot suffisant */
		else {
			this.getRobot().deverserEau(vol_inc);
			this.getIncendie().setVolume(0);
			EvenementMessage event = new EvenementMessage(this.getDate(), "Intervention terminée - Incendie éteint");
			event.execute();
		}
	}
}