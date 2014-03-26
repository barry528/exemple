package fr.upmc.simulator.interfaces;

import fr.upmc.components.interfaces.RequiredI;
import fr.upmc.simulator.tasks.AbstractTask;

public interface EndTaskConsummerI extends RequiredI {
	public void notifyEndTask(AbstractTask task);

}
