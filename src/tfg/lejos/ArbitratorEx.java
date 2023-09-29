package tfg.lejos;

import lejos.nxt.Button;
import lejos.subsumption.Behavior;

/**
 * Arbitrador, responsável pelo gerenciamento de qual comportamento <BR>
 * deve assumir o controle.
 * 
 * @author crawford (LeJOS Forum)
*/

public class ArbitratorEx {
	   public static int NONE = -1;
	   
	   private int currentBehaviorIndex = ArbitratorEx.NONE;
	   private Behavior[] behaviors;
	   
	   public ArbitratorEx(Behavior[] behaviors) {
	      super();
	      this.behaviors = behaviors;
	   }
	   
	   public Behavior[] getBehaviors() {
	      return behaviors;
	   }

	   public synchronized void setCurrentBehaviorIndex(int currentBehaviorIndex) {
	      this.currentBehaviorIndex = currentBehaviorIndex;
	   }
	   
	   public synchronized int getCurrentBehaviorIndex() {
	      int currentBehaviorIndex = this.currentBehaviorIndex;
	      this.currentBehaviorIndex = ArbitratorEx.NONE;
	      return currentBehaviorIndex;
	   }
	   
	   public void start(Monitor monitor) {
	      Thread th = new Thread(monitor);
	      th.setDaemon(true);
	      th.start();
	      
	      do {
	         int currentBehaviorIndex;
	         
	         do {
	            Thread.yield();
	            currentBehaviorIndex = getCurrentBehaviorIndex();
	         } while (currentBehaviorIndex == ArbitratorEx.NONE);
	         
	         behaviors[currentBehaviorIndex].action();
	         
	      } while (!Button.ESCAPE.isPressed());
	      System.exit(0);
	   }

	}