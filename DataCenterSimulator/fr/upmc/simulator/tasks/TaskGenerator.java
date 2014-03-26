package fr.upmc.simulator.tasks;

import java.util.Date;

import fr.upmc.simulator.components.Computer;

public class TaskGenerator extends Thread{
	
	Computer computer;
	
 public TaskGenerator(Computer computer){
	 super();
	 
	 this.computer=computer;
 }
 
 public void run(){
	 
	 
	 for(int i=0;i<10;i++){
		 
		 int r=(int) (Math.random()*20);
		 
		 System.out.println("r : "+r);
		 
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 
		 if((r % 2)==0){
			 
			// this.computer.notiyTaskArrival( new BatchTask(10,System.currentTimeMillis()));
			 
			 }
			 else{
				 
				 //this.computer.notiyTaskArrival(new InteractiveTask()); 
			 }
		 }
	 }
 }

