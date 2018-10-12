package events;

/**
 * Classe Date
 */

public class Date {
	/**
	 * Classe Date (pour dater les évènements)
	 */
	
	private int annee;
	private int mois;
	private int jour;
	
	/* Constructeur */
	public Date(int jour, int mois, int annee) {
		this.jour = jour;
		this.mois = mois;
		this.annee = annee;
	}
	
	/* Incrémentation */
	public void incremente() {
		this.jour = this.jour + 1;
		if(trente(this.mois) && this.jour > 30) {
			this.jour = 1;
			this.mois += 1;
		}
		if(trente_un(this.mois) && this.jour > 31) {
			this.jour = 1;
			this.mois += 1;
		}
		if(this.mois == 2 && this.jour >28) {
			this.jour = 1;
			this.mois += 1;
		}
		
		if(this.mois > 12) {
			this.mois = 1;
			this.annee += 1;
		}
				
	}

	/* Mois à trente jours ? */
	private boolean trente(int mois) {
		switch(mois) {
			case 4:
				return true;
			case 6:
				return true;
			case 9:
				return true;
			case 11:
				return true;
		}
		return false;
	}
	
	/* Mois à trente et un jours ? */
	private boolean trente_un(int mois) {
		switch(mois) {
			case 1:
				return true;
			case 3:
				return true;
			case 5:
				return true;
			case 7:
				return true;
			case 8:
				return true;
			case 10:
				return true;
			case 12:
				return true;
		}
		return false;
	}

	
}
