package neo4j.PeopleWebBuilderStuff;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
		Date date = faker.date().birthday(0, 100);
		LocalDate dob = Instant.ofEpochMilli(date.getTime())
		      .atZone(ZoneId.systemDefault())
		      .toLocalDate();
		Masks masks = null;
		Hygiene hygiene = null;
		SocialGuidelines socialGuidelines = null;
		boolean underLyingCondition = false;
		switch (rand.nextInt(4)) {
		case 0:
			masks = Masks.type1;
			break;
		case 1:
			masks = Masks.type2;
			break;
		case 2:
			masks = Masks.type3;
			break;
		case 3:
			masks = Masks.type4;
			break;
		}
		
		switch (rand.nextInt(3)) {
		case 0:
			hygiene = Hygiene.none;
			break;
		case 1:
			hygiene = Hygiene.normal;
			break;
		case 2:
			hygiene = Hygiene.cleanFreak;
			break;
		}
		
		switch (rand.nextInt(3)) {
		case 0:
			socialGuidelines = SocialGuidelines.doesntFollow;
			break;
		case 1:
			socialGuidelines = SocialGuidelines.kindOfFollows;
			break;
		case 2:
			socialGuidelines = SocialGuidelines.follows;
			break;
		}
		
		int conditionCheck = rand.nextInt(100) + 1;
		if(conditionCheck >=70) {
			underLyingCondition = true;
		}
		Person person = new Person(name, dob, masks, hygiene, socialGuidelines, underLyingCondition);
		int numberOfFriends = rand.nextInt(10) + 1;
		int friendsAdded = 0;
		while(friendsAdded < numberOfFriends) {
			friendsAdded++;
			Person friend = peopleHolder.get(rand.nextInt(1000));
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
			friend.addRelationship(person, relationship);
			person.addRelationship(friend, relationship);
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
		while(numberOfPeopleToAdd < 1000) {
			//AUTOGENERATE THIS SHIT
			Faker faker = new Faker();
			String name = faker.name().fullName();
			Date date = faker.date().birthday(0, 100);
			LocalDate dob = Instant.ofEpochMilli(date.getTime())
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
			Masks masks = null;
			Hygiene hygiene = null;
			SocialGuidelines socialGuidelines = null;
			boolean underLyingCondition = false;
			switch (rand.nextInt(4)) {
			case 0:
				masks = Masks.type1;
				break;
			case 1:
				masks = Masks.type2;
				break;
			case 2:
				masks = Masks.type3;
				break;
			case 3:
				masks = Masks.type4;
				break;
			}
			
			switch (rand.nextInt(3)) {
			case 0:
				hygiene = Hygiene.none;
				break;
			case 1:
				hygiene = Hygiene.normal;
				break;
			case 2:
				hygiene = Hygiene.cleanFreak;
				break;
			}
			
			switch (rand.nextInt(3)) {
			case 0:
				socialGuidelines = SocialGuidelines.doesntFollow;
				break;
			case 1:
				socialGuidelines = SocialGuidelines.kindOfFollows;
				break;
			case 2:
				socialGuidelines = SocialGuidelines.follows;
				break;
			}
			
			int conditionCheck = rand.nextInt(100) + 1;
			if(conditionCheck >=70) {
				underLyingCondition = true;
			}
			
			Person person = new Person(name, dob, masks, hygiene, socialGuidelines, underLyingCondition);
			peopleHolder.add(person);
			numberOfPeopleToAdd++;
		}
	}

	/**
	 * Generates friends
	 */
	public static void generateFriends() {
		int personNumber = 0;
		while(personNumber < 1000) {
			Person person = peopleHolder.get(personNumber);
			int numberOfFriends = person.getNumberOfFriends();
			int numberOfFriendsToAdd = rand.nextInt(10) + 1;
			if(numberOfFriends < numberOfFriendsToAdd) {
				int friendsAdded = 0;
				while(friendsAdded < numberOfFriendsToAdd - numberOfFriends) {
					Person friend = peopleHolder.get(rand.nextInt(1000));
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
					if(person.getRelationships().get(friend) == null) {
						friend.addRelationship(person, relationship);
						person.addRelationship(friend, relationship);
						friendsAdded++;
					}
				}
			}
			personNumber++;
		}
	}
}