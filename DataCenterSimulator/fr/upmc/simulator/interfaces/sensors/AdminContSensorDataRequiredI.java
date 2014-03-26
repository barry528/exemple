package fr.upmc.simulator.interfaces.sensors;

import java.util.List;

import fr.upmc.components.interfaces.DataRequiredI;
import fr.upmc.simulator.computers.devices.Core;
import fr.upmc.simulator.computers.devices.Processor;
import fr.upmc.simulator.tasks.AbstractTask;

public interface AdminContSensorDataRequiredI extends DataRequiredI {
	
	public interface	ComputerDataI extends		DataRequiredI.DataI
	{
		public List<AbstractTask> getTasks();
		
		public List<Processor> getProcessors();
		
		
		public List<Core> getCores();
		
		
		public List<Processor> getProcessors(AbstractTask task);
		

		public List<Core> getCores(AbstractTask task);
		
		
		public int[] getFrequency(Core core);
		

		public int getCurrentEnergy();
	}

	
}
