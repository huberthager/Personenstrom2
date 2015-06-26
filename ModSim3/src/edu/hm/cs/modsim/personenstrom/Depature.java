package edu.hm.cs.modsim.personenstrom;

/**
 * Repräsentiert das Verlassen eines Fußgängers vom Feld
 * 
 * @author Hubert Hager, Tobi Höfer
 *
 */
public class Depature extends Event {

	private Pedestrian pedestrian;

	public Depature(double eventTime, Pedestrian pedestrian) {
		super(eventTime, pedestrian);

	}

	/**
	 * Fußgänger wird vom Feld und aus Simulation entfernt
	 */
	@Override
	public void processEvent(Field field, FutureEventList futureEventList) {

		field.killPedestrianOnTarget(this.getPedestrian());

	}

}
