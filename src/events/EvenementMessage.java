package events;

import gui.Simulateur;

/**
 * Classe EvenementMessage
 */

public class EvenementMessage extends Evenement {
	/**
	 * Classe EvenementMessage :
	 * 		hérite du modèle Evenement et affiche un message sur la console
	 */
	
	private String message;
	
	/*********************************************
	 * 
	 * METHODES DE BASE
	 */
	
	/* Constructeur */
	public EvenementMessage(int date, Simulateur sim, String message) {
		super(date, sim);
		this.setMessage(message);
	}
	
	/* Mutateurs */
	public void setMessage(String msg) {
		this.message = msg;
	}
	
	/* Accesseur */
	public String getMessage() {
		return this.message;
	}
	
	/*********************************************
	 * 
	 * EXECUTION
	 */
	
	/* Exécution de l'évènement */
	public void execute() {
		System.out.println(this.getDate() +" "+ this.getMessage());
	}
}