package neo4j.PeopleWebBuilderStuff;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.github.javafaker.Faker;

public class PeopleBuilder {
	
	// How many people to make
	static private int personCount = 1000;
	
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
		peopleHolder.get(rand.nextInt(personCount)).infect(start);
	}

	/**
	 * Generates a new person
	 * Please do not run until you have initially started the people builder
	 */
	public static void generateNewPerson() {
		Faker faker = new Faker();
		String name = faker.name().fullName();
		Date dob = null;
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
			dob = faker.date().birthday(age-1, age+1);
		}
		else if(generateValue <= 84) {
			age = rand.nextInt(47) + 19;
			dob = faker.date().birthday(age-1, age+1);
		}
		else {
			age = rand.nextInt(36) + 65;
			dob = faker.date().birthday(age-1, age+1);
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
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 9) {
			healthIssues.add(PreexistingCondition.Cancer);
		}
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 2) {
			healthIssues.add(PreexistingCondition.ChronicKidneyDisease);
		}
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 12) {
			healthIssues.add(PreexistingCondition.HeartConditions);
		}
		generateValue = rand.nextInt(1000000) + 1;
		if(generateValue <= 3) {
			healthIssues.add(PreexistingCondition.SickleCell);
		}
		generateValue = rand.nextInt(100) + 1;
		if(age > 19) {
			if(generateValue <= 40) {
				healthIssues.add(PreexistingCondition.Obesity);
			}
			else {
				generateValue = rand.nextInt(100) + 1;
				if(generateValue <= 72) {
					healthIssues.add(PreexistingCondition.OverWeight);
				}
			}
		}
		else if (age >= 12) {
			if(generateValue <= 21) {
				healthIssues.add(PreexistingCondition.Obesity);
			}
		}
		else if (age >= 6) {
			if(generateValue <= 18) {
				healthIssues.add(PreexistingCondition.Obesity);
			}
		}
		else {
			if(generateValue <= 14) {
				healthIssues.add(PreexistingCondition.Obesity);
			}
		}
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 14) {
			healthIssues.add(PreexistingCondition.Smoking);
		}
		generateValue = rand.nextInt(1000000) + 1;
		if(generateValue <= 23) {
			healthIssues.add(PreexistingCondition.OrganTransplant);
		}
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 15) {
			healthIssues.add(PreexistingCondition.Type1Diabetes);
		}
		else {
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 15) {
				healthIssues.add(PreexistingCondition.Type2Diabetes);
			}
		}
		generateValue = rand.nextInt(1000000) + 1;
		if(generateValue <= 36) {
			healthIssues.add(PreexistingCondition.ChronicKidneyDisease);
		}
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 4) {
			healthIssues.add(PreexistingCondition.ImmunnoDeficient);
		}
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 34) {
			healthIssues.add(PreexistingCondition.MetabolicDisorder);
		}
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 2) {
			healthIssues.add(PreexistingCondition.LiverDisease);
		}
		generateValue = rand.nextInt(10000) + 1;
		if(generateValue <= 11) {
			healthIssues.add(PreexistingCondition.LungDiseases);
		}
		boolean hermit = false;//percents 81,19
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 81) {
			hermit = true;
		}
		else {
			hermit = false;
		}
		MaskUsage maskUsage = MaskUsage.always;//// percents 44, 28,11 4, 13
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 44) {
			maskUsage = MaskUsage.always;
		}
		else if(generateValue <= 72) {
			maskUsage = MaskUsage.veryOften;
		}
		else if(generateValue <= 83) {
			maskUsage = MaskUsage.sometimes;
		}
		else if(generateValue <= 87) {
			maskUsage = MaskUsage.rarely;
		}
		else {
			maskUsage = MaskUsage.never;
		}
		
		Masks masks = Masks.type1;
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 55) {
			masks = Masks.type1;
		}
		else if(generateValue <= 70) {
			masks = Masks.type2;
		}
		else if(generateValue <= 85) {
			masks = Masks.type3;
		}
		else {
			masks = Masks.none;
		}

		JobType jobType = JobType.unemployed;
		generateValue = rand.nextInt(100) + 1;
		if(generateValue <= 10) {
			jobType = JobType.unemployed;
		}
		else if(generateValue <= 20) {
			jobType = JobType.outOfWork;
		}
		else if(generateValue <= 30) {
			jobType = JobType.remote;
		}
		else if(generateValue <= 40) {
			jobType = JobType.officeBuilding;
		}		
		else if(generateValue <= 50) {
			jobType = JobType.warehouse;
		}		
		else if(generateValue <= 60) {
			jobType = JobType.noPublicInteraction;
		}		
		else if(generateValue <= 70) {
			jobType = JobType.grocery;
		}		
		else if(generateValue <= 80) {
			jobType = JobType.restaurant;
		}		
		else if(generateValue <= 90) {
			jobType = JobType.teachers;
		}
		else {
			jobType = JobType.medicalWorkers;
		}
		
		SocialClass socialClass = SocialClass.poor;
		generateValue = rand.nextInt(100) + 1;
		if (generateValue <= 12) {
			socialClass = SocialClass.poor;
		} else if (generateValue <= 52) {
			socialClass = SocialClass.middleClass;
		} else if (generateValue <= 87) {
			socialClass = SocialClass.rich;
		} else if (generateValue <= 99) {
			socialClass = SocialClass.onePercenter;
		}
		Person person = new Person(name, age, masks, socialGuidelines, handWashing, healthIssues, hermit, maskUsage, jobType, dob, socialClass);
		
		
		int numberOfFriends = rand.nextInt(5) + 1;
		int friendsAdded = 0;
		while(friendsAdded < numberOfFriends) {
			Person friend = peopleHolder.get(rand.nextInt(1000));
			if(friend.getNumberOfFriends() < 10) {
				String relationship = null;
				switch (rand.nextInt(3)) {
				case 0:
					relationship = "weak";
					break;
				case 1:
					relationship = "medium";
					break;
				case 2:
					relationship = "strong";
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
		while(numberOfPeopleToAdd < personCount) {
			System.out.println(numberOfPeopleToAdd);
			int generateValue = 0;
			Faker faker = new Faker();
			String name = faker.name().fullName();
			Date dob = null;
			SocialGuidelines socialGuidelines = null;//20,40,40
			generateValue = rand.nextInt(5) + 1;
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
				dob = faker.date().birthday(age-1, age+1);
			}
			else if(generateValue <= 84) {
				age = rand.nextInt(47) + 19;
				dob = faker.date().birthday(age-1, age+1);
			}
			else {
				age = rand.nextInt(36) + 65;
				dob = faker.date().birthday(age-1, age+1);
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
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 9) {
				healthIssues.add(PreexistingCondition.Cancer);
			}
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 2) {
				healthIssues.add(PreexistingCondition.ChronicKidneyDisease);
			}
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 12) {
				healthIssues.add(PreexistingCondition.HeartConditions);
			}
			generateValue = rand.nextInt(1000000) + 1;
			if(generateValue <= 3) {
				healthIssues.add(PreexistingCondition.SickleCell);
			}
			generateValue = rand.nextInt(100) + 1;
			if(age > 19) {
				if(generateValue <= 40) {
					healthIssues.add(PreexistingCondition.Obesity);
				}
				else {
					generateValue = rand.nextInt(100) + 1;
					if(generateValue <= 72) {
						healthIssues.add(PreexistingCondition.OverWeight);
					}
				}
			}
			else if (age >= 12) {
				if(generateValue <= 21) {
					healthIssues.add(PreexistingCondition.Obesity);
				}
			}
			else if (age >= 6) {
				if(generateValue <= 18) {
					healthIssues.add(PreexistingCondition.Obesity);
				}
			}
			else {
				if(generateValue <= 14) {
					healthIssues.add(PreexistingCondition.Obesity);
				}
			}
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 14) {
				healthIssues.add(PreexistingCondition.Smoking);
			}
			generateValue = rand.nextInt(1000000) + 1;
			if(generateValue <= 23) {
				healthIssues.add(PreexistingCondition.OrganTransplant);
			}
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 15) {
				healthIssues.add(PreexistingCondition.Type1Diabetes);
			}
			else {
				generateValue = rand.nextInt(100) + 1;
				if(generateValue <= 15) {
					healthIssues.add(PreexistingCondition.Type2Diabetes);
				}
			}
			generateValue = rand.nextInt(1000000) + 1;
			if(generateValue <= 36) {
				healthIssues.add(PreexistingCondition.ChronicKidneyDisease);
			}
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 4) {
				healthIssues.add(PreexistingCondition.ImmunnoDeficient);
			}
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 34) {
				healthIssues.add(PreexistingCondition.MetabolicDisorder);
			}
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 2) {
				healthIssues.add(PreexistingCondition.LiverDisease);
			}
			generateValue = rand.nextInt(10000) + 1;
			if(generateValue <= 11) {
				healthIssues.add(PreexistingCondition.LungDiseases);
			}
			boolean hermit = false;//percents 81,19
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 81) {
				hermit = true;
			}
			else {
				hermit = false;
			}
			MaskUsage maskUsage = MaskUsage.always;//// percents 44, 28,11 4, 13
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 44) {
				maskUsage = MaskUsage.always;
			}
			else if(generateValue <= 72) {
				maskUsage = MaskUsage.veryOften;
			}
			else if(generateValue <= 83) {
				maskUsage = MaskUsage.sometimes;
			}
			else if(generateValue <= 87) {
				maskUsage = MaskUsage.rarely;
			}
			else {
				maskUsage = MaskUsage.never;
			}
			
			Masks masks = Masks.type1;
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 55) {
				masks = Masks.type1;
			}
			else if(generateValue <= 70) {
				masks = Masks.type2;
			}
			else if(generateValue <= 85) {
				masks = Masks.type3;
			}
			else {
				masks = Masks.none;
			}

			JobType jobType = JobType.unemployed;
			generateValue = rand.nextInt(100) + 1;
			if(generateValue <= 10) {
				jobType = JobType.unemployed;
			}
			else if(generateValue <= 20) {
				jobType = JobType.outOfWork;
			}
			else if(generateValue <= 30) {
				jobType = JobType.remote;
			}
			else if(generateValue <= 40) {
				jobType = JobType.officeBuilding;
			}		
			else if(generateValue <= 50) {
				jobType = JobType.warehouse;
			}		
			else if(generateValue <= 60) {
				jobType = JobType.noPublicInteraction;
			}		
			else if(generateValue <= 70) {
				jobType = JobType.grocery;
			}		
			else if(generateValue <= 80) {
				jobType = JobType.restaurant;
			}		
			else if(generateValue <= 90) {
				jobType = JobType.teachers;
			}
			else {
				jobType = JobType.medicalWorkers;
			}
			
			SocialClass socialClass = SocialClass.poor;
			generateValue = rand.nextInt(100) + 1;
			if (generateValue <= 12) {
				socialClass = SocialClass.poor;
			} else if (generateValue <= 52) {
				socialClass = SocialClass.middleClass;
			} else if (generateValue <= 87) {
				socialClass = SocialClass.rich;
			} else if (generateValue <= 99) {
				socialClass = SocialClass.onePercenter;
			}
			Person person = new Person(name, age, masks, socialGuidelines, handWashing, healthIssues, hermit, maskUsage, jobType, dob, socialClass);
			peopleHolder.add(person);
			numberOfPeopleToAdd++;
		}
	}

	/**
	 * Generates friends
	 */
	public static void generateFriends() {
		int personNumber = 0;
		while(personNumber < personCount) {
			Person person = peopleHolder.get(personNumber);
			int numberOfFriends = person.getNumberOfFriends();
			int numberOfFriendsToAdd = rand.nextInt(5) + 1;
			if(numberOfFriends < numberOfFriendsToAdd) {
				int friendsAdded = 0;
				while(friendsAdded < numberOfFriendsToAdd - numberOfFriends) {
					int friendId = rand.nextInt(personCount);
					Person friend = peopleHolder.get(friendId);
					if(person.equals(friend) == false) {
						String relationship = null;
						switch (rand.nextInt(3)) {
						case 0:
							relationship = "weak";
							break;
						case 1:
							relationship = "medium";
							break;
						case 2:
							relationship = "strong";
							break;
						}
						if(friend.getNumberOfFriends() < 10) {
							if(person.getRelationships().containsKey(friend) != true) {
								person.getPidsOfFriends().add(friendId);
								friend.getPidsOfFriends().add(personNumber);
								friend.addRelationship(person, relationship);
								person.addRelationship(friend, relationship);
								friendsAdded++;
							}
						}
					}
				}
			}
			personNumber++;
		}
	}
}
