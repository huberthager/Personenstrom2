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

	public Scheduler(double endSimTime, int sideLength, List<Cell> targets, int szenario) {
		this.clock = 0;
		this.endSimTime = endSimTime;
		this.sideLength = sideLength;
		this.futureEventList = new FutureEventList();
		this.field = new Field(sideLength, targets, szenario);
		this.currentEvent = null;
		pedestriansOnField = field.getPedestriansOnField();
		for (Pedestrian p : pedestriansOnField) {
			futureEventList.addEvent(new Arrival(0, p));
		}
	}
	

	public double run() {
		while (clock < this.endSimTime && !(this.futureEventList.isEmpty())) {
			if(field.isPedestrianWithoutEvent()){
				futureEventList.addEvent(new Arrival(clock,field.getPedestrianReturn()));
			}
			this.currentEvent = futureEventList.removeAndGetFirst();
			this.clock = currentEvent.getEventTime();
			this.currentEvent.processEvent(field, futureEventList);

			String gui = "";
			if (currentEvent instanceof StartMove
					|| currentEvent instanceof Depature) {
				for (int clear = 0; clear < 40; clear++) {
					System.out.println("\b");
				}
				gui = field.guiToString(clock, gui);
				System.out.print(gui);
				System.out.flush();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return clock;
	}

	public void printFieldToConsole() {
		this.field.printToConsole(sideLength);
	}

}
