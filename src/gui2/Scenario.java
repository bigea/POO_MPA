package gui2;

import java.util.ArrayList;

import events.Evenement;
// import gui2.Simulateur;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;

/**
 * Classe Scenario
 *
 */

public class Scenario {

	/**
	 * Classe Scenario :
	 * 		- contient une séquence ordonnée d'évènements
	 */

	private ArrayList<Evenement> sequence;

	/*********************************************
	 *
	 * METHODES DE BASE
	 */

	public Scenario() {
		this.sequence = new ArrayList<Evenement>();
	}

	public ArrayList<Evenement> getScenario(){
		return this.sequence;
	}

	public int getNbEvents() {
		return this.sequence.size();
	}

	/* Renvoi la position du premier évènement de cette date */
	private int getPosition(int date) throws Exception {
		int pos = 0;
		int nbEvents = this.sequence.size();
		if(nbEvents == 0) {
			// si tableau nul
			throw new Exception("Evènement introuvable");
		} else {
			// on cherche ma position de l'évènement
			while(this.sequence.get(pos).getDate() != date && pos < nbEvents) {
				pos += 1;
			}
		}
		/* Si on l'a pas trouvé */
		if(pos == nbEvents) {
			throw new Exception("Evènement introuvable");
		} else {
			return pos;
		}
	}

	/* Renvoie le premier évènement de cette date */
	public Evenement getEvenement(int date){
		try{
			this.sequence.get(this.getPosition(date));
		} catch (Exception e) {
		}
		return null;
	}


	/*********************************************
	 *
	 * METHODES DE GESTION D'EVENEMENTS
	 */

	/* Ajout d'évènements (ordonné !) */
	public void ajouteEvenement(Evenement event) {
		int date = event.getDate();
		int nbEvents = this.sequence.size();
		int pos = 0;
		if(nbEvents == 0) {
			this.sequence.add(event);
		}
		/*[Matthias] C'est pas plus simple d'insérer l'event directement dans l'attribut ? Au lieu de créer une autre liste*/
		else {
			ArrayList<Evenement> newEvents = new ArrayList<Evenement>();
			while(pos < nbEvents && date >= this.sequence.get(pos).getDate()) {
				newEvents.add(this.sequence.get(pos));
				pos += 1;
			}

			// on ajoute l'évènement à la fin de newEvents
			newEvents.add(event);
			// s'il reste des évènements dans la séquence initiale
			if(pos != nbEvents) {
				// on les ajoute
				for(int i=pos;i<nbEvents;i++) {
					newEvents.add(this.sequence.get(i));
				}
			}
			// s'il n'en reste plus : le nouvel évents est à la fin
			/* On a un nouveau tableau avec les évènements ordonnés par date */
			this.sequence = newEvents;
		}
		/*Proposition de modif : */
		/*
		else {
			while(pos < nbEvents) {
				if(date <= this.sequence.get(pos).getDate()) {
					this.sequence.add(pos, event);
					break;
				}
				pos += 1;
			}
			this.sequence.add(pos, event);
		}*/
	}

	/* Exécution entre deux dates */
	public void execute(int avant, int apres) {
		// Tant qu'on a pas dépassé la date
		while(this.sequence.size() != 0 && this.sequence.get(0)!=null && this.sequence.get(0).getDate() < apres) {
			try {
				// on exécute l'évènement
				this.sequence.get(0).execute();
				// on le supprime de la séquence
				this.sequence.remove(0);
				// on adapte la taille de la séquence
				this.sequence.trimToSize();
			} catch (Exception e) {
				System.out.println("Evènement impossible");
			}
		}
	}
}
