package gui2;

import java.util.ArrayList;

import events.Evenement;
import data.*;
import data.enumerate.NatureTerrain;
import data.robot.Robot;
/*
import data.Carte;
import data.Case;
import data.DonneesSimulation;
import data.Incendie;

*/

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
	private int nbLignes;
	private int nbColonnes;
	private int tailleCase;

	/* On incrément de INCRE secondes à chaque fois */
	private static final int INCRE = 60;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	
	public Simulateur(int date, int nbLignes, int nbColonnes, int tailleCase) {
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;
		this.tailleCase = tailleCase;
		this.gui = new GUISimulator(this.nbLignes*this.tailleCase, this.nbColonnes*this.tailleCase, Color.WHITE);
		this.dateSimulation = date;
		this.scenario = new Scenario();
		this.gui.setSimulable(this);
		this.dessinerBase();
	}
	/*
	public Simulateur(int date, DonneesSimulation donnees) {
		this.nbLignes = 10;
		this.nbColonnes = 8;
		this.tailleCase = 10;
		this.gui = new GUISimulator(1000, 800, Color.WHITE);
		this.dateSimulation = date;
		this.scenario = new Scenario();
		this.gui.setSimulable(this);
		this.gererDonnees(donnees);
	}
	*/


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
		for (int i=100; i<(this.nbLignes*this.tailleCase)+100; i+=this.tailleCase) {
			for (int j=100; j<(this.nbColonnes*this.tailleCase)+100; j+=this.tailleCase){
				this.gui.addGraphicalElement(new Rectangle(i, j, Color.BLACK, Color.WHITE, this.tailleCase));
			}
		}
		// à enlever plus tard
		//this.dessinerRobot(140, 160, "Chenilles");
		//this.dessinerIncendie(160, 140);
	}
	
	
	public void dessinerCarte(Carte carte) {
		this.gui.reset();
		for (int i=0; i<this.nbLignes; i+=1) {
			for (int j=0; j<this.nbColonnes; j+=1){
				Case current_case = carte.getCase(i, j);
				NatureTerrain nature_case = current_case.getNature();
				Color couleur_case = Color.WHITE;
				switch(nature_case){
					case EAU:
						couleur_case = Color.BLUE;
						break;
					case FORET:
						couleur_case = Color.GREEN;
						break;
					case ROCHE:
						couleur_case = Color.GRAY;
						break;
					case TERRAIN_LIBRE:
						couleur_case = Color.WHITE;
						break;
					case HABITAT:
						couleur_case = Color.ORANGE;
						break;
					default:
						break;
				
				}
				int iReel = 100 + i*this.tailleCase;
				int jReel = 100 + j*this.tailleCase;
				this.gui.addGraphicalElement(new Rectangle(iReel, jReel, Color.BLACK, couleur_case, this.tailleCase));
				
			}
		}
	}

	public void dessinerRobot(int lig, int col, String robotType) {
		int x = lig*this.tailleCase;
		int y = col*this.tailleCase;
		switch (robotType) {
			case "Chenilles":
				// ImageObserver obs = new Imageobserver();
				this.gui.addGraphicalElement(new Oval(x, y, Color.GREEN, Color.GREEN, this.tailleCase/2, this.tailleCase/2));
				break;
			case "Drone":
				this.gui.addGraphicalElement(new Oval(x, y, Color.BLACK, Color.BLACK, this.tailleCase/2, this.tailleCase/2));
				break;
			case "Pattes":
				this.gui.addGraphicalElement(new Oval(x, y, Color.MAGENTA, Color.MAGENTA, this.tailleCase/2, this.tailleCase/2));
				break;
			case "Roues":
				this.gui.addGraphicalElement(new Oval(x, y, Color.YELLOW, Color.YELLOW, this.tailleCase/2, this.tailleCase/2));
				break;
			default:
				break;
		}
	}

	public void dessinerIncendie(int lig, int col) {
		int x = lig*this.tailleCase;
		int y = col*this.tailleCase;
		// ImageObserver obs = new Component();
		// this.gui.addGraphicalElement(new ImageElement(x, y, "../../Ressources/fire.png", this.tailleCase-4, this.tailleCase-4, obs));
		this.gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.RED, this.tailleCase));
	}
	
	public void dessinerTousLesIncendies(int nbIncendies, Incendie[] incendies) {
		for (int i=0; i<nbIncendies; i++){
			Case position = incendies[i].getPosition();
			int lig = position.getLigne();
			int col = position.getColonne();
			this.dessinerIncendie(lig, col);
		}
	}
	
	public void dessinerTousLesRobots(int nbRobots, Robot[] robots) {
		for (int i=0; i<nbRobots; i++){
			Case position = robots[i].getPosition();
			int lig = position.getLigne();
			int col = position.getColonne();
			this.dessinerRobot(lig, col, "Pattes");
		}
	}
	
	public void gererDonnees(DonneesSimulation donnees) {
		Carte carte = donnees.getCarte();
		Incendie[] incendies = donnees.getIncendies();
		Robot[] robots = donnees.getRobots();
		int nbLignes = carte.getNbLignes();
		int nbColonnes = carte.getNbColonnes();
		int tailleCase = carte.getTailleCases();
		int nbIncendies = donnees.getNbIncendies();
		int nbRobots = donnees.getNbRobots();
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;
		this.tailleCase = 50;
		this.dessinerCarte(carte);
		this.dessinerTousLesIncendies(nbIncendies, incendies);
		this.dessinerTousLesRobots(nbRobots, robots);
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
