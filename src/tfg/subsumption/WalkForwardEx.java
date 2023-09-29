package tfg.subsumption;

import tfg.sensors.VirtualLightSensor;
import tfg.util.Black;
import lejos.navigation.SimpleNavigator;
import lejos.navigation.TachoPilot;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.subsumption.Behavior;

/**
* Comportamento responsável por andar em linha reta 
* enquanto estiver em cima da cor preta.
* <BR><BR>
* ********************************************************************** <BR>
*	<b>UNIFRA - Centro Universitário Franciscano</b> <BR>
*	Graduação em Sistemas de Informação <BR>
*	Trabalho Final de Graduação, 1º Sem/2009 <BR>
*	<b>Orientador:</b> Prof. MSc. Guilherme Dhein <BR>
*	<b>Orientando:</b> Douglas Pereira Pasqualin <BR>
*   <BR>
*	<b>COPYLEFT</b> (Todos os direitos de reprodução autorizados deste que
*	preservados <BR> o nome da instituição e dos autores). <BR>
* **********************************************************************
*	
* @author <a href="mailto:douglas.pasqualin@gmail.com">Douglas Pasqualin</a>
* @version 0.1
*/

public class WalkForwardEx implements Behavior
{
	SimpleNavigator robot;
	VirtualLightSensor sensor;
	Motor LeftMotor, RightMotor;
	final int BLACK = Black.value;
	
	/**
	 * Comportamento responsável por andar em linha reta 
	 * enquanto estiver em cima da cor preta.  <BR>
	 * Método construtor, deve ser passado um <b>SimpleNavigator</b> e
	 * um <b>VirtualLightSensor</b> já instanciados e configurados.
	 * @param navigator objeto do tipo <b>lejos.navigation.SimpleNavigator</b> 
	 * @param sensor objeto do tipo <b>VirtualLightSensor</b> 
	 */		
	public WalkForwardEx(SimpleNavigator navigator, VirtualLightSensor sensor)
	{
		this.robot = navigator;
		this.sensor = sensor;
		this.LeftMotor = ((TachoPilot)robot.getPilot()).getLeft();
		this.RightMotor = ((TachoPilot)robot.getPilot()).getRight();
	}

	public void action() 
	{
		LCD.clear();
		LCD.drawString("WalkEx   ", 1, 2);
		System.out.println(Integer.toString(RightMotor.getSpeed()) + " ->");		
		System.out.println("<- " + Integer.toString(LeftMotor.getSpeed()));
		if (!robot.isMoving())
			this.forward();
	}

	public void suppress() 
	{
		robot.stop();
	}

	public boolean takeControl() 
	{
		return sensor.getLightPercent() <= BLACK;
	}

	/** Não pode usar o forward do SimpleNavigator / TachoPilot, pois ele força os dois motores <BR>
	 * a ficarem com a mesma velocidade, não é esse objetivo do experimento. <BR>
	 * Implementado um forward local*/	
	private void forward()
	{
		LeftMotor.forward();		
		RightMotor.forward();
	}

}
