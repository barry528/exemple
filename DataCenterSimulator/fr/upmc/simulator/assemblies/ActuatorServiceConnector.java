package fr.upmc.simulator.assemblies;

import java.rmi.RemoteException;

import fr.upmc.components.connectors.AbstractConnector;
import fr.upmc.simulator.computers.devices.Core;
import fr.upmc.simulator.computers.devices.Processor;
import fr.upmc.simulator.interfaces.ActuatorConsummerI;
import fr.upmc.simulator.interfaces.ActuatorProviderI;

import fr.upmc.simulator.tasks.AbstractTask;

public class ActuatorServiceConnector extends AbstractConnector 
										implements ActuatorConsummerI {

	@Override
	public void changeFrequency(Processor processor, int frequence) {
		// TODO Auto-generated method stub
		try {
			((ActuatorProviderI)this.offering).changeFrequency( processor, frequence) ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void addProcessor(AbstractTask task, Processor processor) {
		// TODO Auto-generated method stub
		System.out.println("addProcessor 1");
		try {
			((ActuatorProviderI)this.offering).addProcessor( task,  processor) ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void addCore(AbstractTask task, Core core) {
		// TODO Auto-generated method stub
		try {
			((ActuatorProviderI)this.offering).addCore( task,  core);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void substractProcessor(AbstractTask task, Processor processor) {
		// TODO Auto-generated method stub
		try {
			((ActuatorProviderI)this.offering).substractProcessor( task,  processor) ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void substractCore(AbstractTask task, Core core) {
		// TODO Auto-generated method stub
		try {
			((ActuatorProviderI)this.offering).substractCore( task,  core);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
