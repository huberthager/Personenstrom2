package edu.hm.cs.modsim.personenstrom;

public class Depature extends Event {
	
	private Pedestrian pedestrian;
	
	public Depature(double eventTime, Pedestrian pedestrian) {
		super(eventTime, pedestrian);
	
	}
	
	@Override
	public void processEvent(Field field, FutureEventList futureEventList) {

		field.killPedestrianOnTarget(this.getPedestrian());
		
	}

}
