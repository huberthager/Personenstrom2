package edu.hm.cs.modsim.personenstrom;


public class Main {

	public static void main(String[] args) {
		int sideLength = 10;
		double duration = 100;
		Scheduler scheduler = new Scheduler(duration,sideLength,0,0,5,9);
		scheduler.run();
		
	}

}
