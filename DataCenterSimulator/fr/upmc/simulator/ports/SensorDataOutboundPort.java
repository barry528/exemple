package fr.upmc.simulator.ports;

import fr.upmc.components.ComponentI;
import fr.upmc.components.ComponentI.ComponentService;
import fr.upmc.components.interfaces.DataRequiredI.DataI;
import fr.upmc.components.ports.AbstractDataOutboundPort;
import fr.upmc.simulator.components.AdmissionController;
import fr.upmc.simulator.interfaces.sensors.AdminContSensorDataRequiredI;
import fr.upmc.simulator.interfaces.sensors.ComputerData;

public class SensorDataOutboundPort extends AbstractDataOutboundPort 
									implements AdminContSensorDataRequiredI.PullI,
											   AdminContSensorDataRequiredI.PushI
											{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SensorDataOutboundPort(
			String portURI,
			ComponentI owner
			) throws Exception
		{
			super(portURI,
					AdminContSensorDataRequiredI.PullI.class,
					AdminContSensorDataRequiredI.PushI.class,
				  owner) ;
			assert	owner instanceof AdmissionController ;
	}

	public				SensorDataOutboundPort(
			ComponentI owner
			) throws Exception
		{
			super(AdminContSensorDataRequiredI.PullI.class,
					AdminContSensorDataRequiredI.PushI.class,
				  owner) ;
			assert	owner instanceof AdmissionController ;
		}
	


	@Override
	public void receive(DataI d) throws Exception {
		// TODO Auto-generated method stub
		
		final AdmissionController ac = (AdmissionController) this.owner ;
		final ComputerData sd = (ComputerData) d ;
		this.owner.handleRequestAsync(
				new ComponentService<Void>() {
					@Override
					public Void call() throws Exception {
						ac.receive(sd);
						return null;
					}
				}) ;

	}

}
