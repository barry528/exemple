package fr.upmc.simulator.ports;

import fr.upmc.components.ComponentI;
import fr.upmc.components.ComponentI.ComponentService;
import fr.upmc.components.ports.AbstractInboundPort;
import fr.upmc.simulator.components.Computer;
import fr.upmc.simulator.computers.devices.Core;
import fr.upmc.simulator.computers.devices.Processor;
import fr.upmc.simulator.interfaces.ActuatorProviderI;
import fr.upmc.simulator.interfaces.SubmissionProviderI;
import fr.upmc.simulator.tasks.AbstractTask;

public class ActuatorInBoundPort extends AbstractInboundPort
								 implements ActuatorProviderI {

	public ActuatorInBoundPort(String uri, ComponentI owner)
			throws Exception {
		super(uri,ActuatorProviderI.class, owner);
		assert	uri != null && owner instanceof Computer ;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void changeFrequency(Processor processor, int frequence) {
		// TODO Auto-generated method stub
		
		final Computer computer=(Computer)this.owner;
		final Processor p=processor;
		final int f=frequence;
		
		try {
			computer.handleRequest(new ComponentService<String>() {
				@Override
				public String call() throws Exception {
					computer.changeFrequency(p, f);
					return "OK";
				}
			}) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

	}

	@Override
	public void addProcessor(AbstractTask task, Processor processor) {
		// TODO Auto-generated method stub
		System.out.println("addProcessor 2");
		
		final Computer computer=(Computer)this.owner;
		final AbstractTask t=task;
		final Processor p=processor;
		
		try {
			computer.handleRequest(new ComponentService<String>() {
				@Override
				public String call() throws Exception {
					computer.addProcessor(t, p);
					return "OK";
				}
			}) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

	}

	@Override
	public void addCore(AbstractTask task, Core core) {
		// TODO Auto-generated method stub
		final Computer computer=(Computer)this.owner;
		final AbstractTask t=task;
		final Core c=core;
		
		try {
			computer.handleRequest(new ComponentService<String>() {
				@Override
				public String call() throws Exception {
					computer.addCore(t, c);
					return "OK";
				}
			}) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

	}

	@Override
	public void substractProcessor(AbstractTask task, Processor processor) {
		// TODO Auto-generated method stub
		final Computer computer=(Computer)this.owner;
		final AbstractTask t=task;
		final Processor p=processor;
		
		
		try {
			computer.handleRequest(new ComponentService<String>() {
				@Override
				public String call() throws Exception {
					computer.substractProcessor(t, p);
					return "OK";
				}
			}) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

	}

	@Override
	public void substractCore(AbstractTask task, Core core) {
		// TODO Auto-generated method stub
		final Computer computer=(Computer)this.owner;
		final AbstractTask t=task;
		final Core c=core;
		
		
		try {
			computer.handleRequest(new ComponentService<String>() {
				@Override
				public String call() throws Exception {
					computer.substractCore(t, c);
					
					return "OK";
				}
			}) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

	}

}
