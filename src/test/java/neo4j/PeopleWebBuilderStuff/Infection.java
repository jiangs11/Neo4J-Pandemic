package neo4j.PeopleWebBuilderStuff;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

/**
 * Infection Algorithm
 * @author Edward Callihan
 * @author Christina Bannon
 *
 */
public class Infection {
	
	// For Checking Luck Against Risk Of Infection
	private static Random luck = new Random();
	
	/**
	 * These are all the values to weight the different factors of infection
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
	
	// Begin Methods here
	
	
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
		double behaviorMod1 = calculateBehaviorRisk(person);
		double behaviorMod2 = calculateBehaviorRisk(contact);
		double relationshipMod = relationshipFactor.get(relationship);
		return (behaviorMod1 + behaviorMod2) * relationshipMod;
	}
	
	/**
	 * Checks the network for anyone who is infected and alive. Each living, infected person's
	 * relationships to other living, but un-infected people is examined. The potential to 
	 * infect each un-infected person is calculated and checked against a randomly generated 
	 * luck value. The newly infected people are returned in a set so their status can be changed
	 * between time intervals.
	 * @return set of people newly infected.
	 */
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
	
	
}

	
	
	