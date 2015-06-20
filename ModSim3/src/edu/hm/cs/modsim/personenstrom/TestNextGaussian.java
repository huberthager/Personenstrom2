package edu.hm.cs.modsim.personenstrom;

import java.util.ArrayList;
import java.util.Random;

public class TestNextGaussian {

	public static void main(String[] args) {
		ArrayList<Double>test=new ArrayList<>();
		for(int i=0;i<300000;i++){
		double d=new Random().nextGaussian()*0.18 + 1.3;
		if(d<0){
			System.out.println("Ohje");
		}
		test.add(d);
		
		}
		System.out.println("Done");
	}

}
