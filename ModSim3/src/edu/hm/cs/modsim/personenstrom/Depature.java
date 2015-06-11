package edu.hm.cs.modsim.personenstrom;

public class Depature extends Event {
	
	private Pedestrian pedestrian;
	
	public Depature(double eventTime, Pedestrian pedestrian) {
		super(eventTime);
		this.pedestrian = pedestrian;
	}
	
	public Pedestrian getPedestrian() {
		return this.pedestrian;
	}
	
	@Override
	public void processEvent(Field field, FutureEventList futureEventList) {

		field.killPedestrianOnTarget(this.getPedestrian());
		
		field.printToConsole(field.getSideLength());
		System.out.println();
	}

}
