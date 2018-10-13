package events;

/**
 * Classe abstraite Evenement
 */

public abstract class Evenement {
	/**
	 * Classe abstraite Evenement :
	 * 		héritée de sous-classes qui représenteront des évènements réels avec leurs propres propriétés
	 */
	
	private int date;
	
	/* Constructeur */
	public Evenement(int date) {
		this.setDate(date);
	}
	
	/* Mutateurs */
	public void setDate(int date) {
		this.date = date;
	}
	
	/* Accesseur */
	public int getDate() {
		return this.date;
	}
	
	/* Exécution de l'évènement */
	public abstract void execute();
}
