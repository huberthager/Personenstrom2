package edu.hm.cs.modsim.personenstrom;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

  @Override
  public int compare(Event e1, Event e2) {
    return e1.getEventTime() < e2.getEventTime() ? -1 : e1.getEventTime() == e2
        .getEventTime() ? 0 : 1;
  }

}
