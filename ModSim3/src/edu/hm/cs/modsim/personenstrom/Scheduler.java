package edu.hm.cs.modsim.personenstrom;

import java.util.List;

public class Scheduler {

	private double clock;
	private double endSimTime;
	private int sideLength;
	private Field field;
	private FutureEventList futureEventList;
	private Event currentEvent;
	private List<Pedestrian> pedestriansOnField;

	public Scheduler(double endSimTime, int sideLength, int rowSource,
			int colSource, int rowTarget, int colTarget) {
		this.clock = 0;
		this.endSimTime = endSimTime;
		this.sideLength = sideLength;
		this.field = new Field(sideLength, rowSource, colSource, rowTarget,
				colTarget);
		this.futureEventList = new FutureEventList();
		this.currentEvent = null;
		pedestriansOnField = field.getPedestriansOnField();
		// Fuﬂg‰nger betreten Feld w√§hrend der Sim

		// while (clock < this.endSimTime) {
		// Arrival arrival = new Arrival(clock);
		// if (arrival.getEventTime() < endSimTime) {
		// this.futureEventList.addEvent(arrival);
		// }
		// this.clock = arrival.getEventTime();
		// // System.out.println(futureEventList.toString());
		//
		// }
		// this.clock = 0;

		// Fu√üg√§nger sind vor der Sim auf dem Feld
		for(Pedestrian p : pedestriansOnField) {
			futureEventList.addEvent(new Arrival(0,p));
		}
	}

	public void run() {
		while (clock < this.endSimTime && !(this.futureEventList.isEmpty())) {
			this.currentEvent = futureEventList.removeAndGetFirst();
			
			this.clock = currentEvent.getEventTime();
			this.currentEvent.processEvent(field, futureEventList);
			
			if(currentEvent instanceof StopMove){
			System.out.println(clock);
			}
		}
		System.out.println(clock);
	}

	public void printFieldToConsole() {
		this.field.printToConsole(sideLength);
	}
}
