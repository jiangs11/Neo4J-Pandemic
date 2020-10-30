package neo4j.PeopleWebBuilderStuff;

import java.util.ArrayList;
import java.util.Random;

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
		Random rand = new Random();
		peopleHolder.get(rand.nextInt(1000)).infect("2020-01-01");
	}

	/**
	 * Generates a new person
	 * Please do not run until you have initially started the people builder
	 */
	public static void generateNewPerson() {
		Faker faker = new Faker();
		String name = faker.name().fullName();
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
		generateFriends();
	}

	/**
	 * Generates people
	 */
	public static void generatePeople() {
		int numberOfPeopleToAdd = 0;
		while(numberOfPeopleToAdd < 10000) {
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
