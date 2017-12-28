import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * This is the main class for the team selector application. It gets the user
 * prompt and calls methods to read the files and run the simulations.
 *
 * @author Ethan Young and David Seamon
 */
public class TeamSelector {
	static TeamGenerationADT generator = null; //The team generation object
	static ArrayList<Member> allMembers; //A list storing all the member objects
	static ArrayList<Project> allProjects; //A list storing all the projects
	static ArrayList<Member> notSelected; //A list storing the members not selected
	static ArrayList<String> concatList; //A list storing the concatenated generations

	/**
	 * The main method of the Team Selector. This method prompts the user for
	 * any input needed, calls methods to generate the projects and members, and
	 * then calls other methods to generate the assignments.
	 * 
	 * No parameters
	 */
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);

		// creates a reader object1
		Reader reader = new Reader();

		System.out.println("Generate Teams By: Member Preference\n");
		
		System.out.println("USAGE: Create a file of member data and project data. This program will then"
				+ "assign the members to teams based on the factors you specify. \nPlace the data files in the"
				+ "same folder as these java files. Save the files as .txt files with tab delimiters."
				+ "\nNote: The output file will be saved to the same folder as the current java files. Open it with excel.\n");
		
		try {
		generator = new TeamGenerationByMemberPreference();

		// ArrayLists to store all of the Projects and all of the Members
		allProjects = new ArrayList<Project>();
		allMembers = new ArrayList<Member>();
		notSelected = new ArrayList<Member>();
		concatList = new ArrayList<String>();
		
		// Prompt for user input
		System.out.println("Input the project data file name with the .txt included: ");
		String projectFileName = scnr.next();
		
		System.out.println("Input the member data file name with the .txt included: ");
		String memberFileName = scnr.next();
		
		System.out.println("Input the out file name you would like with the .txt included: ");
		String outFileName = scnr.next();
		
		System.out.print("Input the minimum amount of members per project: ");
		int minMem = scnr.nextInt();
		
		System.out.print("Input the maximum amount of members per project: ");
		int maxMem = scnr.nextInt();

		// Gets all of the Projects and the Members from the files
		allProjects = reader.inputProjectInfo(minMem, maxMem, projectFileName);
		allMembers = reader.inputMemberInfo(allProjects, memberFileName);

		calculateProjectPopularity(allMembers);

		sortByProjectScore(allProjects);

		run();

		scnr.close();
		
		boolean reached = false;
		
		// Loops As long as concatList < 1 or min not found
		while (true) {
			System.out.println("_______________________________________");
			// Loops through all projects and eliminates projects that are assigned 
			// less than min members and then resets/reruns the assignment
			for (int i = allProjects.size() - 1; i >= 0; i--) {
				if (allProjects.get(i).getNumMembers() < allProjects.get(i)
						.getminMembers() & allProjects.get(i).getActive()) {
					
					allProjects.get(i).setActive(false);
					Project.resetMembers(allProjects);
					run();
					i = allProjects.size() - 1;
				}
			}
			
			//Adds the successful iteration to a master list
			concatList.add(generator.generateConcatenation());

			//Prints final distribution if min found
			if (!checkMinNotSelected()) {
				generator.printFinalDistribution(
						concatList.get(concatList.size() - 2), outFileName);
				reached = true;
				break;
			}

			//Resets for next iteration if min not found
			for (int i = 0; i < allProjects.size(); i++) {
				allProjects.get(i).setActive(true);
				Project.resetMembers(allProjects);
			}

		}
		
		//Prints final distribution if best was the last change
		if (!reached) {
			generator.printFinalDistribution(
					concatList.get(concatList.size() - 1), outFileName);
		}
		} catch(Exception e) {
			System.out.println("Exception thrown. Check the excel file for correct input. Then get a developer.");
		}
	}
	
	/**
	 * Runs the simulation of assignments by using a priority que to make
	 * project assignments. This calls the generate projects method of the
	 * TeamGeneration.
	 */
	private static void run() {

		// Creates a PriorityQueue and adds all of the Members to it
		PriorityQueue<Member> pq = new PriorityQueue<Member>();
		
		for (int i = 0; i < allMembers.size(); i++) {
			pq.add(allMembers.get(i));
		}

		// generates the teams and prints them
		generator.generateProjects(pq, allProjects);

	}

	/**
	 * Sorts the allProjects list by popularity. Putting the most popular project at index
	 * 0.
	 * 
	 * @param: allProjects: Unsorted project list
	 */
	private static void sortByProjectScore(ArrayList<Project> allProjects) {
		Project temp = null;
		
		//Switches projects that are in the incorrect order
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

	/**
	 * Calculates the popularity of each project throughout all members. Gives 
	 * a score for the top 3, giving the 1st choice 3 points, 2nd 2 points, and 
	 * 1 point to everything else
	 */
	private static void calculateProjectPopularity(
			ArrayList<Member> allMembers) {
		
		// generates the project scores (how popular they are)
		for (int i = 0; i < allMembers.size(); i++) {
			int scoreAddition = 3;
			
			//Iterates through each of the member's project choices
			for (int j = 0; j < allMembers.get(i).getTeamChoices().size(); j++) {
				Project tempProject = allMembers.get(i).getTeamChoices().get(j);
				tempProject.increaseProjectScore(scoreAddition);
				
				//Decrements the scoreAddition for each iteration
				if (scoreAddition > 1) {
					scoreAddition--;
				}
			}
		}
	}

	/**
	 * Checks if the minimum amount of people not selected on this iteration.
	 * 
	 * @return false if this iteration was larger than the last, meaning that the 
	 * 				program should stop iterating
	 */
	private static boolean checkMinNotSelected() {
		
		//If only one added, keep continuing
		if (concatList.size() <= 1) {
			return true;
		}
		
		//Compares the number not selected of the current and the last (At end of concat)
		int tempLast = Integer.parseInt(concatList.get(concatList.size() - 2)
				.substring(allProjects.size()));
		int tempCurrent = Integer.parseInt(concatList.get(concatList.size() - 1)
				.substring(allProjects.size()));
		
		return (tempLast > tempCurrent);
		
	}


}