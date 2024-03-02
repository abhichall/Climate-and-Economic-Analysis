package climate;

import java.util.ArrayList;

import org.w3c.dom.Node;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        String arr[] = inputLine.split(",");   //reads the input for the csv files, and splits each input with a comma

        StateNode x = new StateNode(arr[2], null, null);  //sets every node; the arr[2] refers to the state as each states index is 2

        if (firstState == null) {  // this if statement runs and says that if the first state is null, meaning empty list
            this.firstState = x; // then the node becomes the first state
            return;                 //although void, this will end the execution of the entire function
        }

        StateNode ptr = firstState;  //this will be used to iterate throught the linked list; connects back at the end of the if/else
        StateNode prev = null;  //this will be used to iterate throught the linked list; connects back at the end of the if/else


        if (ptr.getName().equals(x.getName())) {   //this is checking if the name of the next node in the list, is the same as the one needing to be inputted
            return;                                //will terminate the function if state already exists
        }
        else {
            while (ptr.getNext() != null) {        //this whole while loop will go through each node to check if there are duplicates
                                                   // and when it reaches the last node in the list, the .getNext method will return null, therefore terminating the while loop
                
                    if (ptr.getNext().getName().equals(x.getName())) {  //checks if they are the same
                    return;
                }

                    
                    prev = ptr;  //used to iterate through list
                    ptr = ptr.next; //used to iterate through list
                }

        ptr.setNext(x); //after the while loop is terminated, and no duplicates are found, this line will set the next node to x which will create a node after the current last node of the linked list
            }

    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        String[] county = inputLine.split(",");
        StateNode ptr = firstState;
        CountyNode currCounty = new CountyNode(county[1], null, null);

        while (!(ptr.getName().equals(county[2])) && ptr.getNext() != null) {    //iterates through list chekcing to see if theres anything already 
            ptr = ptr.next;                                                      //equal to what was inputted or until it reaches the end
        }

        if(ptr.getDown() == null) { //checks if a county is pointed down upon in the state
            ptr.setDown(currCounty); //if the county is not already present in the state and is not pointed to, then it the new one is set
            return;
        }

        if (ptr.getDown().getName().equals(currCounty.getName())) {
            return;
        }
            else{
                CountyNode ptrCounty = ptr.getDown(); //holds the job of creating a pointer for the new county
                while (ptrCounty.next != null) {
                        if (ptrCounty.getNext().getName().equals(currCounty.getName())) {
                            return;
                        }
                        ptrCounty = ptrCounty.next;
                }
                        ptrCounty.setNext(currCounty); //if it doesnt exist add the current county
                        return;

            }

    }

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {  //im too lazy to explain this

       String[] community = inputLine.split(",");
       StateNode ptr = firstState;
       Data information = new Data(Double.parseDouble(community[3]), Double.parseDouble(community[4]), Double.parseDouble(community[5]), Double.parseDouble(community[8]), Double.parseDouble(community[9]), community[19], Double.parseDouble(community[49]), Double.parseDouble(community[37]), Double.parseDouble(community[121]));
       CommunityNode currCommunity = new CommunityNode(community[0], null, information);

       while (!(ptr.getName().equals(community[2])) && ptr.getNext() != null) {
            ptr = ptr.getNext();
       }

       CountyNode ptrCounty = ptr.getDown();

       while (!(ptrCounty.getName().equals(community[1])) && ptrCounty.getNext()!= null) {
                ptrCounty = ptrCounty.getNext();
       }

       if(ptrCounty.getDown() == null) {
            ptrCounty.setDown(currCommunity);
            return;
       }

       CommunityNode ptrCommunity = ptrCounty.getDown();
       while (ptrCommunity.getNext() != null) {
            ptrCommunity = ptrCommunity.getNext();
        
       }
       ptrCommunity.setNext(currCommunity);

    }

   
    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {

        int counter = 0;
        StateNode ptr = firstState;
        
        while (ptr!=null) {
            CountyNode ptrCounty = ptr.getDown();

            while (ptrCounty != null) {
                CommunityNode ptrCommunity = ptrCounty.getDown();

                while (ptrCommunity != null) {
                    double perc = 0.0;
                    if (race.equals("African American")) {
                        perc = ptrCommunity.getInfo().getPrcntAfricanAmerican();
                    }
                    else if (race.equals("Asian American")) {
                        perc = ptrCommunity.getInfo().getPrcntAsian();
                    }
                    else if (race.equals("Native American")) {
                        perc = ptrCommunity.getInfo().getPrcntNative();
                    }
                    else if (race.equals("Hispanic American")) {
                        perc = ptrCommunity.getInfo().getPrcntHispanic();
                    }
                    else if (race.equals("White American")) {
                        perc = ptrCommunity.getInfo().getPrcntWhite();
                    }
                    if (ptrCommunity.getInfo().getAdvantageStatus().equals("True") && perc*100 >= userPrcntage) {
                        counter++;
                    }
                    ptrCommunity = ptrCommunity.getNext();
                }
                ptrCounty = ptrCounty.getNext();
            }
            ptr = ptr.getNext();
        }
        return counter;
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        int counter = 0;
        StateNode ptr = firstState;
        
        while (ptr!=null) {
            CountyNode ptrCounty = ptr.getDown();

            while (ptrCounty != null) {
                CommunityNode ptrCommunity = ptrCounty.getDown();

                while (ptrCommunity != null) {
                    double perc = 0.0;
                    if (race.equals("African American")) {
                        perc = ptrCommunity.getInfo().getPrcntAfricanAmerican();
                    }
                    else if (race.equals("Asian American")) {
                        perc = ptrCommunity.getInfo().getPrcntAsian();
                    }
                    else if (race.equals("Native American")) {
                        perc = ptrCommunity.getInfo().getPrcntNative();
                    }
                    else if (race.equals("Hispanic American")) {
                        perc = ptrCommunity.getInfo().getPrcntHispanic();
                    }
                    else if (race.equals("White American")) {
                        perc = ptrCommunity.getInfo().getPrcntWhite();
                    }
                    if (ptrCommunity.getInfo().getAdvantageStatus().equals("False") && perc*100 >= userPrcntage) {
                        counter++;
                    }
                    ptrCommunity = ptrCommunity.getNext();
                }
                ptrCounty = ptrCounty.getNext();
            }
            ptr = ptr.getNext();
        }
        return counter;
    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {

        ArrayList<StateNode> stateList = new ArrayList<StateNode>();
        StateNode ptr = firstState;
        boolean value = false;
            while (ptr != null) {
                CountyNode ptrCounty = ptr.getDown();
                while (ptrCounty != null ) {
                    CommunityNode ptrCommunity = ptrCounty.getDown();
                    while (ptrCommunity != null) {
                        if (ptrCommunity.getInfo().getPMlevel() >= PMlevel) {
                            value = true;
                            break;
                        }
                        ptrCommunity = ptrCommunity.getNext();
                    }
                    ptrCounty = ptrCounty.getNext();
                }
                if (value) {
                    stateList.add(ptr);
                }
                value = false;
                ptr = ptr.getNext();
            }

        return stateList; 
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {

        int counter = 0;
        StateNode ptr = firstState;

        while (ptr != null) {
            CountyNode ptrCounty = ptr.getDown();
            while (ptrCounty != null) {
                CommunityNode ptrCommunity = ptrCounty.getDown();
                while (ptrCommunity != null) {
                    if (ptrCommunity.getInfo().getChanceOfFlood() >= userPercntage)
                        counter++;
                    
                        ptrCommunity = ptrCommunity.getNext();
                }
                ptrCounty = ptrCounty.getNext();
            }
            ptr = ptr.getNext();
        }
        return counter;
    }


    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

        ArrayList<CommunityNode> communityList = new ArrayList<CommunityNode>();
        StateNode ptr = firstState;

        while (!(ptr.getName().equals(stateName)) && ptr.getNext() != null) {
            ptr = ptr.getNext();
        }

        CountyNode ptrCounty = ptr.getDown();

        while (ptrCounty != null) {
            CommunityNode ptrCommunity = ptrCounty.getDown();
            
            while (ptrCommunity != null) {
                int counter = 0;
                if (communityList.size() < 10) {
                    communityList.add(ptrCommunity);
                }
                if(communityList.size()==10 && communityList.get(9)!=ptrCommunity){
                            
                    for(int i=0; i<communityList.size(); i++){
                        if(communityList.get(i).getInfo().getPercentPovertyLine()<communityList.get(counter).getInfo().getPercentPovertyLine()){
                            counter = i;
                        }
                    }
                    
                    if(ptrCommunity.getInfo().getPercentPovertyLine()>communityList.get(counter).getInfo().getPercentPovertyLine()){
                        communityList.set(counter, ptrCommunity);
                    }
                    counter = 0;

                    
                }

                ptrCommunity = ptrCommunity.getNext();
                
            }
            ptrCounty = ptrCounty.getNext();
        }
            return communityList;
    }
}
