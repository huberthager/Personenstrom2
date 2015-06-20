package edu.hm.cs.modsim.personenstrom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;





public class Main {
	static ArrayList<Double> simTimes=new ArrayList<>();
	
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
		int sideLength = 10;
		double duration = 1000;
		List<Cell> targets=twoDoorSzenario();
		//List<Cell> targets=fourDoorSzenario();
		//List<Cell> targets = chickenTest();
		Scheduler scheduler = new Scheduler(duration,sideLength,0,0,targets);
		double tmp=scheduler.run();
		simTimes.add(tmp);
		}
		try {
			FileOutputStream fos=new FileOutputStream("Simtime.txt");
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			for(Double d:simTimes){
				oos.writeDouble(d);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static List<Cell> twoDoorSzenario(){
		//2-Tueren:
		List<Cell> targets=new ArrayList<>();
		targets.add(new Cell(5,9,null));
		targets.add(new Cell(5,0,null));
		return targets;
	}
	
	private static List<Cell> chickenTest(){
		//2-Tueren:
		List<Cell> targets=new ArrayList<>();
		targets.add(new Cell(5,9,null));
		return targets;
	}
	
	private static List<Cell> fourDoorSzenario(){
		//4 Tueren
		List<Cell> targets=new ArrayList<>();
		targets.add(new Cell(5,9,null));
		targets.add(new Cell(5,0,null));
		targets.add(new Cell(0,5,null));
		targets.add(new Cell(9,5,null));
		return targets;
	}

}
