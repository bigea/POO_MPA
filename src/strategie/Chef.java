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
 * Classe Chef
 *    Pemet de définir la stratégie, avec un chef pompier
 */

public abstract class Chef {

    protected static final long INFINI = 2147483647;

    protected Simulateur sim;
    protected DonneesSimulation donnees;
    protected Carte carte;
    protected ArrayList<Incendie> incendies;
    protected int nbIncendies;
    protected Robot[] robots;
    protected int nbRobots;
    protected HashMap<Incendie,Robot> affectations; // liste des incendies et de leur robots affectés

    /*********************************************
    *
    * METHODES DE BASE
    */

    public Chef(Simulateur sim) {
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

    /* Le Chef est le chef de tous les robots */
    private void initChef() {
        for (int i=0; i<this.getNbRobots(); i++) {
            this.robots[i].setChef(this);
        }
        for (int j=0; j<this.nbRobots; j++) {
            Robot robot = this.robots[j];
            boolean possibleIntervention = false;
            for (int i=0; i<this.incendies.size(); i++) {
                if (robot.possibleDeplacement(this.incendies.get(i).getPosition())) {
                    possibleIntervention = true;
                }
            }
            if (!possibleIntervention) {
                robot.setDateDisponibilite(INFINI);
            }
        }
    }

    /*********************************************
    *
    * METHODES DE STRATEGIE
    */

    /* Retourne vraie s'il reste des incendies non affectés */
    protected boolean resteIncendiePasAffecte() {
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

    /* Retourne l'incendie non affecté */
    protected Incendie incendieNonAffecte() {
        for (int i=0; i<this.incendies.size(); i++) {
            Incendie inc = this.incendies.get(i);
            if (this.affectations.get(inc) == null) {
                return inc;
            }
        }
        return null;
    }

    /* Retourne vraie s'il reste des robots non affectés */
    protected boolean resteRobotPasAffecte() {
        long dateCourante = this.sim.getDateSimulation();
        for (int i=0; i<this.nbRobots; i++) {
            Robot robot = this.robots[i];
            if (robot.estDisponible(dateCourante)) {
                return true;
            }
        }
        return false;
    }

    /* Retourne le robot qui peut s'occuper de cet incendie
     * 		Et met à jour la disponibilité à INFINI si le robot ne peut pas se déplacer sur cet incendie
     */
    protected Robot choisirRobot(Incendie incendie) {
        long dateCourante = this.sim.getDateSimulation();
        for (int i=0; i<this.nbRobots; i++) {
            Robot robot = this.robots[i];
            // if (robot.possibleDeplacement(incendie.getPosition())) {
            if (robot.estDisponible(dateCourante) && robot.possibleDeplacement(incendie.getPosition())) {
                return robot;
            }
            // } else {
            //     robot.setDateDisponibilite(INFINI);
            // }
        }
        return null;
    }

    /* Méthode de stratégie unitaire :
     * 		Affecte un robot à un incendie
     */
    protected void strategieUnitaire() {
        Incendie incendieChoisi = this.incendieNonAffecte();
        Robot robotChoisi = this.choisirRobot(incendieChoisi);
        if (robotChoisi != null && incendieChoisi != null) {
            System.out.println("incendie " + incendieChoisi + " affecté à " + robotChoisi);
            this.affectations.put(incendieChoisi, robotChoisi);
            robotChoisi.ordreIntervention(this.sim, incendieChoisi);
        }
    }

    /* Méthode de stratégie (dépend du type de Chef) */
    public abstract void strategie();

    /* Méthode principale qui dit au Chef de commencer la stratégie
     * 		Initialise le Chef de tous les robots
     * 		Indique au simulateur qu'il est le chef
     * 		Effectue la stratégie
     */
    public void commencerStrategie() {
        this.initChef();
        this.sim.setChef(this);
        this.strategie();
    }

}
