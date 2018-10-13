package data.enumerate;

/**
 * NatureTerrain (type enum)
 */

public enum NatureTerrain {
	
	/**
	 * NatureTerrain (type enum)
	 * 		Enumère les 5 types de terrains
	 */
	
	EAU ("EAU"),
	FORET ("FORET"),
	ROCHE ("ROCHE"),
	TERRAIN_LIBRE ("TERRAIN LIBRE"),
	HABITAT ("HABITAT");
	
	private final String value;
	
	/* Constructeur */
	private NatureTerrain(String value){
		this.value = value;
	}
	
	/* Accesseur */
	public String getNom(){
		return value;
	}
}

