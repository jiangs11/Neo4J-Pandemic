# Neo4J-Pandemic

Update: 10/29

Let’s focus on the CORE set of what is needed.  We can expand the model as development goes on.

- Build the network of people (natural network) STEVE, KODY, ALEX

Person for now
Subclasses? Student?
Age of people
Relationships with other People w/strength. (any other attributes?)

OVERALL GOAL:
Minimally, build PersonPopulator class with static methods including infect() which will give one unlucky Person COVID-19.
DUE FOR 10/28:
Reasonably complete list of subtypes (if any) for Persons
Reasonably complete list of demographic attributes for Persons with their valid values
Reasonable complete list of Person to Person relationship attributes with their valid values.


- Set disease attributes for Persons. Maybe other, like hand washing. Mask level, other Spread/Contract factors, “social hermitcy”, fear. PROBABILITY    DAVE, AUSTIN

OVERALL GOAL:
Minimally, build PersonDefiner class with static methods to set attributes for Persons in a representative way. (You should take into account demographic attributes, like age from Group 1.)
DUE FOR 10/28:
Reasonably complete list of COVID-relevant attributes for Persons with their valid values.


- Create events and enroll people VICTORIA, BARRETT

Characterize with subclasses? Outdoors, density (social distancing), capacity (50%), encouragement status (signs, enforcement…)?
ATTENDS relationship could have an independent Mask attribute?
Event active/inactive. Event duration…
OVERALL GOAL:
Minimally, build EventGenerator class with static methods to create events and to use the methods from Group 1 and Group 2 to make “out of network” Persons who don’t know anybody else, but who are attending the event. We should add the label Attendee
DUE FOR 10/28:
Reasonably complete list of COVID-relevant attributes for Events with their valid values.


- Time sequencing move the clock forward.  One Day  MATT, JOE

Does it introduce events? Introduce out-of-network people?
Hourly? Daily?
Run infection algorithm, run death algorithm
Step wise
The execution driver
Capture statistics about all prior states. So we can analyze the model
OVERALL GOAL:
Minimally, build Simulator class with static methods to exercise the model (i.e., run the methods the other groups give you), but also record, save, and report on what is happening at each step. Think how you would want to present it.  Can we graph it?
You will also have the main Driver class.
DUE FOR 10/28:
Preliminary ideas on reporting. Mockups of reports/graphs


- Infection algorithm – look at current state and determine who is infected, how infections people are.?  PROBABLITY?  Length, masks,   ED, CHRISTINA

OVERALL GOAL:
Cycle through every non-infected Person node, determine if the person should become infected, and, if so, add the label InfectedPerson. Create static methods infectThruNetwork() and infectThruEvent()
DUE FOR 10/28:
A list of attributes for Persons and Events that you would want to use.
 

- Death algorithm look at current state and kill people  PROBABILITY  Diabetc? 10%   Diabetic + Old  5%  TOM, MARMADOU

OVERALL GOAL:
Cycle through every InfectedPerson node, determine if the person will perish., and, if so, replace all Person labels with DeceasedPerson. Create static methods perish() and, perhaps, alterViralLoad() which could affect the Infection algorithm.
DUE FOR 10/28:
A list of attributes for Persons that you would want to use.
