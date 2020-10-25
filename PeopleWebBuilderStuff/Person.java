import java.util.Date;
import java.util.HashMap;

public class Person {

	private boolean infected;
	private boolean deceased;
	private String name;
	private Date birthDate;
	private int age;
	private HashMap<Person, Relationships> relationships;
	private MaskUsage maskUsage;
	private MaskType maskType;
	private HandWashing handWashing;
	private Hygiene hygiene;
	private SocialGuidelines socialGuidelines;
	public Person() {

	}
	public boolean isInfected() {
		return infected;
	}
	public boolean isDeceased() {
		return deceased;
	}
	public void setDeceased(boolean deceased) {
		this.deceased = deceased;
	}
	public void setInfected(boolean infected) {
		this.infected = infected;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public HashMap<Person, Relationships> getRelationships() {
		return relationships;
	}
	public void setRelationships(HashMap<Person, Relationships> relationships) {
		this.relationships = relationships;
	}
	public MaskUsage getMaskUsage() {
		return maskUsage;
	}
	public void setMaskUsage(MaskUsage maskUsage) {
		this.maskUsage = maskUsage;
	}
	public MaskType getMaskType() {
		return maskType;
	}
	public void setMaskType(MaskType maskType) {
		this.maskType = maskType;
	}
	public HandWashing getHandWashing() {
		return handWashing;
	}
	public void setHandWashing(HandWashing handWashing) {
		this.handWashing = handWashing;
	}
	public Hygiene getHygiene() {
		return hygiene;
	}
	public void setHygiene(Hygiene hygiene) {
		this.hygiene = hygiene;
	}
	public SocialGuidelines getSocialGuidelines() {
		return socialGuidelines;
	}
	public void setSocialGuidelines(SocialGuidelines socialGuidelines) {
		this.socialGuidelines = socialGuidelines;
	}
}
