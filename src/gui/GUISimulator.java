package gui;

import java.awt.Color;

/**
 * Classe GUISimulator
 */

public class GUISimulator {

	/**
	 * Classe GUISimulator
	 */

	/* Attributs */
	private int lar;
	private int hau;
	private java.awt.Color bgColor;
	private Simulable simulateur;

	/* Constructeur */
	public GUISimulator(int lar, int hau, java.awt.Color bgColor) {
		this.lar = lar;
		this.hau = hau;
		this.bgColor = bgColor;
	}

	public GUISimulator(int lar, int hau, java.awt.Color bgColor, Simulable simulateur) {
		this.lar = lar;
		this.hau = hau;
		this.bgColor = bgColor;
		this.simulateur = simulateur;
	}

	public void addGraphicalElement(GraphicalElement e) {
		// TODO
	}

	/* Accesseur */
	public int getHeight() {
		return this.hau;
	}
	public int getWidth() {
		return this.lar;
	}

	public void reset() {
		// TODO
	}

	/* Mutateur */
	public void setSimulable(Simulable simulateur) {
		// TODO
		this.simulateur = simulateur;
	}
}
