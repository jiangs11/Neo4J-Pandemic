package neo4j.TimeSeq;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.jfree.data.time.Day;
import org.neo4j.driver.Session;

import neo4j.pandemic.*;
import neo4j.Death.Killer;
import neo4j.EventBuilder.EventBuilder;
import neo4j.infection.*;
public class Simulator {
	/**
	 * Static method that handles the running of the different
	 * methods created by other groups. Will also call the 
	 * graphing methods.
	 * @param pids The id's for the nodes we created
	 */
	public static void main(String[] args) {
		String bolt1 = "bolt://54.237.9.240:7687";  // Main DB Server
		String bolt2 = "bolt://54.90.41.128:7687";  // Dedicated neo server
		String bolt3 = "bolt://localhost:7687"; 	// Localhost

		// Uncomment the bolt assignment that you want to use
		//String bolt = bolt1;
		String bolt = bolt2;

		NeoConnector nc = new NeoConnector(bolt, "neo4j", "graphme");
		Session ses = nc.getDriver().session();

		//Comment out if already have graph
		//Driver.start(ses);

		//create variables here
		int[] infected = new int[365];
		int[] dead = new int[365];
		int[] data = null;
		Date date = null;
		for (int i = 0; i < 365; i++){
			date = Date.from(LocalDate.of(2020,1,1).plusDays(i-1).atStartOfDay(ZoneId.systemDefault()).toInstant());
			EventBuilder.generateEvent("Event number " + i, date, 10);
			if(i % 35 == 0) {
				EventBuilder.generateEvent("Event number " + i, date, 100);
			}
			Infection.infectThruNetwork(ses, date);
			
			Infection.infectThruEvent(ses, date);
			Killer.getInfectedPerson_attributes(ses);
			
			data = NeoOperations.getData(ses, date);
			System.out.println(data[0] +" " + data[1]);
			if (i > 0) {
				dead[i] = data[1] - sumArray(dead, i);
				infected[i] = data[0] - sumArray(infected, i) + data[1];
			}
			else {
				infected[i] = data[0];
				dead[i] = data[1];
			}
		}


		try {
			Graph infect = new Graph(infected, 
					"Covid-19 Infections per day in 2020",
	                "Day",
	                "of Deaths",
	                ".\\Charts\\infectDaily.png");

			Graph death = new Graph(dead, 
					"Deaths per Day as a Result of Covid-19 in 2020",
	                "Day",
	                "of Deaths",
	                ".\\Charts\\deadDaily.png");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			ses.close();
			nc.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static int sumArray(int[] ar, int i) {
		int sum = 0;
		for (int j = 0; j < i; j++) {
			sum +=ar[j];
		}
		return sum;
	}


}