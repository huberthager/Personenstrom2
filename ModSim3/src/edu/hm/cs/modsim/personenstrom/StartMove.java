package edu.hm.cs.modsim.personenstrom;

public class StartMove extends Event {

	public StartMove(double clock, Pedestrian pedestrian) {
		super(clock, pedestrian);
	}

	@Override
	public void processEvent(Field field, FutureEventList futureEventList) {
		Pedestrian pToMove = this.getPedestrian();
		Cell target;
		double timeToMove;
		double realVelocity;
		
		field.setPedestrianToMove(pToMove); 
		target = field.getTargetCellForNextStep(); 
		timeToMove = field.getTimeToMovePedestrian(pToMove.getLocation(),
				target);
		field.movePedestrian(super.eventTime,timeToMove); 
		if(timeToMove==0){
			timeToMove=1;
		}
		
		futureEventList.addEvent(new StopMove(this.getEventTime() + timeToMove,
				pToMove)); 
	}

}
