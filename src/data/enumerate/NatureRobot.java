package data.enumerate;

public enum NatureRobot {
	/**
	 * Type Enum NatureRobot
	 */
	
	DRONE,
	PATTES,
	ROUES,
	CHENILLES;
	
	private String nom = "";

	/*********************************************
	 * 
	 * METHODES DE BASE
	 */
	
	/* Constructeur */
	public void Nature(String nom){
		this.nom = nom;
	}
	/* Accesseur */
	public String toString() {
		return this.nom;
	}
}
