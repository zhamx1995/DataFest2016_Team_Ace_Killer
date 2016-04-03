/**
 * This class defines the object Event, generates weights for significant factors,
 * and computes possibility of this person becoming scalper judging from this event
 * @author Mingxuan Zha
 * @date April 2016, created
 */
public class Event {
	private String event_id = "";
	//parameters from data
	private String venue_city = "";
	private String print_flg = "";
	private String event_dt = "";
	private String sales_ord_create_dttm = "";
	private int qty = 0;
	private String trans_face_val_amt = "";
	//analysis data
	private double dateGap = 0.0;
	private double cityValue = 0.0;
	private double flagValue = 0.0;
	private double qtyValue = 0.0;
	private double priceAVG = 0.0;
	private double possiValue = computePoss();
	
	public Event(String id,String s0, String s1, String s2, String s3, String s4, String s5,
			int dateDiff, double cityindex, double printflag){
		//assign initialization
		event_id = id;
		venue_city = s0;
		print_flg = s1;
		event_dt = s2;
		sales_ord_create_dttm = s3;
		qty = Integer.parseInt(s4);
		trans_face_val_amt = s5;
		//assign analysis
		dateGap = computeDayDiffValue(dateDiff);
		cityValue = cityindex;
		flagValue = printflag;
		qtyValue = computeQtyValue(qty);
		priceAVG = computePAVGValue((double) Double.parseDouble(trans_face_val_amt)/qty);
		
		possiValue = computePoss();
	}
	
	public void printEvent(){
		System.out.println("---------------Event's id is: "+event_id+" ---------------");
		System.out.println("venue_city: "+venue_city);
		System.out.println("print_flg: "+print_flg);
		System.out.println("event_dt: "+event_dt);
		System.out.println("sales_ord_create_dttm: "+sales_ord_create_dttm);
		System.out.println("qty: "+qty);
		System.out.println("trans_face_val_amt: "+trans_face_val_amt);
		System.out.println("##### Analysis of purchase data #####");
		System.out.println("day gap between purchase and event: "+dateGap);
		System.out.println("cityValue: " + cityValue);
		System.out.println("flagValue: " + flagValue);
		System.out.println("priceAVG: " + priceAVG);
		System.out.println("possiValue: " + possiValue);
//		System.out.println("---------------Event printing ended---------------");
	}
	
	/*
	 * Getters. Limit data access
	 */
	public String getEventCity(){
		return venue_city;
	}
	
	public double gateDateDiff(){
		return dateGap;
	}
	
	public double getCityValue(){
		return cityValue;
	}
	
	public double getFlagValue(){
		return flagValue;
	}
	
	public double getPriceAVG(){
		return priceAVG;
	}
	
	public double getPossiValue(){
		return possiValue;
	}
	
	/*
	 * Compute weights for each factor
	 */
	public double computeDayDiffValue(int diff){
		return Math.min((double) diff/3, 100.0);
	}
	
	public double computeQtyValue(int quant){
		//assign value of purchase quantity factor
		if(quant<4){
			return 0.0;
		}
		else if(quant == 4){
			return 25.0;
		}
		else if(quant == 5){
			return 50.0;
		}
		else if(quant == 6){
			return 75.0;
		}
		else{
			return 100.0;
		}
	}
	
	public double computePAVGValue(double avgPrice){
		//assign value of average price factor by comparing with 500
		if(avgPrice > 500){
			return 100.0;
		}
		else{
			return avgPrice/5;
		}
	}
	
	public double computePoss(){
		//compute possibility for current event purchase
		double dayW = 0.2;
		double cityW = 0.1;
		double flagW = 0.1;
		double qtyW = 0.3;
		double priceQW = 0.3;
		return dateGap*dayW + cityValue*cityW + flagValue*flagW + qty*qtyW + priceAVG*priceQW;
	}

}
