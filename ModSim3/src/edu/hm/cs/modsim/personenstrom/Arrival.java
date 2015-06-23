package edu.hm.cs.modsim.personenstrom;

import java.util.Random;

public class Arrival extends Event {

	public Arrival(double eventTime, Pedestrian pedestrian) {
		super(eventTime, pedestrian);
		this.eventTime = eventTime;
	}

	/**
	 * 
	 */
	@Override
	public void processEvent(Field field, FutureEventList futureEventList) {

		double clockOfArrival = this.getEventTime();

		futureEventList.addEvent(new StartMove(clockOfArrival+(1/pedestrian.getFreeFlowVelocity()), pedestrian));

	}
	
	public String toString() {
		return "" + pedestrian;
	}

	
}
