package edu.hm.cs.modsim.personenstrom;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
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
	private List<Double> realVelsMean = new ArrayList<>();
	private List<Double> flow = new ArrayList<>();
	
	
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
		
//		frame = new JFrame();
//		frame.add(new JLabel("Personenstrom Simulation"), BorderLayout.NORTH);
//		ta = new JTextArea();
//		
//		frame.add(ta);
//		frame.setSize(1400, 400);
//		taos = new TextAreaOutputStream(ta,60);
//		ps = new PrintStream(taos);
		
//		System.setOut(ps);
//		System.setErr(ps);
//		frame.setVisible(true);
		
		
	}
	

	public double run() {
		while (clock < this.endSimTime && !(this.futureEventList.isEmpty())) {
			if(field.isPedestrianWithoutEvent()){
				futureEventList.addEvent(new Arrival(clock,field.getPedestrianReturn()));
			}
			this.currentEvent = futureEventList.removeAndGetFirst();
			this.clock = currentEvent.getEventTime();
			this.currentEvent.processEvent(field, futureEventList);
			
			
			String gui = "";
			if (currentEvent instanceof StartMove
					|| currentEvent instanceof Depature) {
//				for (int clear = 0; clear < 40; clear++) {
//					System.out.println("\b");
//				}
//				taos.clear();
//				gui = field.guiToString(clock, gui);
//				System.out.print(gui);
//				try {
//					Thread.sleep(30);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		}
		
		/**
		 * Datenauswertung
		 */
		List<Double> realVels = field.getRealVels();
		List<Double> realVelEventTimes = field.getRealVelEventTimes();
		List<Double> pFlow = field.getPersonFlow();
		//Durchschnitt der realisierten Geschwindigkeit
		for(int i = 1; i <= realVels.size(); i++) {
			double mean = 0;
			for(int j = 1; j <= i; j++) {
				mean += realVels.get(j-1);
			}
			realVelsMean.add(mean/i);
		}
		System.out.println(realVelsMean.size());
		
		
		for(int i = 0; i < pFlow.size(); i++) {
			flow.add(pFlow.get(i)/realVelsMean.get(i));
		}
		System.out.println("Fluss" + flow);
		return clock;
	}

//	public void printFieldToConsole() {
//		this.field.printToConsole(sideLength);
//	}

}
