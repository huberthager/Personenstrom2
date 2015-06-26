package edu.hm.cs.modsim.personenstrom;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Hubert Hager, Tobi Höfer Um die Szenarien: 1) Freier Fluss 2)
 *         Hühnertest 3) Fundamentaldiagramm (Korridor) 4) Evakuierungsszenario
 *         zu simulieren, die entpsrechende anderen Targets einkommentieren und
 *         mit der Szenario ID (1 - 4) den Scheduler erzeugen.
 * 
 *
 */

public class Main {
	static ArrayList<Double> simTimes = new ArrayList<>();

	public static void main(String[] args) throws InterruptedException {
		int scenario;
		// Test: Freier Fluss 1
		List<Cell> targets = simpleMovement();
		scenario = 1;

		// Test: Huehnertest 2
		// List<Cell> targets = chickenTest();
		// scenario = 2;

		// Test: Fundamentaldiagramm 3
		// List<Cell> targets = fundamentalDiag();
		// scenario = 3;

		// Test: Evakuriungsszenario 4
		// List<Cell> targets=twoDoorSzenario();
		// List<Cell> targets=fourDoorSzenario();
		// scenario = 4;

		/**
		 * duration: Simulationsdauer
		 */
		int sideLength = 10;
		double duration = 100;

		Scheduler scheduler = new Scheduler(duration, sideLength, targets,
				scenario);

		double tmp = scheduler.run(); // Startet Simulation

	}

	private static List<Cell> simpleMovement() {

		List<Cell> targets = new ArrayList<>();
		targets.add(new Cell(5, 9, null));
		return targets;
	}

	private static List<Cell> fundamentalDiag() {

		List<Cell> targets = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			targets.add(new Cell(i, 64, null));
		}
		return targets;
	}

	private static List<Cell> chickenTest() {
		List<Cell> targets = new ArrayList<>();
		targets.add(new Cell(5, 9, null));
		return targets;
	}

	private static List<Cell> twoDoorSzenario() {
		// 2-Tueren:
		List<Cell> targets = new ArrayList<>();
		targets.add(new Cell(5, 9, null));
		targets.add(new Cell(5, 0, null));
		return targets;
	}

	private static List<Cell> fourDoorSzenario() {
		// 4 Tueren
		List<Cell> targets = new ArrayList<>();
		targets.add(new Cell(5, 9, null));
		targets.add(new Cell(5, 0, null));
		targets.add(new Cell(0, 5, null));
		targets.add(new Cell(9, 5, null));
		return targets;
	}

}
