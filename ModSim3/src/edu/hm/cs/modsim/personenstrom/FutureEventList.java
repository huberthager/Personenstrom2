package edu.hm.cs.modsim.personenstrom;

import java.text.DecimalFormat;
import java.util.PriorityQueue;

public class FutureEventList {

	private PriorityQueue<Event> priorityQueue;

	public FutureEventList() {
		priorityQueue = new PriorityQueue<>(100, new EventComparator());
	}

	public void addEvent(Event e) {
		this.priorityQueue.add(e);
	}

	public Event removeAndGetFirst() {
		return this.priorityQueue.poll();
	}

	public boolean isEmpty() {
		return priorityQueue.size() == 0;
	}

	public PriorityQueue<Event> getPriorityQueue() {
		return priorityQueue;
	}

	public int size() {
		return this.priorityQueue.size();
	}

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
