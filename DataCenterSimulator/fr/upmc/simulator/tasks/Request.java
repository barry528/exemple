package fr.upmc.simulator.tasks;

/**
 * The class Request represents a request handled by interactives tasks
 * @author mamads
 *
 */
public class Request  {
	
	/**
	 * number of instructions of the request
	 */
	private double instructions;
	
	/**
	 * when end execution
	 */
	private long leaveDate;
	
	/**
	 * arrival int the task
	 */
	private long arrivalDate;



	/**
	 * constructor
	 * @param instructions
	 */
	public Request(double instructions){
		super();
		this.instructions=instructions;
	}
	
	public Request(){
		super();
	}
	
	public double getInstructions() {
		return instructions;
	}

	public void setInstructions(double instructions) {
		this.instructions = instructions;
	}
	public long getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(long leaveDate) {
		this.leaveDate = leaveDate;
	}

	public long getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(long arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
}
