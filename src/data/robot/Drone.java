package data.robot;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.DonneesSimulation;
import data.enumerate.Direction;
import data.enumerate.NatureRobot;
import data.enumerate.NatureTerrain;
import gui2.Simulateur;

/**
 * Classe Robot Drone
 */

public class Drone extends Robot {

    /**
     * Classe Drone
     * 	 	Sous-classe de Robot
	 * 		Contient toutes les méthodes particulières à ce robot
     */

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	public Drone(Case pos) {
		/* Valeurs par défault*/
		super(pos, NatureRobot.DRONE, 0);
		this.setCapacite(10000);
        this.setCapaciteMaximale(10000);
		this.setVitesse(100);
		this.setTempsRemplissageComplet(30*60);
		this.setTempsVidageComplet(30);
        this.setTempsVidageUnitaire(30);
        this.setVolumeVidageUnitaire(10000);
		this.vitesseRemplissage =  (double)this.capacite/(double)this.getTempsRemplissageComplet();
		/*Le drone n'a pas de vitesse de vidage car son intervention unitaire vide la totalité de son réservoir*/
	}

	public void setVitesse(double vitesse) {
		if(vitesse > 150) {
			this.vitesse = 150;
		} else {
			this.vitesse = vitesse;
		}
	}

	@Override
	public String toString() {
		return this.getPosition().getLigne()+" "+this.getPosition().getColonne()+" DRONE "+this.getVitesse(this.getPosition().getNature());
	}


	/*********************************************
	 *
	 * METHODES DE REMPLISSAGE
	 */

	/* Possibilité de remplir sur la case donnée */
	public boolean possibleRemplissage(Case cas, Carte carte) {
		if(cas.getNature() == NatureTerrain.EAU) {
			return true;
		}
		return false;
	}

	/* Remplissage effectif */
	public void remplirReservoir() {
		this.setCapacite(10000);
	}


	/*********************************************
	 *
	 * METHODES DE DEPLACEMENT
	 */

	/* Calcul du plus court chemin */
	@Override
	public Chemin plusCourt(Case dest, long date, Carte carte) {
		Case src = this.getPosition();
		Chemin chemin = new Chemin();
		/* Tant qu'on a pas atteint la destination */
		Direction direction = null;
		while(!src.equals(dest)) {
			/* 	On choisit simplement la direction
			 * 		qui nous rapproche le plus de la dest
			 * 		en "ligne droite" sans se soucier de la nature du terrain
			 */
			if(src.getLigne() < dest.getLigne()) {
				direction = Direction.SUD;
			} else if (src.getLigne() > dest.getLigne()) {
				direction = Direction.NORD;
			} else if(src.getColonne() < dest.getColonne()) {
					direction = Direction.EST;
			} else if (src.getColonne() > dest.getColonne()) {
					direction = Direction.OUEST;
			}
			/* On ajoute le déplacement élémentaire dans le simulateur
			 * 		La date dépend de la durée du déplacement donc de la vitesse du robot
			 * 		Temps calculé dans calculTemps() par le Chemin
			 */
			Case voisin = carte.voisin(src, direction);
			chemin.ajoutCase(voisin, date, this, carte);
            date += this.calculTemps(src, voisin, carte);
			/* On réactualise la case qui est virtuellement la position du robot */
			src = voisin;
		}
		return chemin;
	}

	/* Déplacement possible du drone partout */
	public boolean possibleDeplacement(Case voisin) {
		return true;
	}

    @Override
    /* Renvoie la case d'eau pour laquelle le trajet vers cele-ci est le plus rapide */
	public Case choisirCaseEau(Simulateur sim) {
		long minTemps = 0;
		Case caseEauChoisie = this.position;
		DonneesSimulation donnees = sim.getDonnees();
	    Carte carte = donnees.getCarte();
		long date = this.getDateDisponibilite();
		Case[] eaux = donnees.getEaux();
		int nbEaux = donnees.getNbEaux();
		/* Pour chaque case d'eau on va calculer le plus court chemin et le temps que notre robot met pour le faire */
		for (int i=0; i<nbEaux; i++) {
			Case caseEau = eaux[i];
			Chemin chemin = this.plusCourt(caseEau, date, carte);
			long temps = chemin.tempsChemin(this, carte);
			if (i==0) {		// Initialisation de minTemps et de caseEauChoisie en i==0
				minTemps = temps;
				caseEauChoisie = caseEau;
			}
			if (temps < minTemps) {
				minTemps=temps;
				caseEauChoisie = caseEau;
			}
		}
		return caseEauChoisie;
	}
}
