package events;

/**
 * Classe EvenementMessage
 */

public class EvenementMessage extends Evenement {
	/**
	 * Classe EvenementMessage :
	 * 		hérite du modèle Evenement et affiche un message sur la console
	 */
	
	private String message;
	
	/* Constructeur */
	public EvenementMessage(int date, String message) {
		super(date);
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
	
	
	/* Exécution de l'évènement */
	public void execute() {
		System.out.println(this.getDate() + this.getMessage());
	}
}