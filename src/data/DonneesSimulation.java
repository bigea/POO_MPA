package data;

import data.robot.Robot;

/**
 * Classe Robot
 */

public class DonneesSimulation {

    /**
     * Classe DonneesSimulation : classe principale regroupant toutes les données du problème
     */
	
	/* Attributs */
	private Carte carte;
	private Incendie[] incendies;
	private Robot[] robots;

	/* Constructeur */
	public DonneesSimulation(Carte carte, int nbIncendies, int nbRobots) {
		this.carte = carte;
		this.incendies = new Incendie[nbIncendies];
		this.robots = new Robot[nbRobots];
	}
	
	/* Accesseurs */
	public Carte getCarte() {
		return this.carte;
	}
	public Incendie[] getIncendies() {
		return this.incendies;
	}
	public Robot[] getRobot() {
		return this.robots;
	}
}
