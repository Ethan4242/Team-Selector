public class Member {

	private String name;
	private int yearInSchool;
	private int major;   /////////////
	
	//0 tinkerer	1 hacker	2 buisness tycoon	3leader
	double[] technicalSkills = new double[4];
	private Project[] choices = new Project[3];
	private int[] tempChoices = new int[3];
	private double pastPerformance;		//How do we rank this
	private int insightExperience;
	private double[] personalityRating = new double[4];		//how do we rank this
	private double priorityRating;
	private boolean joiningMember;
	private int currentProject;
	private boolean nullMember;
	private int tempCurrentProject = -1;
	private int tempChoiceNum; //1 for first choice, 2 for second, 3 for third, and 4 for not selected
	
	//Constructor for a null member
	public Member() {
		nullMember  = true;
		name = "Open";
	}
	
	public Member(String name, int yearInSchool, int major, int returningMember) {
		this.name = name;
		this.yearInSchool = yearInSchool;
		this.major = major;
		nullMember = false;
		
		if (returningMember == 1) {
			joiningMember = true;
		}
	}
	
	//setting technical skills, what do you think of these 4 as the base?
	public void setTechnicalSkills(int compSci, int engineer, int business, int communication) {
		double totalSkills = compSci + engineer + business + communication;
		technicalSkills[0] = compSci / totalSkills;
		technicalSkills[1] = engineer / totalSkills;
		technicalSkills[2] = business / totalSkills;
		technicalSkills[3] = communication / totalSkills;
	}
	public double[] getTechnicalSkills() {
		return technicalSkills;
	}
	
	//Sets member project choices
	public void addProjectChoices(int choice1, int choice2, int choice3) {
		this.tempChoices[0] = choice1;
		this.tempChoices[1] = choice2;
		this.tempChoices[2] = choice3;
		convertIntToProjectType(tempChoices);
	}
	
	//Converts the int to the corresponding project
	public void convertIntToProjectType(int [] intChoices) {
		for (int i = 0; i < Sorter.allProjects.size(); ++i) {
			if (intChoices[0] == Sorter.allProjects.get(i).getProjectNumber()) {
				choices[0] = Sorter.allProjects.get(i);
			}
			if (intChoices[1] == Sorter.allProjects.get(i).getProjectNumber()) {
				choices[1] = Sorter.allProjects.get(i);
			}
			if (intChoices[2] == Sorter.allProjects.get(i).getProjectNumber()) {
				choices[2] = Sorter.allProjects.get(i);
			}
		}

	}
	
	public int[] getProjectIntChoices() {
		return tempChoices;
	}
	public Project[] getProjectChoices() {
		return this.choices;
	}
	
	//Sets past performance calculated as percent success
	//numPrototypes is number of successful, working-ish prototypes
	public void setPastPerformance(int numPastSemesters, int numPrototypes) {
		if (numPrototypes != 0) {
		double pastPerf = (double)numPastSemesters / numPrototypes;
		pastPerformance = pastPerf;
		insightExperience = numPastSemesters;
		}
		
		else {
			pastPerformance = 0;
			insightExperience = numPastSemesters;
		}
	}
	public double getPastPerformance() {
		return pastPerformance;
	}
	public double getInsightExperience() {
		return this.insightExperience;
	}
	public String getMemberName() {
		return name;
	}
	
	//In future can make program more versitile by letting user pick the skills to be rated
	public void setPersonalityRatings(int leadership, int workinginTeams, int organizationSkills, int initiative){
		double totalSkills = leadership + workinginTeams + organizationSkills + initiative;
		personalityRating[0] = leadership / totalSkills;
		personalityRating[1] = workinginTeams / totalSkills;
		personalityRating[2] = organizationSkills / totalSkills;
		personalityRating[3] = initiative / totalSkills;
	}
	public double[] getPersonalityRatings() {
		return this.personalityRating;
	}
	
	//Priority based on cumulative attendence and unexcused absences LAST SEMESTER penalize you big time
	//attendence is cumulative from ALL past semesters (so if in Insight longer, you get priority)
	public void setPriorityRating(int attendence, int unexcused) {
		this.priorityRating = ((double)attendence * insightExperience) / unexcused;
	}
	public double getPriorityRating() {
		return this.priorityRating;
	}
	
	//Sets the current project a team needs to be assigned to
	public void setCurrentProject(int currentProject) {
		this.currentProject = currentProject;
	}
	public int getCurrentProject() {
		return currentProject;
	}
	
	public boolean getIfNull() {
		return nullMember;
	}
	
	public void setTempCurrentProject(int projectNumber) {
		tempCurrentProject = projectNumber;
	}
	public int getTempCurrentProject() {
		return tempCurrentProject;
	}
	public static void resetTempCurrentProject() {
		for (int i = 0; i < Sorter.allMembers.size(); ++i) {
			Sorter.allMembers.get(i).setTempCurrentProject(-1);
		}
	}
	
	public void setTempChoiceNum(int choiceNum) {
		tempChoiceNum = choiceNum;
	}
	public int getTempChoiceNum() {
		return tempChoiceNum;
	}
}
