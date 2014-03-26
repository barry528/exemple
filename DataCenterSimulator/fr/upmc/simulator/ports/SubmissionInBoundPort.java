package fr.upmc.simulator.ports;

import fr.upmc.components.ComponentI;
import fr.upmc.components.ComponentI.ComponentService;
import fr.upmc.components.examples.basic_cs.components.URIProvider;
import fr.upmc.components.ports.AbstractInboundPort;
import fr.upmc.simulator.components.Computer;
import fr.upmc.simulator.interfaces.SubmissionProviderI;
import fr.upmc.simulator.tasks.AbstractTask;

public class SubmissionInBoundPort extends AbstractInboundPort 
									implements	SubmissionProviderI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public SubmissionInBoundPort(String uri,ComponentI owner)
			throws Exception {
		super(uri,SubmissionProviderI.class, owner);
		assert	uri != null && owner instanceof Computer ;
	}

	@Override
	public void submitTask(AbstractTask task) {
		// TODO Auto-generated method stub

		final Computer computer=(Computer)this.owner;
		final AbstractTask t=task;
		
		try {
			computer.handleRequest(new ComponentService<String>() {
				@Override
				public String call() throws Exception {
					 computer.arrivalHandler(t); 
					return "OK";
				}
			}) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void stopTask(AbstractTask task) {
		// TODO Auto-generated method stub
		
		final Computer computer=(Computer)this.owner;
		final AbstractTask t=task;
		
		try {
			computer.handleRequest(new ComponentService<String>() {
				@Override
				public String call() throws Exception {
					 computer.stopTaskHandler(t);
					return "OK";
				}
			}) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void notifyEndTask(AbstractTask task) {
		// TODO Auto-generated method stub

	}

}
