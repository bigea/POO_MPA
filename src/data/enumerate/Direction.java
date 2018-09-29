package data.enumerate;

/**
 * Direction (type enum)
 */

public enum Direction {
	NORD,
	SUD,
	EST,
	OUEST;
	
	private String nom = "";
	
	/** Constructeur */
	public void Nature(String nom){
		this.nom = nom;
	}
	
	/** Accesseur */
	public String toString() {
		return this.nom;
	}
}
