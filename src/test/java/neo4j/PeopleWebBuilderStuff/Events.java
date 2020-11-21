package neo4j.PeopleWebBuilderStuff;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * Events class for pandemic project
 */

enum EventPlace { indoor, outdoor; }
enum VentilationType { not_upgraded, upgraded; }
enum EventType { political, wedding, concert, flea_market, carnival, sports; }

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
	
	/**
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
	
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
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

	public void setVenue(EventPlace venue) {
		this.venue = venue;
	}

	public VentilationType getIndoorVentilation() {
		return indoorVentilation;
	}

	public void setIndoorVentilation(VentilationType indoorVentilation) {
		this.indoorVentilation = indoorVentilation;
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
	

	


