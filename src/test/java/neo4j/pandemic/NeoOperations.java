package neo4j.pandemic;

import static org.neo4j.driver.Values.parameters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Value;

//import neo4j.EventBuilder.EventPlace;
//import neo4j.EventBuilder.EventType;
import neo4j.EventBuilder.Events;
//import neo4j.EventBuilder.VentilationType;
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
	public static void infectNodes(Session session, String idFilter, Date date) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			StringBuilder cmd = new StringBuilder(); 
			cmd.append("match(h:Person) where (ID(h) in [").append(idFilter).append("]) ");
			cmd.append("set h:infected, h.date_infected = " + date.toString());
			tx.run(cmd.toString());
			tx.commit(); 
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}


	public static void healAll(Session session) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			tx.run("match (n:Person:infected) remove n:infected");
			tx.commit(); 
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static ArrayList<Integer> getRandomPeople(Session session, int numPeople) {
		Transaction tx = null;
		ArrayList<Integer> peopleIds = new ArrayList<>(); 
		Record currentRecord = null; 
		try {
			tx = session.beginTransaction();
			Result result = tx.run("MATCH (p:Person) return id(p), rand() as rand order by rand ASC Limit " + numPeople);
			while (result.hasNext()) {
				currentRecord = result.next(); 
				peopleIds.add(Integer.parseInt(currentRecord.get("id(p)").toString()));
			}
			tx.commit(); 
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return peopleIds; 
	}

	public static void infectFirstPerson(Session session) {
		Transaction tx = null;
		Record currentRecord = null; 
		try {
			tx = session.beginTransaction();
			Result result = tx.run("MATCH (p:Person) return id(p), rand() as rand order by rand ASC Limit 1");
			currentRecord = result.next(); 
			Integer id = Integer.parseInt(currentRecord.get("id(p)").toString());
			result = tx.run("MATCH (n) WHERE id(n) = " + id + " SET n:infected RETURN n.name, labels(n)");
			currentRecord = result.next(); 
			String name = currentRecord.get("n.name").toString();
			tx.commit(); 
			System.out.println("First Infection: " + id + ": " + name); 
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
	public static int[] getData(Session session, Date date) {
		int[] results = new int[2];
		try {

			Result result = session.run("match(n:InfectedPerson) where n.date = " + date.toString() + " return count(n) as count");
			results[0] = Integer.parseInt(result.next().get("count").toString());
			result = session.run("match(n:DeceasedPerson) return count(n) as count");
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

	private static Person pullPersonFromEventRecord(Record currentRecord) {
		Person person = new Person(); 
		String alias = "i";
		person.setName(currentRecord.get(alias + ".name").toString().replace('"', ' ').trim());
		person.setMask(currentRecord.get(alias + ".masks").toString().replace('"', ' ').trim());
		person.setMaskUsage(currentRecord.get(alias + ".maskUsage").toString().replace('"', ' ').trim());
		person.setJobType(currentRecord.get(alias + ".jobType").toString().replace('"', ' ').trim());
		person.setSocialGuidelines(currentRecord.get(alias + ".socialGuidelines").toString().replace('"', ' ').trim());
		person.setHandWashing(currentRecord.get(alias + ".handWashing").toString().replace('"', ' ').trim().equals("true")? true : false);
		person.setHermit(currentRecord.get(alias + ".hermit").toString().replace('"', ' ').trim().equals("true")? true : false);
		return person; 
	}
	
	/**
	 * Made for testing, pass an event and this method will create a node from it
	 * @param session
	 * @param event
	 * @return
	 */
	public static int addEventNode(Session session, Events event) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			StringBuffer buffer = new StringBuffer(); 
			buffer.append("CREATE (n:Event ");
			buffer.append("{ name : '").append(event.getEventName()).append("', ");
			buffer.append("project : 'pandemic', ");
			buffer.append("eventDate : '").append(event.getEventDate().getTime()).append("', ");
			buffer.append("eventType : '").append(event.getEventTypeString()).append("', ");
			buffer.append("eventCapacity : ").append(event.getEventCapacity()).append(", ");
			buffer.append("venue : '").append(event.getVenueString()).append("', ");
			if (event.getVenueString().equals("indoor")){
				buffer.append("indoorVentilation : '").append(event.getIndoorVentilationString()).append("', ");
			}
			buffer.append("maskEnforcement : ").append(event.getMaskEnforcement()).append(", ");
			buffer.append("socialDistancing : ").append(event.getSocialDistancing()).append(", ");
			buffer.append("tempChecks : ").append(event.getTempChecks()).append(", ");
			buffer.append("handSanitizerAvailable : ").append(event.getHandSanitizerAvailable()).append(", ");
			buffer.append("CDCApprovedCleaning : ").append(event.getCDCApprovedCleaning()).append(" })");
			buffer.append("RETURN id(n)");
			Result result = tx.run(buffer.toString());
			int id = result.single().get(0).asInt();
			tx.commit();
			System.out.println("New Event [" + event.getEventName() + "] with id [" + id + "] committed.");
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
	 * evenList of 
	 *  - event HashMaps
	 *  --  eventobject
	 *  --  eventId
	 *  --  sickList
	 *  ---- personHashMaps
	 *  --- healthyList
	 *  ---- personHashMaps
	 *  
	 *  eventMap (eid, eventObj
	 *    each 
	 *  
	 * @param session
	 * @return
	 */
	public static ArrayList getContaminatedEvents(Session session, Date date) {
			Transaction tx = null;
			ArrayList eventList = new ArrayList<>(); 
			HashMap resultsMap = new HashMap(); 
			HashMap personTransmitterRelationshipMap = new HashMap<>(); 
			Record currentEventRecord = null; 
			Record currentPersonRecord = null; 
			String currentEventId = null; 
			Events event = null; 
			try {
				tx = session.beginTransaction();
				StringBuilder cmd = new StringBuilder(); 
				cmd.append("MATCH (i:Person:infected)-[r:ATTENDS]->(e:Event) "); 
				cmd.append("WHERE e.eventDate = '").append(date.getTime()).append("' ");
				cmd.append("RETURN distinct ID(e), e.name, e.eventDate, e.eventType, e.eventCapacity, e.venue, e.indoorVentilation, e.maskEnforcement, ");
				cmd.append("e.socialDistancing, e.tempChecks, e.handSanitizerAvailable, e.CDCApprovedCleaning");
				Result result = tx.run(cmd.toString());
				while (result.hasNext()) {
					currentEventRecord = result.next();
					String eventId = currentEventRecord.get("ID(e)").toString().replace('"', ' ').trim();
					HashMap eventMap = new HashMap(); 
					ArrayList infectedList = new ArrayList(); 
					ArrayList healthyList = new ArrayList(); 
					
					event = new Events();
					event.setEventName(currentEventRecord.get("e.name").toString().replace('"', ' ').trim());
					long eventDateTime = Long.parseLong(currentEventRecord.get("e.eventDate").toString().replace('"', ' ').trim());
					event.setEventDate(new Date(eventDateTime));
					//	event.setEventDate(currentEventRecord.get("e.eventDate").toString().replace('"', ' ').trim());
					event.setEventType(currentEventRecord.get("e.eventType").toString().replace('"', ' ').trim());
					event.setEventCapacity(Integer.parseInt(currentEventRecord.get("e.eventCapacity").toString()));
					event.setVenueFromString(currentEventRecord.get("e.venue").toString().replace('"', ' ').trim());
					if (event.getVenueString().equals("indoor")) {
						event.setIndoorVentilationFromString(currentEventRecord.get("e.indoorVentilation").toString());
					} 
					event.setMaskEnforcement(currentEventRecord.get("e.maskEnforcement").equals("true"));
					event.setSocialDistancing(currentEventRecord.get("e.socialDistancing").equals("true"));
					event.setTempChecks(currentEventRecord.get("e.tempChecks").equals("true"));
					event.setHandSanitizerAvailable(currentEventRecord.get("e.handSanitizerAvailable").equals("true"));
					event.setCDCApprovedCleaning(currentEventRecord.get("e.CDCApprovedCleaning").equals("true"));
					
					cmd = new StringBuilder(); 
					cmd.append("MATCH (i:Person)-[r:ATTENDS]->(e:Event) "); 
					cmd.append("where ID(e) = ").append(eventId).append(" ");
					cmd.append("return ID(i), i.name,  i.maskUsage, i.masks, i.jobType, i.socialGuidelines, i.handWashing, i.hermit, labels(i)");
					result = tx.run(cmd.toString());
					
					while (result.hasNext()) {
						currentPersonRecord = result.next();
						String personID = currentPersonRecord.get("ID(i)").toString().replace('"', ' ').trim();
						String [] labels = currentPersonRecord.get("labels(i)").toString().split(","); 
						Person person = pullPersonFromEventRecord(currentPersonRecord);
						
						HashMap personMap = new HashMap(); 
						personMap.put("personID", personID);
						personMap.put("person", person);
						if (labels.length > 1) {
							infectedList.add(personMap);
						} else {
							healthyList.add(personMap);
						}
					}
					eventMap.put("eventID", eventId);
					eventMap.put("event", event);
					eventMap.put("healthyList", healthyList);
					eventMap.put("infectedList", infectedList);
					eventList.add(eventMap);
				}
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
			}	
			finally {
				tx.close();
			}
			return eventList; 
		}
}
