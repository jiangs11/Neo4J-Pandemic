package neo4j.pandemic;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

public class ValidValues {

	// Lists of values
	public final static List<String> booleans = new ArrayList<>(Arrays.asList("true", "false"));
	public final static List<String> relStrengths = new ArrayList<>(Arrays.asList("strong", "moderate", "weak"));
	public final static List<String> masks = new ArrayList<>(Arrays.asList("none", "level3", "level2", "level1"));
//	public final static List<String> booleans = new ArrayList<>(List.of("true", "false"));
//	public final static List<String> relStrengths = new ArrayList<>(List.of("strong", "moderate", "weak"));
//	public final static List<String> masks = new ArrayList<>(List.of("none", "level3", "level2", "level1"));
	
	// Populate Map
	public final static Map<Attribute, List<String>> keyValues;
	static {
		keyValues = new HashMap<>();
		keyValues.put(new Attribute(Attribute.Property.alive, Attribute.Datatype.bool), booleans);
		keyValues.put(new Attribute(Attribute.Property.dob, Attribute.Datatype.date), null);
		keyValues.put(new Attribute(Attribute.Property.infection_date, Attribute.Datatype.date), null);
		keyValues.put(new Attribute(Attribute.Property.creator, Attribute.Datatype.string), null);
		keyValues.put(new Attribute(Attribute.Property.relationship_strength, Attribute.Datatype.string), relStrengths);
		keyValues.put(new Attribute(Attribute.Property.masks, Attribute.Datatype.string), masks);
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
