package fr.upmc.simulator.ports;

import fr.upmc.components.ComponentI;
import fr.upmc.components.ComponentI.ComponentService;
import fr.upmc.components.interfaces.DataOfferedI;
import fr.upmc.components.interfaces.DataOfferedI.DataI;
import fr.upmc.components.ports.AbstractDataInboundPort;
import fr.upmc.simulator.components.Computer;
import fr.upmc.simulator.interfaces.sensors.ComputerSensorDataOfferedI;

public class SensorDataInBoundPort extends AbstractDataInboundPort 
									implements 	ComputerSensorDataOfferedI.PullI,
												ComputerSensorDataOfferedI.PushI{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SensorDataInBoundPort(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri,
				ComputerSensorDataOfferedI.PullI.class,
				ComputerSensorDataOfferedI.PushI.class,
			  owner) ;
		assert	owner instanceof Computer ;
	}
		

	@Override
	public DataI get() throws Exception {
		// TODO Auto-generated method stub
		
		final Computer fs = (Computer) this.owner ;
		return this.owner.handleRequestSync(
					new ComponentService<DataOfferedI.DataI>() {
						@Override
						public DataOfferedI.DataI call() throws Exception
						{
							return fs.get() ;
						}
					}) ;
	}

}
