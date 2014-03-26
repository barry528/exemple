package fr.upmc.simulator.tasks;

import java.util.ArrayList;
import java.util.Date;

import fr.upmc.simulator.components.Computer;
import fr.upmc.simulator.computers.devices.Processor;

/**
 * the class BacthTask represents a Batch Task
 * @author mamads
 *
 */
public class BatchTask extends AbstractTask{
	/**
	 * number of instruction of the task
	 */
	private double instructions;


	public BatchTask(double instructions, long datedebut) {

		super(0);
		this.setExecuted(false);
		this.instructions = instructions;
		this.setDatedebut(datedebut);
	}
	
	public BatchTask(){
		super(0);
		this.setExecuted(false);
	}

	@Override
	public int performance() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getInstructions() {
		return instructions;
	}

	public void setInstructions(double instructions) {
		this.instructions = instructions;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	public long tempsRestan(){
		return 10;
	}

	@Override
	public void execute(Processor processor,Computer c) {
		// TODO Auto-generated method stub
		this.setProcessor(processor);

		processor.beginExecution(new Date(this.getDatedebut()),new Date( this.getDatefin()), this.instructions,this);

			this.setExecuted(true);
	}

	/**
	 * Le restant de son execution
	 * @return
	 */
	public long getRemaingTime(){
		long max=0;
		
		Processor p=this.getProcessor();
		int[] t=p.getCoresInUsed();
		ArrayList<Integer> curF=p.getCurrentFrequency();
		
		double ins=this.getInstructions()/t.length;
		
		for(int i=0;i<t.length;i++){
			if(t[i]==1){
				if((long)(ins/curF.get(i))>max)
					max=(long)(ins/curF.get(i));
			}
		}
		
		return max;
		
	}
	
	/**
	 * retourne le facteur d'acceleration de la tache
	 * nombre de coeur alloué sur nombre total de coeur
	 * @return
	 */
	public int getAcceleration(){
		Processor p=this.getProcessor();
		int[] t=p.getCoresInUsed();
		
		return t.length/p.getNumberCore();
		
	}



}
