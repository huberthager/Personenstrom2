package edu.hm.cs.modsim.personenstrom;

public abstract class Event {
	
	protected double eventTime;
	
	public Event(double eventTime) {
		this.eventTime = eventTime;
	}
	
	public double getEventTime(){
		return eventTime;
	}
	
	public abstract void processEvent(Field field, FutureEventList futureEventList);

}
