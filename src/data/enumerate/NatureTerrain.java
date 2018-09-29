package data.enumerate;

/**
 * NatureTerrain (type enum)
 */

public enum NatureTerrain {
	
	/**
	 * NatureTerrain (type enum)
	 * 		Enumère les 5 types de terrains
	 */
	
	EAU,
	FORET,
	ROCHE,
	TERRAIN_LIBRE,
	HABITAT;
	
	private String nom = "";
	
	/* Constructeur */
	public void Nature(String nom){
		this.nom = nom;
	}
	
	/* Accesseur */
	public String toString() {
		return this.nom;
	}
}

