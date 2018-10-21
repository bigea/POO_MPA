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

	/*********************************************
	 * 
	 * METHODES DE BASE
	 */
	/* réservoir infini */
	private static final int INFINI = 300000;
	
	public Pattes(Case pos, Carte carte) {
		super(pos, carte);
		this.setVolume(INFINI);
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
		this.setVolume(INFINI);
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

	/* Calcul du plus court chemin */
	@Override
	public Chemin plusCourt(Case dest, int date) {
		Chemin chemin = new Chemin(this, date);
		chemin.plusCourt(dest);
		return chemin;
	}
}
