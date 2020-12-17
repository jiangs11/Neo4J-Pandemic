package neo4j.pandemic;

import static org.neo4j.driver.Values.parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Value;

import neo4j.PeopleWebBuilderStuff.Person;
import neo4j.PeopleWebBuilderStuff.PreexistingCondition;

import org.neo4j.driver.Transaction;

public class NeoOperations {

	/**
	 * Adds a node with a label and the name attribute to neo4j database.
	 * Also sets the project to pandemic and the alive property to TRUE.
	 * 
	 * @param session	the Session object from NeoConnector
	 * @param label		the label of the node, e.g., Person:Student
	 * @param name		the value for the name attribute which will display in graph
	 * @return			the id of the added node.
	 */
	public static int addNode(Session session, String label, String name) {
		String nodeString = "(n:" + label + ") ";
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Result result = tx.run( "CREATE " + nodeString +
					"SET n.name = $name, n.project = 'pandemic', n.alive =  TRUE " +
					"RETURN id(n)",
					parameters("name", name));
			int id = result.single().get(0).asInt();
			tx.commit();
			System.out.println("Node " + id + " committed.");
			return id;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return -1;
		}
		finally {
			tx.close();
		}
	}

	/**
	 * adds or overwrites a property for an existing node
	 * 
	 * @param session	the Session object from NeoConnector
	 * @param id		the id of the node to which 
	 * @param attr		the Attribute object with property name and datatype
	 * @param value		the proposed new value for the property
	 */
	public static void addPropertyToNode(Session session, int id, Attribute attr, String value) {
		String key = attr.getKey().name();
		String valueString = null; 

		try {
			valueString = validateValue(attr, key, value);
		}
		catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		Transaction tx = null;
		try  {
			tx = session.beginTransaction();
			if (valueString.length() != 0) {
				String cmd = "MATCH (n) WHERE id(n) = " + id + " " +
						"SET n." + key + " = " + valueString + " " +
						"RETURN n." + key;	    
				Result result = tx.run( "MATCH (n) WHERE id(n) = " + id + " " +
						"SET n." + key + " = $value " +
						"RETURN n." + key,
						parameters("value", valueString));
				String newVal = result.single().get(0).asString();
				tx.commit();
				tx.close();
				System.out.println("Node " + id + " (" + key + ":" + newVal + ") committed.");
			}
			else {
				System.out.println("Property change not processed.");
			}
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		finally {
			tx.close();
		}
	}

	public static void addAttributesToNode(Session ses, int id, Map<Attribute, String> attrs) {
		attrs.forEach( (k, v) -> addPropertyToNode(ses, id, k, v));
	}

	/**
	 * builds a relationship between two different nodes
	 * 
	 * @param session			the Session object from NeoConnector
	 * @param nodeId1			the id of node 1
	 * @param nodeId2			the id of node 2
	 * @param relationship		the relationship to be created between the nodes
	 */
	public static void relateTwoNodes(Session session, int nodeId1, int nodeId2, String relationship) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Result result = tx.run( "MATCH (n1), (n2) " + 
					"WHERE id(n1) = " + nodeId1 + " and " +
					"id(n2) = " + nodeId2 + " " +
					"CREATE (n1) - [r:" + relationship.toUpperCase() + "] -> (n2) " +
					"RETURN TYPE(r)");
			String newVal = result.single().get(0).asString();
			tx.commit();
			System.out.println("Node " + nodeId1 + " " + newVal + " Node " + nodeId2+ " committed.");
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		finally {
			tx.close();
		}
	}

	/**
	 * adds or overwrites a property for an existing relationship
	 * 
	 * @param session			the Session object from NeoConnector
	 * @param nodeId1			the id of the first node in the relationship
	 * @param nodeId2			the id of the first node in the relationship
	 * @param relationship		the name of the relationship type
	 * @param attr				the Attribute object with property name and datatype
	 * @param value				the proposed new value for the property
	 */
	public static void addPropertyToRelationship(Session session, int nodeId1, int nodeId2, 
			String relationship, Attribute attr, String value) {
		relationship = relationship.toUpperCase();

		String key = attr.getKey().name();
		String valueString = null; 

		try {
			valueString = validateValue(attr, key, value);
		}
		catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			throw e;
		}

		if (valueString.length() != 0) {
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				String cmd = "MATCH (n1) - [r:" + relationship + "] - (n2) " + 
						"WHERE id(n1) = " + nodeId1 + " and " +
						"id(n2) = " + nodeId2 + " " +
						"SET r." + key + " = '" + valueString + "' " +
						"RETURN r." + key;
				Result result = tx.run(cmd);
				String newVal = result.single().get(0).asString();
				tx.commit();
				System.out.println("Node " + nodeId1 + " " + relationship + " Node " + nodeId2 + 
						" (" + key + ", " + value + ") committed.");
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
			}
			finally {
				tx.close();
			}
		}
	}
	//TODO Change this to check if a relatoinship exists
	public static void addPropertyToRelationshipOneway(Session session, int nodeId1, int nodeId2, 
			String relationship, Attribute attr, String value) {
		relationship = relationship.toUpperCase();

		String key = attr.getKey().name();
		String valueString = null; 

		try {
			valueString = validateValue(attr, key, value);
		}
		catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			throw e;
		}

		if (valueString.length() != 0) {
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				String cmd = "MATCH (n1) - [r:" + relationship + "] -> (n2) " + 
						"WHERE id(n1) = " + nodeId1 + " and " +
						"id(n2) = " + nodeId2 + " " +
						"SET r." + key + " = '" + valueString + "' " +
						"RETURN r." + key;
				Result result = tx.run(cmd);
				String newVal = result.single().get(0).asString();
				tx.commit();
				System.out.println("Node " + nodeId1 + " " + relationship + " Node " + nodeId2 + 
						" (" + key + ", " + value + ") committed.");
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
			}
			finally {
				tx.close();
			}
		}
	}
	/**
	 * adds a Label to an existing node
	 * 
	 * @param session			the Session object from NeoConnector
	 * @param id				the id of the node who's label is to be replaced
	 * @param newLabel			the new label of the node
	 */
	public static void addLabelToNode(Session session, int id, String newLabel) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String cmd = "MATCH (n) " + 
					"WHERE id(n) = " + id + " " +
					"SET n:" + newLabel + " " +
					"RETURN labels(n)";
			Result result = tx.run(cmd);
			String labels  = result.list().get(0).toString();

			tx.commit();
			System.out.println("Node " + id + " labeled as " + labels); 
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}	
		finally {
			tx.close();
		}
	}

	public static void relabelNode(Session session, int id, String newLabel) {
		// First delete existing labels
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// Retrieve existing labels
			String cmd = "MATCH (n) " + 
					"WHERE id(n) = " + id + " " +
					"RETURN labels(n)";
			Result result = tx.run(cmd);   	
			List<Record> records  = result.list();
			List<Value> values = records.get(0).values();
			List<Object> objects = values.get(0).asList();
			for (Object obj : objects) {
				cmd = "MATCH (n) " + 
						"WHERE id(n) = " + id + " " + 
						"REMOVE n:" + obj;
				System.out.println(cmd);
				result = tx.run(cmd);
			}
			tx.commit();
			// In a separate transaction, add in the new labels
			addLabelToNode(session, id, newLabel);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}	
		finally {
			tx.close();
		}
	}

	/**
	 * checks to see if the value is valid for the Attribute.  Uses the datatype of
	 * the Attribute object to ensure proper neo4j format.  Also checks to see if the
	 * key is valid (i.e., registered to ValidValues class).  And, if the key is valid,
	 * if there is a list of valid values, it checks to make sure that the property value
	 * is in that list. 
	 * 
	 * @param attr						the Attribute object containing name and datatype
	 * @param key						the key of the property
	 * @param value						the value of the property
	 * @return							a valid String to use in neo4j, appropriate to datatype
	 * @throws InvalidKeyException		if the key is not in the ValidValues list
	 * @throws InvalidValueException	if the value is not appropriate for the key
	 */
	public static String validateValue(Attribute attr, String key, String value)
			throws InvalidKeyException, InvalidValueException {
		// Check to see if value allowed
		if (ValidValues.keyValues.keySet().contains(attr)) {
			// Valid key
			if (ValidValues.keyValues.get(attr) == null ||
					ValidValues.keyValues.get(attr).contains(value)) {
				// Valid value; null means all allowed
				if (attr.getDatatype().name().equals("date")) {
					if (ValidValues.isValidDate(value)) {
						return "date('" + value + "')";
					}
					else {
						throw new InvalidValueException("Invalid date.");
					}
				}
				else {
					return value;
				}
			}
			else {
				throw new InvalidValueException(value + " is not a valid value for key " + key + ".");
			}
		}
		else {
			throw new InvalidKeyException(key + " is not a valid key.");
		}
	}


	/**
	 * returns a HashMap containing
	 * - HashMap infectedPeopleMap (node id, Person) of infected individuals who have just exposed another (key: "INFECTED")
	 * 		by entering in the neo-given id of the node, you get the Person object back
	 * - ArrayList exposedPeopleList (node id, HashMap) of HashMaps, each inner map containing (key: EXPOSED)
	 *   values in HashMap: 
	 *     - String: given neo id of the node (key: "id")
	 *     - Person: exposed person (key: "person")
	 *     - String: relationship strength (key: "relationship")
	 *     - String: id of infected person or "transmitter" (key: "transmitterId")
	 * 
	 * @param session : the Session object from NeoConnector
	 */
	public static HashMap getHealthyNeighborsOfInfectedNodes(Session session) {
		Transaction tx = null;
		HashMap resultsMap = new HashMap(); 
		try {
			tx = session.beginTransaction();
			StringBuilder cmd = new StringBuilder(); 

			cmd.append("MATCH (i:infected:Person)-[r:KNOWS]-(h:Person) ");
			cmd.append("where not(h:infected) "); 
			cmd.append("and  (i.alive = true) and (h.alive = true) ");
			cmd.append("return ID(i), i.name,  i.maskUsage, i.masks, i.jobType, i.socialGuidelines, i.handWashing, i.hermit, "); 
			cmd.append("ID(h), h.name,  h.maskUsage, h.masks, h.jobType, h.socialGuidelines, h.handWashing, h.hermit, "); 
			cmd.append("r.relationship_strength ");
			Result result = tx.run(cmd.toString());
			
			HashMap infectedPeopleMap = new HashMap<>(); 
			ArrayList<HashMap> exposedPeopleList = new ArrayList<>();  
			HashMap personTransmitterRelationshipMap = new HashMap<>(); 
			Record currentRecord = null; 
			while (result.hasNext()) {
				currentRecord = result.next(); 
				String exposedId = currentRecord.get("ID(h)").toString();
					personTransmitterRelationshipMap = new HashMap<>(); 
					String transmitterId = currentRecord.get("ID(i)").toString();
					Person exposedPerson = pullPersonFromRecord(false, currentRecord); 
					personTransmitterRelationshipMap.put("id", exposedId); 
					personTransmitterRelationshipMap.put("person", exposedPerson); 
					personTransmitterRelationshipMap.put("transmitterId", transmitterId); 
					personTransmitterRelationshipMap.put("relationship", currentRecord.get("r.relationship_strength").toString().replace('"', ' ').trim());
					exposedPeopleList.add(personTransmitterRelationshipMap); 
					
					if (!infectedPeopleMap.containsKey(transmitterId)) {
						Person infectedPerson = pullPersonFromRecord(true, currentRecord); 
						infectedPeopleMap.put(transmitterId, infectedPerson); 
					}
			}
			resultsMap.put("INFECTED", infectedPeopleMap);
			resultsMap.put("EXPOSED", exposedPeopleList); 
			
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}	
		finally {
			tx.close();
		}
		return resultsMap; 
	}
	
	
	/**
	 * TIME SEQUENCING - in this update you will want to set the dateInfected
	 * 
	 * updates the nodes to reflect newly infected people
	 * 
	 * @param session   : the Session object from NeoConnector
	 * @param idFilter  : 
	 */
	public static void infectNodes(Session session, String idFilter) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			StringBuilder cmd = new StringBuilder(); 
			cmd.append("match(h:Person) where (ID(h) in [").append(idFilter).append("]) ");
			cmd.append("set h:infected ");
			tx.run(cmd.toString());
			tx.commit(); 
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * TIME SEQUENCING - in this update you will want to set the dateInfected
	 * 
	 * updates the nodes to reflect newly infected people
	 * 
	 * @param session   : the Session object from NeoConnector
	 * @param idFilter  : 
	 */
	public static int[] getData(Session session) {
		Transaction tx = null;
		int[] results = new int[2];
		try {
			tx = session.beginTransaction();
			StringBuilder cmd = new StringBuilder(); 
			cmd.append("match(h:infected) return count(n) as count");
			tx.run(cmd.toString());
			tx.commit(); 
			Result result = tx.run(cmd.toString());
			results[0] = Integer.parseInt(result.next().get("count").toString());
			tx = session.beginTransaction();
			cmd = new StringBuilder(); 
			cmd.append("match(h:dead) return count(n) as count");
			tx.run(cmd.toString());
			tx.commit(); 
			result = tx.run(cmd.toString());
			results[1] = Integer.parseInt(result.next().get("count").toString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return results;
	}
	
	private static Person pullPersonFromRecord(boolean infected, Record currentRecord) {
		Person person = new Person(); 
		String alias = (infected ? "i" : "h"); 
		person.setName(currentRecord.get(alias + ".name").toString().replace('"', ' ').trim());
		person.setMask(currentRecord.get(alias + ".masks").toString().replace('"', ' ').trim());
		person.setMaskUsage(currentRecord.get(alias + ".maskUsage").toString().replace('"', ' ').trim());
		person.setJobType(currentRecord.get(alias + ".jobType").toString().replace('"', ' ').trim());
		person.setSocialGuidelines(currentRecord.get(alias + ".socialGuidelines").toString().replace('"', ' ').trim());
		person.setHandWashing(currentRecord.get(alias + ".handWashing").toString().replace('"', ' ').trim().equals("true")? true : false);
		person.setHermit(currentRecord.get(alias + ".hermit").toString().replace('"', ' ').trim().equals("true")? true : false);
		return person; 
	}
}
