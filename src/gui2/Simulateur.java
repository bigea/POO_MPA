package gui2;

import java.util.ArrayList;

import events.Evenement;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.*;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Oval;
import gui.Simulable;
import gui.Text;
import gui.ImageElement;

/**
 * Classe Simulateur
 */

public class Simulateur implements Simulable {


	/**
	 * Classe Simulateur :
	 * 		- dateSimulation : date actuelle du simulateur, pour classer les évents
	 * 		- scenario : objet contenant une séquence continuellement ordonnée d'évènements
	 */


	private GUISimulator gui;
	private int dateSimulation;
	private Scenario scenario;
	private int length;
	private int height;
	private int tailleCase;

	/* On incrément de INCRE secondes à chaque fois */
	private static final int INCRE = 60;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	public Simulateur(int date, int length, int height, int tailleCase) {
		this.length = length;
		this.height = height;
		this.tailleCase = tailleCase;
		this.gui = new GUISimulator(this.length, this.height, Color.WHITE);
		this.dateSimulation = date;
		this.scenario = new Scenario();
		this.gui.setSimulable(this);
		this.dessinerBase();
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

	public void next() {

	}

	public void restart() {

	}

	/*********************************************
	*
	* METHODES DE DESSIN (AVEC GUI)
	*/

	public void dessinerBase() {
		this.gui.reset();
		for (int i=100; i<this.length+100; i+=this.tailleCase) {
			for (int j=100; j<this.height+100; j+=this.tailleCase){
				this.gui.addGraphicalElement(new Rectangle(i, j, Color.BLACK, Color.WHITE, this.tailleCase));
			}
		}
		// à enlever plus tard
		this.dessinerRobot(140, 160, "Chenilles");
		this.dessinerIncendie(160, 140);
	}

	public void dessinerRobot(int x, int y, String robotType) {

		switch (robotType) {
			case "Chenilles":
				// ImageObserver obs = new Imageobserver();
				this.gui.addGraphicalElement(new Oval(x, y, Color.GREEN, Color.GREEN, this.tailleCase-4, this.tailleCase-4));
				break;
			case "Drone":
				this.gui.addGraphicalElement(new Oval(x, y, Color.BLACK, Color.BLACK, this.tailleCase-4, this.tailleCase-4));
				break;
			case "Pattes":
				this.gui.addGraphicalElement(new Oval(x, y, Color.RED, Color.RED, this.tailleCase-4, this.tailleCase-4));
				break;
			case "Roues":
				this.gui.addGraphicalElement(new Oval(x, y, Color.YELLOW, Color.YELLOW, this.tailleCase-4, this.tailleCase-4));
				break;
			default:
				break;
		}
	}

	public void dessinerIncendie(int x, int y) {
		// ImageObserver obs = new Component();
		// this.gui.addGraphicalElement(new ImageElement(x, y, "../../Ressources/fire.png", this.tailleCase-4, this.tailleCase-4, obs));
		this.gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.RED, this.tailleCase));
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
	public void incrementeDate() {
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
