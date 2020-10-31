package neo4j.pandemic;

public class Attribute {

	public enum Property {
		dob, infection_date, alive, creator, relationship_strength, masks, jobType, maskUsage,
		socialGuidelines, preExistingConditions, infected,age, handWashing,hermit, preivouslyInfected,
		numberOfFriends
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


