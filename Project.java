import java.util.ArrayList;

public class Project {
	//all of the data fields that are read in from the file
	private String projectName;
	private Integer projectPriority;
	private Integer difficulty;
	private Integer cost;
	private Integer minMembers;
	private Integer maxMembers;
	private Integer timeCommitment; //1 is low, 2 is medium, 3 is High
	private ArrayList<Integer> skills;
	private Integer numOpenSpots;
	ArrayList<Member> members;
	private Integer projectScore;
	private boolean active;
	private double totalTime;
	private int day1Count;
	private int day2Count;
	private boolean day1Assigned;
	private boolean day2Assigned;
	private boolean switched;
	
	/**
	 * Inputs all of the  data from the file into Project objects
	 */
	public Project(String projectName, ArrayList<Integer>intData) {
		members = new ArrayList<Member>();
		skills = new ArrayList<Integer>();
		this.projectName = projectName;
		projectPriority = intData.get(0);
		difficulty = intData.get(1);
		cost = intData.get(2);
		minMembers = intData.get(3);
		maxMembers = intData.get(4);
		timeCommitment = intData.get(5);
		skills.add(intData.get(6));
		skills.add(intData.get(7));
		skills.add(intData.get(8));
		numOpenSpots = maxMembers;
		projectScore = 0;
		active = true;
	}
	
	public void addMember(Member currentMember) {
		members.add(currentMember);
		numOpenSpots--;
	}
	
	public boolean isFull() {
		if (numOpenSpots <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setSwitch() {
		switched = true;
	}
	
	public boolean getSwitch() {
		return switched;
	}
	
	public void addDay1Count(int score) {
		day1Count += score;
	}
	
	public void addDay2Count(int score) {
		day2Count += score;
	}
	
	public void assignDay1() {
		day1Assigned = true;
		day2Assigned = false;
	}
	
	public void assignDay2() {
		day1Assigned = false;
		day2Assigned = true;
	}
	
	public boolean getDay1() {
		return day1Assigned;
	}
	
	public boolean getDay2() {
		return day2Assigned;
	}
	
	public int getNumMembers() {
		return maxMembers - numOpenSpots;
	}
	
	public int getminMembers() {
		return minMembers;
	}
	
	public ArrayList<Member> getMembers () {
		return members;
	}
	
	public String getName() {
		return projectName;
	}
	
	public void setActive(boolean value) {
		if (value == false) {
			this.active = false;
		}
		else {
			this.active = true;
		}
	}
	
	public boolean getActive() {
		return this.active;
	}
	
	public Integer getTimeCommitment() {
		return timeCommitment;
	}
	
	public Integer getProjectScore() {
		return projectScore;
	}
	
	public Integer getDay1Score() {
		return day1Count;
	}
	
	public Integer getDay2Score() {
		return day2Count;
	}
	
	public static void resetMembers(ArrayList<Project> allProjects) {
		for (int i = 0; i < allProjects.size(); ++i) {
			allProjects.get(i).members = new ArrayList<Member>();
			allProjects.get(i).numOpenSpots = allProjects.get(i).maxMembers;
		}
	}
	
	//Calculates the average year for a given project
	public Double calculateAverageYear() {
		int totalYear = 0;
		for (int i = 0; i < members.size(); ++i) {
			totalYear =+ members.get(i).getYear();
		}
		
		Double averageYear = (double)totalYear / members.size();
		return averageYear;
	}
	
	public Double calculateAverageRanking() {
		double totalRanking = 0;
		for (int i = 0; i < members.size(); ++i) {
			totalRanking =+ members.get(i).getPriorityRanking();
		}
		
		Double averageYear = (double)totalRanking / members.size();
		return averageYear;
	}
	
	public Double calculateProjectFit(Member potentialMember) {
		double projectScore = 0;
		for (int i = 0; i < potentialMember.getSkills().size(); ++i) {
			projectScore =+ (double)potentialMember.getSkills().get(i) * skills.get(i);
		}
		
		return projectScore;
	}
	
	public Double calculateTimeCommitment() {
		Integer totalTime = 0;
		for (int i = 0; i < members.size(); ++i) {
			totalTime =+ members.get(i).getTimeCommitment();
		}
		return (double)totalTime/this.members.size();
	}
	
	public void increaseProjectScore(int value) {
		this.projectScore += value;
	}
	
	//Calculates the total project score, the average member choice, average year, average time commitment match, average experience, etc..
	public void calculateStatistics() {
		
	}
	public String toString() {
		String toReturn = null;
		toReturn = projectName + ": \n";
		for (int i = 0; i < members.size(); i++) {
			toReturn += members.get(i).getName() + "\n";
		}
		return toReturn + "\n";
				
	}
	
	public boolean dayMatch(Member currentMember) {
		if (this.getDay1() == true && currentMember.getDay1() == true) {
			return true;
		}
		
		else if (this.getDay2() == true && currentMember.getDay2() == true) {
			return true;
		}
		
		else {
			return false;
		}
		
	}
}
