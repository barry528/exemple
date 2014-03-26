package fr.upmc.simulator.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



import java.util.concurrent.TimeUnit;

import fr.upmc.components.AbstractComponent;
import fr.upmc.components.assemblies.AbstractAssembly;
import fr.upmc.components.interfaces.DataOfferedI;
import fr.upmc.components.interfaces.DataOfferedI.DataI;
import fr.upmc.components.ports.PortI;
import fr.upmc.simulator.computers.devices.Core;
import fr.upmc.simulator.computers.devices.Processor;
import fr.upmc.simulator.interfaces.ActuatorProviderI;
import fr.upmc.simulator.interfaces.EndTaskConsummerI;
import fr.upmc.simulator.interfaces.SubmissionProviderI;
import fr.upmc.simulator.interfaces.sensors.ComputerData;
import fr.upmc.simulator.interfaces.sensors.ComputerSensorDataOfferedI;
import fr.upmc.simulator.ports.ActuatorInBoundPort;
import fr.upmc.simulator.ports.EndTaskOutBoundPort;
import fr.upmc.simulator.ports.SensorDataInBoundPort;
import fr.upmc.simulator.ports.SubmissionInBoundPort;
import fr.upmc.simulator.tasks.AbstractTask;
import fr.upmc.simulator.tasks.BatchTask;
import fr.upmc.simulator.tasks.InteractiveTask;
import fr.upmc.simulator.tasks.Mygenerator;
import fr.upmc.simulator.tasks.TaskGenerator;

/**
 * This class represents the computer component
 * @author mamads
 *
 */
public class Computer extends AbstractComponent
						implements fr.upmc.simulator.interfaces.sensors.SensorI{

	protected String	uriPrefix ;
	
	private TaskGenerator taskGen;

	private ArrayList<Processor> processors;

	private int[] processorsInUsed;

	private int nbProcessors;
	/**
	 * stack of tasks
	 */
	private Queue<AbstractTask> stack;	

	protected int counter;

	Mygenerator gen;
	
	protected EndTaskOutBoundPort       endTaskOutPort;
	protected SensorDataInBoundPort			sensorInPort ;

	public Computer(String uriPrefix,
			String providerSubmissionURI,
			String providerActuatorURI,
			String eTport,
			String sensorInPort,
			boolean isDistributed,
			int nbprocessor) {

		super(true,true) ;

		this.nbProcessors=nbprocessor;

		this.counter=0;
		processors= new ArrayList<Processor>(nbprocessor);

		processorsInUsed=new int[this.nbProcessors];

		for(int i=0;i<this.nbProcessors;i++){
			processors.add(new Processor(2,this,i));
			processorsInUsed[i]=0;
		}


		System.out.println("Nombre de processeur : "+processors.size());

		this.stack = new LinkedList<AbstractTask>();
		gen=new Mygenerator();
		
		////////////////////////
		this.uriPrefix = uriPrefix ;

		this.endTaskOutPort = new EndTaskOutBoundPort(eTport, this);
		
		// Sensors offered
			this.addOfferedInterface(ComputerSensorDataOfferedI.PullI.class) ;
			this.addRequiredInterface(ComputerSensorDataOfferedI.PushI.class);
				
				
		
		// put the offered interface in the set of interfaces offered by
		// the component.
		this.addOfferedInterface(SubmissionProviderI.class) ;
		this.addOfferedInterface(ActuatorProviderI.class) ;
		///
		
		this.addRequiredInterface(EndTaskConsummerI.class);
		// create the port that exposes the offered interface
		
		PortI subPort;
		PortI act;
		
		
		try {
			//sensors
			this.sensorInPort = new SensorDataInBoundPort(sensorInPort, this) ;
			if (AbstractAssembly.isDistributed) {
				this.sensorInPort.publishPort() ;
			} else {
				this.sensorInPort.localPublishPort() ;
			}
			this.addPort(this.sensorInPort) ;
	
			////
			subPort = new SubmissionInBoundPort(providerSubmissionURI, this);
			act = new ActuatorInBoundPort(providerActuatorURI,this);
			
			
			
			// add the port to the set of ports of the component
			this.addPort(subPort) ;
			this.addPort(act) ;
			
			this.addPort(this.endTaskOutPort);
			
			this.endTaskOutPort.localPublishPort();
			// publish the port
			if (isDistributed) {
				// if distributed, publish on registry
				subPort.publishPort() ;
				act.publishPort() ;
				
			} else {
				// if not distributed, a local publication is sufficient
				subPort.localPublishPort() ;
				act.localPublishPort() ;
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}




	/**
	 * this method check and : if no processor is free return -1 else return processor number
	 * @return
	 */
	public synchronized int getUnsedProcessor(){
		int num=-1;
		boolean trouvee=false;
		int i=0;

		while(!trouvee && i<this.nbProcessors){
			trouvee=(processorsInUsed[i]==0);
			i++;
		}

		if(trouvee){
			num=i-1;
		}


		return num;
	}



	/**
	 * add a task in the stack
	 * @param task
	 */
	public synchronized void pushTask(AbstractTask task){
		try{
			this.stack.add(task);
			System.out.println("tache ajoutée dans la file");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * retrieve a task from the stack
	 */
	public synchronized AbstractTask popTask(){
		System.out.println("One task retreive and size="+this.stackTaskSize());
		return this.stack.poll();
	}

	/**
	 * the stack size
	 * @return
	 */
	public int stackTaskSize(){
		return this.stack.size();
	}


	public Queue<AbstractTask> getStack() {
		return stack;
	}




	public void setStack(Queue<AbstractTask> stack) {
		this.stack = stack;
	}



	@Override
	public void			start() throws Exception
	{
		super.start() ;
//		final Computer uc = this ;
//
//		for(int i=0;i<10;i++) {
//			final AbstractTask task=gen.generate();
//
//			this.run(
//					new ComponentTask() {
//						@Override
//						public void run() {
//							try {
//								uc.arrivalHandler(task);
//
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					);
//		}
	}

	/**
	 * manage tasks arrival and launches the task execution
	 * @param task
	 */
	public void arrivalHandler(AbstractTask task){
		task.setDatedebut(System.currentTimeMillis());
		this.pushTask(task);

		int tmp=this.getUnsedProcessor();

		if(tmp!=-1){

			final Computer cp=this;
			final int num=tmp;

			this.scheduleTask(
					new ComponentTask() {
						@Override
						public void run() {
							try {
								System.out.println("arrivée de tâche");
								cp.beginHandler(num);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					},
					0, TimeUnit.MILLISECONDS) ;
		}

	}

	/**
	 * manage and launches the tasks executions
	 * @param numProcessor
	 */
	public void beginHandler(int numProcessor){

		AbstractTask task=this.popTask();


		this.processorsInUsed[numProcessor]=1;

		int freqMin= this.processors.get(numProcessor).getRange().getMinFreq();


		final Computer cp=this;
		final Processor p=this.processors.get(numProcessor);

		if(task.getType()==0){
			((BatchTask)task).setInstructions((double) (Math.random()*300000));
			//on repartit les instructions sur l'ensemble des coeurs pour simplifier
			long delta=(long) (((BatchTask)task).getInstructions()/freqMin)*p.getNumberCore();
			task.setDatefin(delta);

			final AbstractTask tc = task ;

			this.scheduleTask(
					new ComponentTask() {
						@Override
						public void run() {
							try {

								System.out.println("batch task en éxécution");
								tc.execute(p,cp);

								cp.endHandler(tc, p);


							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					},
					delta, TimeUnit.MILLISECONDS) ;
		}else{
			final AbstractTask tc = task ;
			this.scheduleTask(
					new ComponentTask() {
						@Override
						public void run() {
							try {
								System.out.println("interactive task task en éxécution");
								tc.execute(p,cp);
								boolean endExecution=false;
								while(!endExecution){													

									Thread.sleep(100);
									endExecution=tc.isExecuted();
								}

								cp.endHandler(tc, p);


							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					},
					0, TimeUnit.MILLISECONDS) ;
		}

	}

	/**
	 * manages the frequency changes of processor's cores
	 */
	public void ressourceChangeHandler(Processor processor,AbstractTask task){
		System.out.println("ressources change");

		final AbstractTask tc = task ;
		final Computer cp=this;
		final Processor p=processor;


		this.scheduleTask(
				new ComponentTask() {
					@Override
					public void run() {
						try {

							Date tEcoule=p.getElapsedTime();
							Date tPrevu=p.getExpectedDuration();
							p.setElapsedTime(tEcoule);
							double insRestant=((BatchTask)tc).getInstructions()-p.getOldFreq()*tEcoule.getTime();

							long nouveauTemps=(long) (insRestant/p.getNewFreq());

							//cancel the task execution and begin an other

							cp.endHandler(tc, p);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				},
				0, TimeUnit.MILLISECONDS) ;

	}

	/**
	 * manages the end of execution of the tasks
	 * @param task
	 * @param processor
	 */
	public void endHandler(AbstractTask task,Processor processor){
		System.out.println("enddd handler");

		final int tmp=processor.getNumber();
		final AbstractTask t=task;

		this.processorsInUsed[tmp]=0;

		if(this.stackTaskSize()>0){

			final Computer cp=this;

			this.scheduleTask(
					new ComponentTask() {
						@Override
						public void run() {
							try {
								cp.notifyEndExecution(t);
								cp.beginHandler(tmp);

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					},
					0, TimeUnit.MILLISECONDS) ;
		}


	}

	/**
	 * end the abstrack execution
	 * @param task
	 */
	public void stopTaskHandler(AbstractTask task){
		System.out.println("stop handler");
		if(task.getType()==0){
			Processor p=task.getProcessor();
			this.endHandler(task, p);
		}else{
			((InteractiveTask) task).stop();
		}
	}
	
	/**
	 * notify the end of task execution
	 * @param task
	 */
	public void notifyEndExecution(AbstractTask task){
		
			final AbstractTask t=task;
			
			final Computer comp=this;
			
			this.scheduleTask(new ComponentTask() {
							@Override
							public void run() {
								try {
									comp.endTaskOutPort.notifyEndTask(t);
									
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
			},
			   10, TimeUnit.MILLISECONDS) ;	
		
	}
	
	
	
	
	////////////////////////
	//A faire dans le bouchon
    public void changeFrequency(Processor processor, int frequence){
    	processor.setNewFreq(frequence);
    	AbstractTask t=processor.getTask();
    	
    	if(t!=null){
    		this.ressourceChangeHandler(processor, t);
    	}
    }

	public void addProcessor(AbstractTask task,Processor processor){
		System.out.println("addProcessor 3");
		task.addProcessor(processor);
	}
	

	public void addCore(AbstractTask task,Core core){
		task.addCore(core);
	}
	

	public void substractProcessor(AbstractTask task,Processor processor){
		task.removeProcessor(processor);
	}
	
	public void substractCore(AbstractTask task,Core core){
		task.removeCore(core);
	}

/////////////////////////////
public int getEnergyconsumption(){
	return 10;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AbstractTask> getTasks(){
		return (List<AbstractTask>) this.stack;
		}
	
	
	public List<Processor> getProcessors(){
		
		return this.processors;
		}
	
	
	public List<Core> getCores(){
		Iterator<Processor> iter=this.processors.iterator();
		List<Core> cores=new ArrayList<Core>();
		Processor p;
		
		while(iter.hasNext()){
			p= iter.next();
			cores.addAll(p.getCores());
		}
		
		return cores;
	}
	
	
	public List<Processor> getProcessors(AbstractTask task){
		return task.getProcessors();
		}
	

	public List<Core> getCores(AbstractTask task){//cores in used
		return task.getCores();
		}
	
	
	public int[] getFrequency(Core core){
		int[] res=new int[2];
		res[0]=core.getPlage().getMinFreq();
		res[1]=core.getPlage().getMaxFreq();
		return res;
		}
	

	public int getCurrentFrequency(Core core){
		return core.getFrequncy();
		}




	@Override
	public int produceEnergy() {
		// TODO Auto-generated method stub
		return this.getEnergyconsumption();
	}




	@Override
	public List<AbstractTask> produceTasks() {
		// TODO Auto-generated method stub
		return this.getTasks();
	}




	@Override
	public List<Processor> produceProcessors() {
		// TODO Auto-generated method stub
		return this.getProcessors();
	}




	@Override
	public List<Core> produceCores() {
		// TODO Auto-generated method stub
		return this.getCores();
	}




	@Override
	public List<Processor> produceProcessors(AbstractTask task) {
		// TODO Auto-generated method stub
		return this.getProcessors(task);
	}




	@Override
	public List<Core> produceCores(AbstractTask task) {
		// TODO Auto-generated method stub
		return this.getCores(task);
	}




	@Override
	public int[] produceFrequency(Core core) {
		// TODO Auto-generated method stub
		return this.produceFrequency(core);
	}




	@Override
	public int produceCurrentFrequency(Core core) {
		// TODO Auto-generated method stub
		return this.getCurrentFrequency(core);
	}
	
	public DataI get(){
		
		DataOfferedI.DataI data;
		List<AbstractTask> task=this.getTasks();
		
		List<Processor>  pro=this.getProcessors();
		List<Core> cores=this.getCores();
		int currentEnergy=this.getEnergyconsumption();
		
		 data=new ComputerData(task,pro, cores,currentEnergy);
		
		return data;
	}
}
