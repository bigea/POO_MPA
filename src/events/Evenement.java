package events;

import gui.Simulateur;

/**
 * Classe abstraite Evenement
 */

public abstract class Evenement {
	/**
	 * Classe abstraite Evenement :
	 * 		héritée de sous-classes qui représenteront des évènements réels avec leurs propres propriétés
	 */
	
	private int date;
	private Simulateur sim;
	
	/* Constructeur */
	public Evenement(int date, Simulateur sim) {
		this.setDate(date);
		this.sim = sim;
	}
	
	/* Mutateurs */
	public void setDate(int date) {
		this.date = date;
	}
	
	/* Accesseur */
	public int getDate() {
		return this.date;
	}
	public Simulateur getSimulateur() {
		return this.sim;
	}
	
	/* Exécution de l'évènement */
	public abstract void execute();
}
