package data.robot;

import java.util.List;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.enumerate.Direction;
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
	
	/*********************************************
	 * 
	 * METHODES DE BASE
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
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" DRONE "+this.getVitesse(this.getPosition().getNature());
	}

	
	/*********************************************
	 * 
	 * METHODES D'ACTION
	 */
	
	@Override
	public void deverserEau(int vol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remplirReservoir() {
		//TODO: gérer le déplacement jusqu'à un réservoir d'eau
	}

	@Override
	/*
	 * Déplacement du robot vers une Case :
	 * 		crée la liste d'évènements à partir de la date actuelle du simulateur (=CheminDrone)
	 * 		et les ajoute dans le simulateur
	 */
	public void deplacementCase(Case dest, Simulateur sim) {
		/* Calcul du plus court dans chemin */
		Chemin chemin = this.plusCourt(dest, sim.getDateSimulation());
		/* On récupère les caractéristiques du plus court chemin */
		int nbCase = chemin.getNbCase();
		List<Case> cases = chemin.getChemin();
		List<Integer> dates = chemin.getDates();
		/* Ajout des évènements au simulateur */
		for(int i = 0; i<nbCase; i++) {
			int date = dates.get(i);
			Case deplacement = cases.get(i);
			sim.ajouteEvenement(new EvenementDeplacementUnitaire(date, sim, this, deplacement));		
		}
	}
	
	/* Calcul du plus court chemin */
	public Chemin plusCourt(Case dest, int date) {
		Carte carte = this.getCarte();
		Case src = this.getPosition();
		int x_src = src.getLigne();
		int y_src = src.getColonne();
		int x_dest = dest.getLigne();
		int y_dest = dest.getColonne();
		Chemin chemin = new Chemin(this, date);
		/* Tant qu'on a pas atteint la destination */
		while((x_src != x_dest)&&(y_src != y_dest)) {
			Direction direction = null;
			/* 	On choisit simplement la direction
			 * 		qui nous rapproche le plus de la dest
			 * 		en "ligne droite" sans se soucier de la nature du terrain
			 */
			if(y_src < y_dest) {
				direction = Direction.SUD;
			} else if (y_src > y_dest) {
				direction = Direction.NORD;
			} else {
				if(x_src < x_dest) {
					direction = Direction.EST;
				} else if (x_src < x_dest) {
					direction = Direction.OUEST;
				}
			}
			/* On ajoute le déplacement élémentaire dans le simulateur
			 * 		La date dépend de la durée du déplacement donc de la vitesse du robot
			 * 		Temps calculé dans calculTemps() par le Chemin
			 */
			Case deplacement = carte.getVoisin(src, direction);
			date += chemin.calculTemps(src, deplacement);
			chemin.ajoutCase(deplacement, date);
			/* On réactualise la case qui est virtuellement la position du robot */
			src = deplacement;
			x_src = src.getLigne();
			y_src = src.getColonne();
		}
		return chemin;
	}

	/* Déplacement possible du drone partout */
	@Override
	public boolean possibleDeplacement(Case voisin) {
		return true;
	}
}