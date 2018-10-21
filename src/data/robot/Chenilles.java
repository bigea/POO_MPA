package data.robot;

import java.util.List;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.enumerate.NatureTerrain;
import events.EvenementDeplacementUnitaire;
import gui.Simulateur;

/**
 * Classe Chenilles
 */


public class Chenilles extends Robot {

	/**
	 * Classe Chenilles (Robot Terrestre)
	 */

	/*********************************************
	 * 
	 * METHODES DE BASE
	 */
	
	public Chenilles(Case pos, Carte carte) {
		super(pos, carte);
		this.setVolume(2000);
		this.setVitesse(60);
	}
	@Override
	public void setVitesse(int vitesse) {
		if(vitesse > 80) {
			this.vitesse = 80;
		} else if (this.getPosition().getNature() == NatureTerrain.FORET) {
			this.vitesse = vitesse/2;
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
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" CHENILLES "+this.getVitesse(this.getPosition().getNature());
	}

	
	/*********************************************
	 * 
	 * METHODES D'ACTION
	 */
	
	@Override
	public void deverserEau(int vol) {
		// TODO Auto-generated method stub
	}

	/* Remplissage mais instanciation d'évènements pour ce faire (déplacement puis sur place) */
	@Override
	public void remplirReservoir() {
		//TODO
	}
	
	/* Remplissage effectif */
	@Override
	public void remplirEffectif() {
		this.setVolume(2000);
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
	
	/* Indique si le déplacement est possible pour ce robot */
	@Override
	public boolean possibleDeplacement(Case voisin) {
		NatureTerrain nature = voisin.getNature();
		switch(nature) {
			case EAU:
				return false;
			case ROCHE:
				return false;
			default:
				return true;
		}
	}
}
