/**
 * This class analyzes data from DataFest 2016.
 * Please choose txt file after running this class.
 * @author Mingxuan Zha
 * @date April 2016, created
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.JFileChooser;
public class Main {
    public static HashMap<String,Person> dataMap = new HashMap<>();//stores each person with purchase_party_id
    public static boolean processCurData = true;//boolean value to check if current data line is valid

public static void main (String[] args)throws Exception{
	Main myMain = new Main();
	JFileChooser ourChooser = new JFileChooser(".");
	int retval = ourChooser.showOpenDialog(null);
    if (retval == JFileChooser.APPROVE_OPTION) {
        File file = ourChooser.getSelectedFile();
        Scanner input = new Scanner(file);
        int lineCount = 0;
        while(input.hasNextLine()){
        	lineCount++;
        	processCurData = true;
        	String curLine = input.nextLine();
        	String[] curInfo = curLine.split("	");
        	if(curInfo != null){//check null
        		if(curInfo.length==8){
        			/*
    	        	 * This part constructs an event with relative data from Excel file,
    	        	 * has multiple checks for invalid inputs,
    	        	 * and store the information in dataMap
    	        	 */
        			//old table for parameters in the original Excel file
//		        	String id = curInfo[0]; //event_id (A)
//        			String curpid = curInfo[3]; // user id (D)
//		        	String s0 = curInfo[23]; //venue city (X) 演出城市
//					String s1 = curInfo[27]; //print_flg (AB) 是否印票
//					String s2 = curInfo[16]; //event_dt (Q)
//					String s3 = curInfo[19]; //sales_ord_create_dttm (T)
//					String s4 = curInfo[12]; //tickets_purchased_qty (M)
//					String s5 = curInfo[13]; //trans_face_val_amt
        			
        			//new table for parameters in the concise Excel file
        			String id = curInfo[0]; //event_id
        			String curpid = curInfo[1]; // user id
		        	String s0 = curInfo[2]; //venue city 演出城市
					String s1 = curInfo[3]; //print_flg 是否印票
					String s2 = curInfo[4]; //event_dt
					String s3 = curInfo[5]; //sales_ord_create_dttm
					String s4 = curInfo[6]; //tickets_purchased_qty
					String s5 = curInfo[7]; //trans_face_val_amt
					
					if(id!=null&&s0!=null&&s1!=null&&s2!=null&&s3!=null&&s4!=null&&s5!=null){//check null
			        	Event curEvent = new Event(id,s0,s1,s2,s3,s4,s5,
			        			myMain.computeDateDifference(s3,s2),
			        			myMain.computeCityVal(s0),
			        			myMain.computePrintFlagVal(s1));
			        	if(processCurData){// data is valid, store information in map
				        	if(dataMap.containsKey(curpid)){
				        		Person curperson = dataMap.get(curpid);
				        		curperson.addEvent(curEvent);
				        	}
				        	else{
				        		Person curperson = new Person(curpid);
				        		curperson.addEvent(curEvent);
				        		dataMap.put(curpid, curperson);
				        	}
			        	}
					}
        		}
        	}
        }       
        /*
         * This part stores individual possibilities, sorts values and makes histogram
         */
        double[] results = new double[dataMap.keySet().size()];
        int index = 0;
        for(String person: dataMap.keySet()){
        	Person curPerson = dataMap.get(person);
        	curPerson.computeFinalPossi();
        	//comment out the next line to print all data of Person and Events
//        	curPerson.printPerson();
        	results[index] = curPerson.getFinalPossi();
        	index++; 
        }
        //print analysis
        System.out.println("Size of sample is "+lineCount);
        System.out.println("Number of people is "+dataMap.keySet().size());
        Arrays.sort(results);
        //compute possibility value of different percentiles, print them for "y" in Matlab
        System.out.print(results[(int) (results.length*0.01)]+",");
        System.out.print(results[(int) (results.length*0.1)]+",");
        System.out.print(results[(int) (results.length*0.2)]+",");
        System.out.print(results[(int) (results.length*0.3)]+",");
        System.out.print(results[(int) (results.length*0.4)]+",");
        System.out.print(results[(int) (results.length*0.5)]+",");
        System.out.print(results[(int) (results.length*0.6)]+",");
        System.out.print(results[(int) (results.length*0.7)]+",");
        System.out.print(results[(int) (results.length*0.8)]+",");
        System.out.print(results[(int) (results.length*0.9)]+",");
        System.out.print(results[(int) (results.length*0.99)]);
    }
}

	/*
	 * This part computes values of correct formats
	 */

	public int computeDateDifference(String buy, String show){
		//compute date difference from purchase date to show date
		String[] buyDate = buy.split("/");
		String[] showDate = show.split("/");
		if(buyDate == null || showDate == null){
			processCurData = false;
			return 0;
		}
		else{
			if(buyDate.length<2 || showDate.length<2){
				processCurData = false;
				return 0;
			}
			else{
				processCurData = true;
				String[] buyYear = buyDate[2].split(" ");
				int yearDiff = Integer.parseInt(showDate[2]) - Integer.parseInt(buyYear[0]);
				int monthDiff = Integer.parseInt(showDate[0]) - Integer.parseInt(buyDate[0]);
				int dayDiff = Integer.parseInt(showDate[1]) - Integer.parseInt(buyDate[1]);
				return dayDiff + monthDiff*30 + yearDiff*365;
			}
		}
	}
	
	
	public double computeCityVal(String city){
		//assign value of venue_city factor by checking if it is city with huge population
		HashSet<String> citySet2 = new HashSet<String>(Arrays.asList("LOS ANGELES","CHICAGO","HOUSTON"));
		HashSet<String> citySet3 = new HashSet<String>(Arrays.asList("PHILADELPHIA","PHOENIX","SAN ANTONIO","DALLAS"));
		if(city.equals("NEW YORK")){
			return 100.0;
		}
		else if(citySet2.contains(city)){
			return 45.0;
		}
		else if(citySet3.contains(city)){
			return 30.0;
		}
		else{
			return 10.0;
		}
	}
	
	public double computePrintFlagVal(String flag){
		//assign value of print_flag factor by checking if the paper ticket is printed out
		if(flag.equals("F")){
			return 1.0;
		}
		else{
			return 0.0;
		}
	}
    
    
}
