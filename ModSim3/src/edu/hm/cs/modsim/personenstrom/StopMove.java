package edu.hm.cs.modsim.personenstrom;

/**
 * Repräsentiert Zeitpunkt wann ein Fußgänger bereit ist für nächsten Schritt
 * @author Hubert Hager, Tobi Höfer
 *
 */
public class StopMove extends Event {

	public StopMove(double clock, Pedestrian pedestrian) {
		super(clock, pedestrian);
	}
	
	/**
	 * Entweder Event für nächsten Schritt oder auf Ziel befindlicher Fußgöänger wird vom Feld genommen.
	 */
	@Override
	public void processEvent(Field field, FutureEventList futureEventList) {
		Pedestrian pToMove = this.getPedestrian();
		boolean isOnTarget=false;
		double timeForNextStep = this.getEventTime();

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
