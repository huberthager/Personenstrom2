package edu.hm.cs.modsim.personenstrom;

import java.util.Random;

public class Pedestrian {

	private double freeFlowVelocity;
	private Cell location;

	public Pedestrian(Cell location) {
		this.location = location;
		this.freeFlowVelocity=this.freeFlowVelocity();
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

	private  double  freeFlowVelocity() {
		return new Random().nextGaussian()*0.18 + 1.3;
//		return 1.3;
	}
}
