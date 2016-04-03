/**
 * This class defines the object Person, and computes scalper possibility of this person.
 * @author Mingxuan Zha
 * @date April 2016, created
 */
import java.util.*;

public class Person {
	private String pid = "";
	private ArrayList<Event> eventList = new ArrayList<>();
	private TreeSet<String> citySet = new TreeSet<>();//no repetitive elements
	private double finalPossiVal = 0.0;
	
	public Person(String id){
		pid = id;
	}
	
	public void addEvent(Event curEvent){
		eventList.add(curEvent);
		citySet.add(curEvent.getEventCity());
	}
	
	public void printPerson(){
		System.out.println("================================Person's id is: "+pid+" ================================");
		for(int i=0; i<eventList.size(); i++){
			eventList.get(i).printEvent();
		}
		System.out.println("Possibility of scalper is: "+getFinalPossi()+"%");
		System.out.println("================================Person printing ended================================");
	}
	
	public void computeFinalPossi(){
		double finalPossi = 0.0;
		for(int i=0; i<eventList.size(); i++){
			finalPossi += eventList.get(i).getPossiValue();
		}
		finalPossi /= eventList.size();
		finalPossi += Math.log(citySet.size()); //if person buys ticket for event in multiple states, increase possibility
		finalPossiVal = finalPossi;
	}
	
	public double getFinalPossi(){
		return finalPossiVal;
	}

}
