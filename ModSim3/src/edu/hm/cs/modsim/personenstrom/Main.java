package edu.hm.cs.modsim.personenstrom;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;





public class Main {
	static ArrayList<Double> simTimes=new ArrayList<>();
	
	
	public static void main(String[] args) {
		
		for(int i=0;i<1;i++){
			//Fundamentaldiagramm: sideLength=65
		int sideLength = 10;
		double duration = 1000;
		
		//Test: Freier Fluss  1
//		List<Cell> targets=simpleMovement();
		
		//Test: Huehnertest 2
//		List<Cell> targets = chickenTest();
		
		//Test: Fundamentaldiagramm 3
//		List<Cell>targets=fundamentalDiag();
		
		//Test: Evakuriungsszenario 4
		List<Cell> targets=twoDoorSzenario();
//		List<Cell> targets=fourDoorSzenario();
		
		Scheduler scheduler = new Scheduler(duration,sideLength,0,0,targets,4);
		double tmp=scheduler.run();
		simTimes.add(tmp);
		}
		
		//OutputStream
//		try {
//			PrintWriter outputStream =new PrintWriter("Simtime44.txt");
//			
//			for(Double d:simTimes){
//				outputStream.print(d + " ");
//			}
//			outputStream.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
private static List<Cell> simpleMovement(){
		
		List<Cell> targets=new ArrayList<>();
		targets.add(new Cell(5,9,null));
		return targets;
	}
	
	
	private static List<Cell> fundamentalDiag(){
		
		List<Cell> targets=new ArrayList<>();
		for(int i=0;i<12;i++){
		targets.add(new Cell(i,64,null));
		}
		return targets;
	}
	
	
	private static List<Cell> chickenTest(){
		//2-Tueren:
		List<Cell> targets=new ArrayList<>();
		targets.add(new Cell(5,9,null));
		return targets;
	}
	
	private static List<Cell> twoDoorSzenario(){
		//2-Tueren:
		List<Cell> targets=new ArrayList<>();
		targets.add(new Cell(5,9,null));
		targets.add(new Cell(5,0,null));
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
