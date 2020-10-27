import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class PeopleBuilder {
	static private ArrayList<Person> peopleHolder = new ArrayList<Person>();
	static private Random rand = new Random();

	public static ArrayList<Person> getPeopleHolder() {
		return peopleHolder;
	}

	public static void setPeopleHolder(ArrayList<Person> peopleHolder) {
		PeopleBuilder.peopleHolder = peopleHolder;
	}
	//TODO Make the rest of this class
	//TODO Check the rest of my other classes to see if I need to add stuff
	//TODO Add faker to this class
	//TODO Java doc all this shit
	public static void startInfection() {
		Random rand = new Random();
		peopleHolder.get(rand.nextInt(1000));
	}

	//TODO Change the constructor used and add more params
	public static void generateNewPerson() {
		Person person = new Person();
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

	public static void fillPeopleList() {
		//Please only call this once otherwise it will be another thousand people
		generatePeople();
		generateFriends();
	}

	public static void generatePeople() {
		int numberOfPeopleToAdd = 0;
		while(numberOfPeopleToAdd < 1000) {
			//AUTOGENERATE THIS SHIT
			String name = null;
			LocalDate dob = LocalDate.parse("yyyy-mm-dd");
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
		}
	}

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
		}
	}
}
