package neo4j.pandemic;

public class Attribute {

	public enum Property {
		dob, infection_date, alive, creator, relationship_strength, masks, jobType, maskUsage,
		socialGuidelines, infected,age, handWashing,hermit, preivouslyInfected,
		numberOfFriends,Cancer,	ChronicKidneyDisease,
		HeartConditions, Obesity, OverWeight,
		Smoking,Type2Diabetes, HIVOrSTD,ImmunnoDeficient,
		OrganTransplant,MetabolicDisorder, LiverDisease,
		LungDiseases, Type1Diabetes,SickleCell
	}
	
	public enum Datatype {
		date, string, number, bool
	}
	
	Property key;
	Datatype datatype;
	
	public Attribute(Property key, Datatype datatype) {
		this.key = key;
		this.datatype = datatype;
	}

	public Property getKey() {
		return key;
	}

	public Datatype getDatatype() {
		return datatype;
	}
	
	@Override
	public String toString() {
		return "Attribute [key=" + key + ", datatype=" + datatype + "]";
	}	
	
}


