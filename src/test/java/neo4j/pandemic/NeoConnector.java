package neo4j.pandemic;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import static org.neo4j.driver.Values.parameters;

public class NeoConnector implements AutoCloseable
{
    private final Driver driver;

    /**
     * Full argument constructor for connecting to any neo4j instance via bolt
     * 
     * @param uri		the bolt connection URI
     * @param user		the username for neo4j instance
     * @param password	the password for neo4j instance
     */
	public NeoConnector(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }
	
    /**
     * Default constructor makes a driver for the class neo4j instance
     */
	public NeoConnector() {
		//driver =  GraphDatabase.driver("bolt://54.237.9.240:7687", AuthTokens.basic("neo4j", "graphme"));
		driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "graphme"));
	}

	/** 
	 * closes the driver
	 */
    @Override
    public void close() throws Exception {
        driver.close();
    }

    /**
     * A sample method that creates a Greeting node and sets its message field.
     * 
     * It opens a session using the driver, then uses that session to write a 
     * transaction.  The writeTransaction method requires a new TransactionWork
     * object
     * 
     * @param message	the message you want the node to store
     */
    public void printGreeting( final String message ) {
        try (Session session = driver.session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx ) {
                    Result result = tx.run( "CREATE (a:Greeting) " +
                                            "SET a.message = $message " +
                                            "RETURN a.message + ', from node ' + id(a)",
                            parameters( "message", message ) );
                    return result.single().get(0).asString();
                }
            } );
            System.out.println(greeting);
        }
    }

	public Driver getDriver() {
		return driver;
	}


}