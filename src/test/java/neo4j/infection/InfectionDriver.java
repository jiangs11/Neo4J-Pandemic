package neo4j.infection;

import java.util.ArrayList;
import java.util.Date;

import org.neo4j.driver.Session;

import neo4j.EventBuilder.EventBuilder;
import neo4j.EventBuilder.Events;
import neo4j.pandemic.NeoConnector;
import neo4j.pandemic.NeoOperations;

public class InfectionDriver {

	public static void main(String [] args) {
		NeoConnector nc = null; 
		Session ses = null; 
		try {
			String bolt1 = "bolt://54.237.9.240:7687";  // Main DB Server
			String bolt2 = "bolt://54.90.41.128:7687";  // Dedicated neo server
			String bolt3 = "bolt://localhost:7687"; 	// Localhost

			// Uncomment the bolt assignment that you want to use
			//String bolt = bolt1;
			String bolt = bolt3;

			nc = new NeoConnector(bolt, "neo4j", "Christina");
			ses = nc.getDriver().session();
			
			createEvents(ses);
			
			Infection.infectThroughNetwork(ses); 		

			Infection.infectThruEvent(ses, new Date(2020, 5, 5));
			
			ses.close();
			nc.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void createEvents(Session ses) {
		EventBuilder.fillEventHolder(10);
		ArrayList<Events> eventHolder = EventBuilder.getEventHolder();
		for(int i = 0; i < eventHolder.size(); i++) {
			Events event = eventHolder.get(i);
			int eventId = NeoOperations.addEventNode(ses, event);
			ArrayList<Integer> people = NeoOperations.getRandomPeople(ses, 10);
			for (int j = 0; j < people.size(); j++) {
				NeoOperations.relateTwoNodes(ses, people.get(j), eventId, "attends");
			}
		}
	}
	
	
	
}
