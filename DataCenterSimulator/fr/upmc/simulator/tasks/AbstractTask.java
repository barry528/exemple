package fr.upmc.simulator.tasks;

import java.util.ArrayList;

import fr.upmc.simulator.components.Computer;
import fr.upmc.simulator.computers.devices.Core;
import fr.upmc.simulator.computers.devices.Processor;
import fr.upmc.simulator.interfaces.TaskI;
/**
 * The class AbstractTask represents the basic information and methods for Task
 * @author mamads
 *
 */
public abstract class AbstractTask implements TaskI, Runnable {

	/**
	 * Processor used by this task
	 */
	private ArrayList<Processor> processors=new ArrayList<Processor>() ;
	


	/**
	 * Cores used by the tasks
	 */
	private ArrayList<Core> cores=new ArrayList<Core>();

	/**
	 * Number of cores
	 */
	private int numberCores;

	/**
	 * type of the task : 0=batch task and 1=interactive task
	 */
	private int type;
	
	
	/**
	 * deadline for execution
	 */
	private long datefin;
	
	/**
	 * ceartion for date
	 */
	private long datedebut;
	

	

	/**
	 * a true si la tache est éxecuté
	 */
	protected boolean isExecuted;


	public ArrayList<Processor> getProcessors() {
		return processors;
	}

	public void setProcessors(ArrayList<Processor> processors) {
		this.processors = processors;
	}

	public AbstractTask(int type){
		this.type=type;
	}
	
	public long getDatefin() {
		return datefin;
	}


	public void setDatefin(long datefin) {
		this.datefin = datefin;
	}


	public long getDatedebut() {
		return datedebut;
	}


	public void setDatedebut(long datedebut) {
		this.datedebut = datedebut;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	/**
	 * performance of the task
	 * @return
	 */
	public abstract int performance();


	public ArrayList<Core> getCores() {
		return cores;
	}

	public void setCores(ArrayList<Core> cores) {
		this.cores = cores;
	}

	public int getNumberCores() {
		return numberCores;
	}

	public void setNumberCores(int numberCores) {
		this.numberCores = numberCores;
	}


	public Processor getProcessor(int index) {
		return processors.get(index);
	}

	public void addProcessor(Processor processor) {
		System.out.println("processor ajouté");
		this.processors.add(processor);
	}
	
	public void removeProcessor(Processor processor){
		this.processors.remove(processor);
	}

	public abstract void  execute(Processor processor,Computer c);

	public  boolean isExecuted(){
		return this.isExecuted;
	}

	public void setExecuted(boolean isExecuted) {
		this.isExecuted = isExecuted;
	}

	public void addCore(Core core){
		System.out.println("core ajouté");
		this.cores.add(core);
	}
	
	public Core getCore(int index){
		return this.cores.get(index);
	}
	
	public void removeCore(Core core){
		this.cores.remove(core);
	}
	
	public Processor getProcessor() {
		return this.processors.get(0);
	}

	public void setProcessor(Processor processor) {
		this.processors.add(0, processor);
	}

}
