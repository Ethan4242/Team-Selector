import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * This class is the main application of the Team Selector
 *
 */
public class TeamSelector {
	static TeamGenerationADT generator = null;
	static ArrayList<Member> allMembers;
	static ArrayList<Project> allProjects;
	static ArrayList<Member> notSelected;
	static ArrayList<String> concatList;

	/**
	 * The main method of the Team Selector
	 */
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);

		// creates a reader object1
		Reader reader = new Reader();

		System.out.println("Generate Teams By:");
		System.out.println("Member Preference");
		
		generator = new TeamGenerationByMemberPreference();

		// ArrayLists to store all of the Projects and all of the Members
		allProjects = new ArrayList<Project>();
		allMembers = new ArrayList<Member>();
		notSelected = new ArrayList<Member>();
		concatList = new ArrayList<String>();

		// gets all of the Projects and the Members from the files
		allProjects = reader.inputProjectInfo();
		allMembers = reader.inputMemberInfo(allProjects);
		
		calculateProjectPopularity(allMembers);
		
		sortByProjectScore(allProjects);

		run();

		//boolean valid = false;
		boolean reached = false;
		// As long as concatList < 1
		while (true) {
			System.out.println("_______________________________________");
			// Eliminates projects that are assigned less than min members
			// (inactivates then reruns)
			//valid = false;
			//while (!valid) {
				for (int i = allProjects.size() - 1; i >= 0; i--) {
					if (allProjects.get(i).getNumMembers() < allProjects.get(i).getminMembers()
							& allProjects.get(i).getActive()) {
						//System.out.println(allProjects.get(i).getName());
						allProjects.get(i).setActive(false);
						Project.resetMembers(allProjects);
						run();
						i = allProjects.size() - 1;
					}
				}
				//valid = true;
			//}

			concatList.add(generator.generateConcatenation());

			if (!checkMinNotSelected()) {
				generator.printFinalDistribution(concatList.get(concatList.size() - 2));
				reached = true;
				break;
			}

			//generateNewAssignments();

			for (int i = 0; i < allProjects.size(); i++) {
				allProjects.get(i).setActive(true);
				Project.resetMembers(allProjects);
			}

		}

		if (!reached) {
			generator.printFinalDistribution(concatList.get(concatList.size() - 1));
		}
		//permutation("TFTFTFTF");
	}

	private static void run() {

		// creates a PriorityQueue and adds all of the Members to it
		PriorityQueue<Member> pq = new PriorityQueue<Member>();
		//System.out.println("Order");
		for (int i = 0; i < allMembers.size(); i++) {
			pq.add(allMembers.get(i));
		}

		// Determines project day assignments
		// generateDayScores(allMembers, allProjects);

		// generates the teams and prints them
		generator.generateProjects(pq, allProjects);

	}

	private static void generateNewAssignments() {
		Project currProject = allProjects.get(findLowestDifference());

		if (currProject.getDay1()) {
			currProject.assignDay2();
		} else {
			currProject.assignDay1();
		}

		currProject.setSwitch();
	}

	private static Integer findLowestDifference() {
		int tempDiff = 100000000;
		int bestDiff = 100000000;
		int bestProjectIndex = 0;
		for (int i = 0; i < allProjects.size(); ++i) {
			if (!allProjects.get(i).getSwitch()) {
				// Only runs if project is on opposite day of most common day in
				// not selected
				if (generator.notSelectedDay(notSelected) == 2 && allProjects.get(i).getDay1()) {
					tempDiff = Math.abs(allProjects.get(i).getDay1Score() - allProjects.get(i).getDay2Score());
				} else if (generator.notSelectedDay(notSelected) == 1 && allProjects.get(i).getDay2()) {
					tempDiff = Math.abs(allProjects.get(i).getDay1Score() - allProjects.get(i).getDay2Score());
				} else {
					continue;
				}
			}

			else {
				continue;
			}

			if (tempDiff < bestDiff) {
				bestDiff = tempDiff;
				bestProjectIndex = i;
			}

		}
		return bestProjectIndex;
	}

	private static void sortByProjectScore(ArrayList<Project> allProjects) {
		Project temp = null;
		for (int i = 0; i < allProjects.size(); i++) {
			for (int j = 1; j < allProjects.size(); j++) {
				if (allProjects.get(j - 1).getProjectScore() < allProjects.get(j).getProjectScore()) {
					temp = allProjects.get(j - 1);
					allProjects.set(j - 1, allProjects.get(j));
					allProjects.set(j, temp);
				}
			}
		}
	}

	// Generates initial day scores and assigns initial values
	private static void generateDayScores(ArrayList<Member> memberList, ArrayList<Project> projectList) {
		Project temp = null;
		for (int i = 0; i < memberList.size(); i++) {
			for (int j = 0; j < memberList.get(i).getTeamChoices().size(); j++) {
				temp = memberList.get(i).getTeamChoices().get(j);

				if (memberList.get(i).getDay1()) {
					temp.addDay1Count(5 - j);
				}

				if (memberList.get(i).getDay2()) {
					temp.addDay2Count(5 - j);
				}

			}
		}

		// Assigns initial values
		for (int i = 0; i < projectList.size(); ++i) {
			if (projectList.get(i).getDay1Score() < projectList.get(i).getDay2Score()) {
				projectList.get(i).assignDay2();
			}
			// Puts on day 1 to start
			else {
				projectList.get(i).assignDay1();
			}
		}
	}

	// I don't get this method...
	private static void calculateProjectPopularity(ArrayList<Member> allMembers) {
		// generates the project scores (how popular they are)
		// Takes into account days of the week
		for (int i = 0; i < allMembers.size(); i++) {
			int scoreAddition = 3;
			for (int j = 0; j < allMembers.get(i).getTeamChoices().size(); j++) {
				Project tempProject = allMembers.get(i).getTeamChoices().get(j);
				tempProject.increaseProjectScore(scoreAddition);
				if (scoreAddition > 1) {
					scoreAddition--;
				}
			}
		}
	}

	// Returns true if program should continue testing
	private static boolean checkMinNotSelected() {
		if (concatList.size() <= 1) {
			return true;
		}
		int tempLast = Integer.parseInt(concatList.get(concatList.size() - 2).substring(allProjects.size()));
		int tempCurrent = Integer.parseInt(concatList.get(concatList.size() - 1).substring(allProjects.size()));
		if (tempLast > tempCurrent) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void permutation(String str) { 
	    permutation("", str); 
	}

	private static void permutation(String prefix, String str) {
	    int n = str.length();
	    if (n == 0) System.out.println(prefix);
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}

}