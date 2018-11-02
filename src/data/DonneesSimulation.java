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
	private Case[] eaux;
	private int nbIncendies;
	private int nbRobots;
	private int nbEaux;

	/* Constructeur */
	public DonneesSimulation(Carte carte, int nbIncendies, int nbRobots, int nbEaux) {
		this.carte = carte;
		this.nbIncendies = nbIncendies;
		this.nbRobots = nbRobots;
		this.nbEaux = nbEaux;
		this.setIncendies(new Incendie[nbIncendies]);
		this.robots = new Robot[nbRobots];
		this.eaux = new Case[nbEaux];
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

	public Case[] getEaux() {
		return this.eaux;
	}

	public int getNbIncendies() {
		return this.nbIncendies;
	}
	public int getNbRobots() {
		return this.nbRobots;
	}
	public int getNbEaux() {
		return this.nbEaux;
	}

    public void setNbIncendies(int nbIncendies) {
        this.nbIncendies = nbIncendies;
    }
    public void setIncendies(Incendie[] incendies) {
        this.incendies = incendies;
    }

	/* Ajout d'un incendie */
	public void addIncendie(Incendie inc, int n) {
		this.incendies[n] = inc;
	}

	/* Ajout d'un robot */
	public void addRobot(Robot rob, int n) {
		this.robots[n] = rob;
	}

	/* Ajout d'une case eau */
	public void addEau(Case caseEau, int n) {
		this.eaux[n] = caseEau;
	}
}
