package edu.hm.cs.modsim.personenstrom;

import java.text.DecimalFormat;
import java.util.PriorityQueue;

/**
 * Sortierte Eventliste, geordnet nach der Simulationszeit und Eventtyp
 * 
 * @author Hubert Hager, Tobi Höfer
 *
 */
public class FutureEventList {

	private PriorityQueue<Event> priorityQueue;

	public FutureEventList() {
		priorityQueue = new PriorityQueue<>(100, new EventComparator());
	}

	/**
	 * Neues Event der Liste hinzufügen
	 * 
	 * @param e
	 *            Event
	 */
	public void addEvent(Event e) {
		this.priorityQueue.add(e);
	}

	/**
	 * Forderstes Event aus der Liste abholen und löschen
	 * 
	 * @return
	 */
	public Event removeAndGetFirst() {
		return this.priorityQueue.poll();
	}

	/**
	 * Empty Methode
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return priorityQueue.size() == 0;
	}

	/**
	 * Getter für Eventliste
	 * 
	 * @return
	 */
	public PriorityQueue<Event> getPriorityQueue() {
		return priorityQueue;
	}

	/**
	 * Aktuelle Größe der Liste
	 * 
	 * @return
	 */
	public int size() {
		return this.priorityQueue.size();
	}

	/**
	 * toString Methode
	 */
	@Override
	public String toString() {
		String out = "" + priorityQueue.size();
		DecimalFormat df = new DecimalFormat("#.00");
		for (Event e : priorityQueue) {
			out += " " + e.getClass().getSimpleName() + "("
					+ df.format(e.getEventTime()) + ")";
		}
		return out;
	}
}
