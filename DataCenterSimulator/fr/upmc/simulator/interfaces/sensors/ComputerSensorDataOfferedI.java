package fr.upmc.simulator.interfaces.sensors;

import fr.upmc.components.interfaces.DataOfferedI;

public interface ComputerSensorDataOfferedI extends DataOfferedI {
	
	public interface	ComputerDataI extends		DataOfferedI.DataI
	{
		
		public ComputerDataI	getComputerData() ;
	}

}
