package gui;

import events.Evenement;

/**
 * Classe Simulateur
 */

public class Simulateur implements Simulable {
	
	/**
	 * Classe Simulateur :
	 * 		- dateSimulation : date actuelle du simulateur, pour classer les évents
	 * 		- évents : séquence continuellement ordonnée d'évènements
	 */
	
	private int dateSimulation;
	private Evenement[] events;
	
	/*********************************************
	 * 
	 * METHODES DE BASE
	 */
	
	/* Constructeur */
	public Simulateur(int date) {
		this.dateSimulation = date;
		this.events = new Evenement[0];
	}
	
	
	/* Mutateur */
	public void setDateSimulation(int date) {
		this.dateSimulation = date;
	}
	
	/* Accesseur */
	public int getDateSimulation() {
		return this.dateSimulation;
	}
	public Evenement getEvenement(int date) throws Exception  {
		int pos = 0;
		/* On cherche l'évènement dont la date correspond */
		while(this.events[pos].getDate() != date && pos < this.events.length) {
			pos += 1;
		}
		/* Si on l'a pas trouvé */
		if(pos == this.events.length) {
			throw new Exception("Evènement introuvable");
		} else {
			return this.events[pos];
		}
	}
	
	/*********************************************
	 * 
	 * METHODES GESTION DES EVENEMENTS
	 */
	
	/* Ajout d'évènements (ordonné !) */
	public void ajouteEvenement(Evenement event) {
		int date = event.getDate();
		int pos = 0;
		/* Nouveau tableau d'évènement */
		Evenement[] newEvents = new Evenement[this.events.length + 1];
		/* Tant que la date est supérieure aux dates des évènements du tableau, on avance dans le tableau
		 * et on ajoute les évènements déjà présents dans le nouveau tableau
		 */
		while(date >= this.events[pos].getDate() && pos < this.events.length) {
			newEvents[pos] = this.events[pos];
			pos += 1;
		}
		if(pos == this.events.length) {
			/* On ajoute le nouvel évènement */
			newEvents[pos] = event;
		} else {
			/* On ajoute le nouvel évènement */
			newEvents[pos] = event;
			/* Puis on ajoute les autres anciens qui restent */
			for(int k = pos; k < this.events.length; k++) {
				newEvents[pos+1] = this.events[pos];
			}
		}
		/* On a un nouveau tableau avec les évènements ordonnés par date */
		this.events = newEvents;
	}
	
	/* Incrémente date et exécute tous les évènements jusqu'à cette date */
	public void incrementeDate() throws Exception {
		int avant = this.dateSimulation;
		/* On incrémente de 60 secondes */
		this.dateSimulation += 60;
		/* On cherche le premier évènement à exécuter */
		int pos = 0;
		while(this.events[pos].getDate() != avant) {
			pos += 1;
		}
		/* On éxécute tous les évènements jusqu'au dernier avant la date */
		while(this.events[pos].getDate() < this.dateSimulation) {
			try {
				this.events[pos].execute();
				pos += 1;
	        } catch (Exception e) {
	            System.out.println("Déplacement impossible");
	        }
		}
	}
	
	/* Simulation terminée */
	public boolean simulationTerminee() {
		if(this.dateSimulation == this.events.length) {
			return true;
		}
		return false;
	}

}
