package edu.hm.cs.modsim.personenstrom;

/**
 * Kleinster Bestandteil des Feldes. Eine Zelle besteht aus eindeutigen
 * Koordinaten und hat Zustände entsprechend ihrem Inhalt.
 * 
 * @author Hubert Hager, Tobi Höfer
 *
 */
public class Cell {

	private int row;
	private int col;
	private Pedestrian pedestrian;
	private Barrier barrier;
	private Target target;

	public Cell(int row, int col, Pedestrian pedestrian) {
		this.row = row;
		this.col = col;
		this.pedestrian = pedestrian;
		this.barrier = null;
		this.target = null;
	}

	/**
	 * Getter für row
	 * 
	 * @return
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Setter für row
	 * 
	 * @param row
	 */
	// public void setRow(int row) {
	// this.row = row;
	// }

	/**
	 * Getter für col
	 * 
	 * @return
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Setter für col
	 * 
	 * @param col
	 */
	// public void setCol(int col) {
	// this.col = col;
	// }

	/**
	 * Getter für Fußgänger
	 * 
	 * @return
	 */
	public Pedestrian getPedestrian() {
		return pedestrian;
	}

	/**
	 * Setter für Fußgänger
	 * 
	 * @param pedestrian
	 */
	public void setPedestrian(Pedestrian pedestrian) {
		this.pedestrian = pedestrian;
	}

	/**
	 * Getter Für Barriere
	 * 
	 * @return
	 */
	public Barrier getBarrier() {
		return barrier;
	}

	/**
	 * Setter für Barrier
	 * 
	 * @param barrier
	 */
	public void setBarrier(Barrier barrier) {
		this.barrier = barrier;
	}

	/**
	 * Getter für Zielzelle
	 * 
	 * @return
	 */
	public Target getTarget() {
		return target;
	}

	/**
	 * Setter für Target
	 * 
	 * @param target
	 */
	public void setTarget(Target target) {
		this.target = target;
	}

	/**
	 * toString Methode
	 */
	public String toString() {
		return "[" + row + " " + col + "]";
	}

	/**
	 * equals Methode vergleicht wann zwei Zellen Gleichheit besitzen
	 * 
	 * @param other
	 * @return
	 */
	public boolean equals(Cell other) {
		return (this.row == other.row && this.col == other.col) ? true : false;
	}

	// public void printToConsole(int sideLength) {
	// if (this.barrier != null) {
	// System.out.print("[+]");
	// } else if (this.pedestrian != null) {
	// System.out.print("[P]");
	// } else if (this.target != null) {
	// System.out.print("[T]");
	// } else {
	// System.out.print("[ ]");
	// }
	// if (sideLength - 1 == this.getCol()) {
	// System.out.println();
	// }
	// }

	/**
	 * Gibt Auskunft ob einer Zelle belegt ist
	 * 
	 * @return
	 */
	public boolean isOccupied() {
		return this.pedestrian != null || this.barrier != null;
	}

	/**
	 * Zelle wird bezgl ihres Inhalts grafisch dargestellt
	 * 
	 * @param sideLength
	 * @param result
	 * @return
	 */
	public String guiToString(int sideLength, String result) {
		if (this.barrier != null) {
			result += "[+]";
		} else if (this.pedestrian != null) {
			result += "[p]";
		} else if (this.target != null) {
			result += "[T]";
		} else {
			result += "[ ]";
		}
		if (sideLength - 1 == this.getCol()) {
			result += "\n";
		}
		return result;
	}

}
