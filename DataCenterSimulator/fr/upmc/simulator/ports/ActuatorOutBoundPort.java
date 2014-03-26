package fr.upmc.simulator.ports;

import fr.upmc.components.ComponentI;
import fr.upmc.components.ports.AbstractOutboundPort;
import fr.upmc.simulator.computers.devices.Core;
import fr.upmc.simulator.computers.devices.Processor;
import fr.upmc.simulator.interfaces.ActuatorConsummerI;
import fr.upmc.simulator.interfaces.SubmissionConsumerI;
import fr.upmc.simulator.tasks.AbstractTask;

public class ActuatorOutBoundPort extends AbstractOutboundPort 
								implements ActuatorConsummerI {

	public ActuatorOutBoundPort(String uri, ComponentI owner) {
		super(uri,ActuatorConsummerI.class, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void changeFrequency(Processor processor, int frequence) {
		// TODO Auto-generated method stub
		((ActuatorConsummerI)this.connector).changeFrequency(processor,frequence) ;

	}

	@Override
	public void addProcessor(AbstractTask task, Processor processor) {
		// TODO Auto-generated method stub
		((ActuatorConsummerI)this.connector).addProcessor(task, processor);
	}

	@Override
	public void addCore(AbstractTask task, Core core) {
		// TODO Auto-generated method stub
		((ActuatorConsummerI)this.connector).addCore(task, core);

	}

	@Override
	public void substractProcessor(AbstractTask task, Processor processor) {
		// TODO Auto-generated method stub
		((ActuatorConsummerI)this.connector).substractProcessor(task, processor);
	}

	@Override
	public void substractCore(AbstractTask task, Core core) {
		// TODO Auto-generated method stub
		((ActuatorConsummerI)this.connector).substractCore(task, core);

	}

}
