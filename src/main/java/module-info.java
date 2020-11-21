/**
 * 
 */
/**
 * @author edwar
 *
 */
module pandemic {
	exports neo4j.PeopleWebBuilderStuff;
	exports neo4j.pandemic;

	requires javafaker;
	requires org.neo4j.cypher.internal.expressions;
	requires org.neo4j.driver;
}