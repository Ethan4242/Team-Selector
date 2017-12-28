import java.util.ArrayList;

/**
 * This class is used to represent an member to be assigned and stores all of
 * their data as well as contains algorithms that generate their "priority" to
 * determine who gets added to teams first
 * 
 * @author Ethan Young and David Seamon
 */
public class Member implements Comparable<Member> {
	private String name; // Name of the member
	private ArrayList<String> projectStrings; // Projects as strings
	private ArrayList<Factor> factors; // Factors for project
	private Double continuingProject; // Always first int column and is if they
										// were on project already
	private ArrayList<Project> projectChoices; // The projects in Project object
	private Double priorityRanking; // Their choice ranking

	/**
	 * The constructor for a Member that assigns all of the data from the file
	 * to object variables
	 * 
	 * @param stringData:
	 *            The data in the input file that is explicitly a string
	 *            (Name..)
	 * @param factorNames:
	 *            The names of the factors that were in the input file
	 * @param intData:
	 *            The data in the input that is explicitly non string
	 * @param allProjects:
	 *            All of the possible projects allowed
	 * @param weights:
	 *            These are the weights for each factor for the priority ranking
	 */
	public Member(ArrayList<String> stringData, ArrayList<String> factorNames,
			ArrayList<Double> intData, ArrayList<Project> allProjects,
			ArrayList<Double> weights) throws Exception {
		// Initialize all of the arrayLists
		projectStrings = new ArrayList<String>();
		factors = new ArrayList<Factor>();
		projectChoices = new ArrayList<Project>();
		priorityRanking = .0;

		// The name is always the first column
		name = stringData.get(0);

		// Add projects to the projectStrings array
		for (int i = 1; i < stringData.size(); i++) {
			projectStrings.add(projectStrings.size(),
					stringData.get(i));
		}

		// Whether continuing is always first column int column
		continuingProject = intData.get(0);

		// Creates factors out of the intData items
		for (int i = 1; i < intData.size(); i++) {
			// factorNames i - 1 because of the continuing projects
			factors.add(new Factor(factorNames.get(i - 1), intData.get(i)));
		}

		// Generates the priority rankings for each member
		generatePriorityRanking(weights);

		// Converts the project strings to projects
		convertProjects(allProjects);
	}

	/**
	 * Converts all of the names of the Projects (since they're passed in as
	 * Strings) to actual Project objects
	 * 
	 * @param allProjects:
	 *            The list of all of the projects they can request
	 */
	private void convertProjects(ArrayList<Project> allProjects) {
		// Looks through both lists and adds to members projectChoices array
		// when match found
		for (int i = 0; i < projectStrings.size(); i++) {
			for (int j = 0; j < allProjects.size(); j++) {
				if (projectStrings.get(i)
						.equals(allProjects.get(j).getName())) {
					projectChoices.add(allProjects.get(j));
					break;
				}
			}
		}
	}

	/**
	 * This is where the priority ranking for a Member is generated. The user
	 * will input the weights for each factor at the beginning of the program
	 * 
	 * @param weights:
	 *            The coeff for each factor in the priority calculation (Linear
	 *            combo)
	 */
	private void generatePriorityRanking(ArrayList<Double> weights)
			throws Exception {
		try {
			for (int i = 0; i < weights.size(); i++) {
				priorityRanking += weights.get(i) * factors.get(i).getValue();
			}
		} catch (Exception e) {
			System.out.println(
					"Error in generating Priority. This may be due to not all members having values for each factor.");
			throw new Exception();
		}

	}

	/**
	 * This method retrieves the factor at the given index
	 * 
	 * @param index:
	 *            The index in the factors array of the factor to be retrieved
	 * 
	 * @return The requested factor
	 */
	public Factor getFactor(int index) {
		if (index > factors.size() || index < 0)
			System.out.println("ERROR: Invalid factor accessed");
		;
		return factors.get(index);
	}

	/**
	 * This method retrieves the name of the member
	 * 
	 * @return The name of the member
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method retrieves the priority ranking of the member
	 * 
	 * @return The priority ranking of the member
	 */
	public Double getPriorityRanking() {
		return priorityRanking;
	}

	/**
	 * This method retrieves the team choices for the member
	 * 
	 * @return The team choices for the member
	 */
	public ArrayList<Project> getTeamChoices() {
		return projectChoices;
	}

	/**
	 * This method retrieves the factor list for the member
	 * 
	 * @return The factor list for the member
	 */
	public ArrayList<Factor> getFactors() {
		return factors;
	}

	/**
	 * This method indicates if the member is returning or not
	 * 
	 * @return True if the member is continuing
	 */
	public boolean getContinuing() {
		return (continuingProject == 1);
	}

	/**
	 * This is the method the priority queue uses to determine priority
	 * 
	 * @param other:
	 *            The other member being compared to the current member
	 * 
	 * @return 1 if the other member has higher priority -1 if the current
	 *         member has higher priority
	 */
	@Override
	public int compareTo(Member other) {
		if (this.getPriorityRanking() < other.getPriorityRanking()) {
			return 1;
		} else {
			return -1;
		}
	}
}