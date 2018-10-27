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
	private int nbIncendies;
	private int nbRobots;

	/* Constructeur */
	public DonneesSimulation(Carte carte, int nbIncendies, int nbRobots) {
		this.carte = carte;
		this.nbIncendies = nbIncendies;
		this.nbRobots = nbRobots;
		this.incendies = new Incendie[nbIncendies];
		this.robots = new Robot[nbRobots];
	}

	/* Affichage comme dans le fichier d'origine*/
	public String toString() {
		String chaine = this.getCarte().toString();
		int nbLignes = this.getCarte().getNbLignes();
		int nbColonnes = this.getCarte().getNbColonnes();
		chaine += "\n";
		chaine += this.getCarte().getTailleCases();
		chaine += "\n";
		for(int i = 0; i < nbLignes; i++) {
			chaine += "#l"+i;
			chaine += "\n";
			for(int j = 0; j < nbColonnes; j++) {
				chaine += this.getCarte().getCase(i,j).toString();
				chaine += "\n";
			}
			chaine += "\n";
		}
		chaine += "# Incendies";
		chaine += "\n";
		int nbIncendies = this.getNbIncendies();
		chaine += nbIncendies + "\n";
		for(int i = 0; i < nbIncendies; i++) {
    		chaine += this.getIncendies()[i].toString();
    		chaine += "\n";
        }
		chaine += "\n";
		chaine += "# Robots";
		chaine += "\n";
		int nbRobots = this.getNbRobots();
		chaine += nbRobots + "\n";
    	for(int i = 0; i < nbRobots; i++) {
    		chaine += this.getRobots()[i].toString();
    		chaine += "\n";
        }
    	chaine += "\n";
    	return chaine;
	}

	/* Accesseurs */
	public Carte getCarte() {
		return this.carte;
	}
	public Incendie[] getIncendies() {
		return this.incendies;
	}
	public Robot[] getRobots() {
		return this.robots;
	}
	public int getNbIncendies() {
		return this.nbIncendies;
	}
	public int getNbRobots() {
		return this.nbRobots;
	}

	/* Ajout d'un incendie */
	public void addIncendie(Incendie inc, int n) {
		this.incendies[n] = inc;
	}

	/* Ajout d'un robot */
	public void addRobot(Robot rob, int n) {
		this.robots[n] = rob;
	}
}
