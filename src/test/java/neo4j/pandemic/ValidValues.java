package neo4j.pandemic;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import neo4j.PeopleWebBuilderStuff.JobType;
import neo4j.PeopleWebBuilderStuff.MaskUsage;
import neo4j.PeopleWebBuilderStuff.Masks;
import neo4j.PeopleWebBuilderStuff.Person;
import neo4j.PeopleWebBuilderStuff.PreexistingCondition;
import neo4j.PeopleWebBuilderStuff.Relationships;
import neo4j.PeopleWebBuilderStuff.SocialGuidelines;

import java.util.Arrays;

public class ValidValues {

	// Lists of values
	public final static List<String> booleans = new ArrayList<String>(Arrays.asList("true", "false"));
	public final static List<String> relStrengths = new ArrayList<String>(Arrays.asList("strong", "medium", "weak"));
	public final static List<String> masks = new ArrayList<String>(Arrays.asList("none", "level3", "level2", "level1"));
	public final static List<String> jobType = new ArrayList<String>(Arrays.asList("unemployed","outOfWork","remote","officeBuilding",
			"warehouse","noPublicInteraction", "grocery,servers","teachers", "medicalWorkers"));
	public final static List<String> maskUsage = new ArrayList<String>(Arrays.asList("always", "veryOften", "sometimes", "rarely", "never"));
	public final static List<String> socialGuidelines = new ArrayList<String>(Arrays.asList("always", "veryOften", "sometimes", "rarely", "never"));
	// Populate Map
	public final static Map<Attribute, List<String>> keyValues;
	static {
		keyValues = new HashMap<Attribute, List<String>>();
		keyValues.put(new Attribute(Attribute.Property.alive, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.dob, Attribute.Datatype.date), null);
		keyValues.put(new Attribute(Attribute.Property.infection_date, Attribute.Datatype.date), null);
		keyValues.put(new Attribute(Attribute.Property.creator, Attribute.Datatype.string), null);
		keyValues.put(new Attribute(Attribute.Property.relationship_strength, Attribute.Datatype.string), relStrengths);
		keyValues.put(new Attribute(Attribute.Property.masks, Attribute.Datatype.string), masks);
		keyValues.put(new Attribute(Attribute.Property.jobType, Attribute.Datatype.string), jobType);
		keyValues.put(new Attribute(Attribute.Property.maskUsage, Attribute.Datatype.string), maskUsage);
		keyValues.put(new Attribute(Attribute.Property.masks, Attribute.Datatype.string), masks);
		keyValues.put(new Attribute(Attribute.Property.infected, Attribute.Datatype.bool), booleans);
		//Figure out how to add age
		keyValues.put(new Attribute(Attribute.Property.handWashing, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.hermit, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.preivouslyInfected, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.Cancer, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.ChronicKidneyDisease, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.HeartConditions, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.HIVOrSTD, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.ImmunnoDeficient, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.LiverDisease, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.LungDiseases, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.MetabolicDisorder, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.Obesity, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.OrganTransplant, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.SickleCell, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.Smoking, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.Type1Diabetes, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.Type2Diabetes, Attribute.Datatype.bool), booleans);
	}
	
	public static Attribute getAttribute(Attribute.Property field) {
		Set<Attribute> attrs = keyValues.keySet();
		for (Attribute a : attrs) {
			if (a.getKey().equals(field)) return(a); 
		}
		throw new InvalidKeyException(field.name() + " is not a valid Field.");
	}
	
	public static void showValidKeys() {
		keyValues.entrySet().forEach(e -> System.out.println(e));
	}
	
    public static boolean isValidDate(String dateStr) {
    	try {
            LocalDate.parse(dateStr);
        } 
    	catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
