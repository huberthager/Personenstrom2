package edu.hm.cs.modsim.personenstrom;

public class StopMove extends Event {

	private Pedestrian pedestrian;

	public StopMove(double clock, Pedestrian pedestrian) {
		super(clock);
		this.pedestrian = pedestrian;
	}

	@Override
	public void processEvent(Field field, FutureEventList futureEventList) {
		Pedestrian pToMove = this.getPedestrian();
		double timeForNextStep = super.getEventTime();
		boolean isOnTarget = pToMove.getLocation().equals(field.getTargetCell());

		if(!isOnTarget) {
			futureEventList.addEvent(new StartMove(timeForNextStep, pToMove));
		} else {
			futureEventList.addEvent(new Depature(timeForNextStep, pToMove));

		}
		
	}

	public Pedestrian getPedestrian() {
		return this.pedestrian;
	}

}
