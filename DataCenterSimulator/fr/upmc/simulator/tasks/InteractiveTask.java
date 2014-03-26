package fr.upmc.simulator.tasks;

import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import fr.upmc.components.ComponentI.ComponentTask;
import fr.upmc.simulator.components.Computer;
import fr.upmc.simulator.computers.devices.Core;
import fr.upmc.simulator.computers.devices.Processor;


/**
 * the class InteractiveTask represents a Interactive Task
 * @author mamads
 *
 */
public class InteractiveTask extends AbstractTask {

	/**
	 * stack of requests
	 */
	private Queue<Request> stack;
	/**
	 * Profile arrival of requests
	 */
	private Profil profil;

	/**
	 * gernerate request according proflil
	 */
	private RequestGenerator generator;
	
	private int nombreRequest;
	
	private long tempsAttente;



	public InteractiveTask(long datedebut){
		super(1);
		this.setExecuted(false);

		System.out.println("interactive task généré");
		stack= new LinkedList <Request>();

		this.profil=new Profil(20, 10, 1000,3000);

		generator=new RequestGenerator(this,this.profil);
		this.setDatedebut(datedebut);

	}
	
	public InteractiveTask(){
		super(1);
		this.setExecuted(false);

		stack= new LinkedList <Request>();

		this.profil=new Profil(20, 10, 1000,3000);

		generator=new RequestGenerator(this,this.profil);
		
		this.nombreRequest=0;
		this.tempsAttente=0;
	}

	@Override
	public int performance() {
		// TODO Auto-generated method stub
		
		return (int) (this.tempsAttente/this.nombreRequest);
	}

	/**
	 * add a request in the stack
	 * @param request
	 */
	public synchronized void pushRequest(Request request){
		try{
			this.nombreRequest++;
			this.stack.add(request);
			System.out.println("requete généré");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public synchronized Request popRequest(){
		System.out.println("requete ajouté dans la file");
		return this.stack.poll();
	} 

	/**
	 * retrieve a request from the stack
	 */
	public synchronized void removeRequest(){
		System.out.println("requete enlevée");
		this.stack.poll();
	}

	public Queue <Request> getStack() {
		return stack;
	}

	public void setStack(Deque<Request> stack) {
		this.stack = stack;
	}


	public Profil getProfil() {
		return profil;
	}


	public void setProfil(Profil profil) {
		this.profil = profil;
	}
	/**
	 * stop the task
	 * @return
	 */
	public void stop(){
		this.setExecuted(true);
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(Processor processor,Computer c) {
		// TODO Auto-generated method stub
		this.setProcessor(processor);

		this.getProcessor().beginExecution(new Date(), new Date(), 0, this);

		System.out.println("Exécuté sur le processeur : "+this.getProcessor().getNumber());

	}


	/**
	 * manage request arrival and launches the request execution
	 * @param request
	 */
	public void rarrivalHandler(Request request){

		request.setArrivalDate(System.currentTimeMillis());


		this.pushRequest(request);

		int tmp=this.getProcessor().getUnsedCore();

		if(tmp!=-1){

			final InteractiveTask t=this;
			final int n=tmp;

			this.getProcessor().getComputer().scheduleTask(
					new ComponentTask() {
						@Override
						public void run() {
							try {
								System.out.println("arrivee de requete");
								t.rbeginHandler(n);

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					},
					0, TimeUnit.MILLISECONDS) ;
		}

	}

	/**
	 * manage and launches the requests executions
	 * @param numProcessor
	 */
	public void rbeginHandler(int numCore){

		if(this.stack.size()> 0 ){
			Request request=this.popRequest();
			
			this.tempsAttente+=(System.currentTimeMillis()-request.getArrivalDate());
			
			request.setInstructions(Math.random()*this.getProfil().getInstructions());

			System.out.println("execution d'une requete");

			//coeur en cours d'utilisation
			this.getProcessor().getCoresInUsed()[numCore]=1;
			//stocké la fréquence courante
			this.getProcessor().addCurrentFrequency(this.getProcessor().getCore(numCore).getFrequncy());

			//exécute sur un coeur
			//this.getProcessor().getCore(numCore).execute(request.getInstructions());
			
			System.out.println("f"+this.getProcessor().getCore(numCore).getFrequncy());

			long duree=(long)request.getInstructions()/this.getProcessor().getCore(numCore).getFrequncy();

			request.setLeaveDate(duree);

			final InteractiveTask t=this;
			final Request req=request;
			final Core c=this.getProcessor().getCore(numCore);


			this.getProcessor().getComputer().scheduleTask(
					new ComponentTask() {
						@Override
						public void run() {
							try {

								t.rendHandler(req, c);

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					},
					request.getLeaveDate(), TimeUnit.MILLISECONDS) ;



		}

	}


	/**
	 * manages the end of execution of the tasks
	 * @param task
	 * @param processor
	 */
	public void rendHandler(Request request,Core core){

		final int tmp=core.getNumber();

		//libère le coeur
		this.getProcessor().getCoresInUsed()[tmp]=0;
		//quand pas fin tache
		if(! this.isExecuted()){
			this.rbeginHandler(tmp);

		}else{//sinon
			if(this.stack.size()> 0)
				this.rbeginHandler(tmp);
			else{
				System.out.println("end requeste");
				this.setExecuted(true);
			}
		}

	}

	public void start(){

		final InteractiveTask task=this;
		final RequestGenerator generator=this.generator;
		final long time=(long)this.getProfil().getIntervalle();

		int i=0;
		//ordre de terminaison
		//while(!isExecuted){
		while(i<10){

			this.getProcessor().getComputer().scheduleTask(
					new ComponentTask() {
						@Override
						public void run() {
							try {
								task.rarrivalHandler(generator.generate());


							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					},
					time, TimeUnit.MILLISECONDS) ;

			i++;
		}
		this.setExecuted(true);

	}
	
	


}
