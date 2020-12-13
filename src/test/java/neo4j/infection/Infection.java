package neo4j.infection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.neo4j.driver.Session;

import neo4j.PeopleWebBuilderStuff.JobType;
import neo4j.PeopleWebBuilderStuff.MaskUsage;
import neo4j.PeopleWebBuilderStuff.Masks;
import neo4j.PeopleWebBuilderStuff.PeopleBuilder;
import neo4j.PeopleWebBuilderStuff.Person;
import neo4j.PeopleWebBuilderStuff.Relationships;
import neo4j.PeopleWebBuilderStuff.SocialGuidelines;
import neo4j.pandemic.NeoOperations;

import java.util.Random;

/**
 * Infection Algorithm
 * @author Edward Callihan
 * @author Christina Bannon
 *
 *TODO: 
 * - Event infecting
 * - Maybe improve on how we determine a risk factor
 * - How should we handle the end result, when there are no more nodes to infect?
 * 		- Are we considering recovery time?
 * - segment out infectThroughNetwork
 * - Maybe we should verify each db update with a result??
 * 
 * 
 * 
 * https://www.wsj.com/articles/how-many-people-might-one-person-with-coronavirus-infect-11581676200
 * https://19andme.covid19.mathematica.org/
 */
public class Infection {
	
	// For Checking Luck Against Risk Of Infection
	private static Random luck = new Random();
	
	/**
	 * Values to weigh the different relationship factors of infection
	 */
	
	// Relationship Strength Values
	private static double relWeak = 0.2;
	private static double relMedium = 0.6;
	private static double relStrong = 1.0;
	
	// Mask Type Values
	private static double mType3 = 0.05; // N-95
	private static double mType2 = 0.2;  // 2-Layer Cloth
	private static double mType1 = 0.9;  // Gaiter
	private static double mType0 = 1.0;  // None
	
	// Mask Usage Consistency Values
	private static double useAlways = 0.05;
	private static double useOften = 0.5;
	private static double useSometimes = 0.7;
	private static double useRarely = 0.9;
	private static double useNever = 1.0;
	
	// Job Type Values
	private static double jobNoInteraction = 0.1;
	private static double jobRemote = 0.2;
	private static double jobUnemployed = 0.3;
	private static double jobOutOfWork = 0.3;
	private static double jobOfficeBuilding = 0.6;
	private static double jobWareHouse = 0.7;
	private static double jobGrocery = 0.9;
	private static double jobRestaurant = 0.9;
	private static double jobTeachers = 0.9;
	private static double jobMedical = 0.95;
	
	// Social Guideline Adherence Values
	private static double socFollows = 0.2;
	private static double socKindOf = 0.5;
	private static double socNever = 0.7;
	
	
	// Handwashing Values
	private static double washTrue = 0.7;
	private static double washFalse = 1.0;
	
	// Hermit Values
	private static double hermitTrue = 0.3;
	private static double hermitFalse = 1.0;
	/*
	 * Factor Weights.
	 * 
	 * Each set of weights describes the contribution to infection probability
	 * from the various factors of interaction between an infected and a 
	 * healthy person.
	 */
	
	// Infection Type Weights
	private static double airborneWT = 0.75;
	private static double handToHandWT = 0.25;
	
	// Airborne Infection Weights
	private static double maskWT = 0.5;
	private static double jobAirWT = 0.3;
	private static double sDistAirWT = 0.2;
	
	// Hand to Hand Infection Weights
	private static double hWashWT = 0.5;
	private static double jobHigieneWT = 0.3;
	private static double sDistHigieneWT = 0.2;
	
	// Individual Health Infection Weights
	private static double healthyWT = 0.4;
	private static double infectedWT = 0.6;
	
	
	/**
	 * Values to weigh the different event factors of infection
	 */
	private static double eventOutdoors = 0.2;
	private static double eventIndoors = 0.8; 
	
	private static double ventilationUpgraded = 0.2;
	private static double ventilationNotUpgraded = 0.8;

	private static double carnivalEvent = 0.5;
	private static double sportsEvent = 0.5;
	private static double fleaMarketEvent = 0.7;
	private static double weddingEvent = 0.8;
	private static double concertEvent = 0.8;
	private static double politicalEvent = 1;
	
	private static double masksEnforced = 0.5;
	private static double masksNotEnforced= 1;
	
	private static double socialDistancingEvent = 0.5;
	private static double nonSocialDistancingEvent = 1;
	
	private static double tempChecks = 0.5; 
	private static double noTempChecks = 1;  
	
	private static double handSanitizerAvailable = 0.2;
	private static double handSanitizerNotAvailable = .8; 
	
	/**
	 * Mappings of feature categories to their respective values.
	 */	
	private static HashMap<Masks, Double> maskFactor = new HashMap<Masks, Double>(){
		{
			put(Masks.none, mType0);
			put(Masks.type1, mType1);
			put(Masks.type2, mType2);
			put(Masks.type3, mType3);
		}
	};
	
	private static HashMap<MaskUsage, Double> maskUseFactor = new HashMap<MaskUsage, Double>(){
		{
			put(MaskUsage.always, useAlways);
			put(MaskUsage.veryOften, useOften);
			put(MaskUsage.sometimes, useSometimes);
			put(MaskUsage.rarely, useRarely);
			put(MaskUsage.never, useNever);
		}
	};
	
	private static HashMap<JobType, Double> jobTypeFactor = new HashMap<JobType, Double>(){
		{
			put(JobType.unemployed, jobUnemployed);
			put(JobType.outOfWork, jobOutOfWork);
			put(JobType.remote, jobRemote);
			put(JobType.officeBuilding, jobOfficeBuilding);
			put(JobType.warehouse, jobWareHouse);
			put(JobType.noPublicInteraction, jobNoInteraction);
			put(JobType.grocery, jobGrocery);
			put(JobType.restaurant, jobRestaurant);
			put(JobType.teachers, jobTeachers);
			put(JobType.medicalWorkers, jobMedical);
		}
	};
	
	private static HashMap<SocialGuidelines, Double> socialFactor = new HashMap<SocialGuidelines, Double>(){
		{
			put(SocialGuidelines.follows, socFollows);
			put(SocialGuidelines.kindOfFollows, socKindOf);
			put(SocialGuidelines.doesntFollow, socNever);
		}
	};
	
	/**
	 * This relationshipFactor may have to be used if the person.getRelationships() changes to return
	 * the enum.
	 */
	private static HashMap<String, Double> relationshipFactor = new HashMap<String, Double>(){
		{
			put("weak", relWeak);
			put("medium", relMedium);
			put("strong", relStrong);
		}
	};
	
	
	/**
	 * Calculates the risk a person contributes to spreading the virus.
	 * @param person
	 * @return risk contribution value
	 */
	public static double calculateBehaviorRisk(Person person) {
		double risk = 1.0;
		risk *= maskFactor.get(person.getMask());
		risk *= maskUseFactor.get(person.getMaskUsage());
		risk *= jobTypeFactor.get(person.getJobType());
		risk *= socialFactor.get(person.getSocialGuidelines());
		risk *= person.isHandWashing() ? washTrue : washFalse;
		risk *= person.isHermit() ? hermitTrue : hermitFalse;
		return risk;
	}

	/**
	 * Calculates the risk of passing an infection from a person to their contact.
	 * @param person : infected individual
	 * @param contact : Non-infected individual
	 * @param relationship : relationship strength
	 * @return risk value of spreading infection
	 */
	public static double calculateInteractionRisk(Person person, Person contact, String relationship) {
		double behaviorMod1 = personalRisk(person);
		double behaviorMod2 = personalRisk(contact);
		double relationshipMod = relationshipFactor.get(relationship);
		return (behaviorMod1 + behaviorMod2) * relationshipMod;
	}
	
	
	public static double personalRisk(Person person) {
		// Contact contribution
		double hWashSub = 1 - (person.isHandWashing() ? washTrue : washFalse);
		double hWashVal = hWashWT * hWashSub;
		double jobHigieneSub = 1 - (jobTypeFactor.get(person.getJobType()));
		double jobHigieneVal = jobHigieneWT * jobHigieneSub;
		double sDistHigieneSub = 1 - (socialFactor.get(person.getSocialGuidelines()));
		double sDistHigieneVal = sDistHigieneWT  * sDistHigieneSub;
		double handToHandSub = hWashVal + jobHigieneVal + sDistHigieneVal;
		double handToHandVal = handToHandWT * handToHandSub;

		// Airborne Contribution
		double maskSub = 1 - (maskFactor.get(person.getMask()) * maskUseFactor.get(person.getMaskUsage()));
		double maskVal = maskWT * maskSub;
		double jobAirSub = 1 - (jobTypeFactor.get(person.getJobType()));
		double jobAirVal = jobAirWT * jobAirSub;
		double sDistAirSub = 1 - (socialFactor.get(person.getSocialGuidelines()));
		double sDistAirVal = sDistAirWT * sDistAirSub;
		double airborneSub = maskVal + jobAirVal + sDistAirVal;
		double airborneVal = airborneWT * airborneSub;

		// Total Contribution Weight by (Infected or Healthy)
		double infWT = person.isInfected() ? infectedWT : healthyWT;
		double personalSub = handToHandVal + airborneVal;
		double personalVal = infWT * personalSub;

		return personalVal;
	}
	
	/**
	 * Checks the network for anyone who is infected and alive. Each living, infected person's
	 * relationships to other living, but un-infected people is examined. The potential to 
	 * infect each un-infected person is calculated and checked against a randomly generated 
	 * luck value. The newly infected people are returned in a set so their status can be changed
	 * between time intervals.
	 * @return set of people newly infected.
	 *
	public static Set<Person> infectThruNetwork() {
		Set<Person> newInfections = new HashSet<Person>();
		for(Person person: PeopleBuilder.getPeopleHolder()) {
			if(person.isInfected() && !person.isDeceased()) {
				HashMap<Person, String> potentialInfected = new HashMap<Person, String>();
				HashSet<Person> neighborhood = new HashSet<Person>();
				person.getRelationships().forEach((contact, relation) -> {
					if(!neighborhood.contains(contact) && !contact.isDeceased() && !contact.isInfected()) {
						neighborhood.add(contact);
						potentialInfected.put(contact, relation);
					}
				});
				potentialInfected.forEach((contact, relation) -> {
					double risk = calculateInteractionRisk(person, contact, relation);
					if(risk > luck.nextDouble()) {
						newInfections.add(contact);
					}
				});
			}
		}
		return newInfections;
	}
	*/
	
	/**
	 * NeoOperations.getHealthyNeighborsOfInfectedNodes to pull all of the healthy Person nodes who have 
	 * been exposed to an infected node from db. 
	 * Iterates through map of exposed individuals and calculates risk factor for each
	 * If an Person object's risk factor outweighs a random luck factor, the Person is infected. 
	 * 
	 * @param session       Session object connecting to db
	 */
	public static void infectThroughNetwork(Session session) {
		HashMap map = NeoOperations.getHealthyNeighborsOfInfectedNodes(session);
		HashMap infectedPeopleMap = (HashMap)map.get("INFECTED");
		ArrayList<HashMap> exposedPeopleList = (ArrayList)map.get("EXPOSED"); 
        StringBuilder newlyInfectedIds = new StringBuilder();
        String prefix = "";
        
		for (HashMap exposedPersonFieldMap : exposedPeopleList) {
            String exposedPersonId = (String)exposedPersonFieldMap.get("id");        
            Person exposedPerson = (Person)exposedPersonFieldMap.get("person"); 
            String infectedPersonId = (String)exposedPersonFieldMap.get("transmitterId");
            String relationshipStrength = (String)exposedPersonFieldMap.get("relationship");
            
            String exposedPersonName = exposedPerson.getName();
            Person infectedPerson = (Person)infectedPeopleMap.get(infectedPersonId);
            String infectedPersonName = infectedPerson.getName(); 
            
            double riskFactor = calculateInteractionRisk(exposedPerson, infectedPerson, relationshipStrength); 
            double luckFactor = luck.nextDouble(); 
            if (riskFactor > luckFactor) {
            	System.out.println("-Infected person [" + infectedPersonName + 
            			"] HAS infected [" + exposedPersonName + "] as their risk factor was [" + riskFactor +
            			"] and their luck factor was [" + luckFactor + "] ");
            	newlyInfectedIds.append(prefix);
            	newlyInfectedIds.append(exposedPersonId);
            	prefix = ", ";
            } else {
            	System.out.println("-Infected person [" + infectedPersonName + 
            			"] HAS NOT infected [" + exposedPersonName + "] as their risk factor was [" + riskFactor +
            			"] and their luck factor was [" + luckFactor + "] ");
            }
		}
		if (newlyInfectedIds.length() > 0) {
			System.out.println("Updating nodes " + newlyInfectedIds.toString());
			NeoOperations.infectNodes(session, newlyInfectedIds.toString()); 
			System.out.println("Update complete");
		} else {
			System.out.print("No node updates");
		}
	}
	
	/*
	private String padSpaces(String name) {
		
	}
	*/
}

	
	
	