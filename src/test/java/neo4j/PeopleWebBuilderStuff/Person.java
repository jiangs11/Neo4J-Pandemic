package neo4j.PeopleWebBuilderStuff;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;


/**
 * Person Class for neo4j project
 *
 */
public class Person {

	/**
	 * If a person is infected
	 */
	private boolean infected;
	/**
	 * If a person is deceased
	 */
	private boolean deceased;
	/**
	 * A  person's name
	 */
	private String name;
	/**
	 * A person's Date of birth
	 */
	private LocalDate dob;
	/**
	 * A person's age
	 */
	private int age;
	/**
	 * The relationships between people
	 */
	private HashMap<Person, Relationships> relationships = new HashMap<Person, Relationships>();
	/**
	 * The type of mask a person wears
	 */
	private Masks masks;
	/**
	 * The hygiene of a person
	 */
	private Hygiene hygiene;
	/**
	 * The level of guidelines a person follows
	 */
	private SocialGuidelines socialGuidelines;
	/**
	 * The underlying conditions of a person
	 */
	private boolean underLyingCondition;
	/**
	 * The date person was infected
	 */
	private LocalDate dateInfected;
	/**
	 * If a person was previously infected
	 */
	private boolean preivouslyInfected;
	/**
	 * The number of friends a person has
	 */
	private int numberOfFriends = 0;

	/**
	 * The constructor for a person
	 * 
	 * @param name
	 * @param birth
	 * @param masks
	 * @param hygiene
	 * @param socialGuidlines
	 * @param underLyingCondition
	 */
	public Person(String name, LocalDate birth, Masks masks, Hygiene hygiene,
			SocialGuidelines socialGuidlines, boolean underLyingCondition) {
		infected = false;
		deceased = false;
		this.name = name;
		dob = birth;
		this.masks = masks;
		this.hygiene = hygiene;
		this.socialGuidelines = socialGuidlines;
		this.underLyingCondition = underLyingCondition;
		updateAge(LocalDate.parse("2020-01-01"));
	}

	/**
	 * Default constructor
	 */
	public Person() {
		
	}
	/**
	 * This checks if a person is infected
	 * 
	 * @return Infection status
	 */
	public boolean isInfected() {
		return infected;
	}

	/**
	 * This sets a person infection
	 * 
	 * @param infected
	 */
	public void setInfected(boolean infected) {
		this.infected = infected;
	}

	/**
	 * Checks id a person is dead
	 * 
	 * @return Deceased status
	 */
	public boolean isDeceased() {
		return deceased;
	}

	/**
	 * Sets a person to deceased
	 * 
	 * @param deceased
	 */
	public void setDeceased(boolean deceased) {
		this.deceased = deceased;
	}

	/**
	 * Gets the name
	 * 
	 * @return Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets a person's name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the date of birth
	 * 
	 * @return Date of Birth
	 */
	public LocalDate getdob() {
		return dob;
	}

	/**
	 * Sets the date of birth
	 * 
	 * @param dob
	 */
	public void setdob(LocalDate dob) {
		this.dob = dob;
	}

	/**
	 * Gets the age of a person
	 * 
	 * @return Age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets a person's age
	 * 
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Gets the person's relationships
	 * 
	 * @return relationships
	 */
	public HashMap<Person, Relationships> getRelationships() {
		return relationships;
	}

	/**
	 * Sets a person's relationships
	 * 
	 * @param relationships
	 */
	public void setRelationships(HashMap<Person, Relationships> relationships) {
		this.relationships = relationships;
	}

	/**
	 * Gets the mask usage of a person
	 * 
	 * @return Mask usage
	 */
	public Masks getMaskUsage() {
		return masks;
	}

	/**
	 * Sets the mask usage
	 * 
	 * @param maskUsage
	 */
	public void setMaskUsage(Masks maskUsage) {
		this.masks = maskUsage;
	}


	/**
	 * Gets the hygiene level
	 * 
	 * @return Hygiene
 	 */
	public Hygiene getHygiene() {
		return hygiene;
	}

	/**
	 * Sets a person's hygiene level
	 * 
	 * @param hygiene
	 */
	public void setHygiene(Hygiene hygiene) {
		this.hygiene = hygiene;
	}

	/**
	 * Gets a person's social guidelines status
	 * 
	 * @return Social Guidelines
	 */
	public SocialGuidelines getSocialGuidelines() {
		return socialGuidelines;
	}

	/**
	 * Sets a person's social guidelines status
	 * 
	 * @param socialGuidelines
	 */
	public void setSocialGuidelines(SocialGuidelines socialGuidelines) {
		this.socialGuidelines = socialGuidelines;
	}

	/**
	 * Gets a person's underlying condition
	 * 
	 * @return Underlying conditions
	 */
	public boolean getUnderLyingCondition() {
		return underLyingCondition;
	}

	/**
	 * Sets a person's underlying conditions
	 * 
	 * @param underLyingCondition
	 */
	public void setUnderLyingCondition(boolean underLyingCondition) {
		this.underLyingCondition = underLyingCondition;
	}

	/**
	 * Gets the date of infection
	 * 
	 * @return Date of infection
	 */
	public LocalDate getDateInfected() {
		return dateInfected;
	}

	/**
	 * Sets the date of infection
	 * 
	 * @param dateInfected
	 */
	public void setDateInfected(LocalDate dateInfected) {
		this.dateInfected = dateInfected;
	}

	/**
	 * Checks the status if a person was previously infected
	 * 
	 * @return Previous infection status
	 */
	public boolean isPreivouslyInfected() {
		return preivouslyInfected;
	}

	/**
	 * Sets the status if a person was previously infected
	 * 
	 * @param preivouslyInfected
	 */
	public void setPreivouslyInfected(boolean preivouslyInfected) {
		this.preivouslyInfected = preivouslyInfected;
	}

	/**
	 * Get number of friends a person has
	 * 
	 * @return Number of friends
	 */
	public int getNumberOfFriends() {
		return numberOfFriends;
	}

	/**
	 * Sets the number of friends
	 * 
	 * @param numberOfFriends
	 */
	public void setNumberOfFriends(int numberOfFriends) {
		this.numberOfFriends = numberOfFriends;
	}

	/**
	 * Infect a person
	 * 
	 * @param date
	 */
	public void infect(String date) {
		infected = true;
		dateInfected = LocalDate.parse(date);
		preivouslyInfected = true;
	}

	/**
	 * Cure a person
	 */
	public void cure() {
		infected = false;
		dateInfected = null;
	}

	/**
	 * Kill a person
	 */
	public void kill() {
		if(!isDeceased()) {
			deceased = true;
		}
	}

	/**
	 * Add a relationship between two people
	 * 
	 * @param person
	 * @param relationship
	 */
	public void addRelationship(Person person, Relationships relationship) {
		relationships.put(person, relationship);
		person.setNumberOfFriends(person.getNumberOfFriends() + 1);
	}
	
	/**
	 * Update a person's age
	 * 
	 * @param date
	 */
	public void updateAge(LocalDate date) {
		age = Period.between(dob, date).getYears();
	}
	
	/*
	 * Returns the person's info
	 * @return Returns the person's info
	 */
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Infected ");
		stringBuilder.append(infected);
		stringBuilder.append(" Deceased ");
		stringBuilder.append(deceased);
		stringBuilder.append(" Name ");
		stringBuilder.append(name);
		stringBuilder.append(" DOB ");
		stringBuilder.append(dob.toString());
		stringBuilder.append(" Age ");
		stringBuilder.append(age);
		stringBuilder.append(" Mask ");
		stringBuilder.append(masks);
		stringBuilder.append(" Hygiene ");
		stringBuilder.append(hygiene);
		stringBuilder.append(" Social Guidelines ");
		stringBuilder.append(socialGuidelines);
		stringBuilder.append(" Underlying condition ");
		stringBuilder.append(underLyingCondition);
		stringBuilder.append(" Number of Friends ");
		stringBuilder.append(numberOfFriends);
		return stringBuilder.toString();
	}
}
