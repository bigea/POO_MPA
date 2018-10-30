package gui2;

import java.util.ArrayList;

import events.Evenement;
import data.*;
import data.enumerate.NatureRobot;
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
import java.awt.image.ImageObserver;

import java.awt.Panel;

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
	private DonneesSimulation donnees;

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
		this.tailleCase = 50;
		this.gui = new GUISimulator(this.nbLignes*this.tailleCase, this.nbColonnes*this.tailleCase, Color.WHITE);
		this.dateSimulation = date;
		this.scenario = new Scenario();
		this.gui.setSimulable(this);
		this.dessinerBase();
	}



	public Simulateur(int date, DonneesSimulation donneesSim) {
		this.donnees = donneesSim;
		Carte carte = this.donnees.getCarte();
		this.nbLignes = carte.getNbLignes();
		this.nbColonnes = carte.getNbColonnes();
		this.tailleCase = 800/this.nbLignes;
		this.tailleCase = 50;
		this.gui = new GUISimulator(1000, 800, Color.WHITE);
		this.dateSimulation = date;
		this.scenario = new Scenario();
		this.gui.setSimulable(this);
		this.gui.addGraphicalElement(new Rectangle(this.tailleCase,0,Color.BLACK, Color.BLACK, 100));
		this.dessinerDonnees();
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
		this.incrementeDate();
	}

	public void restart() {

	}

	/*********************************************
	*
	* METHODES DE DESSIN DE BASE (AVEC GUI)
	*/


	private void dessinerBase() {
		this.gui.reset();
		for (int i=100; i<(this.nbLignes*this.tailleCase)+100; i+=this.tailleCase) {
			for (int j=100; j<(this.nbColonnes*this.tailleCase)+100; j+=this.tailleCase){
				this.gui.addGraphicalElement(new Rectangle(j, i, Color.BLACK, Color.WHITE, this.tailleCase));
			}
		}
	}


	private void dessinerCarte(Carte carte) {
		for (int i=0; i<this.nbLignes; i+=1) {
			for (int j=0; j<this.nbColonnes; j+=1){
				Case currentCase = carte.getCase(i, j);
				this.dessinerCase(currentCase);
			}
		}
	}

	private void dessinerCase(Case currentCase) {
		NatureTerrain nature_case = currentCase.getNature();
		int i = currentCase.getLigne();
		int j = currentCase.getColonne();
		int iReel = this.tailleCase + i*this.tailleCase;
		int jReel = this.tailleCase + j*this.tailleCase;
		int iImage = iReel - this.tailleCase/2 + 2;
		int jImage = jReel - this.tailleCase/2 + 2;
		Color couleur_case = Color.WHITE;
		ImageObserver obs = new Panel();
		switch(nature_case){
			case EAU:
				couleur_case = Color.BLUE;
				this.gui.addGraphicalElement(new Rectangle(jReel, iReel, Color.BLACK, couleur_case, this.tailleCase));
//				this.gui.addGraphicalElement(new ImageElement(iImage, jImage, "/home/ensimag/unison_ensimag/S3/POO/TPL/POO_MPA/Ressources/wave.png", this.tailleCase-4, this.tailleCase-4, obs));
				break;
			case FORET:
				couleur_case = Color.GREEN;
				this.gui.addGraphicalElement(new Rectangle(jReel, iReel, Color.BLACK, couleur_case, this.tailleCase));
				this.gui.addGraphicalElement(new ImageElement(jImage, iImage, "/home/matthias/Bureau/Dropbox/ENSIMAG/2A/POO/POO_MPA/Ressources/tree.png", this.tailleCase-4, this.tailleCase-4, obs));
				break;
			case ROCHE:
				couleur_case = Color.GRAY;
				this.gui.addGraphicalElement(new Rectangle(jReel, iReel, Color.BLACK, couleur_case, this.tailleCase));
				break;
			case TERRAIN_LIBRE:
				couleur_case = Color.WHITE;
				this.gui.addGraphicalElement(new Rectangle(jReel, iReel, Color.BLACK, couleur_case, this.tailleCase));
				break;
			case HABITAT:
				couleur_case = Color.ORANGE;
				this.gui.addGraphicalElement(new Rectangle(jReel, iReel, Color.BLACK, couleur_case, this.tailleCase));
				break;
			default:
				break;

		}
		if (i==1 && j==4) {
			this.gui.addGraphicalElement(new Rectangle(this.tailleCase*2, this.tailleCase*5, Color.BLACK, Color.BLACK, this.tailleCase/2));
		}
	}

	private void dessinerIncendie(int lig, int col) {
		int x = this.tailleCase + col*this.tailleCase - this.tailleCase/2 + 2;
		int y = this.tailleCase + lig*this.tailleCase - this.tailleCase/2 + 2;
		ImageObserver obs = new Panel();
		this.gui.addGraphicalElement(new ImageElement(x, y, "/home/matthias/Bureau/Dropbox/ENSIMAG/2A/POO/POO_MPA/Ressources/fire.png", this.tailleCase, this.tailleCase, obs));
		//this.gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.RED, this.tailleCase));
	}

	private void dessinerTousLesIncendies(int nbIncendies, Incendie[] incendies) {
		for (int i=0; i<nbIncendies; i++){
			Case position = incendies[i].getPosition();
			int lig = position.getLigne();
			int col = position.getColonne();
			this.dessinerIncendie(lig, col);
		}
	}

	private void dessinerRobot(int lig, int col, Robot robot) {
		NatureRobot robotType = robot.getNature();
		int x = this.tailleCase + col*this.tailleCase;
		int y = this.tailleCase + lig*this.tailleCase;
		ImageObserver obs = new Panel();
		int xImage = x - this.tailleCase/2 + 2;
		int yImage = y - this.tailleCase/2 + 2;
		switch (robotType) {
			case CHENILLES:
				//this.gui.addGraphicalElement(new Oval(x, y, Color.GREEN, Color.GREEN, this.tailleCase/2, this.tailleCase/2));
				this.gui.addGraphicalElement(new ImageElement(xImage, yImage, "/home/matthias/Bureau/Dropbox/ENSIMAG/2A/POO/POO_MPA/Ressources/chenilles.png", this.tailleCase-4, this.tailleCase-4, obs));
				this.gui.addGraphicalElement(new Text(x, y, Color.BLACK, "CHENILLES"));
				break;
			case DRONE:
				//this.gui.addGraphicalElement(new Oval(x, y, Color.BLACK, Color.BLACK, this.tailleCase/2, this.tailleCase/2));
				this.gui.addGraphicalElement(new ImageElement(xImage, yImage, "/home/matthias/Bureau/Dropbox/ENSIMAG/2A/POO/POO_MPA/Ressources/drone.png", this.tailleCase-4, this.tailleCase-4, obs));
				this.gui.addGraphicalElement(new Text(x, y, Color.WHITE, "DRONE"));
				break;
			case PATTES:
				this.gui.addGraphicalElement(new Oval(x, y, Color.MAGENTA, Color.MAGENTA, this.tailleCase/2, this.tailleCase/2));
				this.gui.addGraphicalElement(new Text(x, y, Color.BLACK, "PATTES"));
				break;
			case ROUES:
				this.gui.addGraphicalElement(new Oval(x, y, Color.YELLOW, Color.YELLOW, this.tailleCase/2, this.tailleCase/2));
				this.gui.addGraphicalElement(new Text(x, y, Color.BLACK, "ROUES"));
				break;
			default:
				break;
		}
	}

	private void dessinerTousLesRobots(int nbRobots, Robot[] robots) {
		for (int i=0; i<nbRobots; i++){
			Case position = robots[i].getPosition();
			int lig = position.getLigne();
			int col = position.getColonne();
			//NatureRobot nature = robots[i].getNature();
			this.dessinerRobot(lig, col, robots[i]);
		}
	}

	private void dessinerDonnees() {
		Carte carte = this.donnees.getCarte();
		Incendie[] incendies = this.donnees.getIncendies();
		Robot[] robots = this.donnees.getRobots();
		//int nbLignes = carte.getNbLignes();
		//int nbColonnes = carte.getNbColonnes();
		//int tailleCase = carte.getTailleCases();
		int nbIncendies = this.donnees.getNbIncendies();
		int nbRobots = this.donnees.getNbRobots();
		//this.nbLignes = nbLignes;
		//this.nbColonnes = nbColonnes;
		//this.tailleCase = tailleCase;
		//this.tailleCase = 50;
		//this.gui.reset();
		this.dessinerCarte(carte);
		this.dessinerTousLesIncendies(nbIncendies, incendies);
		this.dessinerTousLesRobots(nbRobots, robots);
	}

	/*********************************************
	 *
	 * METHODES DE REDESSIN (MOUVEMENT)
	 */

	// private void bougerRobot(Robot robot, int newLig, int newCol) {
	// 	Case actualPosition = robot.getPosition();
	// 	int actualLig = actualPosition.getLigne();
	// 	int actualCol = actualPosition.getColonne();
	// 	//NatureRobot nature = robot.getNature();
	// 	int x = this.tailleCase + actualCol*this.tailleCase;
	// 	int y = this.tailleCase + actualLig*this.tailleCase;
	// 	this.dessinerCase(actualPosition);
	// 	this.dessinerRobot(newLig, newCol, robot);
	// }

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
		this.dessinerDonnees();
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
