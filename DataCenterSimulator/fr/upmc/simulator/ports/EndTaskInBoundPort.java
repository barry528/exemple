package fr.upmc.simulator.ports;

import fr.upmc.components.ComponentI;
import fr.upmc.components.ComponentI.ComponentService;
import fr.upmc.components.ports.AbstractInboundPort;
import fr.upmc.simulator.components.AdmissionController;
import fr.upmc.simulator.components.Computer;
import fr.upmc.simulator.computers.devices.Processor;
import fr.upmc.simulator.interfaces.ActuatorProviderI;
import fr.upmc.simulator.interfaces.EndTaskProviderI;
import fr.upmc.simulator.tasks.AbstractTask;

public class EndTaskInBoundPort extends AbstractInboundPort implements EndTaskProviderI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EndTaskInBoundPort(String uri, ComponentI owner)
			throws Exception {
		super(uri,EndTaskProviderI.class, owner);
		assert	uri != null && owner instanceof AdmissionController ;
	}

	@Override
	public void notifyEndTask(AbstractTask task) {
		// TODO Auto-generated method stub
		final AdmissionController ac=(AdmissionController)this.owner;
		final AbstractTask t=task;
		
		try {
			ac.handleRequest(new ComponentService<String>() {
				@Override
				public String call() throws Exception {
					ac.notifyEndTask(t);
					return "OK";
				}
			}) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}

}
