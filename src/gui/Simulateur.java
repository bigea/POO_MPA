package gui;

import events.Date;
import events.Evenement;

/**
 * Classe Simulateur
 */

public class Simulateur implements Simulable {
	
	/**
	 * Classe Simulateur
	 */
	
	private Date dateSimulation;
	
	/* Constructeur */
	public Simulateur(Date date) {
		this.setDateSimulation(date);
	}
	
	
	/* Mutateur */
	public void setDateSimulation(Date date) {
		this.dateSimulation = date;
	}
	/* Accesseur */
	public Date getDateSimulation() {
		return this.dateSimulation;
	}
	
	
	/* Ajout d'évènements */
	public void ajouteEvenement(Evenement event) {
		//TODO
	}
	/* Incrémente date et exécute tous les évènements jusqu'à cette date */
	public void incrementeDate() {
		this.getDateSimulation().incremente();
		//TODO
	}
	/* Simulation terminée */
	public boolean simulationTerminee() {
		boolean termine = false;
		//TODO
		return termine;
	}
}
