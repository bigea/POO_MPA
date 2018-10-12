package gui;

import events.Evenement;

/**
 * Classe Simulateur
 */

public class Simulateur implements Simulable {
	
	/**
	 * Classe Simulateur
	 */
	
	private int dateSimulation;
	private Evenement[][] events;
	
	/* Constructeur */
	public Simulateur(int date) {
		this.setDateSimulation(date);
		this.events = new Evenement[0][0];
	}
	
	
	/* Mutateur */
	public void setDateSimulation(int date) {
		this.dateSimulation = date;
	}
	/* Accesseur */
	public int getDateSimulation() {
		return this.dateSimulation;
	}
	
	
	/* Ajout d'évènements */
	public void ajouteEvenement(Evenement event) {
		//TODO
	}
	/* Incrémente date et exécute tous les évènements jusqu'à cette date */
	public void incrementeDate() {
		int avant = this.dateSimulation;
		this.dateSimulation += 1;
		
		for(int dt = avant; dt < this.dateSimulation; dt++) {
			for(int e = 0; e < this.events[dt].length; e++) {
				this.events[dt][e].execute();
			}
		}
	}
	/* Simulation terminée */
	public boolean simulationTerminee() {
		if(this.dateSimulation == this.events.length) {
			return true;
		}
		return false;
	}

}
