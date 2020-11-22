package neo4j.PeopleWebBuilderStuff;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.neo4j.driver.Session;
import neo4j.pandemic.NeoConnector;

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

			nc = new NeoConnector(bolt, "neo4j", "graphme");
			ses = nc.getDriver().session();

			
			Infection.infectThroughNetwork(ses); 
			
			ses.close();
			nc.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
