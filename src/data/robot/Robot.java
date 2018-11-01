package data.robot;

import java.util.ArrayList;
import java.util.List;

import chemin.Chemin;
import data.Carte;
import data.Case;
import data.DonneesSimulation;
import data.enumerate.NatureRobot;
import data.enumerate.NatureTerrain;
import events.*;
import gui2.Simulateur;
import data.enumerate.Direction;

/**
 * Classe Robot
 */

public abstract class Robot {

    /**
     * Classe Robot
     * 		Avec hérachie (sous-classes)
     */
	private static final long INFINI = 32000;
	/* Attributs */
	private NatureRobot nature;
	protected Case position;
	protected int capacite; //en litre
	protected int vitesse; //en km/h
	protected int tempsRemplissage; //temps en seconde
	protected int tempsVidage; //temps en seconde
	protected double vitesseRemplissage; //en l/s
	protected double vitesseVidage; //en l/s
	protected long dateDisponibilite;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	/* Constructeur */
	public Robot(Case pos, NatureRobot nature, long dateDisponibilite) {
		this.setPosition(pos);
		this.setDateSimulation(dateDisponibilite);
		this.nature = nature;
	}

	/* Affichage */
	public abstract String toString();

	/* Accesseurs */
	public Case getPosition() {
		return this.position;
	}
	public NatureRobot getNature() {
		return this.nature;
	}

	/* Mutateurs */
	public void setPosition(Case cas) {
		this.position = cas;
	}

	public abstract void setVitesse(int vitesse);
	public double getVitesse(NatureTerrain nt) {
		return this.vitesse;
	}

	public void setCapacite(int capacite){
		this.capacite = capacite;
	}
	public int getCapacite(){
		return this.capacite;
	}

	public int getTempsRemplissage() {
		if(this.capacite == 0){
			return this.tempsRemplissage;
		}
		else{
			return (this.capaciteMaximale - this.capacite) / this.vitesseRemplissage;
		}
	}
	public void setTempsRemplissage(int temps){
		this.tempsRemplissage = temps;
	}

	public int getTempsVidageComplet() {
		return this.tempsVidage;
	}
	public void setTempsVidageComplet(int temps){
		this.tempsVidage = temps;
	}

	public double getVitesseRemplissage(){
		return this.vitesseRemplissage;
	}
	public void setVitesseRemplissage(int tempsRemplissage, int capacite) {
		this.vitesseRemplissage = (float)capacite/(float)tempsRemplissage;
	}

	public double getVitesseVidage(){
		return this.vitesseVidage;
	}
	public void setVitesseVidage(int tempsVidage, int capacite) {
		this.vitesseVidage = (float)capacite/(float)tempsVidage;
	}

	public long getDateDisponibilite() {
		return this.dateDisponibilite;
	}
	public void setDateDisponibilite(int date){
		this.dateDisponibilite = date;
	}

	public boolean estDisponible(long date){
		if(date < this.getDateDisponibilite()){
			return false;
		}
		else{
			return true;
		}
	}
	/*********************************************
	 *
	 * METHODES DE DEPLACEMENT
	 */

	/* Déplacement du robot vers une case et ajout des évènements au simulateur */
	public Chemin deplacementCase(Case dest, Simulateur sim, long date) {
		/* Calcul du plus court dans chemin */
			Chemin chemin = this.plusCourt(dest, date, sim.getDonnees().getCarte());
			this.ajoutSimulateurDeplacement(sim,chemin);
			return chemin;
	}

	/* Déplacement possible selon la nature du robot */
	public abstract boolean possibleDeplacement(Case voisin);

    /* Calcul du temps de déplacement
	 * 		Dépend de la vitesse du robot et de la nature du terrain,
	 * 		donc de la nature du terrain sur la moitié de la première
	 * 		case et la moitié de la seconde case
	 */
	public int calculTemps(Case src, Case voisin, Carte carte) {
		/* Vitesse sur la case src en m/s*/
		double vitesseSrc = (this.getVitesse(src.getNature()))/3.6;
		/* Vitesse sur la case dest en m/s */
		double vitesseVoisin = (this.getVitesse(voisin.getNature()))/3.6;
		/* Taille de la case, on prend comme distance la moitié */
		int tailleCase = carte.getTailleCases();
		int distance = tailleCase/2;
		/* Calcul du temps sur les deux terrains */
		double tempsSrc = distance/vitesseSrc;
		double tempsVoisin = distance/vitesseVoisin;
		/* On renvoie le temps, arrondi au supérieur */
		return (int) Math.round(tempsSrc+tempsVoisin);
	}


/*FONCTION UTILE AU CALCUL DE PLUS COURT CHEMIN********************************************/

	/* calcul du plus court chemin */
	protected Chemin plusCourt(Case dest, long date, Carte carte) {
		Chemin chemin = Dijkstra(dest, date, carte);
		return chemin;
	}
	/* Initialisation du graphe (poids infini) */
	protected void Initialisation(Carte carte, Case src, List<Case> noeuds, long[][] poids){
		for(int l=0; l<carte.getNbLignes(); l++) {
			for(int c=0; c<carte.getNbColonnes(); c++) {
				poids[l][c] = INFINI;
				noeuds.add(carte.getCase(l, c));
			}
		}
		poids[src.getColonne()][src.getLigne()] = 0;
	}
	/*Trouve le noeud de poids minimum parmis tous les noeuds*/
	protected Case TrouveMin(List<Case> noeuds, long[][] poids){
		long min = INFINI;
		Case noeudChoisi = null;
		for(Case noeud : noeuds){
			if(poids[noeud.getColonne()][noeud.getLigne()] < min){
				min = poids[noeud.getColonne()][noeud.getLigne()];
				noeudChoisi = noeud;
			}
		}
		return noeudChoisi;
	}
	/* Met à jour la distance entre le voisin et le noeud */
	protected void majPoids(Case[][] predecesseurs, long[][] poids, Case src, Case voisin, Carte carte) {
		int temps = this.calculTemps(src, voisin, carte);
		long poidsSrc = poids[src.getColonne()][src.getLigne()];
		long poidsVoisin = poids[voisin.getColonne()][voisin.getLigne()];
		if(poidsVoisin > poidsSrc+temps) {
			poids[voisin.getColonne()][voisin.getLigne()] = poidsSrc+temps;
			predecesseurs[voisin.getColonne()][voisin.getLigne()] = src;
		}
	}
	/*Algorithme qui nous permet de trouver le plus court chemin,*/
	protected Chemin Dijkstra(Case dest, long date, Carte carte){
        /* Sauvegarde de la date de début */
        long dateDebut = date;
		/*création du tableau des predecesseurs*/
		Case[][] predecesseurs = new Case[carte.getNbColonnes()][carte.getNbLignes()];
		/*Données nécessaires à l'algorithme*/
		Case src = this.getPosition();
		/* Ensemble des cases */
		ArrayList<Case> noeuds = new ArrayList<Case>();
		/* Ensemble des poids */
		long[][] poids = new long[carte.getNbColonnes()][carte.getNbLignes()];
		/* Initialisation du graphe (poids infini) */
		Initialisation(carte, src, noeuds, poids);

		Case noeud = src;
		/* Tant que l'on a pas parcouru toutes les cases ou atteint la dest */
		while(!noeuds.isEmpty()) {
			// on récupère la case dont la distance est minimale
			noeud = TrouveMin(noeuds, poids);
			// on supprime ce noeud
			noeuds.remove(noeud);
			if(noeud.equals(dest)){ //On s'arrête dès qu'on a atteint la destination pour éviter des calculs inutiles
				break;
			}
			// on s'occupe des noeuds restants
			// on met à jour les distances pour tous les voisins du minimum
			Case voisin = carte.voisin(noeud, Direction.SUD);
			if( (voisin != null) && (noeuds.contains(voisin)&&(this.possibleDeplacement(voisin)))){
				majPoids(predecesseurs, poids, noeud, voisin, carte);
			}
			voisin = carte.voisin(noeud, Direction.NORD);
			if((voisin != null) && (noeuds.contains(voisin)&&(this.possibleDeplacement(voisin)))){
				majPoids(predecesseurs, poids, noeud, voisin, carte);
			}
			voisin = carte.voisin(noeud, Direction.EST);
			if((voisin != null) && (noeuds.contains(voisin)&&(this.possibleDeplacement(voisin)))){
				majPoids(predecesseurs, poids, noeud, voisin, carte);
			}
			voisin = carte.voisin(noeud, Direction.OUEST);
			if((voisin != null) && (noeuds.contains(voisin)&&(this.possibleDeplacement(voisin)))){
				majPoids(predecesseurs, poids, noeud, voisin, carte);
			}
		}
		/*On récupère le plus court chemin à partir du tableau des prédécesseurs*/
		Chemin chemin = recupPlusCourtChemin(predecesseurs, poids, dest, src, carte, dateDebut);
		return chemin;
	}

	/*A partir du tableau des predecesseurs on récupère toutes les cases qui formeront le plus court chemin*/
	protected Chemin recupPlusCourtChemin(Case[][] predecesseurs, long[][] poids, Case caseFinale, Case caseInitiale, Carte carte, long date){
		Chemin chemin = new Chemin();
		Case cas = caseFinale;
		ArrayList<Case> listeCases = new ArrayList<Case>();
		while(cas != caseInitiale){
			listeCases.add(cas);
			cas = predecesseurs[cas.getColonne()][cas.getLigne()];
		}
		for(int i = listeCases.size()-1; i >=0; i--){
			chemin.ajoutCase(listeCases.get(i), date, this, carte);
            System.out.println("poids : " + poids[listeCases.get(i).getColonne()][listeCases.get(i).getLigne()]);
			date = poids[listeCases.get(i).getColonne()][listeCases.get(i).getLigne()];
		}
		return chemin;
	}


	/*********************************************
	 *
	 * METHODES DE REMPLISSAGE
	 */

	/*Remplie le réservoir du robot. Private car on passe par ordreRemplissage*/
	public abstract void remplirReservoir();

	/* Possibilité de se remplir sur la position donnée */
	public abstract boolean possibleRemplissage(Case cas, Carte carte);

	/*ordre de remplissage donné au robot*/ /*fonction qui remplacera remplir Reservoir*/
	/*Cette fonction appelera remplirResevoir une fois le robot arrivé sur la zone d'eau*/
	public  void ordreRemplissage(Simulateur sim) {
	    long date = sim.getDateSimulation(); // Gerer
	    if (this.possibleRemplissage(this.getPosition(), sim.getDonnees().getCarte())) {
	        this.ajoutSimulateurRemplissage(sim, date, this.getTempsRemplissage());
	    } else {
	        /* On recupere la case eau la plus proche en temps */
	        Case destinationEau = this.choisirCaseEau(sim);
	        /* On se déplace jusqu'à cette case */
	        Chemin chemin = this.deplacementCase(destinationEau, sim, this.getDateDisponibilite());
	        /* Date de fin du déplacement */
	        date = date + chemin.tempsChemin(this, sim.getDonnees().getCarte());
	        /* Ajout au simulateur du remplissage */
	        this.ajoutSimulateurRemplissage(sim, date, this.getTempsRemplissage()); // A revoir pourquoi donner la date de fin de remplissage [PHILEMON]
	    }
	}

	/* Renvoie la case d'eau pour laquelle le trajet vers cele-ci est le plus rapide */
	public Case choisirCaseEau(Simulateur sim) {
		long minTemps = 0;
		Case caseEauChoisie = this.position;
		DonneesSimulation donnees = sim.getDonnees();
	    Carte carte = donnees.getCarte();
		long date = sim.getDateSimulation();
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

	/*********************************************
	 *
	 * METHODE D'INTERVENTION
	 */

	/* Déverser l'eau */
	public abstract void deverserEau(int vol);


	/*********************************************
	 *
	 * METHODE D'AJOUT AU SIMULATEUR
	 */

	/* Ajout au simulateur d'un déplacement vers une case */
	public void ajoutSimulateurDeplacement(Simulateur sim, Chemin chemin) {
		/* On récupère les caractéristiques du chemin */
		int nbCase = chemin.getNbCase();
		List<Case> cases = chemin.getChemin();
		List<Long> dates = chemin.getDates();
		/* Ajout des évènements au simulateur */
		for(int i = 0; i<nbCase; i++) {
			long date = dates.get(i);
			Case deplacement = cases.get(i);
            long duree = 0;
            if (i+1<nbCase) {
                duree = dates.get(i+1) - date;
            } else {
                duree = dates.get(0) + chemin.tempsChemin(this, sim.getDonnees().getCarte()) - date;
            }
            System.out.println("On ajoute un DeplacementUnitaire à la date : " + date);
			this.setDateDisponibilite(this.getDateDisponibilite()+duree);
			sim.ajouteEvenement(new DeplacementUnitaire(date, sim, this, deplacement));
		}
	}

	/* Ajout au simulateur d'un remplissage effectif */
	public void ajoutSimulateurRemplissage(Simulateur sim, long date, long duree) {
		this.setDateDisponibilite(this.getDateDisponibilite()+duree);
        sim.ajouteEvenement(new Remplissage(date, sim, this));
	}

}
