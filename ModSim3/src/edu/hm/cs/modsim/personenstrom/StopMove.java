package edu.hm.cs.modsim.personenstrom;

public class StopMove extends Event {

	public StopMove(double clock, Pedestrian pedestrian) {
		super(clock, pedestrian);
	}

	@Override
	public void processEvent(Field field, FutureEventList futureEventList) {
		Pedestrian pToMove = this.getPedestrian();
		double timeForNextStep = this.getEventTime();
		boolean isOnTarget = pToMove.getLocation().equals(field.getTargetCell());

		if(!isOnTarget) {
			futureEventList.addEvent(new StartMove(timeForNextStep, pToMove));
		} else {
			futureEventList.addEvent(new Depature(timeForNextStep, pToMove));

		}
	}
}
