package strategie;

import java.util.HashMap;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.DonneesSimulation;
import data.Incendie;
import data.enumerate.NatureRobot;
import data.enumerate.NatureTerrain;
import events.*;
import gui2.Simulateur;
import data.enumerate.Direction;
import data.robot.*;

/**
 * Classe ChefElementaire
 */

public class ChefElementaire {

    Simulateur sim;
    DonneesSimulation donnees;
    Carte carte;
    Incendie[] incendies;
    int nbIncendies;
    Robot[] robots;
    int nbRobots;
    HashMap<Incendie,Robot> affectations;

    /*********************************************
    *
    * METHODES DE BASE
    */

    public ChefElementaire(Simulateur sim) {
        this.setSimulateur(sim);
        this.setDonnees(sim.getDonnees());
        this.setCarte(sim.getDonnees().getCarte());
        this.setIncendies(sim.getDonnees().getIncendies());
        this.setNbIncendies(sim.getDonnees().getNbIncendies());
        this.setRobots(sim.getDonnees().getRobots());
        this.setNbRobots(sim.getDonnees().getNbRobots());
        this.initAffectations();
    }

    public Simulateur getSimulateur() {
        return this.sim;
    }
    private void setSimulateur(Simulateur sim) {
        this.sim = sim;
    }

    public DonneesSimulation getDonnees() {
        return this.donnees;
    }
    private void setDonnees(DonneesSimulation donnees) {
        this.donnees = donnees;
    }

    public Carte getCarte() {
        return this.carte;
    }
    private void setCarte(Carte carte) {
        this.carte = carte;
    }

    public Incendie[] getIncendies() {
        return this.incendies;
    }
    private void setIncendies(Incendie[] incendies) {
        this.incendies = incendies;
    }

    public int getNbIncendies() {
        return this.nbIncendies;
    }
    private void setNbIncendies(int nbIncendies) {
        this.nbIncendies = nbIncendies;
    }

    public Robot[] getRobots() {
        return this.robots;
    }
    private void setRobots(Robot[] robots) {
        this.robots = robots;
    }

    public int getNbRobots() {
        return this.nbRobots;
    }
    private void setNbRobots(int nbRobots) {
        this.nbRobots = nbRobots;
    }

    private void initAffectations() {
        this.affectations = new HashMap<Incendie,Robot>();
        for (int i=0; i<this.nbIncendies; i++) {
            this.affectations.put(this.incendies[i], null);
        }
    }


    /*********************************************
    *
    * METHODES DE STRATEGIE
    */

    private boolean resteIncendiePasAffecte() {
        for (int i=0; i<this.nbIncendies; i++) {
            if (this.affectations.get(this.incendies[i]) == null) {
                return true;
            }
        }
        return false;
    }

    private Incendie incendieNonAffecte() {
        for (int i=0; i<this.nbIncendies; i++) {
            if (this.affectations.get(this.incendies[i]) == null) {
                return incendies[i];
            }
        }
        return null;
    }

    private boolean resteRobotPasAffecte() {
        long dateCourante = this.sim.getDateSimulation();
        for (int i=0; i<this.nbRobots; i++) {
            Robot robot = this.robots[i];
            if (robot.estDisponible(dateCourante)) {
                return true;
            }
        }
        return false;
    }

    private Robot choisirRobot(Incendie incendie) {
        long dateCourante = this.sim.getDateSimulation();
        for (int i=0; i<this.nbRobots; i++) {
            Robot robot = this.robots[i];
            if (robot.estDisponible(dateCourante) && robot.possibleDeplacement(incendie.getPosition())) {
                return robot;
            }
        }
        return null;
    }

    public void strategie() {
        if (this.resteIncendiePasAffecte() && this.resteRobotPasAffecte()) {
            Incendie incendieChoisi = this.incendieNonAffecte();
            Robot robotChoisi = this.choisirRobot(incendieChoisi);
            if (robotChoisi != null) {
                robotChoisi.ordreIntervention(this.sim, incendieChoisi);
            }
        }
    }

}
