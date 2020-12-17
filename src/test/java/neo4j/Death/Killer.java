package neo4j.Death;
import org.neo4j.codegen.api.InstanceOf;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.internal.value.MapValue;
import org.neo4j.driver.internal.value.NodeValue;

import neo4j.pandemic.NeoConnector;

import java.util.*;

public class Killer {

	/**
	 * @param args
	 */
	public static final double THRESHOLD=0.019;
	public static final double BASE_DEATH_RATE=0.016;
	/* This function retrieve infected people and their attributes and labels */
	
	public static void getInfectedPerson_attributes()
	{
		String bolt = "bolt://54.90.41.128:7687"; 
		NeoConnector nc = new NeoConnector(bolt, "neo4j", "graphme");
		Session ses = nc.getDriver().session();
		Transaction tx=null;
		try {
			tx=ses.beginTransaction();
			// Retrieve the infected Persons and their labels
			String cmd2="match(n) where n:InfectedPerson or n:infected return n, properties(n) , labels(n)";
			//Execute the query
			Result res=tx.run(cmd2);
			//loop through the nodes
			
			do {
				int age=0;
				boolean obesity=false,metabolicdisorder=false;
				boolean ChronicKidneyDisease=false,Cancer=false,HeartConditions=false,ImmunnoDeficient=false,smoking=false;
				boolean LiverDisease=false,LungDiseases=false,OverWeight=false,Type1Diabetes=false,Type2Diabetes=false, alive=false;
				
				
				
				
				//getting a record id
				Record record=res.next();
				Value nv=record.get(0);
				long  node_id = nv.asNode().id();
				
				
				// getting the properties
				Map<String, Object> mv= record.get(1).asMap();
				System.out.println("Node id :"+ node_id);
				System.out.println(" Name : "+ mv.get("name"));
				//Getting all the attributes
				//get alive attributes
				if (mv.containsKey("Obesity") && mv.get("Obesity")=="true")
					alive=true;
				// getting the age
				if (mv.containsKey("age"))
					age=Integer.parseInt((String) mv.get("age"));
				// getting obesity
				if (mv.containsKey("Obesity") && mv.get("Obesity")=="true")
					obesity=true;
				// getting metabolicDisorder
				if (mv.containsKey("MetabolicDisorder") && mv.get("MetabolicDisorder")=="true")
					metabolicdisorder=true;
				//getting the chronic disease
				if (mv.containsKey("ChronicKidneyDisease") && mv.get("ChronicKidneyDisease")=="true")
					ChronicKidneyDisease=true;
				// getting the cancer attributes
				if (mv.containsKey("Cancer") && mv.get("Cancer")=="true")
					Cancer=true;
				// getting the heart conditions 
				if (mv.containsKey("HeartConditions") && mv.get("HeartConditions")=="true")
					HeartConditions=true;
				// getting the attributes ImmunnoDeficient
				if (mv.containsKey("ImmunnoDeficient") && mv.get("ImmunnoDeficient")=="true")
					ImmunnoDeficient=true;
				// LivearDisease 
				if (mv.containsKey("LiverDisease") && mv.get("LiverDisease")=="true")
					LiverDisease=true;
				//Lung Disease
				if (mv.containsKey("LungDiseases") && mv.get("LungDiseases")=="true")
					LungDiseases=true;
				//OverWeight
				if (mv.containsKey("OverWeight") && mv.get("OverWeight")=="true")
					OverWeight=true;		
				
				//Type1Diabetes
				if (mv.containsKey("Type1Diabetes") && mv.get("Type1Diabetes")=="true")
					Type1Diabetes=true;
				//Type2Diabetes
				if (mv.containsKey("Type2Diabetes") && mv.get("Type2Diabetes")=="true")
					Type2Diabetes=true;
				// Smoking
				if (mv.containsKey("Smoking") && mv.get("Smoking")=="true")
					smoking=true;
				
				
				
				/*System.out.println("Node : "+ mv.get("name"));
				System.out.println("Node properties " + mv);
				System.out.println(record.get(2).asList());*/
				List<Object> labels=record.get(2).asList();
				System.out.println(" Age : "+age);
				System.out.println(" Labels  : "+labels);
				System.out.println(" Attributes : "+mv);
				
				//System.out.println("id = "+node_id + " age "+ age + " obisity ="+obesity +" labels " +labels +" metabolic "+metabolicdisorder);
				
				// cheking if the infected persons hasTodie
				if (hasToDie(age,obesity,metabolicdisorder,ChronicKidneyDisease,Cancer,HeartConditions,ImmunnoDeficient,
						LiverDisease,LungDiseases,OverWeight,Type1Diabetes,Type2Diabetes,smoking)) {
					//Remove all the labels associates to the ID
					System.out.println(" ---- Killing ---");
					removeLabels(ses,node_id,labels,tx);
					//letting the person die
					perish(ses,node_id,"DeceasedPerson",tx);
					//set the attribute alive to false
					String cmd_alive = "MATCH (n) " + 
		                      "WHERE id(n) = " + node_id + " " + 
		    				  "set n.alive=false return n";
					Result res_alive=tx.run(cmd_alive);
					
									
				}
				//tx.commit();
			    
			}while(res.hasNext());
			
			tx.commit();
			tx.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.getStackTrace();}
		
		/* close the connection */
		try{
			ses.close();
			nc.close();
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		
		
	}
	public static void perish(Session session, long id,String newLabel,Transaction tx) {
		//Transaction tx = null;
		System.out.println("Function perish");
		try {
    		//tx = session.beginTransaction();
    		String cmd = "MATCH (n) " + 
                         "WHERE id(n) = " + id + " " +
                         "SET n:" + newLabel + " " +
                         "RETURN labels(n)";
    		Result result = tx.run(cmd);
    		String labels  = result.list().get(0).toString();

  			//tx.commit();
    		System.out.println("Node " + id + " labeled as " + labels); 
    	}
    	catch (Exception e) {
    		System.err.println(e.getMessage());
    	}	
		finally {
			
		}
	}
	//remove all other labels 
	public static void removeLabels(Session session, long id, List<Object> labels,Transaction tx ) {
		// First delete existing labels
		
		
		try {
    		//tx = session.beginTransaction();
    		// Retrieve existing labels
    		String cmd = "";
    		Result result;
    		for (Object obj : labels) {
    			cmd = "MATCH (n) " + 
                      "WHERE id(n) = " + id + " " + 
    				  "REMOVE n:" + obj;
    			System.out.println(cmd);
    			result = tx.run(cmd);
    			//tx.commit();
    		}
    		
         	}
    	catch (Exception e) {
    		System.err.println(e.getMessage());
    	}	
		finally {
			//tx.close();
		}
	}
	/* get the death multiplier associated with the age
	 * */
	public static  double getAgeMultiplier(int age)
	{
		double multiplier=1;
		if(age <=1)
			multiplier=0.001;
		else {
			if((age>1) && (age <=4))
				multiplier=0.0007;
			else {
				if((age>=5)&&(age<=14))
					multiplier=0.002;
				else {
					if((age>=15)&&(age<=24))
						multiplier=0.02;
					else {
						if((age>=25)&&(age<=34))
							multiplier=0.08;
						else {
							if((age>=35)&&(age<=44))
								multiplier=0.2;
							else {
								if((age>=45)&&(age<=54))
									multiplier=0.5;
								else {
									if((age>=55)&&(age<=64))
										multiplier=1.3;
									else {
										if(age>=65 && age<=74)
											multiplier=2.16;
										else {
											if(age>=75 && age<=84)
												multiplier=2.8;
											else
												multiplier=3.1;
										}
									}
								}
							}
						}
					}
				}
			}
				
		}
		
		return multiplier;
	}

	/* This Function determine wether or not the person will die given his attributes */
	public static boolean hasToDie(int age,boolean obesity,boolean metabolic,boolean ckdis,boolean cancer, boolean hcdt,
			boolean immdef,boolean liverdis,boolean lungdis, boolean overw,
			boolean type1diabetes, boolean types2diabetes,boolean smoking) {
		double multiplier=getAgeMultiplier(age);
		 //System.out.println(" Age multiplier= "+multiplier);
		if(obesity) /* Obesity */
			multiplier=multiplier*8.6;
		if(metabolic) // metabolicdisorder
			multiplier=multiplier*6.5;
		if(ckdis) // chronicKidneyDisease
			multiplier=multiplier*9;
		if(cancer) // CAncer
			multiplier=multiplier*15;
		if(hcdt) // HeartCondition
			multiplier=multiplier*15;
		if(immdef) // Immunodeficient
			multiplier=multiplier*15;
		if(liverdis) // liverdisease
			multiplier=multiplier*8;
		if(lungdis) // Lungdisease
			multiplier=multiplier*8.5;
		if(overw) // Overweight
			multiplier=multiplier*4.5;
		if(type1diabetes) // type1diabetes
			multiplier=multiplier*12.5;
		if(types2diabetes) // types2diabetes
			multiplier=multiplier*15;
		if(smoking) // types2diabetes
			multiplier=multiplier*8.68;
		
		 System.out.println(" multiplier= "+multiplier);
		
	    double death_factor=multiplier*BASE_DEATH_RATE;
	    System.out.println(" The death factor= "+death_factor);
	    System.out.println(" Threshold = "+THRESHOLD);
	    //System.out.println("here "+death_factor +  " "+THRESHOLD);
	    if(death_factor >= THRESHOLD)
	    {  // System.out.println("here "+death_factor +  " "+THRESHOLD);
	    	return true;
	    }
		return false;
	}
	public static void main(String[] args) {
		/*// TODO Auto-generated method stub
        //  System.out.println("Toto");
       // Connector to the main DB server
  		
  		String bolt1 = "bolt://54.237.9.240:7687";  // Main DB Server
  		String bolt2 = "bolt://54.90.41.128:7687";  // Dedicated neo server
  		
  		// Uncomment the bolt assignment that you want to use
  		//String bolt = bolt1;
  		String bolt = bolt2;
  		/*try {
  		Driver driver = GraphDatabase.driver(bolt, AuthTokens.basic("neo4j", "graphme"));
  		
  		Session ses = driver.session();
  		
			ses.close();
			driver.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}*/
  		/*NeoConnector nc = new NeoConnector(bolt, "neo4j", "graphme");
		Session ses = nc.getDriver().session();
		Transaction tx=null;
		try{
		tx=ses.beginTransaction();
		String cmd = "MATCH (n) " + 
        "WHERE id(n) = " + 14411 + " " +
        "RETURN labels(n)";
		
        Result result = tx.run(cmd);   	
		
		System.out.println(result.list());
		//String cmd2="match(n:InfectedPerson)-[]-(p) return n,p";
		String cmd2="match(n:InfectedPerson) return n, properties(n) , labels(n)";
		//String cmd2="match(n) where n.infected='true' return n, properties(n) , labels(n)";
		Result res=tx.run(cmd2);
		
		
		do {
			
			//getting a record id
			Record record=res.next();
			Value nv=record.get(0);
			long  node_id = nv.asNode().id();
			
			// getting the properties
			Map<String, Object> mv= record.get(1).asMap();
			System.out.println("Node id :"+ node_id);
			System.out.println(" contains key "+ mv.containsKey("name"));
			System.out.println("Node : "+ mv.get("name"));
			System.out.println("Node properties " + mv);
			System.out.println(record.get(2).asList());
		}while(res.hasNext());
		
		}catch(Exception e1)
		{
			System.out.println(e1);
		}*/
		System.out.println(" ---- calling getInfectedPerson_attributes ---");
		getInfectedPerson_attributes();
		
		/*try{
		ses.close();
		nc.close();
		/*System.out.println(" factor = " +
		hasToDie(30,true,false,false,false,false,false,false,false,false,true,false,false));*
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}*/
	}

}
