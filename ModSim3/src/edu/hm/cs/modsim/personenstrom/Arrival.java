package edu.hm.cs.modsim.personenstrom;

import java.util.List;
import java.util.Random;

public class Arrival extends Event {

	public Arrival(double eventTime, Pedestrian pedestrian) {
		super(eventTime, pedestrian);
		// this.eventTime += interArrivalTime(); Falls Fußgänger während
		// Simulation das Feld betreten
		this.eventTime = eventTime; // Falls Fußgänger vor Simulation schon da
									// sind
	}

	/**
	 * Ein neuer Fußgänger betritt(addto pedestriansOnField) das Feld. Die
	 * Quelle setzt Position(location) auf dem Feld bzw teilt Fußgänger seine
	 * Position(location) mit. Jeder Fußgänger erhält initial einen nächsten
	 * Schritt.
	 * 
	 */
	@Override
	public void processEvent(Field field, FutureEventList futureEventList) {

//		List<Pedestrian> pOnField = field.getPedestriansOnField();
		double clockOfArrival = this.getEventTime();
//		Pedestrian p;

//		// checken ob quelle besetzt ist
//		if (!field.sourceIsOccupied()) {
//			p = new Pedestrian(field.getSourceCell(), this.freeFlowVelocity());
//		} else {
//			p = new Pedestrian(field.getCell(9, 0), this.freeFlowVelocity());
//		}
		

//		 pOnField.add(p); // in P-Container
//		 field.createPedestrian(p); // auf Feld-location !!! oben nicht mit
//		 // source initialisieren wenn 2. Ped.
		
		 futureEventList.addEvent(new StartMove(clockOfArrival, pedestrian));

//		field.printToConsole(field.getSideLength());
//		System.out.println();
	}

	private double freeFlowVelocity() {
		 return new Random().nextGaussian() + 0.8;
	}
	

	// private double interArrivalTime() {
	// double lambda = 1;
	// double u = Math.random();
	// return Math.log(1 - u) / (-1) * lambda;
	// }
}
