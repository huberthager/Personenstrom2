package edu.hm.cs.modsim.personenstrom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Field {

	private int sideLength;
	private double cellLength;

	private Cell sourceCell;
	private List<Cell> targets;
	private Cell targetCellForNextStep;
	

	private Pedestrian pedestrianToMove;

	private List<Cell> field = new ArrayList<>();
	private List<Pedestrian> pedestriansOnField;

	public Field(int sideLength, int rowSource, int colSource, List<Cell> targets,int szenario) {
		this.sideLength = sideLength;
		this.cellLength = 1;
		this.initCells(rowSource, colSource, targets);


		this.sourceCell = this.getSourceCell();
		this.targets=targets;
		this.pedestriansOnField = new LinkedList<>();
		this.initPedestrians(szenario); 
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

	public List<Cell> getTargets() {
		return targets;
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

//	// Wo Fußgänger hinsetzen falls Source besetzt?
//	public void createPedestrian(Pedestrian p) {
//		if (!this.sourceIsOccupied()) {
//			this.getSourceCell().setPedestrian(p);
//		} else {
//			// this.getCell(9, 0).setPedestrian(p);
//
//		}
//	}

	public Cell getCell(int row, int col) {
		return this.field.get(row * sideLength + col);
	}

	public boolean pedestrianIsOnTarget() {
		for(Cell c:targets){
			if(this.pedestrianToMove.getLocation().equals(c)){
				return true;
			}
		}
		return false;
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

	public void initPedestrians(int szenario) {
		Pedestrian p;
		
		if(szenario==1){
//		// Geschwindigkeittest: Horizontal
//		p = new Pedestrian(this.getCell(0, 0));
//		this.getCell(0, 0).setPedestrian(p);
//		this.pedestriansOnField.add(p);

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

		}else if(szenario==2){
		// H�hnertest!!
		 for (int row = 2; row < 8; row++) {
			 p = new Pedestrian(this.getCell(row, 0));
			 this.getCell(row, 0).setPedestrian(p);
			 this.pedestriansOnField.add(p);
		 }
 		 this.initBarriers(szenario);
			
		}else if(szenario==3){
		//Fundamentaldiagramm
		 for (int row = 0; row < 12; row++) {
		 p = new Pedestrian(this.getCell(row, 0));
		 this.getCell(row, 0).setPedestrian(p);
		 this.pedestriansOnField.add(p);
		 }
		 this.initBarriers(szenario);
		}else if(szenario==4){
		
		//Evakuirungsszenario 1
		
		for(int row = 3;row<7;row++){
			for(int col=3;col<7;col++){
				p = new Pedestrian(this.getCell(row, col));
				this.getCell(row, col).setPedestrian(p);
				this.pedestriansOnField.add(p);
			}
			//Evakuirungsszenario 2
			
//			for(int row = 2;row<8;row++){
//				for(int col=2;col<8;col++){
//					p = new Pedestrian(this.getCell(row, col));
//					this.getCell(row, col).setPedestrian(p);
//					this.pedestriansOnField.add(p);
//				}
		}
		
		}
		
		
	}

	public void killPedestrianOnTarget(Pedestrian pedestrian) {
		pedestriansOnField.remove(pedestrian); // Aus Pedestrian Container
		for(Cell c:targets){
			if(pedestrian.getLocation().equals(c)){
				pedestrian.getLocation().setPedestrian(null);
			}
		}
		
	}

	public Cell getTargetCellForNextStep() {
		//Initialisiere die naechst beste Zelle mit der Zelle auf der er steht.
		this.targetCellForNextStep = pedestrianToMove.getLocation();
		double utilityValue = 1;
		double tmp;
		Cell myTarget=pedestrianToMove.getLocation();
		double myDist=Double.MAX_VALUE;
		
		//Freie Sicht auf Ziel
		Set<Cell> neighbours = this
				.getNeighboursOfPedestrian(this.pedestrianToMove,0);
		//Welches Target ist das beste fuer mich?
		for(Cell d:targets){
			tmp=this.euklidDist(pedestrianToMove.getLocation(),d);
			if(tmp<myDist){
				myTarget=d;
				myDist=tmp;
			}
			
			
		}
		for (Cell c : neighbours) {
			tmp = this.euklidDist(c,myTarget ) * (-1);
			tmp += friedrichsMollifier(c);
			if (utilityValue == 1 || tmp > utilityValue) {
				utilityValue = tmp;
				this.targetCellForNextStep = c;
			}
		}
		return targetCellForNextStep;
	}

//	private Cell Dijkstra(Cell start){
//		//Initzialisiere
//		ArrayList<Double> distance=new ArrayList<>();
//		ArrayList<Cell> ancestor=new ArrayList<>();
//		int counter=0;
//		int startKnot=start.getRow()*sideLength+start.getCol();
//		for(Cell c:field){
//			counter++;
//			distance.add(counter, Double.MAX_VALUE);
//			ancestor.add(counter,null);
//			distance.add(startKnot,0.0);
//		}
//		ArrayList<Cell> Q =new ArrayList<>();
//		Q.addAll(field);
//		while(!Q.isEmpty()){
//			u=distance.
//		}
//	}

	 
	private double friedrichsMollifier(Cell cell) {
		int h = 2;
		int w = 2;
		double dist = 0;
		double result = 0;
		Set<Cell> cellsInRadius = new HashSet<>();
		Set<Cell> tmp = new HashSet<>();
		Set<Pedestrian> pedestriansInRadius = new HashSet<>();
		cellsInRadius.addAll(this.getNeighboursOfPedestrian(new Pedestrian(
				cell),1));
		for (Cell c : cellsInRadius) {
			tmp.addAll(this.getNeighboursOfPedestrian(new Pedestrian(c),1));
		}
		cellsInRadius.addAll(tmp);

		for (Cell c : cellsInRadius) {
			if (c.getPedestrian() != null && !c.getPedestrian().equals(pedestrianToMove)) {
				pedestriansInRadius.add(c.getPedestrian());
			}

		}

		if (pedestriansInRadius.isEmpty()) {
			return result;
		} else {
			for (Pedestrian p : pedestriansInRadius) {
				dist = euklidDist(cell, p.getLocation());
				if(Math.abs(dist)<w){
				result+= -h
						* this.cellLength
						* Math.exp(1 / (Math.pow((dist / w * this.cellLength),
								2)-1));
				}else{
					result+=0;
				}
			}
		}
//		System.out.println("Mollifier :" +result);
		return result;

	}

	public boolean movePedestrian() {
		Cell pedStart;
		Cell pedMoveTo;

		if (!pedestrianToMove.getLocation().equals(targetCellForNextStep)) {
			pedStart = this.getCell(pedestrianToMove.getLocation().getRow(), 
																				
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

	
	public Set<Cell> getNeighboursOfPedestrian(Pedestrian p,int mollifier) {
		int row = p.getLocation().getRow();
		int col = p.getLocation().getCol();
		return neighboursOfCell(row, col, mollifier);

	}

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
		if(mollifier==0){
		neighbours.removeAll(delete);
		}
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
	private void initCells(int rowSource, int colSource, List<Cell> targets) {
		
		for (int row = 0; row < sideLength; row++) {
			for (int col = 0; col < sideLength; col++) {
				if (rowSource == row && colSource == col) {
					Cell source = new Cell(rowSource, colSource, null);
					source.setSource(new Source());
					field.add(source); 
					this.sourceCell = source;
//				} else if (rowTarget == row && colTarget == col) {
//					Cell target = new Cell(rowTarget, colTarget, null);
//					target.setTarget(new Target());
//					field.add(target); 
//					this.targetCell = target;
				} else {
					field.add(new Cell(row, col, null));
				}
			}
		}
		for(Cell c:targets){
			Cell target=new Cell(c.getRow(),c.getCol(),null);
			field.get(c.getRow()*sideLength+c.getCol()).setTarget(new Target());
			}
	}

	private void initBarriers(int szenario) {
		if(szenario==2){
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
		}else if(szenario==3){
		//Fundamentaldiagramm
		for(int i=0;i<65;i++){
			this.getCell(12, i).setBarrier(new Barrier());
			}
		}
	}

	private double euklidDist(Cell that, Cell other) {
		double x1 = that.getRow() + cellLength / 2;
		double y1 = that.getCol() + cellLength / 2;
		double x2 = other.getRow() + cellLength / 2;
		double y2 = other.getCol() + cellLength / 2;

		return Math.sqrt(Math.pow(Math.abs(x2 - x1), 2)
				+ Math.pow(Math.abs(y2 - y1), 2));
	}

	public boolean sourceIsOccupied() {
		return (this.getSourceCell().getPedestrian() != null) ? true : false;
	}
}
