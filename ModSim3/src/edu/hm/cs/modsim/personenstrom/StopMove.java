package edu.hm.cs.modsim.personenstrom;

public class StopMove extends Event {

	public StopMove(double clock, Pedestrian pedestrian) {
		super(clock, pedestrian);
	}

	@Override
	public void processEvent(Field field, FutureEventList futureEventList) {
		Pedestrian pToMove = this.getPedestrian();
		boolean isOnTarget=false;
		double timeForNextStep = this.getEventTime();
		//hier der Fehler
		for(Cell c:field.getTargets()){
		isOnTarget = pToMove.getLocation().equals(c);
		if(isOnTarget){
			break;
		}
		}
		if(!isOnTarget) {
			futureEventList.addEvent(new StartMove(timeForNextStep, pToMove));
			
		} else {
			futureEventList.addEvent(new Depature(timeForNextStep, pToMove));
			
		}
		
		
	}
}
