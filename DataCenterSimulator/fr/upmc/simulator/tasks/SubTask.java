package fr.upmc.simulator.tasks;


public class SubTask implements SubTaskI {
	/**
	 * number of instruction of the task
	 */
	private double instructions;
	
	/**
	 * deadline for execution
	 */
	private long datefin;
	
	
	/**
	 * ceartion for date
	 */
	private long datedebut;
	
	public long getDatedebut() {
		return datedebut;
	}

	public void setDatedebut(long datedebut) {
		this.datedebut = datedebut;
	}

	public double getInstructions() {
		return instructions;
	}

	public void setInstructions(double instructions) {
		this.instructions = instructions;
	}

	public long getDatefin() {
		return datefin;
	}

	public void setDatefin(long datefin) {
		this.datefin = datefin;
	}


	

	public SubTask(double instructions, long datefin) {
		
		super();
		this.instructions = instructions;
		this.datedebut = System.currentTimeMillis();
		this.datefin=this.datedebut+datefin;
		
			}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
