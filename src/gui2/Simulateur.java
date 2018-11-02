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
	private long dateSimulation;
	private Scenario scenario;
	private int nbLignes;
	private int nbColonnes;
	private int tailleCase;
	private DonneesSimulation donnees;

	/* On incrément de INCRE secondes à chaque fois */
	public static final int INCRE = 6;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeurs */

	/* Constructeur quand on n'a pas de donnees */
	public Simulateur(long date, int nbLignes, int nbColonnes, int tailleCase) {
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;
		this.tailleCase = 50;
		this.gui = new GUISimulator(this.nbLignes*this.tailleCase, this.nbColonnes*this.tailleCase, Color.WHITE);
		this.dateSimulation = date;
		this.scenario = new Scenario();
		this.gui.setSimulable(this);
		this.dessinerBase();
	}


	/* Constructeur quand on a des donnees */
	public Simulateur(long date, DonneesSimulation donneesSim) {
		this.donnees = donneesSim;
		Carte carte = this.donnees.getCarte();
		this.nbLignes = carte.getNbLignes();
		this.nbColonnes = carte.getNbColonnes();
		this.tailleCase = 800/this.nbLignes;
		this.tailleCase = 35;
		this.gui = new GUISimulator(1000, 800, Color.WHITE);
		this.dateSimulation = date;
		this.scenario = new Scenario();
		this.gui.setSimulable(this);
		this.dessinerDonnees();
	}


	/* Mutateur */
	public void setDateSimulation(long date) {
		this.dateSimulation = date;
	}
	/* Accesseur */
	public GUISimulator getGui() {
		return this.gui;
	}
	public long getDateSimulation() {
		return this.dateSimulation;
	}
	public Evenement getEvenement(long date) throws Exception  {
		return this.scenario.getEvenement(date);
	}
	public ArrayList<Evenement> getScenario(){
		return this.scenario.getScenario();
	}
	public DonneesSimulation getDonnees() {
		return this.donnees;
	}
	public int getNbLignes() {
		return this.nbLignes;
	}
	public int getNbColonnes() {
		return this.nbColonnes;
	}
	public int getTailleCase() {
		return this.tailleCase;
	}

	public void next() {
		this.incrementeDate();
		System.out.println("après incrémentation, date est : " + this.dateSimulation);
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
		String path = System.getProperty("user.dir" );
		path = path + "/Ressources/";
		switch(nature_case){
			case EAU:
				couleur_case = Color.BLUE;
				// this.gui.addGraphicalElement(new Rectangle(jReel, iReel, Color.BLACK, couleur_case, this.tailleCase));
				this.gui.addGraphicalElement(new ImageElement(jImage, iImage, path + "water.png", this.tailleCase, this.tailleCase, obs));
				break;
			case FORET:
				couleur_case = Color.GREEN;
				// this.gui.addGraphicalElement(new Rectangle(jReel, iReel, Color.BLACK, couleur_case, this.tailleCase));
				this.gui.addGraphicalElement(new ImageElement(jImage, iImage, path + "tree.png", this.tailleCase, this.tailleCase, obs));
				break;
			case ROCHE:
				couleur_case = Color.GRAY;
				// this.gui.addGraphicalElement(new Rectangle(jReel, iReel, Color.BLACK, couleur_case, this.tailleCase));
				this.gui.addGraphicalElement(new ImageElement(jImage, iImage, path + "rock.png", this.tailleCase, this.tailleCase, obs));
				break;
			case TERRAIN_LIBRE:
				couleur_case = Color.WHITE;
				// this.gui.addGraphicalElement(new Rectangle(jReel, iReel, Color.BLACK, couleur_case, this.tailleCase));
				this.gui.addGraphicalElement(new ImageElement(jImage, iImage, path + "free.png", this.tailleCase, this.tailleCase, obs));
				break;
			case HABITAT:
				couleur_case = Color.ORANGE;
				// this.gui.addGraphicalElement(new Rectangle(jReel, iReel, Color.BLACK, couleur_case, this.tailleCase));
				this.gui.addGraphicalElement(new ImageElement(jImage, iImage, path + "house.png", this.tailleCase, this.tailleCase, obs));
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
		String path = System.getProperty("user.dir" );
		path = path + "/Ressources/";
		this.gui.addGraphicalElement(new ImageElement(x, y, path + "fire.png", this.tailleCase, this.tailleCase, obs));
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
		int xImage = x - this.tailleCase/2 + 2;
		int yImage = y - this.tailleCase/2 + 2;
		ImageObserver obs = new Panel();
		String path = System.getProperty("user.dir" );
		path = path + "/Ressources/";
		switch (robotType) {
			case CHENILLES:
				//this.gui.addGraphicalElement(new Oval(x, y, Color.GREEN, Color.GREEN, this.tailleCase/2, this.tailleCase/2));
				this.gui.addGraphicalElement(new ImageElement(xImage, yImage, path + "chenilles.png", this.tailleCase, this.tailleCase, obs));
				// this.gui.addGraphicalElement(new Text(x, y, Color.BLACK, "CHENILLES"));
				break;
			case DRONE:
				//this.gui.addGraphicalElement(new Oval(x, y, Color.BLACK, Color.BLACK, this.tailleCase/2, this.tailleCase/2));
				this.gui.addGraphicalElement(new ImageElement(xImage, yImage, path + "drones.png", this.tailleCase, this.tailleCase, obs));
				// this.gui.addGraphicalElement(new Text(x, y, Color.WHITE, "DRONE"));
				break;
			case PATTES:
				// this.gui.addGraphicalElement(new Oval(x, y, Color.MAGENTA, Color.MAGENTA, this.tailleCase/2, this.tailleCase/2));
				this.gui.addGraphicalElement(new ImageElement(xImage, yImage, path + "pattes.png", this.tailleCase, this.tailleCase, obs));
				// this.gui.addGraphicalElement(new Text(x, y, Color.BLACK, "PATTES"));
				break;
			case ROUES:
				// this.gui.addGraphicalElement(new Oval(x, y, Color.YELLOW, Color.YELLOW, this.tailleCase/2, this.tailleCase/2));
				this.gui.addGraphicalElement(new ImageElement(xImage, yImage, path + "roues.png", this.tailleCase, this.tailleCase, obs));
				// this.gui.addGraphicalElement(new Text(x, y, Color.BLACK, "ROUES"));
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
	 * METHODES GESTION DES EVENEMENTS
	 */

	/* Ajout d'un évènement au simulateur */
	public void ajouteEvenement(Evenement event) {
		this.scenario.ajouteEvenement(event);
	}

	/* Incrémente date et exécute tous les évènements jusqu'à cette date */
	public void incrementeDate() {
		long avant = this.dateSimulation;
		long apres = this.dateSimulation+INCRE;
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
