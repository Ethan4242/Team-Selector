import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

//https://stackoverflow.com/questions/4290419/how-to-import-httpclient-to-eclipse


/**
 * This class is responsible for reading the input files for both the Members
 * and the Projects and adding them to ArrayLists
 * Note that the files must be saved in the project file outside of the src file
 *
 */
public class Reader {
	/**
	 * Since the constructor does nothing, maybe we can make this a static
	 * class? I'm not really sure what the advantages/disadvantages are
	 */
	public Reader() {

	}

	/**
	 * Finds the .txt file with the Project data
	 * 
	 * @return
	 */
	public ArrayList<Project> inputProjectInfo() {
		// creates an ArrayList that stores all the Projects and a Scanner to
		// read the
		// .txt files
		ArrayList<Project> projects = new ArrayList<Project>();
		Scanner projectScanner = null;

		// finds the file and inputs it to the Scanner
		try {
			String projectFile = "/Users/ethanyoung/Desktop/SampleProjectData.txt";
			File projectInfo = new File(projectFile);
			projectScanner = new Scanner(projectInfo);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Project File Not Found");
		}

		// calls a helper method to generate the Projects
		generateProjectsFromFile(projectScanner, projects);

		return projects;
	}

	/**
	 * Creates the Projects from the input file
	 */
	private void generateProjectsFromFile(Scanner input, ArrayList<Project> projects) {
		// ignores the first line of headers
		input.nextLine();

		// runs once per line for as many lines as there are in the .txt file
		while (input.hasNext()) {
			
			// stores all of the data that's a String into one array
			String projectName = "";
			projectName = input.next();
			System.out.println(projectName);

			// stores all of the data that are integers into one array
			ArrayList<Integer> intData = new ArrayList<Integer>();
			while (input.hasNextInt()) {
				intData.add(intData.size(), input.nextInt());
			}

			// creates a new Project and adds it to the Projects ArrayList
			Project newProject = new Project(projectName, intData);
			projects.add(newProject);
			try {
				input.nextLine();
			} catch (Exception e){
				
			}
			
		}
	}

	public ArrayList<Member> inputMemberInfo(ArrayList<Project> projects) {
		// Creates an ArrayList to store the Members and a Scanner to read the
		// file
		ArrayList<Member> members = new ArrayList<Member>();
		Scanner personScanner = null;

		// finds the Member file and inputs it into the Scanner
		try {
			String filePath = "/Users/ethanyoung/Desktop/SampleMemberData.txt";
			FileReader personInfo = new FileReader(filePath);
			personScanner = new Scanner(personInfo);
		} catch (FileNotFoundException e) {
			System.out.println("Member File Not Found");
		}

		// calls a helper method to create the Members
		generateMembersFromFile(personScanner, members, projects);
		return members;
	}

	/**
	 * Creates the Members from the member file
	 */
	private void generateMembersFromFile(Scanner input, ArrayList<Member> members, ArrayList<Project> projects) {

		// clears the first line of headers
		input.nextLine();
	
		// runs once per line for as many lines as there are
		while (input.hasNextLine()) {

			// Stores all of the data that are Strings into a single array
			ArrayList<String> stringData = new ArrayList<String>();
			stringData.add(input.next());
			
			while(!input.hasNextInt()) {
				stringData.add(stringData.size(), input.next());
			}

			// Stores all of the data that are integers into a single array
			ArrayList<Integer> intData = new ArrayList<Integer>();
			while(input.hasNextInt()) {
				intData.add(intData.size(), input.nextInt());
			}
			if (input.hasNextLine()) {
				input.nextLine();
			}

			// Creates a new Member and adds it to the Members ArrayList
			Member newMember = new Member(stringData, intData, projects);
			members.add(newMember);
		}
	}
}