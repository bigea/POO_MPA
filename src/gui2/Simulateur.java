package gui2;

import events.Evenement;
import data.*;
import data.enumerate.NatureRobot;
import data.enumerate.NatureTerrain;
import data.robot.Robot;
import strategie.*;
import io.LecteurDonnees;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import java.util.ArrayList;
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


	private String[] args;
	private GUISimulator gui;
	private long dateSimulation;
	private Scenario scenario;
	private int nbLignes;
	private int nbColonnes;
	private int tailleCaseAffichage;
	private DonneesSimulation donnees;
	private Chef chef;

	/* On incrément de INCRE secondes à chaque fois */
	public static final int INCRE = 80;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeurs */

	/* Constructeur quand on a des donnees */
	public Simulateur(String[] args) {
		this.args = args;
		try {
			this.donnees = LecteurDonnees.creeDonnees(args[0]);
		} catch (FileNotFoundException e) {
				System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (DataFormatException e) {
				System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
		}
		Carte carte = this.donnees.getCarte();
		this.nbLignes = carte.getNbLignes();
		this.nbColonnes = carte.getNbColonnes();
		// this.tailleCaseAffichage = 800/this.nbLignes;
		this.tailleCaseAffichage = 35;
		this.gui = new GUISimulator(this.nbColonnes*this.tailleCaseAffichage, this.nbLignes*this.tailleCaseAffichage, Color.WHITE);
		this.dateSimulation = 0;
		this.scenario = new Scenario();
		this.gui.setSimulable(this);
		this.dessinerDonnees();
		this.chef = null;
	}


	/* Mutateur */
	public void setDateSimulation(long date) {
		this.dateSimulation = date;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
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
	public int gettailleCaseAffichage() {
		return this.tailleCaseAffichage;
	}
	public Chef getChef() {
		return this.chef;
	}

	public void next() {
		this.incrementeDate();
		if (this.chef != null) {
			this.chef.strategie();
		}
		System.out.println("après incrémentation, date est : " + this.dateSimulation);
	}

	public void restart() {
		this.gui.reset();
		this.setDateSimulation(0);
		try {
			this.donnees = LecteurDonnees.creeDonnees(this.args[0]);
		} catch (FileNotFoundException e) {
				System.out.println("fichier " + this.args[0] + " inconnu ou illisible");
		} catch (DataFormatException e) {
				System.out.println("\n\t**format du fichier " + this.args[0] + " invalide: " + e.getMessage());
		}
		Carte carte = this.donnees.getCarte();
		this.nbLignes = carte.getNbLignes();
		this.nbColonnes = carte.getNbColonnes();
		this.tailleCaseAffichage = 35;
		this.scenario = new Scenario();
		this.gui.setSimulable(this);
		this.dessinerDonnees();
		chef.commencerStrategie();
	}

	/*********************************************
	*
	* METHODES DE DESSIN DE BASE (AVEC GUI)
	*/

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
		int iReel = this.tailleCaseAffichage + i*this.tailleCaseAffichage;
		int jReel = this.tailleCaseAffichage + j*this.tailleCaseAffichage;
		int iImage = iReel - this.tailleCaseAffichage/2 + 2;
		int jImage = jReel - this.tailleCaseAffichage/2 + 2;
		Color couleur_case = Color.WHITE;
		ImageObserver obs = new Panel();
		String path = System.getProperty("user.dir" );
		path = path + "/Ressources/";
		switch(nature_case){
			case EAU:
				this.gui.addGraphicalElement(new ImageElement(jImage, iImage, path + "water.png", this.tailleCaseAffichage, this.tailleCaseAffichage, obs));
				break;
			case FORET:
				this.gui.addGraphicalElement(new ImageElement(jImage, iImage, path + "tree.png", this.tailleCaseAffichage, this.tailleCaseAffichage, obs));
				break;
			case ROCHE:
				this.gui.addGraphicalElement(new ImageElement(jImage, iImage, path + "rock.png", this.tailleCaseAffichage, this.tailleCaseAffichage, obs));
				break;
			case TERRAIN_LIBRE:
				this.gui.addGraphicalElement(new ImageElement(jImage, iImage, path + "free.png", this.tailleCaseAffichage, this.tailleCaseAffichage, obs));
				break;
			case HABITAT:
				this.gui.addGraphicalElement(new ImageElement(jImage, iImage, path + "house.png", this.tailleCaseAffichage, this.tailleCaseAffichage, obs));
				break;
			default:
				break;

		}
		if (i==1 && j==4) {
			this.gui.addGraphicalElement(new Rectangle(this.tailleCaseAffichage*2, this.tailleCaseAffichage*5, Color.BLACK, Color.BLACK, this.tailleCaseAffichage/2));
		}
	}

	private void dessinerIncendie(int lig, int col) {
		int x = this.tailleCaseAffichage + col*this.tailleCaseAffichage - this.tailleCaseAffichage/2 + 2;
		int y = this.tailleCaseAffichage + lig*this.tailleCaseAffichage - this.tailleCaseAffichage/2 + 2;
		ImageObserver obs = new Panel();
		String path = System.getProperty("user.dir" );
		path = path + "/Ressources/";
		this.gui.addGraphicalElement(new ImageElement(x, y, path + "fire.png", this.tailleCaseAffichage, this.tailleCaseAffichage, obs));
	}

	private void dessinerTousLesIncendies(int nbIncendies, ArrayList<Incendie> incendies) {
		Iterator<Incendie> iter = incendies.iterator();
		while(iter.hasNext()) {
			Incendie inc = iter.next();
			Case position = inc.getPosition();
			int lig = position.getLigne();
			int col = position.getColonne();
			this.dessinerIncendie(lig, col);
		}
	}

	private void dessinerRobot(int lig, int col, Robot robot) {
		NatureRobot robotType = robot.getNature();
		int x = this.tailleCaseAffichage + col*this.tailleCaseAffichage;
		int y = this.tailleCaseAffichage + lig*this.tailleCaseAffichage;
		int xImage = x - this.tailleCaseAffichage/2 + 2;
		int yImage = y - this.tailleCaseAffichage/2 + 2;
		ImageObserver obs = new Panel();
		String path = System.getProperty("user.dir" );
		path = path + "/Ressources/";
		switch (robotType) {
			case CHENILLES:
				this.gui.addGraphicalElement(new ImageElement(xImage, yImage, path + "chenilles.png", this.tailleCaseAffichage, this.tailleCaseAffichage, obs));
				break;
			case DRONE:
				this.gui.addGraphicalElement(new ImageElement(xImage, yImage, path + "drones.png", this.tailleCaseAffichage, this.tailleCaseAffichage, obs));
				break;
			case PATTES:
				this.gui.addGraphicalElement(new ImageElement(xImage, yImage, path + "pattes.png", this.tailleCaseAffichage, this.tailleCaseAffichage, obs));
				break;
			case ROUES:
				this.gui.addGraphicalElement(new ImageElement(xImage, yImage, path + "roues.png", this.tailleCaseAffichage, this.tailleCaseAffichage, obs));
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
			this.dessinerRobot(lig, col, robots[i]);
		}
	}

	private void dessinerDonnees() {
		Carte carte = this.donnees.getCarte();
		ArrayList<Incendie> incendies = this.donnees.getIncendies();
		Robot[] robots = this.donnees.getRobots();
		int nbIncendies = this.donnees.getNbIncendies();
		int nbRobots = this.donnees.getNbRobots();
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

	/* Enlève tous les evenements liés à ce robot commencant à partir de date */
	public void supprimeEvenements(Robot robot, long date) {
		this.scenario.supprimeEvenements(robot, date);
	}

	/* Incrémente date et exécute tous les évènements jusqu'à cette date */
	public void incrementeDate() {

		long avant = this.dateSimulation;
		long apres = this.dateSimulation+INCRE;
		this.scenario.execute(avant,apres);

		this.dessinerDonnees();
		// On incrémente de INCRE secondes
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
