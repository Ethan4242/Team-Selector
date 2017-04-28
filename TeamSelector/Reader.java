import java.util.Scanner;

//Print error for a given cell in excel by adding a counting function to this program
//This class reads excel files and sets up the enviornment for the sorter to work
public class Reader {
	public static void eatFirstLine(Scanner input) {
		input.nextLine();
	}

	public static Member getMemberInfo(Scanner input) {
		// Gets data from one line in the spreadsheet
		String tempName = input.next(); // How to input names that are two words
										// (Ex: Mary Claire)
		int tempYearInSchool = input.nextInt();
		int tempMajor = input.nextInt();
		int tempReturningMember = input.nextInt();
		int tempCompSci = input.nextInt();
		int tempEngineering = input.nextInt();
		int tempBusiness = input.nextInt();
		int tempComm = input.nextInt();
		int tempFirstChoice = input.nextInt();
		int tempSecondChoice = input.nextInt();
		int tempThirdChoice = input.nextInt();
		int tempPastSem = input.nextInt();
		int tempSuccProto = input.nextInt();
		int tempLead = input.nextInt();
		int tempTeamWork = input.nextInt();
		int tempOrgSkills = input.nextInt();
		int tempInit = input.nextInt();
		int tempCumAttend = input.nextInt();
		int tempUnexcAbs = input.nextInt();
		int tempCurrProject = input.nextInt();
		if (input.hasNextLine()) {
			input.nextLine(); // NEED TO GO INTO TEXT FILE AND DELETE PRECEDING
								// SPACES UNTIL LAST NUMBER
		}

		// Creates new member
		Member newMember = new Member(tempName, tempYearInSchool, tempMajor, tempReturningMember);
		newMember.setTechnicalSkills(tempCompSci, tempEngineering, tempBusiness, tempComm);
		newMember.setPastPerformance(tempPastSem, tempSuccProto);
		newMember.setPriorityRating(tempCumAttend, tempUnexcAbs);
		newMember.setPersonalityRatings(tempLead, tempTeamWork, tempOrgSkills, tempInit);
		newMember.addProjectChoices(tempFirstChoice, tempSecondChoice, tempThirdChoice);
		newMember.setCurrentProject(tempCurrProject);

		return newMember;
	}

	public static Project getProjectInfo(Scanner input) {
		// Gets data from one line in the project spreadsheet
		String projectName = "";
		String memberPitchName = "";
		Project newProject;
		//Category projectCategory;
		String stringProjectCategory;

		int projectNumber = input.nextInt();
		while (!input.hasNextInt()) {
			projectName = projectName + input.next();
		}
		int projectDifficulty = input.nextInt();
		int projectClient = input.nextInt();
		if (projectClient == 0) {
			while (!input.hasNextInt()) {
				memberPitchName = memberPitchName + input.next();
			}
		}
		// TODO: Create an instance that sets a projects category
		// int projectCategory = input.nextInt();
		// FOR TESTING, EATS THE PROJECT CATEGORY NAME
		//while (!input.hasNextInt()) {
			//stringProjectCategory = input.next();
		//}
		int projectImportance = input.nextInt();
		int projectOpenSeats = input.nextInt();
		int projectSkill1 = input.nextInt();
		int projectSkill2 = input.nextInt();
		int projectSkill3 = input.nextInt();
		int projectSkill4 = input.nextInt();


		if (input.hasNextLine()) {
			input.nextLine(); // NEED TO GO INTO TEXT FILE AND DELETE PRECEDING
								// SPACES UNTIL LAST NUMBER
		}

		// Creates new project instance and sets variables
		if (projectClient == 1) {
			// Client project initialization
			newProject = new Project(projectName, projectOpenSeats, projectNumber);
		} else {
			// Member project initialization
			newProject = new Project(projectName, memberPitchName, projectOpenSeats, projectNumber);
		}

		newProject.setProjectDifficulty(projectDifficulty);
		newProject.setProjectImportance(projectImportance);

		return newProject;
	}

}
