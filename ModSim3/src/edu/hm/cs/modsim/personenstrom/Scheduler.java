package edu.hm.cs.modsim.personenstrom;

public class Scheduler {

	private double clock;
	private double endSimTime;
	private int sideLength;
	private Field field;
	private FutureEventList futureEventList;

	public Scheduler(double endSimTime, int sideLength, int rowSource,
			int colSource, int rowTarget, int colTarget) {
		this.clock = 0;
		this.endSimTime = endSimTime;
		this.sideLength = sideLength;
		this.field = new Field(sideLength, rowSource, colSource, rowTarget,
				colTarget);
		this.futureEventList = new FutureEventList();
		// Fußgänger betreten Feld während der Sim

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

		// Fußgänger sind vor der Sim auf dem Feld
		futureEventList.addEvent(new Arrival(0));
		futureEventList.addEvent(new Arrival(0));

	}
	
	public void run() {
		while(clock < this.endSimTime && !(this.futureEventList.isEmpty())) {
			futureEventList.removeAndGetFirst().processEvent(field,
					futureEventList);
		}
	}

	public void printFieldToConsole() {
		this.field.printToConsole(sideLength);
	}
}
