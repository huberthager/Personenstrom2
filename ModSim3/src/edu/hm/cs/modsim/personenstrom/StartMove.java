package edu.hm.cs.modsim.personenstrom;

public class StartMove extends Event {

	private Pedestrian pedestrian;

	public StartMove(double clock, Pedestrian pedestrian) {
		super(clock);
		this.pedestrian = pedestrian;
	}

	@Override
	public void processEvent(Field field, FutureEventList futureEventList) {
		Pedestrian pToMove = this.getPedestrian();
		double timeToMove;

		field.setPedestrianToMove(pToMove); // Fußgänger wird aktiv geschaltet
		field.getTargetCellForNextStep(); // Zielzelle für Fußgänger bestimmen
		field.movePedestrian(); // auf Zielbewegen
		timeToMove = field.getTimeToMovePedestrian(); // Zeit für Ende des Moves
														// berechnen um neuen
														// Schrittstart zu
														// setzen

		futureEventList.addEvent(new StopMove(this.getEventTime() + timeToMove,
				pToMove)); // Einordnen in FEL
		//Zeichne Feld nach jedem Schritt
		field.printToConsole(field.getSideLength());
		System.out.println();
	}

	public Pedestrian getPedestrian() {
		return this.pedestrian;
	}

}
