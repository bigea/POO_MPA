package strategie;

import java.util.HashMap;
import java.util.ArrayList;
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

    private static final long INFINI = 2147483647;

    Simulateur sim;
    DonneesSimulation donnees;
    Carte carte;
    ArrayList<Incendie> incendies;
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

    public ArrayList<Incendie> getIncendies() {
        return this.incendies;
    }
    private void setIncendies(ArrayList<Incendie> incendies) {
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

    public HashMap<Incendie,Robot> getAffectations() {
        return this.affectations;
    }
    public void setAffectations(HashMap<Incendie,Robot> affectations) {
        this.affectations = affectations;
    }

    private void initAffectations() {
        this.affectations = new HashMap<Incendie,Robot>();
    		Iterator<Incendie> iter = this.incendies.iterator();
    		while(iter.hasNext()) {
    			this.affectations.put(iter.next(),null);
    		}
    }

    private void initChef() {
        for (int i=0; i<this.getNbRobots(); i++) {
            this.robots[i].setChef(this);
        }
    }

    /*********************************************
    *
    * METHODES DE STRATEGIE
    */

    private boolean resteIncendiePasAffecte() {
        if (this.incendies.size()==0) {
            return false;
        }
        for (int i=0; i<this.incendies.size(); i++) {
            Incendie inc = this.incendies.get(i);
            if (this.affectations.get(inc) == null) {
                return true;
            }
        }
        return false;
    }

    private Incendie incendieNonAffecte() {
        for (int i=0; i<this.incendies.size(); i++) {
            Incendie inc = this.incendies.get(i);
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
            if (robot.possibleDeplacement(incendie.getPosition())) {
                if (robot.estDisponible(dateCourante)) {
                    return robot;
                }
            } else {
                robot.setDateDisponibilite(INFINI);
            }
        }
        return null;
    }

    protected void strategieUnitaire() {
        Incendie incendieChoisi = this.incendieNonAffecte();
        Robot robotChoisi = this.choisirRobot(incendieChoisi);
        if (robotChoisi != null && incendieChoisi != null) {
            System.out.println("incendie " + incendieChoisi + " affecté à " + robotChoisi);
            this.affectations.put(incendieChoisi, robotChoisi);
            robotChoisi.ordreIntervention(this.sim, incendieChoisi);
        }
    }

    public void strategie(){
        if (this.incendies.size()>0) {
            while(this.resteIncendiePasAffecte() && this.resteRobotPasAffecte()){
                this.strategieUnitaire();
            }
        } else {
            System.out.println("On a fini !");
        }
    }

    public void commencerStrategie() {
        this.initChef();
        this.sim.setChef(this);
        this.strategie();
    }

}
