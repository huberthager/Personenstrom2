package edu.hm.cs.modsim.personenstrom;

import java.util.Random;

/**
 * Repräsentiert simulierten Fußgänger
 * 
 * @author Hubert Hager, Tobi Höfer
 *
 */
public class Pedestrian {

	/**
	 * normalverteilte Wunschgeschwindigkeit
	 */
	private double freeFlowVelocity;
	/**
	 * Position im Feld
	 */
	private Cell location;

	public Pedestrian(Cell location) {
		this.location = location;
		this.freeFlowVelocity = this.freeFlowVelocity();
	}

	/**
	 * Getter für Position
	 * 
	 * @return
	 */
	public Cell getLocation() {
		return location;
	}

	/**
	 * Setter für Position
	 * 
	 * @param location
	 */
	public void setLocation(Cell location) {
		this.location = location;
	}

	/**
	 * Getter für Wunschgeschwindigkeit
	 * 
	 * @return
	 */
	public double getFreeFlowVelocity() {
		return freeFlowVelocity;
	}

	/**
	 * Setter für Wunschgeschwindigkeit
	 * 
	 * @param freeFlowVelocity
	 */
	public void setFreeFlowVelocity(double freeFlowVelocity) {
		this.freeFlowVelocity = freeFlowVelocity;
	}

	/**
	 * Erzeugung der normalverteilten Wunschgeschwindigkeit mit Erwartungswert
	 * 1.3 und Standardabweichung 0.18
	 * 
	 * @return
	 */
	private double freeFlowVelocity() {
		return new Random().nextGaussian() * 0.18 + 1.3;
	}

	/**
	 * toString() Methode
	 */
	public String toString() {
		return "{" + location.getRow() + " " + location.getCol() + "}";
	}

	/**
	 * equals Methode
	 */
	@Override
	public boolean equals(Object obj) {
		Pedestrian other = (Pedestrian) obj;
		return this.location.equals(other.location);
	}

}
