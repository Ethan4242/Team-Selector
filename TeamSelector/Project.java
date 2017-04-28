import java.util.ArrayList;

public class Project {
	// FIX ME: Need to put member pitchers on their team unless it isn't
	// picked//
	// Qualifiers
	private int difficulty;
	private boolean clientProject;
	private String memberPitched;
	private int importanceOfCompletion; 
	private int numMentors; //Add to project category
	
	private int projectNumber;
	private int[] skillsNeeded = new int[4];
	private double priorityQuantifier;
	private String projectCategory; //Made a class
	private int numOpenSpots;
	private boolean projectViability;
	private static int[] idealPersonalityRatings = new int[4];
	

	// Characteristics
	private String projectName;
	private ArrayList<Member> members = new ArrayList<Member>();

	//TODO: need to update constructors to handle project category2
	// Constructor for new client project
	public Project(String projectName, int numOpenSpots, int projectNumber) {
		this.projectName = projectName;
		members = new ArrayList<Member>();
		this.clientProject = true;
		this.numOpenSpots = numOpenSpots;
		this.projectNumber = projectNumber;
	}

	// Constructor for member pitched project
	public Project(String projectName, String pitcherName, int numOpenSpots, int projectNumber) {
		this.projectName = projectName;
		members = new ArrayList<Member>();
		this.clientProject = false;
		this.memberPitched = pitcherName;
		this.numOpenSpots = numOpenSpots;
		this.projectNumber = projectNumber;
	}
	
	// Static Method for ideal personality ratings of group
	public static void setIdealPersonalityRatings(int leadership, int workinginTeams, int organizationSkills, int initiative) {
		idealPersonalityRatings[0] = leadership;
		idealPersonalityRatings[1] = workinginTeams;
		idealPersonalityRatings[2] = organizationSkills;
		idealPersonalityRatings[3] = initiative;
	}
	public static int[] getIdealPersonalityRating() {
		return idealPersonalityRatings;
	}

	// ACCESSING/SETTING PROJECT DATA FOR EACH PROJECT
	public String getProjectName() {
		return projectName;
	}
	public void setProjectDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getProjectDifficulty() {
		return this.difficulty;
	}

	public boolean getClientProject() {
		return clientProject;
	}

	public String getMemberPitchedName() {
		return memberPitched;
	}
	
	public void setNumOpenSpots(int openSpots) {
		numOpenSpots = openSpots;
	}
	public int getNumSpotsOpen() {
		return numOpenSpots;
	}
	
	public void setProjectImportance(int importance) {
		this.importanceOfCompletion = importance;
	}

	public int getProjectImportance() {
		return this.importanceOfCompletion;
	}
	
	public void setProjectViability() {
		this.projectViability = true;
	}
	public boolean getProjectViability() {
		return projectViability;
	}
	// I want the num mentors constant for each project category constant
	// throughout
	// Do we use enumerations?
	public void setNumMentors(int numMentors) {
		this.numMentors = numMentors;
	}

	public void setMajorsNeeded(int skill1, int skill2, int skill3, int skill4) {
		skillsNeeded[0] = skill1;
		skillsNeeded[1] = skill2;
		skillsNeeded[2] = skill3;
		skillsNeeded[3] = skill4;
	}
	public int[] getSkillsNeeded() {
		return skillsNeeded;
	}
	
	public int getProjectNumber() {
		return projectNumber;
	}
	
	public double quantifyPriority() {
		priorityQuantifier = importanceOfCompletion + (numMentors / 2.0) + (difficulty / 3.0);
		
		return priorityQuantifier;
	}

	//public void addMemberToProject(Member johnDoe) {
		//members.add(johnDoe);
	//}
	
	//public ArrayList<Member> getMembersOnProject() {
	//	return members;
	//}
	
	//Change to iteration class
	public void resetMembersOnProject() {
		for (int i = 0; i < members.size(); ++i) {
			//TODO: //IDK HOW TO MAKE A NULL MEMBER
		}
	}

}


