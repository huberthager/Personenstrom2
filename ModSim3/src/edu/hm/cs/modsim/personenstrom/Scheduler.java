package edu.hm.cs.modsim.personenstrom;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * Verwaltet Aktionen auf dem Feld
 * 
 * @author Hubert Hager, Tobi Höfer
 *
 */
public class Scheduler {
	/**
	 * Scenario ID für Korridor
	 */
	private static final int SCENARIO_FUNDAMENTAL_DIAGRAMM = 3;

	/**
	 * aktuelle Simulationszeit
	 */
	private double clock;
	/**
	 * Zeitpunkt des Simulationsende
	 */
	private double endSimTime;
	/**
	 * Seitenlänge des quadratischen Feldes
	 */
	private int sideLength;
	/**
	 * Szenario ID
	 */
	private int scenario;
	/**
	 * Feld
	 */
	private Field field;
	/**
	 * Future EventList
	 */
	private FutureEventList futureEventList;
	/**
	 * Aktives Event
	 */
	private Event currentEvent;
	/**
	 * Alle virtuellen Personen auf Feld
	 */
	private List<Pedestrian> pedestriansOnField;

	/**
	 * Datencontainer für statistische Auswertung
	 * 
	 * realisierte mittler Geschwindigkeiten aller Personen
	 */
	private List<Double> realVelsMean = new ArrayList<>();
	/**
	 * Personenfluss in #Personen/m/s
	 */
	private List<Double> flow = new ArrayList<>();

	public Scheduler(double endSimTime, int sideLength, List<Cell> targets,
			int scenario) {
		this.clock = 0;
		this.endSimTime = endSimTime;
		this.sideLength = sideLength;
		this.futureEventList = new FutureEventList();
		this.field = new Field(sideLength, targets, scenario);
		this.currentEvent = null;
		pedestriansOnField = field.getPedestriansOnField();
		for (Pedestrian p : pedestriansOnField) {
			futureEventList.addEvent(new Arrival(0, p));
		}
		this.scenario = scenario;

	}

	/**
	 * Führt aktive Event aus, bis Simulationsdauer überschritten oder
	 * FuturEventlist leer ist. Zeigt grafische Darstellung der Simulation in
	 * jedem Schritt.
	 * 
	 * @return Double Simzeit
	 */
	public double run() {
		int threadMillis = 300;
		if (scenario == SCENARIO_FUNDAMENTAL_DIAGRAMM) {
			threadMillis = 30;
		}
		while (clock < this.endSimTime && !(this.futureEventList.isEmpty())) {
			if (field.isPedestrianWithoutEvent()) {
				futureEventList.addEvent(new Arrival(clock, field
						.getPedestrianReturn()));
			}
			this.currentEvent = futureEventList.removeAndGetFirst();
			this.clock = currentEvent.getEventTime();
			this.currentEvent.processEvent(field, futureEventList);

			String gui = "";
			if (currentEvent instanceof StartMove
					|| currentEvent instanceof Depature) {
				for (int clear = 0; clear < 40; clear++) {
					System.out.println("\n");
				}
				gui = field.guiToString(clock, gui);
				System.out.print(gui);
				try {
					Thread.sleep(threadMillis);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// /**
		// * Datenauswertung
		// */
		// List<Double> realVels = field.getRealVels();
		// List<Double> realVelEventTimes = field.getRealVelEventTimes();
		// List<Double> pFlow = field.getPersonFlow();
		// //Durchschnitt der realisierten Geschwindigkeit
		// for(int i = 1; i <= realVels.size(); i++) {
		// double mean = 0;
		// for(int j = 1; j <= i; j++) {
		// mean += realVels.get(j-1);
		// }
		// realVelsMean.add(mean/i);
		// }
		// System.out.println(realVelsMean.size());
		//
		//
		// for(int i = 0; i < pFlow.size(); i++) {
		// flow.add(pFlow.get(i)/realVelsMean.get(i));
		// }
		// System.out.println("Fluss" + flow);
		return clock;
	}
}
