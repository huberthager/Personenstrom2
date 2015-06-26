package edu.hm.cs.modsim.personenstrom;

import java.util.Comparator;

/**
 * Legt Vergleichskriterien fest, wonach Events verglichen werden
 * 
 * @author Hubert Hager, Tobi Höfer
 *
 */
public class EventComparator implements Comparator<Event> {

	@Override
	public int compare(Event e1, Event e2) {

		if (e1.getEventTime() < e2.getEventTime()) {
			return -1;
		} else if (e1.getEventTime() > e2.getEventTime()) {
			return 1;
		} else if (e1.getEventTime() == e2.getEventTime()) {
			// Arrival mit StartMove
			if (e1 instanceof Arrival && e2 instanceof StartMove) {
				return -1;
			} else if (e2 instanceof Arrival && e1 instanceof StartMove) {
				return 1;
			} else if (e1 instanceof StartMove && e2 instanceof StartMove) {
				return e1.getPedestrian().getFreeFlowVelocity() > e2
						.getPedestrian().getFreeFlowVelocity() ? -1 : e1
						.getPedestrian().getFreeFlowVelocity() == e2
						.getPedestrian().getFreeFlowVelocity() ? 0 : 1;
			} else {
				return 0;
			}
		}
		return 0;

	}
}
