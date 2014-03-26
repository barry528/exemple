package fr.upmc.simulator.interfaces.sensors;

import java.util.List;

import fr.upmc.simulator.computers.devices.Core;
import fr.upmc.simulator.computers.devices.Processor;
import fr.upmc.simulator.tasks.AbstractTask;


public class ComputerData implements ComputerSensorDataOfferedI.ComputerDataI,
								     AdminContSensorDataRequiredI.ComputerDataI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<AbstractTask> tasks;
	
	List<Processor> processors;
	
	List<Core> cores;
	
	int currentEnergy;
	
	public	ComputerData(List<AbstractTask> tasks,List<Processor> processors,List<Core> cores,int currentEnergy)	{ 
			this.tasks=tasks;
			this.processors=processors;
			this.cores=cores;
			this.currentEnergy = currentEnergy;
			}

	

	@Override
	public ComputerData getComputerData() {
		// TODO Auto-generated method stub
		return this;
	}



	@Override
	public List<AbstractTask> getTasks() {
		// TODO Auto-generated method stub
		return this.tasks;
	}



	@Override
	public List<Processor> getProcessors() {
		// TODO Auto-generated method stub
		return this.processors;
	}



	@Override
	public List<Core> getCores() {
		// TODO Auto-generated method stub
		return this.cores;
	}



	@Override
	public List<Processor> getProcessors(AbstractTask task) {
		// TODO Auto-generated method stub
		return task.getProcessors();
	}



	@Override
	public List<Core> getCores(AbstractTask task) {
		// TODO Auto-generated method stub
		return task.getCores();
	}



	@Override
	public int[] getFrequency(Core core) {
		// TODO Auto-generated method stub
		int[] tab= new int[2];
		tab[0]=core.getPlage().getMinFreq();
		tab[1]=core.getPlage().getMaxFreq();
		return tab;
	}



	@Override
	public int getCurrentEnergy() {
		// TODO Auto-generated method stub
		return this.currentEnergy;
	}

}
