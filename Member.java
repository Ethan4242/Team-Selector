import java.util.ArrayList;

/**
 * This class is used to represent an Insight member and stores all of their data as well as contains
 * algorithms that generate their "priority" to determine who gets added to teams first
 */
public class Member implements Comparable<Member>{
	//all of the data read in from the file
	private String name;
	private ArrayList<String> projectChoicesStrings;
	private Integer year;
	private ArrayList<Integer> skills;
	private Integer pastSemesters;
	private Integer successfulSemesters;
	private Integer attendedMeetings;
	private Integer missedMeetings;
	private Integer timeCommitment;
	private Integer continuingProject;
	private ArrayList<Project> projectChoices;
	private Integer priorityRanking;
	private Boolean day1;
	private Boolean day2;
	
	/**
	 * the constructor for a Member that assigns all of the data from the file
	 * to object variables
	 */
	public Member(ArrayList<String> stringData, ArrayList<Integer> intData, ArrayList<Project> allProjects) {
		projectChoicesStrings = new ArrayList<String>();
		skills = new ArrayList<Integer>();
		projectChoices = new ArrayList<Project>();
		name = stringData.get(0);
		for (int i = 1; i < stringData.size(); i++) {
			projectChoicesStrings.add(projectChoicesStrings.size(), stringData.get(i));
		}
		
		
		year = intData.get(0);
		for (int i = 1; i < 4; i++) {
			skills.add(intData.get(i));
		}
		
		pastSemesters = intData.get(5);
		successfulSemesters = intData.get(6);
		attendedMeetings = intData.get(7);
		missedMeetings = intData.get(8);
		timeCommitment = intData.get(9);
		continuingProject = intData.get(10);
		if (intData.get(11) == 1) {
			day1 = true;
		}
		else {
			day1 = false;
		}
		if (intData.get(12) == 1) {
			day2 = true;
		}
		else {
			day2 = false;
		}
			
		generatePriorityRanking();
		convertTeamChoicesToTeams(allProjects);
	}
	
	/**
	 * Converts all of the names of the Projects (since they're passed in as Strings)
	 * to actual Project objects
	 * @param allProjects
	 */
	private void convertTeamChoicesToTeams(ArrayList<Project> allProjects) {
		for (int i = 0; i < projectChoicesStrings.size(); i++) {
			for (int j = 0; j < allProjects.size(); j++) {
				if (projectChoicesStrings.get(i).equals(allProjects.get(j).getName())) {
					projectChoices.add(allProjects.get(j));
					break;
				}
			}
		}
	}
	
	/**
	 * This is where the priority ranking for a Member is generated, should be changed
	 * once an appropriate algorithm is determined
	 */
	private void generatePriorityRanking() {
		priorityRanking = 0;
		//priorityRanking = (missedMeetings / attendedMeetings)* (successfulSemesters / pastSemesters);
		//priorityRanking = pastSemesters + .5 * (successfulSemesters / pastSemesters) + .5 * (attendedMeetings / (attendedMeetings + missedMeetings));
		priorityRanking = pastSemesters;
		}
	
	
	public Integer getYear() {
		return year;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getTimeCommitment() {
		return timeCommitment;
	}
	
	public Integer getPriorityRanking() {
		return priorityRanking;
	}
	
	public ArrayList<Project> getTeamChoices() {
		return projectChoices;
	}
	
	public ArrayList<Integer> getSkills() {
		return skills;
	}
	
	public boolean getDay1() {
		return day1;
	}
	
	public boolean getDay2() {
		return day2;
	}
	
	public boolean getContinuing() {
		if (continuingProject == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * This is the method the priority queue uses to determine priority
	 */
	@Override
	public int compareTo(Member other) {
		if (this.getPriorityRanking() < other.getPriorityRanking()) {
			return 1;
		}
		else {
			return -1;
		}
	}
}