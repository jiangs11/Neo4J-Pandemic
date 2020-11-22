package neo4j.EventBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class EventBuilder {
	
	static private ArrayList<Events> eventHolder = new ArrayList<Events>();

	//set all variables
	Random r = new Random();
	int type;
	int place;
	int vent;
	int cap;
	int maskEn;
	int soc;
	int temp;
	int hand;
	int cdc;
	EventType eventtype;
	int eventCap;
	EventPlace venue;
	VentilationType ventilation;
	Boolean mask = false;
	Boolean social = false;
	Boolean temperature = false;
	Boolean sanitizer = false;
	Boolean cleaning = false;

	/**
	 * Generator function for events
	 */
	public void generateEvent(String name, Date date, int maxNumber) {
		// Randomize our variables
		type = r.nextInt(6);
		place = r.nextInt(2);
		vent = r. nextInt(2);
		cap = r.nextInt(maxNumber);
		maskEn = r.nextInt(10);
		soc = r.nextInt(10);
		temp = r.nextInt(10);
		hand = r.nextInt(10);
		cdc = r.nextInt(10);
		
		// Set our generator variables
		switch (type) {
			case 0: eventtype = EventType.political;
					break;
			case 1: eventtype = EventType.wedding;
					break;
			case 2: eventtype = EventType.concert;
					break;
			case 3: eventtype = EventType.flea_market;
					break;
			case 4: eventtype = EventType.carnival;
					break;
			case 5: eventtype = EventType.sports;
					break;
		}
		
		switch(place) {
			case 0: venue = EventPlace.indoor;
					switch (vent) {
						case 0: ventilation = VentilationType.not_upgraded;
								break;
						case 1: ventilation = VentilationType.upgraded;
								break;
					}
					break;
			case 1: venue = EventPlace.outdoor;
					break;
		}
		
		if (maskEn <5) {
			mask = true;
		}
		
		if (soc <5) {
			social = true;
		}
		
		if (temp <5) {
			temperature = true;
		}
		
		if (hand <5) {
			sanitizer = true;
		}
		
		if (cdc <5) {
			cleaning = true;
		}
		
		// create our events
		if (place == 0) {
			
			Events event = new Events(name, date, eventtype, cap, venue, ventilation, mask, social, temperature, sanitizer, cleaning);
			eventHolder.add(event);
		}
		else {
			
			Events event = new Events(name, date, eventtype, cap, venue, mask, social, temperature, sanitizer, cleaning);
			eventHolder.add(event);
			
		}
		
		

	}

}
