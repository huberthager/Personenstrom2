package edu.hm.cs.modsim.personenstrom;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Scheduler {

	private double clock;
	private double endSimTime;
	private int sideLength;
	private Field field;
	private FutureEventList futureEventList;
	private Event currentEvent;
	private List<Pedestrian> pedestriansOnField;

	private JFrame frame;
	private JTextArea ta;
	private PrintStream ps;
	private TextAreaOutputStream taos;
	
	
	
	public Scheduler(double endSimTime, int sideLength, List<Cell> targets, int szenario) {
		this.clock = 0;
		this.endSimTime = endSimTime;
		this.sideLength = sideLength;
		this.futureEventList = new FutureEventList();
		this.field = new Field(sideLength, targets, szenario);
		this.currentEvent = null;
		pedestriansOnField = field.getPedestriansOnField();
		for (Pedestrian p : pedestriansOnField) {
			futureEventList.addEvent(new Arrival(0, p));
		}
		
		
		frame = new JFrame();
		frame.add(new JLabel("Personenstrom Simulation"), BorderLayout.NORTH);
		ta = new JTextArea();
		
		frame.add(ta);
		frame.setSize(1000, 500);
		taos = new TextAreaOutputStream(ta, 200);
		ps = new PrintStream(taos);
		
		System.setOut(ps);
		System.setErr(ps);


//		frame.pack();
//		frame.setPreferredSize(new Dimension(500, 500));
		frame.setVisible(true);
		
		
	}
	

	public double run() {
		
		
//		System.setOut(ps);
//		System.setErr(ps);
	
		
		
		while (clock < this.endSimTime && !(this.futureEventList.isEmpty())) {
			if(field.isPedestrianWithoutEvent()){
				futureEventList.addEvent(new Arrival(clock,field.getPedestrianReturn()));
			}
			this.currentEvent = futureEventList.removeAndGetFirst();
			this.clock = currentEvent.getEventTime();
			this.currentEvent.processEvent(field, futureEventList);
			
			taos.clear();
			String gui = "";
			if (currentEvent instanceof StartMove
					|| currentEvent instanceof Depature) {
//				for (int clear = 0; clear < 40; clear++) {
//					System.out.println("\b");
//				}
				gui = field.guiToString(clock, gui);
				System.out.print(gui);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return clock;
	}

	public void printFieldToConsole() {
		this.field.printToConsole(sideLength);
	}

}
