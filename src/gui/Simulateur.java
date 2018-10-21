package gui;

import java.util.ArrayList;

import events.Evenement;

/**
 * Classe Simulateur
 */

public class Simulateur { //implements Simulable {


	/**
	 * Classe Simulateur :
	 * 		- dateSimulation : date actuelle du simulateur, pour classer les évents
	 * 		- scenario : objet contenant une séquence continuellement ordonnée d'évènements
	 */

	
	//private GUISimulator gui;
	private int dateSimulation;
	private Scenario scenario;
	/* On incrément de INCRE secondes à chaque fois */
	private static final int INCRE = 60;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	public Simulateur(int date) {
		//this.gui = new GUISimulator(800, 600, Color.BLACK);
		this.dateSimulation = date;
		this.scenario = new Scenario();
	}


	/* Mutateur */
	public void setDateSimulation(int date) {
		this.dateSimulation = date;
	}
	/* Accesseur */
	public int getDateSimulation() {
		return this.dateSimulation;
	}
	public Evenement getEvenement(int date) throws Exception  {
		return this.scenario.getEvenement(date);
	}
	public ArrayList<Evenement> getScenario(){
		return this.scenario.getScenario();
	}


	/*********************************************
	 *
	 * METHODES GESTION DES EVENEMENTS
	 */
	
	/* Ajout d'un évènement au simulateur */
	public void ajouteEvenement(Evenement event) {
		this.scenario.ajouteEvenement(event);
	}

	/* Incrémente date et exécute tous les évènements jusqu'à cette date */
	public void incrementeDate() throws Exception {
		int avant = this.dateSimulation;
		int apres = this.dateSimulation+INCRE;
		this.scenario.execute(avant,apres);
		// On incrémente de 60 secondes
		this.dateSimulation += INCRE;
	}

	/* Simulation terminée */
	public boolean simulationTerminee() {
		if(this.scenario.getNbEvents() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
