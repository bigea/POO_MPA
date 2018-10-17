package data.robot;

import java.util.List;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.enumerate.NatureTerrain;
import events.EvenementDeplacementUnitaire;
import gui.Simulateur;

/**
 * Classe Roues (Robot Terrestre)
 */

public class Roues extends Robot {

	/**
	 * Classe Roues (Robot Terrestre)
	 */
	
	/*********************************************
	 * 
	 * METHODES DE BASE
	 */
	
	public Roues(Case pos, Carte carte) {
		super(pos, carte);
		this.setVolume(5000);
		this.setVitesse(80);
	}
	@Override
	public void setVitesse(int vitesse) {
		if (this.getPosition().getNature() != NatureTerrain.TERRAIN_LIBRE && this.getPosition().getNature() != NatureTerrain.HABITAT) {
			this.vitesse = 0;
		} else {
			this.vitesse = vitesse;
		}
	}
	@Override
	public double getVitesse(NatureTerrain nt) {
		// TODO Auto-generated method stub
		return this.vitesse;
	}
	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" ROUES "+this.getVitesse(this.getPosition().getNature());
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
		//TODO
	}

	@Override
	/*
	 * Déplacement du robot vers une Case :
	 * 		crée la liste d'évènements à partir de la date actuelle du simulateur (=CheminDrone)
	 * 		et les ajoute dans le simulateur
	 */
	public void deplacementCase(Case dest, Simulateur sim) {
		/* Calcul du plus court dans chemin */
		Chemin chemin = plusCourt(dest, sim.getDateSimulation());
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
	@Override
	public Chemin plusCourt(Case dest, int date) {
		Chemin chemin = new Chemin(this, date);
		chemin.plusCourt(dest);
		return chemin;
	}

	/* Indique si le déplacement est possible dans la case voisine */
	@Override
	public boolean possibleDeplacement(Case voisin) {
		NatureTerrain nature = voisin.getNature();
		switch(nature) {
			case HABITAT:
				return true;
			case TERRAIN_LIBRE:
				return true;
			default:
				return false;
		}
	}
}
