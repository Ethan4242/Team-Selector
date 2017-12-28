/**
 * This class is used to represent a factor that the user puts in the
 * spreadsheet for a member or a project. This factor has a name and a value.
 * 
 * Examples: Year, timeCommitment, cost, etc..
 * 
 * @author Ethan Young and David Seamon
 */
public class Factor {
	private String name; // Name of factor
	private Double value; // Value of factor

	public Factor(String name, Double value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * This method gets the name of the factor
	 * 
	 * @return The name of the factor
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method sets the name of the factor
	 * 
	 * @param The
	 *            name of the factor
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method gets the value of the factor
	 * 
	 * @return The value of the factor
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * This method sets the value of the factor
	 * 
	 * @param The
	 *            value of the factor
	 */
	public void setValue(Double value) {
		this.value = value;
	}
}
