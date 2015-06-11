package edu.hm.cs.modsim.personenstrom;

public class Pedestrian {

	private double freeFlowVelocity;
	private Cell location;

	public Pedestrian(Cell location, double freeFlowVelocity) {
		this.location = location;
		this.freeFlowVelocity = freeFlowVelocity;
	}

	public Cell getLocation() {
		return location;
	}

	public void setLocation(Cell location) {
		this.location = location;
	}

	public double getFreeFlowVelocity() {
		return freeFlowVelocity;
	}

	public void setFreeFlowVelocity(double freeFlowVelocity) {
		this.freeFlowVelocity = freeFlowVelocity;
	}

}
