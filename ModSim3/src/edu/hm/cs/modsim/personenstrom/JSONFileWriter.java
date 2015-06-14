package edu.hm.cs.modsim.personenstrom;

import java.io.FileWriter;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings("unused")
public class JSONFileWriter {
	
	JSONObject obj;
	
	//create a new JSONObject to store data in
	public JSONFileWriter(){
		obj = new JSONObject();
	}
	
	@SuppressWarnings("unchecked")
	public void addData(String key, String value){
		obj.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public void addArray(String[] keyValuePairs){
		JSONArray array = new JSONArray();
		for(int i = 0; i<keyValuePairs.length;i++){
			array.add(keyValuePairs[i]);			
		}
	}
	
	public void writeFile() throws IOException{
		//create new file and write data in it
		//file is located in 
		FileWriter file = new FileWriter("jsonfile.txt");
		try{
			file.write(obj.toJSONString());
			System.out.println("JSONObject copied to File");
			System.out.println("\nJSONObject" + obj);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			file.flush();
			file.close();
		}
	}
	

}
