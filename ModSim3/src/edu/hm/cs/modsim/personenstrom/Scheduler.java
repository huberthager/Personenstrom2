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
			int colSource, List<Cell> targets,int szenario) {
		this.clock = 0;
		this.endSimTime = endSimTime;
		this.sideLength = sideLength;
		this.field = new Field(sideLength, rowSource, colSource,targets,szenario);
		this.futureEventList = new FutureEventList();
		this.currentEvent = null;
		pedestriansOnField = field.getPedestriansOnField();
		for(Pedestrian p : pedestriansOnField) {
			futureEventList.addEvent(new Arrival(0,p));
		}
	}

	public double run() {
		while (clock < this.endSimTime && !(this.futureEventList.isEmpty())) {
			this.currentEvent = futureEventList.removeAndGetFirst();
			double tmp=clock;
			this.clock = currentEvent.getEventTime();
			this.currentEvent.processEvent(field, futureEventList);
			
			if(tmp!=clock){
				System.out.println(clock);
			}
		}
		System.out.println(clock);
		return clock;
	}

	public void printFieldToConsole() {
		this.field.printToConsole(sideLength);
	}
	
}
