import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * This is the ADT for the Team Generation algorithm. It has methods
 * that are fundamental to each algorithm for sorting the teams. This 
 * can be used to create additional methods for generating teams.
 * 
 * @author Ethan Young and David Seamon
 */
public interface TeamGenerationADT {
	
	public void generateProjects(PriorityQueue<Member> pq, ArrayList<Project> projects);
	
	public void printProjects();
	
	public String generateConcatenation();
	
	public void printFinalDistribution(String Concat, String fileName);
	
	public void writeTeam(FileWriter writer, Project currentProject) throws IOException;
}