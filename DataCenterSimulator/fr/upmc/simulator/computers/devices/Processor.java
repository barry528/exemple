package fr.upmc.simulator.computers.devices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import javax.swing.text.html.HTMLDocument.Iterator;

import fr.upmc.components.ComponentI;
import fr.upmc.components.ComponentI.ComponentTask;
import fr.upmc.simulator.components.Computer;
import fr.upmc.simulator.tasks.AbstractTask;
import fr.upmc.simulator.tasks.BatchTask;
import fr.upmc.simulator.tasks.InteractiveTask;
import fr.upmc.simulator.tasks.Request;
import fr.upmc.simulator.tasks.SubTask;

/**
 * this class represents à processor of Computer component
 * @author mamads
 *
 */
public class Processor {

	private Computer computer;



	/**
	 * number of cores
	 */
	private int NumberCore;

	/**
	 * cores
	 */
	private ArrayList<Core> cores;

	

	private ArrayList<Integer> currentFrequency;
	
	private int newFreq;



	/**
	 *task
	 */
	AbstractTask task=null;





	/**
	 * frequency range
	 */
	private Plage range;

	/**
	 * show if the processor is on used
	 */
	//private boolean onUsed;

	/**
	 * processeur number
	 */
	private int number;

	/**
	 * 
	 */
	private int[] coresInUsed;

	/**
	 * 
	 */
	private boolean ressourcesChanged;

	/**
	 * date d'arrivée
	 */
	private Date taskArrival;


	/**
	 * duree prevu
	 */
	private Date expectedDuration;

	/**
	 * temps restant
	 */
	private Date remainingTime;

	/**
	 * temps écoulé
	 */
	private Date elapsedTime;


	public Processor(int NumberCore,Computer computer,int number){

		this.number=number;
		this.NumberCore=NumberCore;

		cores=new ArrayList<Core>(NumberCore);
		coresInUsed=new int[this.NumberCore];

		currentFrequency=new ArrayList<Integer>(this.NumberCore);

		range=new Plage();

		range.setMaxFreq(1000);
		range.setMinFreq(100);

		this.computer=computer;

		for(int i=0;i<NumberCore;i++){
			cores.add(new Core(range,i,this));
			coresInUsed[i]=0;
		}

		taskArrival=new Date();
		expectedDuration=new Date();
		remainingTime=new Date();
		elapsedTime=new Date();

		

	}

	public AbstractTask getTask() {
		return task;
	}



	public void setTask(AbstractTask task) {
		this.task = task;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}



	public Plage getRange() {
		return range;
	}

	public void setRange(Plage range) {
		this.range = range;
	}


	public int getNumberCore() {
		return NumberCore;
	}


	public void setNumberCore(int numberCore) {
		NumberCore = numberCore;
	}


	public ArrayList<Core> getCores() {
		return cores;
	}


	public void setCores(ArrayList<Core> cores) {
		this.cores = cores;
	}


	public void addCore(Core core){
		this.cores.add(core);
	} 


	public void removeCore(){
		this.cores.remove(getNumberCore()-1);
		this.NumberCore--;
	}

	

	public void coreExecutionEnd(){

	}


	/**
	 * this method check and return an unused core or null
	 * @return
	 */
	public int getUnsedCore(){
		int num=-1;
		boolean trouvee=false;
		int i=0;

		while(!trouvee && i<this.NumberCore){
			trouvee=(coresInUsed[i]==0);
			i++;
		}

		if(trouvee){
			num=i-1;
		}

		return num;

	}

	

	public boolean isRessourcesChanged() {
		return ressourcesChanged;
	}


	public void setRessourcesChanged(boolean ressourcesChanged) {
		this.ressourcesChanged = ressourcesChanged;
	}

	public void manageCores(){

		int freqMin=0;
		int coreMin;
		if(this.cores.size()>=2){
			for(int i=0;i<this.cores.size()-1;i++){
				if(this.coresInUsed[i]==1 && this.coresInUsed[i+1]==1){

				}

			}

		}

		this.setRessourcesChanged(true);
	}

	public Date getTaskArrival() {
		return taskArrival;
	}


	public void setTaskArrival(Date taskArrival) {
		this.taskArrival = taskArrival;
	}


	public Date getExpectedDuration() {
		return expectedDuration;
	}


	public void setExpectedDuration(Date expectedDuration) {
		this.expectedDuration = expectedDuration;
	}


	public Date getRemainingTime() {
		return new Date(this.expectedDuration.getTime()-System.currentTimeMillis());
	}


	public void setRemainingTime(Date remainingTime) {
		this.remainingTime = remainingTime;
	}


	public Date getElapsedTime() {
		return new Date(System.currentTimeMillis()-this.taskArrival.getTime());
	}


	public void setElapsedTime(Date elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public void changeCurrentFrequency(int ind,int freq){
		this.currentFrequency.set(ind, new Integer(freq));
	}

	public void addCurrentFrequency(int freq){
		this.currentFrequency.add(new Integer(freq));
	}

	/**
	 * lance l'écution sur un cour libre
	 * @param dateDebut
	 * @param dateFin
	 * @param nombreInstructions
	 * @param task
	 */
	public void beginExecution(Date dateDebut,Date dateFin,double nombreInstructions,AbstractTask task){
		this.taskArrival=dateDebut;
		this.setTask(task);

		if(task.getType()==0){
			this.expectedDuration=dateFin;

			long delta=dateFin.getTime()-dateDebut.getTime();
			long deltaBis=0;

			for(int i=1;i<cores.size();i++){//on affecte les intructions aux coeurs de manière  optimiste
				deltaBis=(long) (nombreInstructions/(this.range.getMaxFreq()*i));

				if(delta<deltaBis){

					for(int j=0;j<i;j++){

						coresInUsed[j]=1;	
						this.addCurrentFrequency(cores.get(j).getFrequncy());

					}
				}


			}
		}else{
			//System.out.println("interactive task--------");
			((InteractiveTask)task).start();	
		}

	}


	public ArrayList<Integer> getCurrentFrequency() {
		return currentFrequency;
	}



	public void setCurrentFrequency(ArrayList<Integer> currentFrequency) {
		this.currentFrequency = currentFrequency;
	}



	public int[] getCoresInUsed() {
		return coresInUsed;
	}



	public void setCoresInUsed(int[] coresInUsed) {
		this.coresInUsed = coresInUsed;
	}

	public  Core getCore(int index){
		return this.cores.get(index);
	}

	public Computer getComputer() {
		return computer;
	}



	public void setComputer(Computer computer) {
		this.computer = computer;
	}

	public int getOldFreq() {
		return Collections.min(this.currentFrequency);
	}



	public int getNewFreq() {
		return newFreq;
	}



	public void setNewFreq(int newFreq) {
		this.newFreq = newFreq;
	}



}
