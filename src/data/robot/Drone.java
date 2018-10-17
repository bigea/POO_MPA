package data.robot;

import chemin.CheminDrone;
import data.Carte;
import data.Case;
import data.enumerate.NatureTerrain;
import events.EvenementDeplacementUnitaire;
import gui.Simulateur;

/**
 * Classe Robot Drone
 */

public class Drone extends Robot {

    /**
     * Classe Drone
     * 		Hiérarchie des classes avec Drone => Robot
     */
	
	public Drone(Case pos, Carte carte) {
		super(pos, carte);
		this.setVolume(10000);
		this.setVitesse(100);
	}
	
	@Override
	public void setVitesse(int vitesse) {
		if(vitesse > 150) {
			this.vitesse = 150;
		} else {
			this.vitesse = vitesse;	
		}
	}
		
	@Override
	public double getVitesse(NatureTerrain nt) {
		return this.vitesse;
	}

	@Override
	public void deverserEau(int vol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remplirReservoir() {
		this.setVolume(10000);
	}

	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" DRONE "+this.getVitesse(this.getPosition().getNature());
	}

	@Override
	/*
	 * Déplacement du robot vers une Case :
	 * 		crée la liste d'évènements à partir de la date actuelle du simulateur (=CheminDrone)
	 * 		et les ajoute dans le simulateur
	 */
	public void deplacementCase(Case dest, Simulateur sim) {
		/* Instancier le chemin */
		CheminDrone chemin = new CheminDrone(this, sim.getDateSimulation());
		/* Calcul du plus court dans chemin */
		chemin.plusCourt(dest);
		/* On récupère les caractéristiques du plus court chemin */
		int nbCase = chemin.getNbCase();
		Case[] cases = chemin.getChemin();
		int[] dates = chemin.getDates();
		/* Ajout des évènements au simulateur */
		for(int i = 0; i<nbCase; i++) {
			sim.ajouteEvenement(new EvenementDeplacementUnitaire(dates[i], sim, this, cases[i]));		
		}
	}
}