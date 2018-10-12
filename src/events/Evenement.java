package events;

/**
 * Classe abstraite Evenement
 */

public abstract class Evenement {
	/**
	 * Classe abstraite Evenement :
	 * 		héritée de sous-classes qui représenteront des évènements réels avec leurs propres propriétés
	 */
	
	private Date date;
	
	/* Constructeur */
	public Evenement(Date date) {
		this.setDate(date);
	}
	
	/* Mutateurs */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/* Accesseur */
	public Date getDate() {
		return this.date;
	}
	
	/* Exécution de l'évènement */
	public abstract void execute();
}
