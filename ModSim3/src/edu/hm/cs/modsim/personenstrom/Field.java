package edu.hm.cs.modsim.personenstrom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Field {

	private int sideLength;
	private double cellLength;

	private Cell sourceCell;
	private Cell targetCell;
	private Cell targetCellForNextStep;

	private Pedestrian pedestrianToMove;

	private List<Cell> field = new ArrayList<>();
	private List<Pedestrian> pedestriansOnField;

	public Field(int sideLength, int rowSource, int colSource, int rowTarget,
			int colTarget) {
		this.sideLength = sideLength;
		this.cellLength = 1;
		this.initCells(rowSource, colSource, rowTarget, colTarget);
		// this.initBarriers();

		this.sourceCell = this.getSourceCell();
		this.targetCell = this.getTargetCell();
		this.pedestriansOnField = new LinkedList<>();
		this.initPedestrians(); // zum testen setzt mehrere Personen vor
								// Simbeginn
		// // Pedestrianquelles zum test manuell Pedestrains setzen
		// this.createPedestrian();// TODO Personen müssen verwaltet werden.
		// this.pedestrianToMove = this.getSourceCell().getPedestrian();
		// for (int i = 0; i < 5; i++) {
		// this.targetCellForNextStep = this.getTargetCellForNextStep();
		// this.movePedestrian();
		// }
	}

	public int getSideLength() {
		return sideLength;
	}

	public double getTimeToMovePedestrian(Cell that, Cell other) {
		return euklidDist(that, other)
				/ this.pedestrianToMove.getFreeFlowVelocity();
	}

	public Cell getSourceCell() {
		return sourceCell;
	}

	public Cell getTargetCell() {
		return targetCell;
	}

	public List<Cell> getField() {
		return field;
	}

	public Pedestrian getPedestrianToMove() {
		return pedestrianToMove;
	}

	public void setPedestrianToMove(Pedestrian pedestrianToMove) {
		this.pedestrianToMove = pedestrianToMove;
	}

	public void printToConsole(int sideLength) {
		for (Cell c : field) {
			c.printToConsole(sideLength);
		}
	}

	public List<Pedestrian> getPedestriansOnField() {
		return pedestriansOnField;
	}

	// Wo Fußgänger hinsetzen falls Source besetzt?
	public void createPedestrian(Pedestrian p) {
		if (!this.sourceIsOccupied()) {
			this.getSourceCell().setPedestrian(p);
		} else {
			// this.getCell(9, 0).setPedestrian(p);

		}
	}

	public Cell getCell(int row, int col) {
		return this.field.get(row * sideLength + col);
	}

	public boolean pedestrianIsOnTarget() {
		return this.pedestrianToMove.getLocation().equals(this.targetCell);
	}

	public Cell getCell(Pedestrian pedestrian) {
		for (Cell c : field) {
			if (c.getPedestrian() == pedestrian) {
				return c;
			} else {
				return null;
			}
		}
		return null;
	}

	public void initPedestrians() {
		Pedestrian p;
		// Geschwindigkeittest: Horizontal
		// p=new Pedestrian(this.getCell(5,0),2);
		// this.getCell(5, 0).setPedestrian(p);
		// this.pedestriansOnField.add(p);

		// Geschwindigkeittest: Diagonal
//		p = new Pedestrian(this.getCell(0, 0), 2);
//		this.getCell(0, 0).setPedestrian(p);
//		this.pedestriansOnField.add(p);
		

		// H�hnertest!!
		// for (int row = 2; row < 8; row++) {
		// p = new Pedestrian(this.getCell(row, 0), row+1);
		// this.getCell(row, 0).setPedestrian(p);
		// this.pedestriansOnField.add(p);
		// }
		
		//Personenpotenzialtest
		Pedestrian p1;
		p = new Pedestrian(this.getCell(0, 0), 2);
		this.getCell(0, 0).setPedestrian(p);
		this.pedestriansOnField.add(p);
		
		p1 = new Pedestrian(this.getCell(1, 0), 4);
		this.getCell(1, 0).setPedestrian(p1);
		this.pedestriansOnField.add(p1);

	}

	public void killPedestrianOnTarget(Pedestrian pedestrian) {
		pedestriansOnField.remove(pedestrian); // Aus Pedestrian Container
		this.getTargetCell().setPedestrian(null);
	}

	public Cell getTargetCellForNextStep() {
		// pedestrianToMove immer vor getTargetCellForNextStep ermitteln
		this.targetCellForNextStep = pedestrianToMove.getLocation();
		double utilityValue = 1;
		double tmp;
		Set<Cell> neighbours = this
				.getNeighboursOfPedestrian(this.pedestrianToMove);

		for (Cell c : neighbours) {
			// !!!Spezialfall F�r freie sicht zwischen Person und Ziel
			tmp = this.euklidDist(c, targetCell) * (-1);
			tmp += friedrichsMollifier(c);
			if (utilityValue == 1 || tmp > utilityValue) {
				utilityValue = tmp;
				this.targetCellForNextStep = c;
			}
		}

		// System.out.println(targetCellForNextStep.getRow() + " " +
		// targetCellForNextStep.getCol());
		// this.timeToMovePedestrian = utilityValue * (-1)
		// / pedestrianToMove.getFreeFlowVelocity();
		return targetCellForNextStep;
	}

	private double friedrichsMollifier(Cell cell) {
		int h = 2;
		int w = 2;
		double dist=0;
		double result = 0;
		Set<Cell> cellsInRadius = new HashSet<>();
		Set<Cell> tmp = new HashSet<>();
		Set<Pedestrian> pedestriansInRadius = new HashSet<>();
		cellsInRadius.addAll(this.getNeighboursOfPedestrian(new Pedestrian(
				cell, 0)));
		for (Cell c : cellsInRadius) {
			tmp.addAll(this.getNeighboursOfPedestrian(new Pedestrian(c, 0)));
		}
		cellsInRadius.addAll(tmp);

		for (Cell c : cellsInRadius) {
			if (c.getPedestrian() != null) {
				pedestriansInRadius.add(c.getPedestrian());
			}

		}
		
		if(pedestriansInRadius.isEmpty()){
			return result;
		}else{
			for(Pedestrian p : pedestriansInRadius){
				dist=euklidDist(cell,p.getLocation());
				result+= -h*this.cellLength* Math.exp(1/(Math.pow((dist/w*this.cellLength), 2)));
			}
		}
		System.out.println(result);
		return result;

	}

	public boolean movePedestrian() {
		Cell pedStart;
		Cell pedMoveTo;

		if (!pedestrianToMove.getLocation().equals(targetCellForNextStep)) { // bleibt
																				// auf
																				// Target
																				// stehen
			pedStart = this.getCell(pedestrianToMove.getLocation().getRow(), // Start
																				// Zelle
																				// auf
																				// der
																				// Pedestrian
																				// steht
					pedestrianToMove.getLocation().getCol());
			pedMoveTo = this.getTargetCellForNextStep(); // Zielzelle ermitteln
			pedStart.setPedestrian(null);
			pedMoveTo.setPedestrian(pedestrianToMove);
			this.pedestrianToMove.setLocation(targetCellForNextStep);
			return true;
		} else {
			return false;
		}
	}
//obacht hubert schon wieder scheisse gebaut...puuuh  
	public Set<Cell> getNeighboursOfPedestrian(Pedestrian p) {
		int row = this.getPedestrianToMove().getLocation().getRow();
		int col = this.getPedestrianToMove().getLocation().getCol();
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
		neighbours.removeAll(delete);
		return neighbours;
	}

	/**
	 * Creates field of Cells with size of sideLength*sideLength. Initialize one
	 * Source, one Target and sideLength*sideLengths - 2 free Cells.
	 * 
	 * @param rowSource
	 *            row index of Source.
	 * @param colSource
	 *            col index of Source.
	 * @param rowTarget
	 *            row index of Target.
	 * @param colTarget
	 *            col index of Target.
	 */
	private void initCells(int rowSource, int colSource, int rowTarget,
			int colTarget) {
		for (int row = 0; row < sideLength; row++) {
			for (int col = 0; col < sideLength; col++) {
				if (rowSource == row && colSource == col) {
					Cell source = new Cell(rowSource, colSource, null);
					source.setSource(new Source());
					field.add(source); // Pedestrianquelle
										// setzten
					this.sourceCell = source;
				} else if (rowTarget == row && colTarget == col) {
					Cell target = new Cell(rowTarget, colTarget, null);
					target.setTarget(new Target());
					field.add(target); // Pedestrianziel
										// setzenm
					this.targetCell = target;
				} else {
					field.add(new Cell(row, col, null)); // restlichen frei
					// zugänglichen Zellen
					// setzen
				}
			}
		}
	}

	private void initBarriers() {
		// Testfall1 Barrier durchgehend in mitte
		// for (int i = 0; i < 10; i++) {
		// this.getCell(i, 5).setBarrier(new Barrier());
		// }
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

	}

	private double euklidDist(Cell that, Cell other) {
		// Zellenmittelpunkte zur berechnung verwenden
		double x1 = that.getRow() + cellLength / 2;
		double y1 = that.getCol() + cellLength / 2;
		double x2 = other.getRow() + cellLength / 2;
		double y2 = other.getCol() + cellLength / 2;

		// euklidischer Abstand
		return Math.sqrt(Math.pow(Math.abs(x2 - x1), 2)
				+ Math.pow(Math.abs(y2 - y1), 2));
	}

	public boolean sourceIsOccupied() {
		return (this.getSourceCell().getPedestrian() != null) ? true : false;
	}
	
	
	
	// private boolean isOnEastBorder() {
	// return false;
	// }
	//
	// private boolean isOnSouthBorder() {
	// return false;
	// }
	//
	// private boolean isOnWestBorder() {
	// return false;
	// }
	//
	// private boolean isOnNorthBorder() {
	// return false;
	// }
}
