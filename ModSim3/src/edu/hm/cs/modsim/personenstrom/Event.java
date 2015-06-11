package edu.hm.cs.modsim.personenstrom;

public abstract class Event {
	
	protected double eventTime;
	protected Pedestrian pedestrian;
	
	public Event(double eventTime, Pedestrian pedestrian) {
		this.eventTime = eventTime;
		this.pedestrian = pedestrian;
	}
	
	public double getEventTime(){
		return eventTime;
	}
	
	protected Pedestrian getPedestrian() {
		return this.pedestrian;
	}
	public abstract void processEvent(Field field, FutureEventList futureEventList);

}
