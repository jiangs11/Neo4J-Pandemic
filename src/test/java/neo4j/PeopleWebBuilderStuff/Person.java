package neo4j.PeopleWebBuilderStuff;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Person Class for neo4j project
 *
 */
public class Person {

	/**
	 * If a person is infected
	 */
	private boolean infected = false;
	/**
	 * If a person is deceased
	 */
	private boolean deceased = false;
	/**
	 * A  person's name
	 */
	private String name;
	/**
	 * A person's age
	 */
	private int age;
	/**
	 * The relationships between people
	 */
	private HashMap<Person, String> relationships = new HashMap<Person, String>();
	/**
	 * The type of mask a person wears
	 */
	private Masks masks;
	private MaskUsage maskUsage;
	/**
	 * The hygiene of a person
	 */
	private boolean handWashing;
	private boolean hermit;
	private JobType jobType;
	/**
	 * The level of guidelines a person follows
	 */
	private SocialGuidelines socialGuidelines;
	/**
	 * The underlying conditions of a person
	 */
	private ArrayList<PreexistingCondition> underLyingCondition;
	/**
	 * The date person was infected
	 */
	private Date dateInfected = null;
	private Date dob = null;
	/**
	 * If a person was previously infected
	 */
	private boolean preivouslyInfected = false;
	/**
	 * The number of friends a person has
	 */
	private int numberOfFriends = 0;
	private ArrayList<Integer> pidsOfFriends = new ArrayList<Integer>();
	/**
	 * The constructor for a person
	 * 
	 * @param name
	 * @param birth
	 * @param masks
	 * @param handWashing
	 * @param socialGuidlines
	 * @param underLyingCondition
	 */
	public Person(String name, int age, Masks masks,
			SocialGuidelines socialGuidlines, boolean handWashing, 
			ArrayList<PreexistingCondition> healthIssues, boolean hermit,
			MaskUsage maskUsage, JobType job, Date dob) {
		this.name = name;
		this.masks = masks;
		this.maskUsage = maskUsage;
		this.socialGuidelines = socialGuidlines;
		this.hermit = hermit;
		this.age = age;
		this.handWashing = handWashing;
		this.underLyingCondition = healthIssues;
		this.setJobType(job);
		this.dob = dob;
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
	
	public void age() {
		age++;
	}
	/**
	 * Gets the person's relationships
	 * 
	 * @return relationships
	 */
	public HashMap<Person, String> getRelationships() {
		return relationships;
	}

	/**
	 * Sets a person's relationships
	 * 
	 * @param relationships
	 */
	public void setRelationships(HashMap<Person, String> relationships) {
		this.relationships = relationships;
	}

	/**
	 * Gets the mask usage of a person
	 * 
	 * @return Mask usage
	 */
	public Masks getMask() {
		return masks;
	}

	/**
	 * Sets the mask usage
	 * 
	 * @param maskUsage
	 */
	public void setMask(Masks mask) {
		this.masks = mask;
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
	public ArrayList<PreexistingCondition> getUnderLyingCondition() {
		return underLyingCondition;
	}

	/**
	 * Sets a person's underlying conditions
	 * 
	 * @param underLyingCondition
	 */
	public void setUnderLyingCondition(ArrayList<PreexistingCondition> underLyingCondition) {
		this.underLyingCondition = underLyingCondition;
	}

	/**
	 * Gets the date of infection
	 * 
	 * @return Date of infection
	 */
	public Date getDateInfected() {
		return dateInfected;
	}

	/**
	 * Sets the date of infection
	 * 
	 * @param dateInfected
	 */
	public void setDateInfected(Date dateInfected) {
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

	public MaskUsage getMaskUsage() {
		return maskUsage;
	}

	public void setMaskUsage(MaskUsage maskUsage) {
		this.maskUsage = maskUsage;
	}

	public boolean isHandWashing() {
		return handWashing;
	}

	public void setHandWashing(boolean handWashing) {
		this.handWashing = handWashing;
	}

	public boolean isHermit() {
		return hermit;
	}

	public void setHermit(boolean hermit) {
		this.hermit = hermit;
	}

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public ArrayList<Integer> getPidsOfFriends() {
		return pidsOfFriends;
	}

	public void setPidsOfFriends(ArrayList<Integer> pidsOfFriends) {
		this.pidsOfFriends = pidsOfFriends;
	}

	/**
	 * Infect a person
	 * 
	 * @param date
	 */
	public void infect(Date date) {
		infected = true;
		dateInfected = date;
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
	public void addRelationship(Person person, String relationship) {
		relationships.put(person, relationship);
		person.setNumberOfFriends(person.getNumberOfFriends() + 1);
	}
	
	/**
	 * Update a person's age
	 * 
	 * @param date
	 */
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
		stringBuilder.append(" Age ");
		stringBuilder.append(age);
		stringBuilder.append(" Mask ");
		stringBuilder.append(masks);
		stringBuilder.append(" Hygiene ");
		stringBuilder.append(" Social Guidelines ");
		stringBuilder.append(socialGuidelines);
		stringBuilder.append(" Underlying condition ");
		stringBuilder.append(underLyingCondition);
		stringBuilder.append(" Number of Friends ");
		stringBuilder.append(numberOfFriends);
		return stringBuilder.toString();
	}
}
