package data.robot;

import java.util.List;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.enumerate.NatureTerrain;
import events.EvenementDeplacementUnitaire;
import gui.Simulateur;

/**
 * Classe Pattes
 */

public class Pattes extends Robot {
	
	/**
	 * Classe Pattes (Robot Terrestre)
	 */
	
	public Pattes(Case pos, Carte carte) {
		super(pos, carte);
		this.setVolume(200000);
		this.setVitesse(30);
	}
	
	@Override
	public void setVitesse(int vitesse) {
		if (this.getPosition().getNature() == NatureTerrain.ROCHE) {
			this.vitesse = 10;
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
	public void deverserEau(int vol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remplirReservoir() {
		this.setVolume(200000);
	}

	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" PATTES "+this.getVitesse(this.getPosition().getNature());
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
	public Chemin plusCourt(Case dest, int date, Chemin chemin) {
		//TODO
		return chemin;
	}
	

	/* Déplacement possible partout sauf eau */
	@Override
	public boolean possibleDeplacement(Case voisin) {
		NatureTerrain nature = voisin.getNature();
		switch(nature) {
			case EAU:
				return false;
			default:
				return true;
		}
	}

	@Override
	public Chemin plusCourt(Case dest, int date) {
		// TODO Auto-generated method stub
		return null;
	}
}
