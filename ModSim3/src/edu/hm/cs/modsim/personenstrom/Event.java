package edu.hm.cs.modsim.personenstrom;

/**
 * Abstrakte Eventklasse vereint alle Eigenschaften die ein Event repräsentiert
 * 
 * @author Hubert Hager, Tobi Höfer
 *
 */
public abstract class Event {

	/**
	 * Zeitpunkt zu dem Event stattfindet
	 */
	protected double eventTime;
	/**
	 * Fußgänger der diese Aktion ausführt
	 */
	protected Pedestrian pedestrian;

	public Event(double eventTime, Pedestrian pedestrian) {
		this.eventTime = eventTime;
		this.pedestrian = pedestrian;
	}

	public double getEventTime() {
		return eventTime;
	}

	protected Pedestrian getPedestrian() {
		return this.pedestrian;
	}

	public String toString() {
		return "" + pedestrian;
	}

	public abstract void processEvent(Field field,
			FutureEventList futureEventList);

}
