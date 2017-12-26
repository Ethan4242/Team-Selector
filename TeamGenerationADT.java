import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

//An ADT is used so that different assignment algos can be used
public interface TeamGenerationADT {
	
	public void generateProjects(PriorityQueue<Member> pq, ArrayList<Project> projects);
	
	public void printProjects();
	
	public String generateConcatenation();

	public int notSelectedDay(ArrayList<Member> notSelected);
	
	public void printFinalDistribution(String Concat);
	
	public void writeTeam(FileWriter writer, Project currentProject) throws IOException;
}