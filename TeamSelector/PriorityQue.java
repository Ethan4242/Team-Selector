import java.util.ArrayList;

public class PriorityQue<E> {
	static ArrayList<Member> memberPriorityOrder = new ArrayList<Member>();
	static ArrayList<Project> projectPriorityOrder = new ArrayList<Project>();

	public static void addMemberPriorityQue(Member newMember) {
		// If first entry
		if (memberPriorityOrder.size() == 0) {
			memberPriorityOrder.add(newMember);
		}

		else {
			for (int i = 0; i < memberPriorityOrder.size(); ++i) { 
				// Gets member in array list to compare to inputed member
				Member tempMember = memberPriorityOrder.get(i);
				// Gets temp's priority rating
				double tempPriority = tempMember.getPriorityRating();
				double tempExperience = tempMember.getInsightExperience();
				double tempSuccess = tempMember.getPastPerformance();
				double tempCompSci = tempMember.getTechnicalSkills()[0];
				
				// Compares the two priority ratings. If new members rating is
				// higher than temp's, then adds and breaks loop
				if (newMember.getPriorityRating() > tempPriority) {
					memberPriorityOrder.add(i, newMember);
					break;
				}

				// If tied, break with experience in Insight
				else if (newMember.getPriorityRating() == tempPriority) {

					// System.out.print(newMember.getMemberName() + ": ");
					if (newMember.getInsightExperience() > tempExperience) {
						memberPriorityOrder.add(i, newMember);
						// System.out.println("1 ");
						break;
					}

					// If still tied, compare with success rate in Insight
					else if (newMember.getInsightExperience() == tempExperience) {
						if (newMember.getPastPerformance() > tempSuccess) {
							memberPriorityOrder.add(i, newMember);
							break;
						}

						// All else even, pick comp sci ahead (need to keep
						// those around?)
						
						else if (newMember.getPastPerformance() == tempSuccess) {
							if (newMember.getTechnicalSkills()[0] > tempCompSci) {
								memberPriorityOrder.add(i, newMember);
								break;
							}
						}
					}
				}
				
				if (i == memberPriorityOrder.size() - 1) {
					memberPriorityOrder.add(newMember);
					break;
				}
			}
		}

	}

	public static ArrayList<Member> getMemberPriorityQue() {
		return memberPriorityOrder;
	}

	public static void addProjectPriorityQue(Project newProject) {
		// Adds the first entry
		if (projectPriorityOrder.size() == 0) {
			projectPriorityOrder.add(newProject);
		}

		else {
			// Checks each element to see if importance is higher
			for (int i = 0; i < projectPriorityOrder.size(); ++i) {
				Project tempProject = projectPriorityOrder.get(i);
				double tempProjectPriority = tempProject.quantifyPriority();
				int tempProjectDifficulty = tempProject.getProjectDifficulty();

				// If about to check last spot in arrqy, then adds newProject to
				// end of array
				if (i == projectPriorityOrder.size() - 1) {
					projectPriorityOrder.add(newProject);
					break;
				}

				// Quantifies priority in the project class, and if equal, puts
				// highest difficulty first
				if (newProject.quantifyPriority() > tempProjectPriority) {
					projectPriorityOrder.add(i, newProject);
					break;
				}

				else if (newProject.quantifyPriority() == tempProjectPriority) {
					if (newProject.getProjectDifficulty() > tempProjectDifficulty) {
						projectPriorityOrder.add(i, newProject);
						break;
					} else {
						projectPriorityOrder.add(i + 1, newProject);
						break;
					}
				}

			}
		}

	}

	public static ArrayList<Project> getProjectPriorityQue() {
		return projectPriorityOrder;
	}

}
