
/*
 * Stuff to add later:
 * *Categories as a factor: could make categories a class and then have it hold data like numMentors for each category
 * Major as a factor: could create a class with Major as a data type to assign to each member
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

//THIS class sorts out the team members
public class Sorter {

	// IDK what either of these were for or how to use them
	// private PriorityQue<Project> allProjects; //WILLL NOT BE ARRAY LIST
	// private PriorityQue<Member> allMembers; //I WILL SET UP AN ORGANIZER
	public static ArrayList<Member> allMembers = new ArrayList<Member>();
	public static ArrayList<Project> allProjects = new ArrayList<Project>();

	public static void main(String[] args) throws FileNotFoundException {
		// File open and scanner init for reading spreadsheets
			System.out.print("Opening member information excel file: ");
		String openMemberInfo = "/Users/ethanyoung/Desktop/MemberInformation.txt";
		File memberInfo = new File(openMemberInfo);
		Scanner memberScanner = new Scanner(memberInfo);
			System.out.println("Done");
		
			System.out.print("Opening project information excel file: ");
		String openProjectInfo = "/Users/ethanyoung/Desktop/ProjectInformation.txt";
		File projectInfo = new File(openProjectInfo);
		Scanner projectScanner = new Scanner(projectInfo);
			System.out.println("Done");

			System.out.print("Reading project file and creating project priority que: ");
		// Eats first line of the project spreadsheet
		Reader.eatFirstLine(projectScanner);

		// Creates an array that holds all of the projects and adds to priority
		// order
		for (int i = 0; projectScanner.hasNextLine(); ++i) {
			allProjects.add(Reader.getProjectInfo(projectScanner));
			PriorityQue.addProjectPriorityQue(allProjects.get(i));
		}
			System.out.println("Done");
			
			System.out.print("Reading member project file and creating project priority que: ");
		// Eats the headers of the spreadsheet
		Reader.eatFirstLine(memberScanner);

		// Creates an array that holds all of the members and adds to priority
		// order
		for (int i = 0; memberScanner.hasNextLine(); ++i) {
			allMembers.add(Reader.getMemberInfo(memberScanner));
			PriorityQue.addMemberPriorityQue(allMembers.get(i));
		}
			System.out.println("Done");
			System.out.println("");
			
			System.out.println("Generating potential team distributions based on member and project data: ");
		Iteration.generateIteration();
	}

	// Logic for sorter
	/*
	 * *If continuing project, then it must be selected *EQUATIONS: a) Sum (for
	 * each project) each members skill times the corresponding skill importance
	 * in the project b) Sum choice selection for entire project list c) Number
	 * of people not put on a project d) Sum (for each project) each member's
	 * personality skill times the "ideal" project team score
	 * 
	 * 1) Populates a current project with members on continuing teams
	 * 
	 * 2) Throws out projects that don't have at least 3 number one choices
	 * (Including continuing members)
	 *
	 * 3) Uses ALL projects (excluding those thrown out in the last step) as
	 * viable projects Assign current members to their teams and member projects
	 * to their pitched projects Distributes first choices to all teams based on
	 * priority If team already full, goes to second choices, third, and then a
	 * default list Then calculates the equations Saves list of members that
	 * weren't put on teams Saves equation scores to an array for later use USE
	 * A 2D ARRAY FOR EACH ITERATION, compare each iteration (Would you need a
	 * new class, or can you make a new ArrayList each time in this class?)
	 * 
	 * 4) Throws out project with worst cumulative scores for the equations
	 * Distributes first choices to all teams based on priority If team already
	 * full, goes to second choices, third, and then a default list Then
	 * calculates the equations Saves list of members that weren't put on teams
	 * 
	 * 5) Repeat step 3 until half of all members are without a team 5) Compares
	 * equation scores of each project and determines the optimal bundle 6)
	 * Outputs the number and names of members not selected, asks if you want to
	 * optimally put them on teams (A LATER THING TO DO)
	 * 
	 */
	// Step 1: Populate existing projects
	

	// Step 2: Throws out projects that don't have at least 3 number one choices
	// (Won't be successful)
	

}
