package neo4j.pandemic;

import com.github.javafaker.Faker;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Faker faker = new Faker();
    	
    	String name = faker.name().fullName();
    	String firstName = faker.name().firstName();
    	String lastName = faker.name().lastName();
    	
        System.out.println("Here's your random damn first name: " + firstName);
    }
}
