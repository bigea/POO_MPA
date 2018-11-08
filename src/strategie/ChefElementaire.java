package strategie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import data.Carte;
import data.DonneesSimulation;
import data.Incendie;
import gui2.Simulateur;
import data.robot.*;

/**
 * Classe ChefElementaire
 *    Pemet de définir la stratégie globale
 */

public class ChefElementaire {

    Simulateur sim;
    DonneesSimulation donnees;
    Carte carte;
    HashSet<Incendie> incendies;
    int nbIncendies;
    Robot[] robots;
    int nbRobots;
    HashMap<Incendie,Robot> affectations; // liste des incendies et de leur robots affectés

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

    public HashSet<Incendie> getIncendies() {
        return this.incendies;
    }
    private void setIncendies(HashSet<Incendie> hashSet) {
        this.incendies = hashSet;
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
    		Iterator<Incendie> iter = this.incendies.iterator();
    		while(iter.hasNext()) {
    			this.affectations.put(iter.next(),null);
    		}
    }


    /*********************************************
    *
    * METHODES DE STRATEGIE
    */

    private boolean resteIncendiePasAffecte() {
    		Iterator<Incendie> iter = this.incendies.iterator();
    		while(iter.hasNext()) {
    			Incendie inc = iter.next();
    			if (this.affectations.get(inc) == null) {
                    return true;
          }
    		}
        return false;
    }

    private Incendie incendieNonAffecte() {
    		Iterator<Incendie> iter = this.incendies.iterator();
    		while(iter.hasNext()) {
    			Incendie inc = iter.next();
    			if (this.affectations.get(inc) == null) {
                    return inc;
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

    protected void strategie_unitaire() {
        Incendie incendieChoisi = this.incendieNonAffecte();
        Robot robotChoisi = this.choisirRobot(incendieChoisi);
        if (robotChoisi != null) {
            this.affectations.put(incendieChoisi, robotChoisi);
            System.out.println("On ordonne au robot " + robotChoisi.getNature() + " d'intervenir sur l'incendie " + incendieChoisi);
            robotChoisi.ordreIntervention(this.sim, incendieChoisi);
        }
    }

    public void strategie(){
        // while (this.incendies.size()>0) {
            while(this.resteIncendiePasAffecte() && this.resteRobotPasAffecte()){
                this.strategie_unitaire();
            }
        //     wait();
        // }
    }

}
