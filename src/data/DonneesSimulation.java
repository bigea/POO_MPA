package data;

import java.util.HashSet;
import java.util.Iterator;

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
	private HashSet<Incendie> incendies;
	private Robot[] robots;
	private Case[] eaux;
	private int nbIncendies;
	private int nbRobots;
	private int nbEaux;

	/* Constructeur */
	public DonneesSimulation(Carte carte, int nbIncendies, int nbRobots, int nbEaux) {
		this.carte = carte;
		this.nbIncendies = 0;
		this.nbRobots = nbRobots;
		this.nbEaux = nbEaux;
		this.setIncendies(new HashSet<Incendie>());
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
		Iterator<Incendie> iter = this.getIncendies().iterator();
		while(iter.hasNext()) {
			chaine += iter.next().toString();
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
	public HashSet<Incendie> getIncendies() {
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

    public void setIncendies(HashSet<Incendie> incendies) {
        this.incendies = incendies;
    }

	/* Ajout d'un incendie */
	public void addIncendie(Incendie inc) {
		this.incendies.add(inc);
		this.nbIncendies += 1;
	}

    /* Suppression d'un incendie */
    public void supprimerIncendie(Incendie incendie) {
    	this.incendies.remove(incendie);
    	this.nbIncendies += 1;
//        Incendie[] incendies = this.getIncendies();
//        int nbIncendie = this.getNbIncendies();
//        int indIncendie = 0;
//        /* On trouve l'indice de l'incendie qu'on veut supprimer dans la liste de tous les incendies */
//        for (int i=0; i<nbIncendie; i++) {
//            if (incendies[i] == incendie) {
//                indIncendie = i;
//                i=nbIncendie;
//            }
//        }
//        /* On va recréer une nouvelle liste d'incendies en copiant tous ceux qu'on a sauf celui qu'on vient d'éteindre */
//        Incendie[] newIncendies = new Incendie[nbIncendie-1];
//        int jBis = 0;
//        for (int j=0; j<nbIncendie-1; j++) {
//            if (j!=indIncendie) {
//                newIncendies[j] = incendies[jBis];
//            } else {
//                newIncendies[j] = incendies[jBis+1];
//                jBis++;
//            }
//            jBis++;
//        }
//        this.setNbIncendies(nbIncendie-1);
//        this.setIncendies(newIncendies);
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
