import java.util.ArrayList;

/**
 * This class is used to represent a project that members can be assigned to. It
 * holds the current members in the list and any other factors pertinent to the
 * project.
 * 
 * @author Ethan Young and David Seamon
 */
public class Project {
	private String projectName; // Name of the project
	private Integer minMembers; // Minimum number of members allowed
	private Integer maxMembers; // Maximum number of members allowed
	private ArrayList<Integer> factors; // Inputted factors for the project
	private Integer numOpenSpots; // Number of spots still open on the team
	ArrayList<Member> members; // List of members currently on the project
	private Integer projectScore; // The amount of interest by the members
	private boolean active; // Whether a project is allowed to be assigned
							// members in the iteration

	/**
	 * Inputs all of the data from the file into Project objects
	 * 
	 * @param projectName:
	 *            Name of the project
	 * @param inData:
	 *            The data pulled from the spreadsheet
	 * @param minMem:
	 *            minimum members allowed
	 * @param maxMem:
	 *            maximum members allowed
	 */
	public Project(String projectName, ArrayList<Integer> inData, int minMem,
			int maxMem) {
		// Initialize the arrays
		members = new ArrayList<Member>();
		factors = new ArrayList<Integer>();

		this.projectName = projectName;
		minMembers = minMem;
		maxMembers = maxMem;

		// Adds factors to arrayList
		for (int i = 0; i < inData.size(); i++)
			factors.add(inData.get(i));

		numOpenSpots = maxMem;
		projectScore = 0;

		// Initially in the rotation
		active = true;
	}

	/**
	 * This method adds members to the current project.
	 * 
	 * @param member
	 *            This is the member that needs to be added to the list
	 */
	public void addMember(Member member) {
		members.add(member);
		numOpenSpots--;
	}

	/**
	 * This method adds members to the current project.
	 * 
	 * @return True if spots open, false otherwise
	 */
	public boolean isFull() {
		return (numOpenSpots <= 0);
	}

	/**
	 * This method indicates how many members are assigned to the current
	 * project
	 * 
	 * @return The number of members on the project
	 */
	public int getNumMembers() {
		return maxMembers - numOpenSpots;
	}

	/**
	 * This method returns the number of members required for this to be a
	 * project
	 * 
	 * @return Minimum allowed number of members
	 */
	public int getminMembers() {
		return minMembers;
	}

	/**
	 * This method returns the max number of members required for this to be a
	 * project
	 * 
	 * @return Maximum allowed number of members
	 */
	public int getMaxMembers() {
		return maxMembers;
	}

	/**
	 * This method indicates allows for the access of the members on the project
	 * 
	 * @return the member list for the project
	 */
	public ArrayList<Member> getMembers() {
		return members;
	}

	/**
	 * @return The name of the project
	 */
	public String getName() {
		return projectName;
	}

	/**
	 * This method sets whether a project is valid for the iteration
	 */
	public void setActive(boolean value) {
		active = value;
	}

	/**
	 * This method indicates if project is active for the current iteration
	 * 
	 * @return whether the project is active
	 */
	public boolean getActive() {
		return active;
	}

	/**
	 * This method indicates the total score for the project based on the
	 * members assigned
	 * 
	 * @return The total project score
	 */
	public Integer getProjectScore() {
		return projectScore;
	}

	/**
	 * STATIC MOVE TO DIFFERENT CLASS This method resets each project's assigned
	 * members for use in the next iteration
	 * 
	 * @param the
	 *            Arraylist of projects to reset
	 */
	public static void resetMembers(ArrayList<Project> allProjects) {

		// Loops through the projects to reset members and number of open spots
		for (int i = 0; i < allProjects.size(); ++i) {
			allProjects.get(i).members = new ArrayList<Member>();
			allProjects.get(i).numOpenSpots = allProjects.get(i).maxMembers;
		}
	}

	/**
	 * This method calculates the average for that factor across all members on
	 * the project
	 * 
	 * @param index:
	 *            This is the index of the factor of interest
	 * 
	 * @return the cumulative project score
	 */
	public Double calculateAverageFactor(int index) {
		double total = 0;

		// Calculates the average for that factor
		for (int i = 0; i < members.size(); ++i) {
			total = total + members.get(i).getFactor(index).getValue();
		}

		Double average = total / members.size();
		return average;
	}

	public void increaseProjectScore(int value) {
		this.projectScore += value;
	}

	/**
	 * This is the toString() comparable implementation. The format is..
	 * 
	 * projectName: member1 member2 ...
	 * 
	 * @return A string with information for the project
	 */
	public String toString() {
		String toReturn = null;

		toReturn = projectName + ": \n";

		// Loops through all of the members
		for (int i = 0; i < members.size(); i++) {
			toReturn += members.get(i).getName() + "\n";
		}

		return toReturn + "\n";

	}
}
