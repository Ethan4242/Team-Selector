import java.util.ArrayList;

//This class is used as a data type for storing a complete distribution iteration of members onto teams
//and doing actions on them
public class Iteration {
	// Reference to an array of iterations
	private static ArrayList<Iteration> iterationList = new ArrayList<Iteration>();
	// ArrayList that holds list of members on each project
	private ArrayList<Member> membersOnProject = new ArrayList<Member>();
	// ArrayList of members that weren't selected to be on teams for that
	// iteration
	private ArrayList<Member> notSelected = new ArrayList<Member>();
	// ArrayList that holds the equation scores of that iteration
	ArrayList iterationEquations = new ArrayList();
	// Array that holds equation scores
	private double[] equationScores = new double[5];
	private static int maxMembers = 5; // Max number of members that can be put
	int techSkillFitScore = 0; // Score for equation 1
	int totalProjChoiceScore = 0; // Score for equation 2
	int totalNotSelected = 0; // Score for equation 3

	// Could add all of the reset methods in the constructor
	public Iteration() {

	}

	// NOT DONE
	public static void generateIteration() {
		Iteration currentIteration = new Iteration();
		// Calls populate with null
		currentIteration.populateWithNullMembers();
		// Calls populateContinuing
		currentIteration.populateContinuingProjects(currentIteration);
		// Calls initialize Viable projects (Could create a static list)
		initializeViableProjects();
		// Calls addMembers
		currentIteration.assignTeams(currentIteration);
		// Finds those not put on teams
		currentIteration.addToNotSelected();
		// Prints out everything
		currentIteration.printIteration();
	}

	// Runs the loops that put members on teams
	public void assignTeams(Iteration currentIteration) {
		// Tests first choice (0), second choice (1), and third choice (3)

		for (int j = 0; j < PriorityQue.projectPriorityOrder.size(); ++j) {
			for (int i = 0; i < Sorter.allMembers.size(); ++i) {
				// Goes through each members preferences and assigns before
				// moving on to next member
				for (int k = 0; k < 3; ++k) {
					Member tempMember = PriorityQue.memberPriorityOrder.get(i);
					Project tempProject = PriorityQue.projectPriorityOrder.get(j);

					int[] tempMemChoices = tempMember.getProjectIntChoices();
					// If assigned to a current project already then skips the
					// rest
					if (tempMember.getTempCurrentProject() != -1) {
						break;
					}

					// Calls add member method if the same
					if (tempProject.getProjectNumber() == tempMemChoices[k]) {
						// Takes note of the choice number taken
						tempMember.setTempChoiceNum(k + 1);

						// Only assigns members to viable project
						if (tempProject.getProjectViability()) {
							currentIteration.addMember(tempProject.getProjectNumber(), tempMember, currentIteration);
						}
					}
				}
			}
		}
	}

	// NOTE: Decided that a member in priority order will be put on a team first
	// (not just assign all first picks)
	// Or could make that a tested iteration
	public void addMember(int projectNumber, Member johnDoe, Iteration currentIteration) {
		Project currentProject = Sorter.allProjects.get(projectNumber);
		Member currentMember = johnDoe;
		int insertLocation = currentIteration.checkIfProjectFull(projectNumber);

		// If project full then breaks the method and tests second choice
		if (insertLocation == -1) {
			return;

		} else {
			// Inserts member into open spot in the Array
			membersOnProject.remove(insertLocation);
			membersOnProject.add(insertLocation, currentMember);
			currentMember.setTempCurrentProject(projectNumber);
		}
	}

	public void addToNotSelected() {
		for (int i = 0; i < Sorter.allMembers.size(); ++i) {
			if (Sorter.allMembers.get(i).getTempCurrentProject() == -1)
				notSelected.add(Sorter.allMembers.get(i));
			// Sets the choice num to 4
			Sorter.allMembers.get(i).setTempChoiceNum(4);
		}

	}

	// Checks if any spaces left in project
	public int checkIfProjectFull(int projectNumber) {
		// Loop iterates through the array to check if full
		for (int k = maxMembers * projectNumber; k < maxMembers * projectNumber + maxMembers; ++k) {
			// If project has an open spot, then returns the spot that the
			// member should be inserted
			if (membersOnProject.get(k).getIfNull() == true) {
				int numInsert = k;
				// System.out.println(numInsert);
				return numInsert;
			}
		}
		// If project is full, returns -1
		return -1;
	}

	// NOT DONE
	// ADD: Equation solver
	public double calculateIteratrionScores() {
		// TODO: Create the score holding array or figure out a way to hold all
		// of the project's scores
		// Could do something similar to how you held members in their projects

		/*
		 * EQUATIONS: a) Sum (for each project) each members skill times the
		 * corresponding skill importance in the project b) Sum choice selection
		 * for entire project list c) Number of people not put on a project d)
		 * Sum (for each project) each member's personality skill times the
		 * "ideal" project team score
		 */
		// Equation 1: How well do member's skills fit with project
		for (int j = 0; j < PriorityQue.projectPriorityOrder.size(); ++j) {
			techSkillFitScore = 0;
			for (int i = j * maxMembers; i < j * maxMembers + maxMembers + maxMembers; ++j) {
				Member tempMember = PriorityQue.memberPriorityOrder.get(i);
				Project tempProject = PriorityQue.projectPriorityOrder.get(j);

				double[] tempMemTechSkills = tempMember.getTechnicalSkills();
				int[] tempProjTechSkills = tempProject.getSkillsNeeded();

				double skill1Contrib = tempMemTechSkills[0] * tempProjTechSkills[0];
				double skill2Contrib = tempMemTechSkills[1] * tempProjTechSkills[1];
				double skill3Contrib = tempMemTechSkills[2] * tempProjTechSkills[2];
				double skill4Contrib = tempMemTechSkills[3] * tempProjTechSkills[3];

				double memberContrib = skill1Contrib + skill2Contrib + skill3Contrib + skill4Contrib;

				techSkillFitScore += memberContrib;
			}
			equationScores[0] = techSkillFitScore;
		}

		// Equation 2: Sum of the cumulative position of choices for each
		// project and total for the iteration
		// MAKE SURE TO RESET TEMPNUMCHOICE AFTER EACH ITERATION
		for (int i = 0; i < PriorityQue.memberPriorityOrder.size(); ++i) {
			int tempNumChoice = PriorityQue.memberPriorityOrder.get(i).getTempChoiceNum();
			totalProjChoiceScore += tempNumChoice;
		}
		equationScores[1] = totalProjChoiceScore;

		// Equation 3: Total number of people not put on a project in this
		// iteration
		for (int i = 0; i < PriorityQue.memberPriorityOrder.size(); ++i) {
			if (PriorityQue.memberPriorityOrder.get(i).getTempCurrentProject() == -1) {
				totalNotSelected += 1;
			}
		}
		equationScores[2] = totalNotSelected;
		
		//Equation 4: How well a team adheres to the "ideal" personality traits
		double hey = 0.0;
		return hey;
	}

	// NOT DONE
	// ADD: Different simulations to be run (Could put in sorter)
	public void modifyIterationConditions() {
		// a) Assigns in order of first choice, second, etc...
		// b) Gets rid of project with least first choice interest
		// c) Adds one of the invalid projects back in (Add one with highest
		// total interest among all 3 choices)
		// d) Change max number per team on popular projects
		// e) Lower interest to make a project valid (Throw out projects with
		// only 1 interested)
	}

	// NOT DONE
	public void addToIterationList() {

	}

	// Populates member array with null members before assignment
	public void populateWithNullMembers() {
		int numProjects = Sorter.allProjects.size();
		for (int i = 0; i < numProjects * maxMembers; ++i) {
			membersOnProject.add(new Member());
		}
	}

	// NOT DONE
	// ADD: Populate validated member pitchers
	public void populateContinuingProjects(Iteration currentIteration) {
		Member.resetTempCurrentProject();
		for (int j = 0; j < PriorityQue.projectPriorityOrder.size(); ++j) {
			for (int i = 0; i < PriorityQue.memberPriorityOrder.size(); ++i) {
				// If a member is on a current project, then finds its match and
				// assigns it to that team
				int projectNumber = PriorityQue.projectPriorityOrder.get(j).getProjectNumber();
				if (projectNumber == PriorityQue.memberPriorityOrder.get(i).getCurrentProject()) {
					// If continuing their project, then runs addMember method
					// System.out.print("Current Project: ");
					// System.out.println(projectNumber + ", " +
					// PriorityQue.memberPriorityOrder.get(i).getMemberName());
					currentIteration.addMember(projectNumber, PriorityQue.memberPriorityOrder.get(i), currentIteration);
				}
			}
		}
		// currentIteration.printIteration();
	}

	// What about if a project was thrown out but has members on it (probably
	// just prints out at end what happened) (Add a message for each member and
	// prints to spreadsheet)
	public static void initializeViableProjects() {
		for (int j = 0; j < Sorter.allProjects.size(); ++j) {
			int numInterested = 0;
			for (int i = 0; i < Sorter.allMembers.size(); ++i) {

				// Counts if a member on the continuing project already
				if (Sorter.allProjects.get(j).getProjectNumber() == Sorter.allMembers.get(i).getCurrentProject()) {
					numInterested += 1;
				}
				// Only counts if first choice is the same
				if (Sorter.allMembers.get(i).getProjectChoices()[0] == Sorter.allProjects.get(j)) {
					numInterested += 1;
				}
			}

			// Viable project if at least 3 people intently interested in
			// working on the project
			if (numInterested > 2 ) {
				Sorter.allProjects.get(j).setProjectViability();
			}

		}
	}

	public void printIteration() {
		System.out.println("TEAM SELECTION");
		for (int i = 0; i < membersOnProject.size(); ++i) {
			System.out.println(
					membersOnProject.get(i).getTempCurrentProject() + " " + membersOnProject.get(i).getMemberName());
		}
		System.out.println("");
		System.out.println("NOT SELECTED");
		for (int i = 0; i < notSelected.size(); ++i) {
			System.out.println(notSelected.get(i).getMemberName());
		}

	}
}
