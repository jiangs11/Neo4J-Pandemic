package neo4j.pandemic;

import java.util.ArrayList;
import java.util.HashMap;

import org.neo4j.cypher.internal.expressions.In;
import org.neo4j.driver.Session;
import neo4j.PeopleWebBuilderStuff.*;
public class Driver {


	// Add all the attributes to the person
	public static void addAttributes(Session ses, Integer pid, Person person) {

		if (person.isInfected()) {
			NeoOperations.addLabelToNode(ses, pid, "infected");
		}
		NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.age), String.valueOf(person.getAge()));
		NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.masks), String.valueOf(person.getMask()));
		NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.maskUsage), String.valueOf(person.getMaskUsage()));
		NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.jobType), String.valueOf(person.getJobType()));
		NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.socialGuidelines), String.valueOf(person.getSocialGuidelines()));
		NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.handWashing), String.valueOf(person.isHandWashing()));
		NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.hermit), String.valueOf(person.isHermit()));
		NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.socialClass), String.valueOf(person.getSocialClass()));

		// Underlying Conditions Array
		for (PreexistingCondition disease: person.getUnderLyingCondition()) {
			if(String.valueOf(disease) == "Cancer") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.Cancer), "true");
			}
			if(String.valueOf(disease) == "ChronicKidneyDisease") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.ChronicKidneyDisease), "true");
			}
			if(String.valueOf(disease) == "HeartConditions") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.HeartConditions), "true");
			}
			if(String.valueOf(disease) == "Obesity") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.Obesity), "true");
			}
			if(String.valueOf(disease) == "OverWeight") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.OverWeight), "true");
			}
			if(String.valueOf(disease) == "Smoking") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.Smoking), "true");
			}
			if(String.valueOf(disease) == "Type2Diabetes") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.Type2Diabetes), "true");
			}
			if(String.valueOf(disease) == "HIVOrSTD") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.HIVOrSTD), "true");
			}
			if(String.valueOf(disease) == "ImmunnoDeficient") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.ImmunoDeficient), "true");
			}
			if(String.valueOf(disease) == "OrganTransplant") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.OrganTransplant), "true");
			}
			if(String.valueOf(disease) == "MetabolicDisorder") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.MetabolicDisorder), "true");
			}
			if(String.valueOf(disease) == "LiverDisease") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.LiverDisease), "true");
			}
			if(String.valueOf(disease) == "LungDiseases") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.LungDiseases), "true");
			}
			if(String.valueOf(disease) == "Type1Diabetes") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.Type1Diabetes), "true");
			}
			if(String.valueOf(disease) == "SickleCell") {
				NeoOperations.addPropertyToNode(ses, pid, ValidValues.getAttribute(Attribute.Property.SickleCell), "true");
			}
		}

	}
	
	public static void main(String[] args) {
		//TODO Set relationships to being only one per
		// Connector to the main DB server
		ArrayList<Integer> pids = new ArrayList<>();
		PeopleBuilder.start();
		ArrayList<Person> webOfPeople = PeopleBuilder.getPeopleHolder();
		String bolt1 = "bolt://54.237.9.240:7687";  // Main DB Server
		String bolt2 = "bolt://54.90.41.128:7687";  // Dedicated neo server
		String bolt3 = "bolt://localhost:7687"; 	// Localhost
		
		// Uncomment the bolt assignment that you want to use
		//String bolt = bolt1;
		String bolt = bolt2;

		NeoConnector nc = new NeoConnector(bolt, "neo4j", "graphme");
		Session ses = nc.getDriver().session();
		// ValidValues.showValidKeys();  // Uncomment this to see valid values
		for (Person person : webOfPeople) {
			Integer pid = NeoOperations.addNode(ses, "Person", person.getName());
			addAttributes(ses, pid, person);
			pids.add(pid);
		}
		
		HashMap<Integer, ArrayList<Integer>> relationshipsAdded = new HashMap<>();
		int webSize = webOfPeople.size();
		for (int i = 0; i < webSize; i++) {
			relationshipsAdded.put(i, new ArrayList<>());
			Person person = webOfPeople.get(i);
			HashMap<Person, String> relationship = person.getRelationships();
			int numberOfFriends = relationship.size();
			for (int grabbingPerson = 0; grabbingPerson < numberOfFriends; grabbingPerson++) {
				int locationOfFriend = person.getPidsOfFriends().get(grabbingPerson);
				if(webOfPeople.get(locationOfFriend).getRelationships() != null) {
					String relation = relationship.get(webOfPeople.get(locationOfFriend));
					NeoOperations.relateTwoNodes(ses, pids.get(i), pids.get(locationOfFriend), "knows");
					NeoOperations.addPropertyToRelationshipOneway(ses, pids.get(i), pids.get(locationOfFriend), "knows", 
							ValidValues.getAttribute(Attribute.Property.relationship_strength), relation);
				}
			}
			person.setRelationships(null);
		}
	
		System.out.println("Friends done");

		//		// Basic Node creation - Persons and Events
////		int pid1 = NeoOperations.addNode(ses, "Person:Student", "Fred");
////		int pid2 = NeoOperations.addNode(ses, "Person:Student", "Barney");
////		int pid3 = NeoOperations.addNode(ses, "Person", "Wilma");
////		int pid4 = NeoOperations.addNode(ses, "Person:Student", "Betty");
////		int pid5 = NeoOperations.addNode(ses, "Person", "Mr. Slate");
////		int pid6 = NeoOperations.addNode(ses, "Person", "Ann Margrock");
////		int eid1 = NeoOperations.addNode(ses, "Event", "Political Rally");
//
//		// Augmentation of nodes with properties
//		NeoOperations.addPropertyToNode(ses, pid1, ValidValues.getAttribute(Attribute.Property.creator), "myersjac");
//		NeoOperations.addPropertyToNode(ses, pid1, ValidValues.getAttribute(Attribute.Property.dob), "1996-01-30");
//		NeoOperations.addPropertyToNode(ses, pid2, ValidValues.getAttribute(Attribute.Property.creator), "myersjac");
//		NeoOperations.addPropertyToNode(ses, pid2, ValidValues.getAttribute(Attribute.Property.dob), "1998-03-31");
//		NeoOperations.addPropertyToNode(ses, pid2, ValidValues.getAttribute(Attribute.Property.alive), "true");
//		NeoOperations.addPropertyToNode(ses, pid3, ValidValues.getAttribute(Attribute.Property.creator), "myersjac");
//		NeoOperations.addPropertyToNode(ses, pid4, ValidValues.getAttribute(Attribute.Property.creator), "myersjac");
//		NeoOperations.addPropertyToNode(ses, pid5, ValidValues.getAttribute(Attribute.Property.creator), "myersjac");
//		NeoOperations.addPropertyToNode(ses, pid6, ValidValues.getAttribute(Attribute.Property.creator), "myersjac");
//		NeoOperations.addPropertyToNode(ses, eid1, ValidValues.getAttribute(Attribute.Property.creator), "myersjac");
//
//		// Set up the KNOWS relationship between two people
//		NeoOperations.relateTwoNodes(ses, pid1, pid2, "knows");
//		NeoOperations.addPropertyToRelationship(ses, pid1, pid2, "knows", 
//				ValidValues.getAttribute(Attribute.Property.relationship_strength), "medium");
//
//		NeoOperations.relateTwoNodes(ses, pid1, pid3, "knows");
//		NeoOperations.addPropertyToRelationship(ses, pid1, pid3, "knows", 
//				ValidValues.getAttribute(Attribute.Property.relationship_strength), "strong");
//
//		NeoOperations.relateTwoNodes(ses, pid2, pid4, "knows");
//		NeoOperations.addPropertyToRelationship(ses, pid2, pid4, "knows", 
//				ValidValues.getAttribute(Attribute.Property.relationship_strength), "strong");
//
//		NeoOperations.relateTwoNodes(ses, pid3, pid4, "knows");
//		NeoOperations.addPropertyToRelationship(ses, pid3, pid4, "knows", 
//				ValidValues.getAttribute(Attribute.Property.relationship_strength), "weak");
//
//		NeoOperations.relateTwoNodes(ses, pid1, pid5, "knows");
//		NeoOperations.addPropertyToRelationship(ses, pid1, pid5, "knows", 
//				ValidValues.getAttribute(Attribute.Property.relationship_strength), "strong");
//
//		NeoOperations.relateTwoNodes(ses, pid2, pid5, "knows");
//		NeoOperations.addPropertyToRelationship(ses, pid1, pid5, "knows", 
//				ValidValues.getAttribute(Attribute.Property.relationship_strength), "strong");	
//
//		// Set up the Event attendees
//		NeoOperations.relateTwoNodes(ses, pid1, eid1, "attends");		
//		NeoOperations.addPropertyToRelationship(ses, pid1, eid1, "attends", 
//				ValidValues.getAttribute(Attribute.Property.masks), "none");
//
//		NeoOperations.relateTwoNodes(ses, pid2, eid1, "attends");
//		NeoOperations.addPropertyToRelationship(ses, pid2, eid1, "attends", 
//				ValidValues.getAttribute(Attribute.Property.masks), "level3");
//
//		NeoOperations.relateTwoNodes(ses, pid3, eid1, "attends");
//		NeoOperations.addPropertyToRelationship(ses, pid3, eid1, "attends", 
//				ValidValues.getAttribute(Attribute.Property.masks), "level2");		
//
//		NeoOperations.relateTwoNodes(ses, pid6, eid1, "attends");
//		NeoOperations.addPropertyToRelationship(ses, pid6, eid1, "attends", 
//				ValidValues.getAttribute(Attribute.Property.masks), "level1");	
//
//		// Modify the labels of nodes
//		NeoOperations.addLabelToNode(ses, pid2, "InfectedPerson");
//		NeoOperations.relabelNode(ses, pid1, "DeceasedPerson");

		try {
			ses.close();
			nc.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
//		int numb = 1;
//		for (Person person : webOfPeople) {
//			System.out.println(numb + " " + person.getNumberOfFriends());
//		}
	}


}


