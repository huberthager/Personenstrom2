package edu.hm.cs.modsim.personenstrom;

import java.util.Random;

/**
 * Repräsentiert die Ankunft eines Fußgängers auf dem Spielfeld
 * 
 * @author Hubert Hager, Tobi Höfer
 *
 */
public class Arrival extends Event {

	public Arrival(double eventTime, Pedestrian pedestrian) {
		super(eventTime, pedestrian);
		this.eventTime = eventTime;
	}

	/**
	 * Ordnet Fußgänger seine nächste Aktion nach der Ankunft zu, wobei schnelle
	 * Fußgänger zuerst Aktionen ausführen
	 */
	@Override
	public void processEvent(Field field, FutureEventList futureEventList) {

		double clockOfArrival = this.getEventTime();

		futureEventList.addEvent(new StartMove(clockOfArrival
				+ (1 / pedestrian.getFreeFlowVelocity()), pedestrian));

	}

	/**
	 * toString()
	 */
	public String toString() {
		return "" + pedestrian;
	}

}
