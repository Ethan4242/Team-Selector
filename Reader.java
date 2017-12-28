import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for reading the input files for both the Members
 * and the Projects and adding them to ArrayLists Note that the files must be
 * saved in the project file outside of the src file
 *
 * @author Ethan Young and David Seamon
 */
public class Reader {

	/**
	 * Finds the .txt file with the Project data and parses it to generate the
	 * projects.
	 * 
	 * File Format:
	 * 
	 * ProjectName Factor1 Factor2 Factor3
	 * 
	 * Project name must be one ward and Factor headers are one ward Factor
	 * values can be decimal, put a 0 if no value. No negatives.
	 * 
	 * @param minMem
	 *            The minimum amount of members allowed per project
	 * @param maxMem
	 *            The maximum amount of members allowed per project
	 * @param fileName:
	 *            The name of the file with the project info
	 * 
	 * @return the list of projects generated from the file
	 */
	public ArrayList<Project> inputProjectInfo(int minMem, int maxMem,
			String fileName) {
		ArrayList<Project> projects = new ArrayList<Project>();
		Scanner projectScanner = null;

		// Finds the file and inputs it to the Scanner
		try {
			String projectFile = fileName;
			File projectInfo = new File(projectFile);
			projectScanner = new Scanner(projectInfo);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Project File Not Found");
		}

		// calls a helper method to generate the Projects
		generateProjectsFromFile(projectScanner, projects, minMem, maxMem);

		return projects;
	}

	/**
	 * Creates the Projects from the input file
	 * 
	 * @param input
	 *            The scanner that accesses the project file
	 * @param projects
	 *            The list of projects for the projects to be added to
	 * @param minMem
	 *            The minimum amount of members allowed per project
	 * @param maxMem
	 *            The maximum amount of members allowed per project
	 */
	private void generateProjectsFromFile(Scanner input,
			ArrayList<Project> projects, int minMem, int maxMem) {
		// ignores the first line of headers
		input.nextLine();

		// runs once per line for as many lines as there are in the .txt file
		while (input.hasNext()) {

			// stores all of the data that's a String into one array
			String projectName = "";
			projectName = input.next();

			// stores all of the data that are integers into one array
			ArrayList<Integer> inData = new ArrayList<Integer>();
			while (input.hasNextInt()) {
				inData.add(inData.size(), input.nextInt());
			}

			// creates a new Project and adds it to the Projects ArrayList
			Project newProject = new Project(projectName, inData, minMem,
					maxMem);
			projects.add(newProject);

			// Checks if it is the end of file or not
			try {
				input.nextLine();
			} catch (Exception e) {

			}

		}
	}

	/**
	 * Finds the .txt file with the Member data and parses it to generate the
	 * Members
	 * 
	 * @param projects
	 *            The list of converted projects
	 * @param fileName:
	 *            The name of the file with the member info
	 * 
	 * @return the list of projects generated from the file
	 */
	public ArrayList<Member> inputMemberInfo(ArrayList<Project> projects,
			String fileName) throws Exception {
		// Creates an ArrayList to store the Members and a Scanner to read the
		// file
		ArrayList<Member> members = new ArrayList<Member>();
		Scanner personScanner = null;

		// Finds the Member file and inputs it into the Scanner
		try {
			String filePath = fileName;
			FileReader personInfo = new FileReader(filePath);
			personScanner = new Scanner(personInfo);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Member File Not Found");
		}

		// calls a helper method to create the Members
		generateMembersFromFile(personScanner, members, projects);
		return members;
	}

	/**
	 * Creates the Members from the member file.
	 * 
	 * File format: memberName ProjectChoice1 ProjectChoice2 ...
	 * ContinuingProject Factor1 Factor2 ...
	 * 
	 * Every header must be one word and every project and member name must be
	 * one word. Factor values can be decimals, no negatives. Put a 0 if no
	 * value
	 * 
	 * @param input:
	 *            The scanner that points to the member file
	 * @param members:
	 *            An arrayList that the members will be added to
	 * @param projects:
	 *            An arrayList holding all of the projects
	 */
	private void generateMembersFromFile(Scanner input,
			ArrayList<Member> members, ArrayList<Project> projects)
			throws Exception {

		String factor;
		ArrayList<String> factorNames = new ArrayList<String>();

		// Gets the column header names and picks through for the factor names
		String header[] = input.nextLine().split("\t");

		for (int i = 0; i < header.length; i++) {
			factor = header[i];

			// If a project or name it is not a factor
			if (factor.trim().equalsIgnoreCase("Name")) {
				continue;
			} else if (factor.contains("Project")
					|| factor.contains("project")) {
				continue;
			} else if (factor.contains("continuing")
					|| factor.contains("Continuing")) {
				continue;
			} else {

				// Adds to the names if a factor
				factorNames.add(factor);
			}

		}

		// Prompts user for weights so can initialize members
		ArrayList<Double> weights = getUserWeights(factorNames);

		// runs once per line for as many lines as there are
		while (input.hasNextLine()) {

			// Stores all of the data that are Strings into a single array
			ArrayList<String> stringData = new ArrayList<String>();
			stringData.add(input.next()); // Gets the name of the Member

			// Gets all of the project choices
			while (!input.hasNextInt()) {
				stringData.add(stringData.size(), input.next());
			}

			// Stores all of the data that are integers into a single array
			ArrayList<Double> inData = new ArrayList<Double>();
			while (input.hasNextInt()) {
				inData.add(inData.size(), input.nextDouble());
			}
			if (input.hasNextLine()) {
				input.nextLine();
			}

			// Creates a new Member and adds it to the Members ArrayList
			Member newMember = new Member(stringData, factorNames, inData,
					projects, weights);
			members.add(newMember);
		}
	}

	/**
	 * Prompts the user for inputs for the priority of each factor in the
	 * priority equation
	 * 
	 * @param factorNames:
	 *            The names of the factors in the member file
	 * 
	 * @return An ArrayList holding all of the weights for each factor at the
	 *         same index as the factorlist
	 */
	private ArrayList<Double> getUserWeights(ArrayList<String> factorNames) {
		Scanner input = new Scanner(System.in);
		ArrayList<Double> weights = new ArrayList<Double>();

		System.out.println(
				"Please input the emphasis you would like to place on each of the factors inputted. Put a positive value and it can be a decimal. If you would like to place no emphasis, put 0.");

		// Prints out all of the factors for the user to look at
		System.out.println("Factors are..");
		for (int i = 0; i < factorNames.size(); i++) {
			System.out.print(factorNames.get(i));

			if (i != factorNames.size() - 1)
				System.out.print(", ");
			else
				System.out.println();
		}

		// Gets weights for each factor
		for (int i = 0; i < factorNames.size(); i++) {
			System.out.print("Weight for " + factorNames.get(i) + ": ");
			weights.add(input.nextDouble());
		}

		input.close();
		System.out.println("All weights entered successfully.");

		return weights;
	}
}