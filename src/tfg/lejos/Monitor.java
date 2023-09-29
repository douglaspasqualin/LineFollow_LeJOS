package tfg.lejos;

import lejos.nxt.Button;
import lejos.subsumption.Behavior;

/**
 * Monitor para implementação da Arquitetura Subsumption
 * 
 * @author crawford (LeJOS Forum)
*/


public class Monitor implements Runnable {
	   private Behavior[] behaviors;
	   private ArbitratorEx executor;
	   
	   public Monitor(Behavior[] behaviors, ArbitratorEx executor) {
	      super();
	      this.behaviors = behaviors;
	      this.executor = executor;
	   }

	   public void run() {
	      int current = ArbitratorEx.NONE;
	      int previous = ArbitratorEx.NONE;
	      
	      while (!Button.ESCAPE.isPressed()) {
	         current = ArbitratorEx.NONE;
	                  
	         for (int i = 0; i < behaviors.length; i++) {
	            if (behaviors[i].takeControl()) {
	               current = i;
	               break;
	            }
	         }
	         
	         if (current != ArbitratorEx.NONE) {
	            if (previous > current) {
	               behaviors[previous].suppress();
	            }
	            executor.setCurrentBehaviorIndex(current);
	            previous = current;
	         }
	         
	         Thread.yield();
	      } // while loop  
	      System.exit(0);
	   }   
	   
	} 
