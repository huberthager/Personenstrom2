package edu.hm.cs.modsim.personenstrom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Field {

	private static final int SCENARIO_CHICKEN_TEST = 2;
	private static final int SCENARIO_FUNDAMENTAL_DIAGRAMM = 3;

	private static double STANDARD_LINE_WIDTH = 12;
	private static final int STANDARD_LINE_START = 30;
	private static final int STANDARD_LINE_STOP = 32;
	private static final int SIDE_LENGTH_FUNDEMENTAL = 65;

	private int sideLength;
	private int scenario;
	private double cellLength;

	private List<Cell> targets;
	private Cell targetCellForNextStep;

	private Pedestrian pedestrianToMove;
	private Pedestrian pedestrianReturn;

	private List<Cell> field = new ArrayList<>();
	private List<Pedestrian> pedestriansOnField;
	private Map<Pedestrian, Double> pOnStdLine = new HashMap<>();

	// Daten
	private List<Double> realVels = new ArrayList<>();
	private List<Double> realVelEventTimes = new ArrayList<>();
	private List<Double> personFlow = new ArrayList<>();

	/**
	 * Repräsentiert Feld auf dem Personenstrom simuliert wird.
	 * 
	 * @param sideLength
	 *            Seitenlänge des quadratischen Feldes
	 * @param targets
	 *            Zielzellen, die Fußgänger ansteuern
	 * @param scenario
	 *            Id des Szenarios welches simuliert wird
	 */
	public Field(int sideLength, List<Cell> targets, int scenario) {
		if (scenario == SCENARIO_FUNDAMENTAL_DIAGRAMM) {
			this.sideLength = SIDE_LENGTH_FUNDEMENTAL;
		} else {
			this.sideLength = sideLength;
		}
		this.cellLength = 1;
		this.scenario = 65;
		this.scenario = scenario;
		this.initCells(targets);
		this.pedestrianReturn = null;
		this.targets = targets;
		this.pedestriansOnField = new LinkedList<>();
		this.initPedestrians(scenario);
	}

	/**
	 * Getter für wiederankommenden Fußgänger
	 * 
	 * @return wiederankommenden Fußgänger
	 */
	public Pedestrian getPedestrianReturn() {
		Pedestrian p = pedestrianReturn;
		pedestrianReturn = null;
		return p;
	}

	/**
	 * Zeitberechnung für einen Schritt eines Fußgängers
	 * 
	 * @param that
	 *            Standort des zu bewegenden Fußgängers
	 * @param other
	 *            Zielort des zu bewegenden Fußgängers
	 * @return Double Zeit für nächsten Schritt
	 */
	public double getTimeToMovePedestrian(Cell that, Cell other) {
		return euklidDist(that, other)
				/ this.pedestrianToMove.getFreeFlowVelocity();
	}

	/**
	 * Getter für die Zielzellen
	 * 
	 * @return Array von Zielzellen
	 */
	public List<Cell> getTargets() {
		return targets;
	}

	/**
	 * Getter für Field
	 * 
	 * @return Feld
	 */
	public List<Cell> getField() {
		return field;
	}

	/**
	 * Getter für aktiven Fußgänger
	 * 
	 * @return Fußgänger
	 */
	public Pedestrian getPedestrianToMove() {
		return pedestrianToMove;
	}

	/**
	 * Fußgänger aktiv schalten
	 * 
	 * @param pedestrianToMove
	 */
	public void setPedestrianToMove(Pedestrian pedestrianToMove) {
		this.pedestrianToMove = pedestrianToMove;
	}

	// public void printToConsole(int sideLength) {
	// for (Cell c : field) {
	// c.printToConsole(sideLength);
	// }
	// }

	/**
	 * Getter für alle auf dem Feld befindlichen Fußgänger
	 * 
	 * @return Array von Fußgängern
	 */
	public List<Pedestrian> getPedestriansOnField() {
		return pedestriansOnField;
	}

	/**
	 * Getter für eine bestimmte Zelle auf dem Feld
	 * 
	 * @param row
	 *            Koordinate für die Reihe der Zelle
	 * @param col
	 *            Koordinate für die Spalte der Zelle
	 * @return Zelle
	 */
	public Cell getCell(int row, int col) {
		return this.field.get(row * sideLength + col);
	}

	/**
	 * Gibt an ob Fußgänger auf einem Ziel steht oder nicht
	 * 
	 * @return boolean
	 */
	public boolean pedestrianIsOnTarget() {
		for (Cell c : targets) {
			if (this.pedestrianToMove.getLocation().equals(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Getter für Zelle anhand eines gesuchten Fußgängers
	 * 
	 * @param pedestrian
	 * @return Zelle auf der bestimmer Fußgänger steht
	 */
	public Cell getCell(Pedestrian pedestrian) {
		for (Cell c : field) {
			if (c.getPedestrian() != null) {
				if (c.getPedestrian().equals(pedestrian)) {
					return c;
				}
			}
		}
		return null;
	}

	/**
	 * Setzt Fußgänger auf Startposition im Feld
	 * 
	 * @param szenario
	 */
	public void initPedestrians(int szenario) {
		Pedestrian p;

		if (szenario == 1) {
			// // Geschwindigkeittest: Horizontal
			// p = new Pedestrian(this.getCell(0, 0));
			// this.getCell(0, 0).setPedestrian(p);
			// this.pedestriansOnField.add(p);

			// Geschwindigkeittest: Diagonal
			// p = new Pedestrian(this.getCell(0, 0));
			// this.getCell(0, 0).setPedestrian(p);
			// this.pedestriansOnField.add(p);

			// Personenpotenzialtest
			Pedestrian p1;
			p = new Pedestrian(this.getCell(0, 0));
			this.getCell(0, 0).setPedestrian(p);
			this.pedestriansOnField.add(p);

			p1 = new Pedestrian(this.getCell(1, 0));
			this.getCell(1, 0).setPedestrian(p1);
			this.pedestriansOnField.add(p1);

			p1 = new Pedestrian(this.getCell(2, 0));
			this.getCell(2, 0).setPedestrian(p1);
			this.pedestriansOnField.add(p1);

		} else if (szenario == 2) {
			// Huehnertest!!
			for (int row = 2; row < 8; row++) {
				p = new Pedestrian(this.getCell(row, 0));
				this.getCell(row, 0).setPedestrian(p);
				this.pedestriansOnField.add(p);
			}
			this.initBarriers(szenario);

		} else if (szenario == 3) {
			// Fundamentaldiagramm
			for (int row = 0; row < 12; row++) {
				p = new Pedestrian(this.getCell(row, 0));
				this.getCell(row, 0).setPedestrian(p);
				this.pedestriansOnField.add(p);

				p = new Pedestrian(this.getCell(row, 4));
				this.getCell(row, 4).setPedestrian(p);
				this.pedestriansOnField.add(p);

				p = new Pedestrian(this.getCell(row, 8));
				this.getCell(row, 8).setPedestrian(p);
				this.pedestriansOnField.add(p);

				p = new Pedestrian(this.getCell(row, 14));
				this.getCell(row, 14).setPedestrian(p);
				this.pedestriansOnField.add(p);

				p = new Pedestrian(this.getCell(row, 16));
				this.getCell(row, 16).setPedestrian(p);
				this.pedestriansOnField.add(p);

				p = new Pedestrian(this.getCell(row, 20));
				this.getCell(row, 20).setPedestrian(p);
				this.pedestriansOnField.add(p);

				p = new Pedestrian(this.getCell(row, 24));
				this.getCell(row, 24).setPedestrian(p);
				this.pedestriansOnField.add(p);

				p = new Pedestrian(this.getCell(row, 27));
				this.getCell(row, 27).setPedestrian(p);
				this.pedestriansOnField.add(p);

				p = new Pedestrian(this.getCell(row, 31));
				this.getCell(row, 31).setPedestrian(p);
				this.pedestriansOnField.add(p);

				p = new Pedestrian(this.getCell(row, 37));
				this.getCell(row, 37).setPedestrian(p);
				this.pedestriansOnField.add(p);
			}
			this.initBarriers(szenario);
		} else if (szenario == 4) {

			// Evakuirungsszenario 1

			for (int row = 3; row < 7; row++) {
				for (int col = 3; col < 7; col++) {
					p = new Pedestrian(this.getCell(row, col));
					this.getCell(row, col).setPedestrian(p);
					this.pedestriansOnField.add(p);
				}
				// Evakuirungsszenario 2

				// for(int row = 2;row<8;row++){
				// for(int col=2;col<8;col++){
				// p = new Pedestrian(this.getCell(row, col));
				// this.getCell(row, col).setPedestrian(p);
				// this.pedestriansOnField.add(p);
				// }
			}

		}

	}

	/**
	 * Getter für realisierte Geschwindigkeiten des Personenstroms
	 * 
	 * @return Array von absoluten Geschwindigkeiten
	 */
	public List<Double> getRealVels() {
		return realVels;
	}

	/**
	 * ?
	 * 
	 * @return
	 */
	public List<Double> getRealVelEventTimes() {
		return realVelEventTimes;
	}

	/**
	 * Getter für absolut gemessene Flusswerte des Personenstroms
	 * 
	 * @return
	 */
	public List<Double> getPersonFlow() {
		return personFlow;
	}

	/**
	 * Nimmt Fußgänger auf Zielzelle vom Feld
	 * 
	 * @param pedestrian
	 */
	public void killPedestrianOnTarget(Pedestrian pedestrian) {
		pedestriansOnField.remove(pedestrian); // Aus Pedestrian Container
		for (Cell c : targets) {
			if (pedestrian.getLocation().equals(c)) {
				pedestrian.getLocation().setPedestrian(null);
			}
		}
		if (scenario == SCENARIO_FUNDAMENTAL_DIAGRAMM) {
			int row = randomAccessPoint();
			int col = 0;
			while (getCell(row, col).isOccupied()) {
				col++;
			}
			this.pedestrianReturn = new Pedestrian(this.getCell(row, col));
			this.getCell(row, col).setPedestrian(pedestrianReturn);
			this.pedestriansOnField.add(pedestrianReturn);
		}
	}

	/**
	 * Überprüft ob Fußgänger ohne wartende Aktion auf dem Feld ist
	 * 
	 * @return Fußgänger
	 */
	public boolean isPedestrianWithoutEvent() {
		return pedestrianReturn != null;
	}

	/**
	 * Berechnet Zielzelle für aktiven Fußgänger auf freie Sicht mit
	 * Personenpotential
	 * 
	 * @return Zelle
	 */
	public Cell getTargetCellForNextStep() {
		if (scenario == SCENARIO_CHICKEN_TEST) {
			// Dijkstra anfang:
			int cellStart = field.indexOf(pedestrianToMove.getLocation());
			Cell dijkstraCell = dijkstra(cellStart);
			targetCellForNextStep = dijkstraCell;
			return dijkstraCell;
			// Dijkstra ende
		} else {
			// Initialisiere die naechst beste Zelle mit der Zelle auf der er
			// steht.
			this.targetCellForNextStep = pedestrianToMove.getLocation();
			double utilityValue = 1;
			double tmp;
			Cell myTarget = pedestrianToMove.getLocation();
			double myDist = Double.MAX_VALUE;

			// Freie Sicht auf Ziel
			Set<Cell> neighbours = this.getNeighboursOfPedestrian(
					this.pedestrianToMove, 0);
			// Welches Target ist das beste fuer mich?
			for (Cell d : targets) {
				tmp = this.euklidDist(pedestrianToMove.getLocation(), d);
				if (tmp < myDist) {
					myTarget = d;
					myDist = tmp;
				}

			}
			for (Cell c : neighbours) {
				tmp = this.euklidDist(c, myTarget) * (-1);
				tmp += friedrichsMollifier(c);
				if (utilityValue == 1 || tmp > utilityValue) {
					utilityValue = tmp;
					this.targetCellForNextStep = c;
				}
			}
		}
		return targetCellForNextStep;
	}

	/**
	 * Generiert zufälligen Eintrittsort eines Fußgängers
	 * 
	 * @return int
	 */
	private int randomAccessPoint() {
		return (int) (Math.random() * 12);
	}

	/**
	 * Berechnet Abstoßungsgrad der umliegenden Fußgänger anhand der Position
	 * 
	 * @param cell
	 * @return double Abstoßungsgrad
	 */
	private double friedrichsMollifier(Cell cell) {
		int h = 2;
		int w = 2;
		double dist = 0;
		double result = 0;
		Set<Cell> cellsInRadius = new HashSet<>();
		Set<Cell> tmp = new HashSet<>();
		Set<Pedestrian> pedestriansInRadius = new HashSet<>();
		cellsInRadius.addAll(this.getNeighboursOfPedestrian(
				new Pedestrian(cell), 1));
		for (Cell c : cellsInRadius) {
			tmp.addAll(this.getNeighboursOfPedestrian(new Pedestrian(c), 1));
		}
		cellsInRadius.addAll(tmp);

		for (Cell c : cellsInRadius) {
			if (c.getPedestrian() != null
					&& !c.getPedestrian().equals(pedestrianToMove)) {
				pedestriansInRadius.add(c.getPedestrian());
			}

		}
		if (pedestriansInRadius.isEmpty()) {
			return result;
		} else {
			for (Pedestrian p : pedestriansInRadius) {
				dist = euklidDist(cell, p.getLocation());
				if (Math.abs(dist) < w) {
					result += -h
							* this.cellLength
							* Math.exp(1 / (Math.pow(
									(dist / w * this.cellLength), 2) - 1));
				} else {
					result += 0;
				}
			}
		}
		// System.out.println("Mollifier :" +result);
		return result;

	}

	/**
	 * Bewegt aktiven Fußgänger, im Fall des Korridorszenarios werden Daten zu
	 * Flussdichte bzw der Flussrate gemessen
	 * 
	 * @param eventTime
	 *            Simulationszeitpunkt zu dem Schritt stattfindet
	 * @param timeToMove
	 *            Zeit die Schritt dauert
	 * @return boolean
	 */
	public boolean movePedestrian(double eventTime, double timeToMove) {
		Cell pedStart = getCell(pedestrianToMove);
		Cell pedMoveTo = this.getTargetCellForNextStep();
		double timeInStrip = 0;

		if (!pedStart.equals(pedMoveTo)) {
			if (scenario == SCENARIO_FUNDAMENTAL_DIAGRAMM) {
				if (pedMoveTo.getCol() == STANDARD_LINE_START) {
					if (!pOnStdLine.containsKey(pedestrianToMove)) {
						pOnStdLine
								.put(pedestrianToMove, eventTime + timeToMove);
					}
				}
				if (pedMoveTo.getCol() == STANDARD_LINE_STOP + 1) {
					if (pOnStdLine.containsKey(pedestrianToMove)) {
						timeInStrip = eventTime
								- pOnStdLine.get(pedestrianToMove);
						pOnStdLine.remove(pedestrianToMove);
					}
				}
			}
			pedStart.setPedestrian(null);
			pedMoveTo.setPedestrian(pedestrianToMove);
			this.pedestrianToMove.setLocation(pedMoveTo);
			if (timeInStrip != 0) {
				double vel = (STANDARD_LINE_STOP - STANDARD_LINE_START)
						/ timeInStrip;
				realVelEventTimes.add(eventTime);
				realVels.add(vel);
				personFlow.add(pOnStdLine.size() / STANDARD_LINE_WIDTH);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Ermittelt Nachbarzellen anhand eines Fußgängers
	 * 
	 * @param p
	 *            Fußgänger
	 * @param mollifier
	 *            int
	 * @return Set von Zellen
	 */
	public Set<Cell> getNeighboursOfPedestrian(Pedestrian p, int mollifier) {
		int row = p.getLocation().getRow();
		int col = p.getLocation().getCol();
		return neighboursOfCell(row, col, mollifier);

	}

	/**
	 * Ermittelt Nachbarzellen anhand einer Zelle
	 * 
	 * @param row
	 *            row Koordinate auf Feld
	 * @param col
	 *            col Koordinate auf Feld
	 * @param mollifier
	 *            Abstoßungsgrad
	 * @return Set von Zellen
	 */
	private Set<Cell> neighboursOfCell(int row, int col, int mollifier) {
		int length = this.sideLength - 1;
		Set<Cell> neighbours = new HashSet<>();
		// Ecken
		if (row == 0 && col == 0) {
			neighbours.add(this.getCell(row, col + 1));
			neighbours.add(this.getCell(row + 1, col + 1));
			neighbours.add(this.getCell(row + 1, col));
		} else if (row == length && col == 0) {
			neighbours.add(this.getCell(row - 1, col));
			neighbours.add(this.getCell(row - 1, col + 1));
			neighbours.add(this.getCell(row, col + 1));

		} else if (row == length && col == length) {
			neighbours.add(this.getCell(row, col - 1));
			neighbours.add(this.getCell(row - 1, col - 1));
			neighbours.add(this.getCell(row - 1, col));

		} else if (row == 0 && col == length) {
			neighbours.add(this.getCell(row, col - 1));
			neighbours.add(this.getCell(row + 1, col));
			neighbours.add(this.getCell(row + 1, col - 1));

		} else if (col == 0) {
			neighbours.add(this.getCell(row, col + 1));
			neighbours.add(this.getCell(row + 1, col + 1));
			neighbours.add(this.getCell(row + 1, col));
			neighbours.add(this.getCell(row - 1, col));
			neighbours.add(this.getCell(row - 1, col + 1));
		} else if (row == length) {
			neighbours.add(this.getCell(row, col + 1));
			neighbours.add(this.getCell(row, col - 1));
			neighbours.add(this.getCell(row - 1, col - 1));
			neighbours.add(this.getCell(row - 1, col));
			neighbours.add(this.getCell(row - 1, col + 1));

		} else if (col == length) {
			neighbours.add(this.getCell(row + 1, col));
			neighbours.add(this.getCell(row + 1, col - 1));
			neighbours.add(this.getCell(row, col - 1));
			neighbours.add(this.getCell(row - 1, col - 1));
			neighbours.add(this.getCell(row - 1, col));

		} else if (row == 0) {
			neighbours.add(this.getCell(row, col + 1));
			neighbours.add(this.getCell(row + 1, col + 1));
			neighbours.add(this.getCell(row + 1, col));
			neighbours.add(this.getCell(row + 1, col - 1));
			neighbours.add(this.getCell(row, col - 1));

		} else {
			neighbours.add(this.getCell(row, col + 1));
			neighbours.add(this.getCell(row + 1, col + 1));
			neighbours.add(this.getCell(row + 1, col));
			neighbours.add(this.getCell(row + 1, col - 1));
			neighbours.add(this.getCell(row, col - 1));
			neighbours.add(this.getCell(row - 1, col - 1));
			neighbours.add(this.getCell(row - 1, col));
			neighbours.add(this.getCell(row - 1, col + 1));
		}

		Set<Cell> delete = new HashSet<>();
		for (Cell c : neighbours) {
			if (c.getPedestrian() != null || c.getBarrier() != null) {
				delete.add(c);
			}

		}
		if (mollifier == 0) {
			neighbours.removeAll(delete);
		}
		return neighbours;
	}

	/**
	 * Initialisiert die row und col Koordinaten aller Zellen auf Feld
	 * 
	 * @param targets
	 *            Zellenarray
	 */
	private void initCells(List<Cell> targets) {

		for (int row = 0; row < sideLength; row++) {
			for (int col = 0; col < sideLength; col++) {
				field.add(new Cell(row, col, null));
			}
		}
		for (Cell c : targets) {
			field.get(c.getRow() * sideLength + c.getCol()).setTarget(
					new Target());
		}
	}

	/**
	 * Initialisiert Barrieren anhand des gewählten Szenarios
	 * 
	 * @param szenario
	 *            ID
	 */
	private void initBarriers(int szenario) {
		if (szenario == 2) {
			// Huenertest
			this.getCell(2, 5).setBarrier(new Barrier());
			this.getCell(2, 6).setBarrier(new Barrier());
			this.getCell(2, 7).setBarrier(new Barrier());

			this.getCell(2, 7).setBarrier(new Barrier());
			this.getCell(3, 7).setBarrier(new Barrier());
			this.getCell(4, 7).setBarrier(new Barrier());
			this.getCell(5, 7).setBarrier(new Barrier());
			this.getCell(6, 7).setBarrier(new Barrier());
			this.getCell(7, 7).setBarrier(new Barrier());

			this.getCell(7, 5).setBarrier(new Barrier());
			this.getCell(7, 6).setBarrier(new Barrier());
			this.getCell(7, 7).setBarrier(new Barrier());
		} else if (szenario == 3) {
			// Fundamentaldiagramm
			for (int i = 0; i < 65; i++) {
				this.getCell(12, i).setBarrier(new Barrier());
			}
		}
	}

	/**
	 * Errechnet kürzeste Distanz aus einer Menge an Zellen bzgl einer
	 * Startposition und gibt Zelle mit kürzestem Abstand als ID zurück
	 * 
	 * @param reachables
	 * @return int Zellen ID
	 */
	private int shortestDist(Map<Integer, Double> reachables) {
		int shortest = -1;
		double tmp = 1000;
		Iterator<Entry<Integer, Double>> it = reachables.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Double> pair = it.next();

			double dist = pair.getValue();

			if (tmp > dist) {
				tmp = dist;
				shortest = pair.getKey();
			}
		}
		return shortest;
	}

	/**
	 * Aktualisiert Entfernung von einer Startposition bezüglich potentiell
	 * erreichbaren Zellen
	 * 
	 * @param reachables
	 *            Menge an erreichbaren Zellen
	 * @param deleted
	 *            Menge an markierten Zellen
	 * @param shortest
	 *            ID der Zelle mit kürzestem Abstand zur Startposition
	 * @param distToShortest
	 *            absolute Distanz von shortest
	 * @return
	 */
	private Map<Integer, Double> updateDist(Map<Integer, Double> reachables,
			Map<Integer, Double> deleted, int shortest, double distToShortest) {
		int row = shortest / sideLength;
		int col = shortest % sideLength;
		int index;
		double shortestToNeighbour;
		// Cell Cshort = field.indexOf(shortest);
		Set<Cell> neighbours = neighboursOfCell(row, col, 0);

		for (Cell c : neighbours) {
			index = field.indexOf(c);
			if (!reachables.containsKey(index) && !deleted.containsKey(index)) {
				shortestToNeighbour = euklidDist(field.get(shortest),
						field.get(index));
				reachables.put(index, distToShortest + shortestToNeighbour);
			}
		}
		return reachables;
	}

	/**
	 * Findet kürzesten Weg von Zielposition zur Startposition und gibt erstes
	 * Wegstück zurück
	 * 
	 * @param target
	 * @param distStartToTarget
	 * @param deleted
	 * @return
	 */
	private List<Integer> pathFromTargetToStart(int target,
			double distStartToTarget, Map<Integer, Double> deleted) {
		boolean finished = false;
		int traversal = target;
		int cellToAdd = 1000;
		List<Integer> path = new ArrayList<>();
		Set<Cell> neighbours = new HashSet<>();

		path.add(target);

		while (!finished) { // Wann aufhören
			neighbours = neighboursOfCell(traversal / sideLength, traversal
					% sideLength, 0);
			double tmp = distStartToTarget;
			for (Cell c : neighbours) {
				int index = field.indexOf(c);
				if (tmp > deleted.get(index)) {
					tmp = deleted.get(index);
					cellToAdd = index;
				}

			}
			if (cellToAdd != 1000) {

				distStartToTarget = deleted.get(cellToAdd);
				traversal = cellToAdd;
				path.add(cellToAdd);
				if (deleted.get(cellToAdd) < 2) {
					finished = true;
				}
			} else {
				finished = true;
			}
		}

		return path;
	}

	/**
	 * Berechnet günstigste Zielzelle bzgl einer Position
	 * 
	 * @param location
	 * @return Zielzellen ID
	 */
	private int nearestTarget(int location) {
		int target = location;
		double dist = Double.MAX_VALUE;
		double tmp;
		for (Cell c : targets) {
			tmp = euklidDist(field.get(location), c);
			if (tmp < dist) {
				dist = tmp;
				target = c.getRow() * sideLength + c.getCol();
			}
		}
		return target;
	}

	/**
	 * Berrechnet Nachbarzellen und Entfernung aus der Menge von erreichbaren
	 * Zellen bzgl. Startposition
	 * 
	 * @param reachables
	 * @param cell
	 * @return Map<Zellen ID,, Distanz>
	 */
	private Map<Integer, Double> neighboursFromCell(
			Map<Integer, Double> reachables, int cell) {
		int row = cell / sideLength;
		int col = cell % sideLength;
		int index;
		double dist;
		Set<Cell> neighbours = neighboursOfCell(row, col, 0);
		for (Cell c : neighbours) {
			if (!c.isOccupied()) {
				index = field.indexOf(c);
				dist = euklidDist(field.get(cell), c);

				reachables.put(index, dist);
			}
		}
		return reachables;
	}

	/**
	 * Berrechnet kürzesten Weg bzgl einer Startposition nach dem
	 * Dijkstraalgorithmus
	 * 
	 * @param i
	 *            Zellen ID
	 * @return Zielzelle
	 */
	private Cell dijkstra(int i) {
		int start = i;
		int shortest;
		double distToShortest;
		Map<Integer, Double> reachables = new HashMap<>();
		Map<Integer, Double> deleted = new HashMap<>();

		reachables = neighboursFromCell(reachables, start);

		deleted.put(start, -1.0);
		Cell nextCell = field.get(start);

		while (!reachables.isEmpty() && !deleted.isEmpty()) {
			shortest = shortestDist(reachables);

			deleted.put(shortest, reachables.get(shortest));

			distToShortest = reachables.get(shortest);
			reachables.remove(shortest);

			reachables = updateDist(reachables, deleted, shortest,
					distToShortest);
		}
		int target = nearestTarget(start);
		if (deleted.size() > 1 && deleted.containsKey(target)) {
			double distStartToTarget = deleted.get(target);

			List<Integer> pathReverse = pathFromTargetToStart(target,
					distStartToTarget, deleted);
			nextCell = this.field.get(pathReverse.get(pathReverse.size() - 1));
		}
		return nextCell;
	}

	/**
	 * Berechnet euklidische Distanz zweier Zellen
	 * 
	 * @param that
	 *            Zelle1
	 * @param other
	 *            Zelle 2
	 * @return Double Abstand
	 */
	private double euklidDist(Cell that, Cell other) {
		double x1 = that.getRow() + cellLength / 2;
		double y1 = that.getCol() + cellLength / 2;
		double x2 = other.getRow() + cellLength / 2;
		double y2 = other.getCol() + cellLength / 2;

		return Math.sqrt(Math.pow(Math.abs(x2 - x1), 2)
				+ Math.pow(Math.abs(y2 - y1), 2));
	}

	/**
	 * Erstellt String der grafischen Status der laufenden Simulation abbildet.
	 * 
	 * @param eventTime
	 *            Zeit zu dem eine Aktion auf dem Feld ausgeführt wird
	 * @param result
	 *            String für Ausgabe
	 * @return String Ansicht des Spielfeldes
	 */
	public String guiToString(double eventTime, String result) {
		if (scenario == SCENARIO_FUNDAMENTAL_DIAGRAMM) {
			for (Cell c : field) {
				if (c.getRow() < 13) {
					result = c.guiToString(sideLength, result);
				}
			}
		} else {
			for (Cell c : field) {
				result = c.guiToString(sideLength, result);
			}
		}
		result += "\n" + eventTime + "\n";
		return result;
	}
}
