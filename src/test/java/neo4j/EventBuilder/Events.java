package neo4j.EventBuilder;

import java.util.Date;

/**
 * Events class for pandemic project
 */

public class Events { 

	private String eventName;
	private Date eventDate;
	private EventType eventType;
	private int eventCapacity;
	private EventPlace venue;
	private VentilationType indoorVentilation;
	private Boolean maskEnforcement;
	private Boolean socialDistancing;
	private Boolean tempChecks;
	private Boolean handSanitizerAvailable;
	private Boolean CDCApprovedCleaning;
	
	public Events() {
	}
	/**
	 * Constructor for outdoor events
	 * 
	 * @param name
	 * @param date
	 * @param cap
	 * @param place
	 * @param mask
	 * @param dist
	 */
	public Events(String name, Date date, EventType type, int cap, EventPlace place,
			Boolean mask, Boolean dist, Boolean temp, Boolean hand, Boolean cdc) {
		eventName = name;
		eventDate = date;
		eventType = type;
		eventCapacity = cap;
		venue = place;
		maskEnforcement = mask;
		socialDistancing = dist;
		tempChecks = temp;
		handSanitizerAvailable = hand;
		CDCApprovedCleaning = cdc;
	}
	
	/**
	 * 
	 * Constructor for indoor events
	 * 
	 * @param name
	 * @param date
	 * @param type
	 * @param cap
	 * @param place
	 * @param indoor
	 * @param mask
	 * @param dist
	 * @param temp
	 * @param hand
	 * @param cdc
	 */
	public Events(String name, Date date, EventType type, int cap, EventPlace place, VentilationType indoor,
			Boolean mask, Boolean dist, Boolean temp, Boolean hand, Boolean cdc) {
		
		eventName = name;
		eventDate = date;
		eventType = type;
		eventCapacity = cap;
		venue = place;
		indoorVentilation = indoor;
		maskEnforcement = mask;
		socialDistancing = dist;
		tempChecks = temp;
		handSanitizerAvailable = hand;
		CDCApprovedCleaning = cdc;
		
	}

	
	// Getters and setters
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public EventType getEventType() {
		return eventType;
	}
	
	public String getEventTypeString() {
		String eventTypeString = ""; 
		// political, wedding, concert, flea_market, carnival, sports; 
		switch(eventType) {
		case political: 
			eventTypeString = "political";
			break; 
		case wedding:
			eventTypeString = "wedding";
			break; 
		case concert:
			eventTypeString = "concert";
			break; 
		case flea_market: 
			eventTypeString = "flea_market";
			break; 
		case carnival: 
			eventTypeString = "carnival";
			break;
		case sports: 
			eventTypeString = "sports";
			break; 
		}
		return eventTypeString;
	}
	
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	
	public void setEventType(String eventTypeString) {
		switch(eventTypeString) {
		case "political": 
			eventType = EventType.political;
			break; 
		case "wedding":
			eventType = EventType.wedding;
			break; 
		case "concert":
			eventType = EventType.concert;
			break; 
		case "flea_market": 
			eventType = EventType.flea_market;
			break; 
		case "carnival": 
			eventType = EventType.carnival;
			break;
		case "sports": 
			eventType = EventType.sports;
			break; 
		}
	}
	
	public int getEventCapacity() {
		return eventCapacity;
	}

	public void setEventCapacity(int eventCapacity) {
		this.eventCapacity = eventCapacity;
	}

	public EventPlace getVenue() {
		return venue;
	}

	public String getVenueString() {
		String venueString = "";
		switch(venue) {
		case indoor:
			venueString = "indoor";
			break; 
		case outdoor:
			venueString = "outdoor";
			break; 
		}
		return venueString; 
	}
	
	public void setVenue(EventPlace venue) {
		this.venue = venue;
	}
	
	public void setVenueFromString(String venueString) {
		switch(venueString) {
		case "indoor":
			venue = EventPlace.indoor;
			break; 
		case "outdoor":
			venue = EventPlace.outdoor;
			break; 
		}
	}

	public VentilationType getIndoorVentilation() {
		return indoorVentilation;
	}

	public String getIndoorVentilationString() {
		String indoorVentilationString = "";
		switch(indoorVentilation) {
		case upgraded:
			indoorVentilationString = "upgraded";
			break; 
		case not_upgraded:
			indoorVentilationString = "not_upgraded";
			break; 
		}
		return indoorVentilationString; 
	}
	
	public void setIndoorVentilation(VentilationType indoorVentilation) {
		this.indoorVentilation = indoorVentilation;
	}
	
	public void setIndoorVentilationFromString(String indoorVentilationString) {
		switch(indoorVentilationString) {
		case "upgraded":
			indoorVentilation = VentilationType.upgraded;
			break; 
		case "not_upgraded":
			indoorVentilation = VentilationType.not_upgraded;
			break; 
		} 
	}

	public Boolean getMaskEnforcement() {
		return maskEnforcement;
	}

	public void setMaskEnforcement(Boolean maskEnforcement) {
		this.maskEnforcement = maskEnforcement;
	}

	public Boolean getSocialDistancing() {
		return socialDistancing;
	}

	public void setSocialDistancing(Boolean socialDistancing) {
		this.socialDistancing = socialDistancing;
	}

	public Boolean getTempChecks() {
		return tempChecks;
	}

	public void setTempChecks(Boolean tempChecks) {
		this.tempChecks = tempChecks;
	}

	public Boolean getHandSanitizerAvailable() {
		return handSanitizerAvailable;
	}

	public void setHandSanitizerAvailable(Boolean handSanitizerAvailable) {
		this.handSanitizerAvailable = handSanitizerAvailable;
	}

	public Boolean getCDCApprovedCleaning() {
		return CDCApprovedCleaning;
	}

	public void setCDCApprovedCleaning(Boolean cDCApprovedCleaning) {
		CDCApprovedCleaning = cDCApprovedCleaning;
	}
}
	

	


