package fr.upmc.simulator.tasks;



public class Mygenerator {
	
	
 public Mygenerator(){
	 super();
	
 }
 
 public AbstractTask generate(){
	 
	 AbstractTask res=null;
		 
		 int r=(int) (Math.random()*20);
		 		 
		 if((r % 2)==0){
			 
			//res= new BatchTask((double) (Math.random()*30000),System.currentTimeMillis());
			 //res= new BatchTask();
			 res=new InteractiveTask();
			 
			 }
			 else{
				 
				// res=new InteractiveTask(System.currentTimeMillis()); 
				 res=new InteractiveTask();
			 }
		 return res;
	 }
 }

