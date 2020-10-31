package neo4j.PeopleWebBuilderStuff;

<<<<<<< Updated upstream
=======
import java.time.LocalDate;
import java.time.ZoneId;
>>>>>>> Stashed changes
import java.util.ArrayList;
import java.util.Random;

import org.neo4j.codegen.api.Load;

import com.github.javafaker.Faker;

public class PeopleBuilder {
	/**
	 * Array list that will hold the people
	 */
	static private ArrayList<Person> peopleHolder = new ArrayList<Person>();
	/**
	 * Random generator
	 */
	static private Random rand = new Random();

	/*
	 * Starts the project
	 */
	public static void start() {
		fillPeopleList();
		startInfection();
	}

	/*
	 * Empties the list
	 */
	public static void emptyList() {
		peopleHolder = new ArrayList<Person>();
	}

	/**
	 * Get the people collection
	 * 
	 * @return People collection
	 */
	public static ArrayList<Person> getPeopleHolder() {
		return peopleHolder;
	}

	/**
	 * Set the people collection
	 * 
	 * @param peopleHolder
	 */
	public static void setPeopleHolder(ArrayList<Person> peopleHolder) {
		PeopleBuilder.peopleHolder = peopleHolder;
	}

	/**
	 * Start the infection process
	 */
	public static void startInfection() {
		LocalDate localDate = LocalDate.of(2020, 01, 01);
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date start = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		Random rand = new Random();
		peopleHolder.get(rand.nextInt(10000)).infect(start);
	}

	/**
	 * Generates a new person
	 * Please do not run until you have initially started the people builder
	 */
	public static void generateNewPerson() {
		Faker faker = new Faker();
		String name = faker.name().fullName();
<<<<<<< Updated upstream
		Masks masks = null;
		SocialGuidelines socialGuidelines = null;
		//TODO Auto generate this and get probabilities
		int age = 0;
		boolean handWashing = false;
		ArrayList<PreexistingCondition> healthIssues = new ArrayList<PreexistingCondition>();
		boolean hermit = false;
		MaskUsage maskUsage = MaskUsage.Always;
		JobType jobType = JobType.grocery;
		Person person = new Person(name, age, masks, socialGuidelines, handWashing, healthIssues, hermit, maskUsage, jobType);
=======
		Date dob = faker.date().birthday(0, 101);
		Masks masks = null;
		SocialGuidelines socialGuidelines = null;
		int generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 20) {
			socialGuidelines = SocialGuidelines.doesntFollow;
		}
		else if(generateValue <= 60) {
			socialGuidelines = SocialGuidelines.kindOfFollows;
		}
		else {
			socialGuidelines = SocialGuidelines.follows;
		}
		int age = 0; //percents 22, 62, 16
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 22) {
			age = rand.nextInt(19);
		}
		else if(generateValue <= 84) {
			age = rand.nextInt(47) + 19;
		}
		else {
			age = rand.nextInt(36) + 65;
		}
		boolean handWashing = false; //percents 79, 21
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 79) {
			handWashing = true;
		}
		else {
			handWashing = false;
		}
		ArrayList<PreexistingCondition> healthIssues = new ArrayList<PreexistingCondition>();
		boolean hermit = false;//percents 81,19
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 81) {
			hermit = true;
		}
		else {
			hermit = false;
		}
		MaskUsage maskUsage = MaskUsage.Always;//// percents 44, 28,11 4, 13
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 44) {
			maskUsage = MaskUsage.Always;
		}
		else if(generateValue <= 72) {
			maskUsage = MaskUsage.VeryOften;
		}
		else if(generateValue <= 83) {
			maskUsage = MaskUsage.Someimtes;
		}
		else if(generateValue <= 87) {
			maskUsage = MaskUsage.Rarely;
		}
		else {
			maskUsage = MaskUsage.Never;
		}
		JobType jobType = JobType.grocery;
		Person person = new Person(name, age, masks, socialGuidelines, handWashing, healthIssues, hermit, maskUsage, jobType, dob);
>>>>>>> Stashed changes
		int numberOfFriends = rand.nextInt(35) + 15;
		int friendsAdded = 0;
		while(friendsAdded < numberOfFriends) {
			Person friend = peopleHolder.get(rand.nextInt(10000));
			if(friend.getNumberOfFriends() < 50) {
				Relationships relationship = null;
				switch (rand.nextInt(3)) {
				case 0:
					relationship = Relationships.Weak;
					break;
				case 1:
					relationship = Relationships.Medium;
					break;
				case 2:
					relationship = Relationships.Strong;
					break;
				}
				if(friend.getRelationships().containsKey(person) != true) {
					friendsAdded++;
					friend.addRelationship(person, relationship);
					person.addRelationship(friend, relationship);
				}
			}

		}
		peopleHolder.add(person);
	}

	/**
	 * Fill the list with 1,000 people
	 */
	public static void fillPeopleList() {
		generatePeople();
		//generateFriends();
	}

	/**
	 * Generates people
	 */
	public static void generatePeople() {
		int numberOfPeopleToAdd = 0;
		while(numberOfPeopleToAdd < 10000) {
<<<<<<< Updated upstream
			Faker faker = new Faker();
			String name = faker.name().fullName();
			Masks masks = null;
			SocialGuidelines socialGuidelines = null;//20,40,40
			//TODO Auto generate this
			int age = 0; //percents 22, 62, 16
			boolean handWashing = false; //percents 79, 21
			ArrayList<PreexistingCondition> healthIssues = new ArrayList<PreexistingCondition>();
			boolean hermit = false;//percents 81,19
			MaskUsage maskUsage = MaskUsage.Always;//// percents 44, 28,11 4, 13
			JobType jobType = JobType.grocery;
			Person person = new Person(name, age, masks, socialGuidelines, handWashing, healthIssues, hermit, maskUsage, jobType);
=======
			System.out.println(numberOfPeopleToAdd);
			int generateValue = 0;
			Faker faker = new Faker();
			String name = faker.name().fullName();
			Date dob = faker.date().birthday(0, 101);
			Masks masks = null;
			SocialGuidelines socialGuidelines = null;//20,40,40
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 20) {
				socialGuidelines = SocialGuidelines.doesntFollow;
			}
			else if(generateValue <= 60) {
				socialGuidelines = SocialGuidelines.kindOfFollows;
			}
			else {
				socialGuidelines = SocialGuidelines.follows;
			}
			int age = 0; //percents 22, 62, 16
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 22) {
				age = rand.nextInt(19);
			}
			else if(generateValue <= 84) {
				age = rand.nextInt(47) + 19;
			}
			else {
				age = rand.nextInt(36) + 65;
			}
			boolean handWashing = false; //percents 79, 21
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 79) {
				handWashing = true;
			}
			else {
				handWashing = false;
			}
			ArrayList<PreexistingCondition> healthIssues = new ArrayList<PreexistingCondition>();
			boolean hermit = false;//percents 81,19
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 81) {
				hermit = true;
			}
			else {
				hermit = false;
			}
			MaskUsage maskUsage = MaskUsage.Always;//// percents 44, 28,11 4, 13
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 44) {
				maskUsage = MaskUsage.Always;
			}
			else if(generateValue <= 72) {
				maskUsage = MaskUsage.VeryOften;
			}
			else if(generateValue <= 83) {
				maskUsage = MaskUsage.Someimtes;
			}
			else if(generateValue <= 87) {
				maskUsage = MaskUsage.Rarely;
			}
			else {
				maskUsage = MaskUsage.Never;
			}
			JobType jobType = JobType.grocery;
			Person person = new Person(name, age, masks, socialGuidelines, handWashing, healthIssues, hermit, maskUsage, jobType, dob);
>>>>>>> Stashed changes
			peopleHolder.add(person);
			numberOfPeopleToAdd++;
		}
	}

	/**
	 * Generates friends
	 */
	public static void generateFriends() {
		int personNumber = 0;
		while(personNumber < 10000) {
			Person person = peopleHolder.get(personNumber);
			int numberOfFriends = person.getNumberOfFriends();
			int numberOfFriendsToAdd = rand.nextInt(36) + 15;
			if(numberOfFriends < numberOfFriendsToAdd) {
				int friendsAdded = 0;
				while(friendsAdded < numberOfFriendsToAdd - numberOfFriends) {
					Person friend = peopleHolder.get(rand.nextInt(10000));
					Relationships relationship = null;
					switch (rand.nextInt(3)) {
					case 0:
						relationship = Relationships.Weak;
						break;
					case 1:
						relationship = Relationships.Medium;
						break;
					case 2:
						relationship = Relationships.Strong;
						break;
					}
					if(friend.getNumberOfFriends() < 50) {
						if(person.getRelationships().containsKey(friend) != true) {
							friend.addRelationship(person, relationship);
							person.addRelationship(friend, relationship);
							friendsAdded++;
						}
					}
				}
			}
			personNumber++;
		}
	}
}
