import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;

public class Person {

	private boolean infected;
	private boolean deceased;
	private String name;
	private LocalDate dob;
	private int age;
	private HashMap<Person, Relationships> relationships;
	private Masks masks;
	private Hygiene hygiene;
	private SocialGuidelines socialGuidelines;
	private boolean underLyingCondition;
	private LocalDate dateInfected;
	private boolean preivouslyInfected;
	private int numberOfFriends = 0;

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

	public Person() {
		// TODO Delete this later I was lazy in the personBuilder and made this
	}
	public boolean isInfected() {
		return infected;
	}

	public void setInfected(boolean infected) {
		this.infected = infected;
	}

	public boolean isDeceased() {
		return deceased;
	}

	public void setDeceased(boolean deceased) {
		this.deceased = deceased;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getdob() {
		return dob;
	}

	public void setdob(LocalDate dob) {
		this.dob = dob;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public HashMap<Person, Relationships> getRelationships() {
		return relationships;
	}

	public void setRelationships(HashMap<Person, Relationships> relationships) {
		this.relationships = relationships;
	}

	public Masks getMaskUsage() {
		return masks;
	}

	public void setMaskUsage(Masks maskUsage) {
		this.masks = maskUsage;
	}


	public Hygiene getHygiene() {
		return hygiene;
	}

	public void setHygiene(Hygiene hygiene) {
		this.hygiene = hygiene;
	}

	public SocialGuidelines getSocialGuidelines() {
		return socialGuidelines;
	}

	public void setSocialGuidelines(SocialGuidelines socialGuidelines) {
		this.socialGuidelines = socialGuidelines;
	}

	public boolean getUnderLyingCondition() {
		return underLyingCondition;
	}

	public void setUnderLyingCondition(boolean underLyingCondition) {
		this.underLyingCondition = underLyingCondition;
	}

	public LocalDate getDateInfected() {
		return dateInfected;
	}

	public void setDateInfected(LocalDate dateInfected) {
		this.dateInfected = dateInfected;
	}

	public boolean isPreivouslyInfected() {
		return preivouslyInfected;
	}

	public void setPreivouslyInfected(boolean preivouslyInfected) {
		this.preivouslyInfected = preivouslyInfected;
	}

	public int getNumberOfFriends() {
		return numberOfFriends;
	}

	public void setNumberOfFriends(int numberOfFriends) {
		this.numberOfFriends = numberOfFriends;
	}

	public void infect(String date) {
		infected = true;
		dateInfected = LocalDate.parse(date);
		preivouslyInfected = true;
	}

	public void cure() {
		infected = false;
		dateInfected = null;
	}

	public void kill() {
		if(!isDeceased()) {
			deceased = true;
		}
	}

	public void addRelationship(Person person, Relationships relationship) {
		relationships.put(person, relationship);
		setNumberOfFriends(getNumberOfFriends() + 1);
	}
	
	public void updateAge(LocalDate date) {
		age = Period.between(dob, date).getYears();
	}
}
