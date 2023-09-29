package tfg.subsumption;

import tfg.sensors.VirtualLightSensor;
import tfg.util.*;
import lejos.navigation.SimpleNavigator;
import lejos.navigation.TachoPilot;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.subsumption.Behavior;

/**
* Comportamento responsável por encontrar novamente a linha de cor preta e
* compensar a velocidade dos motores.
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
* @version 0.5
*/

public class FindLineEx implements Behavior 
{
	SimpleNavigator robot;
	VirtualLightSensor sensor;
	Motor leftMotor, rightMotor;
	Side lastSide;
	int lastSpeedLeft, lastSpeedRight;
	int auxLeft, auxRight;
	int n;
	int incSpeed;
	final int BLACK = Black.value;
	
	/**
	 * Comportamento responsável por encontrar novamente a linha de cor preta 
	 * e compensar a velocidade dos motores.
	 * Método construtor, deve ser passado um <b>SimpleNavigator</b> e
	 * um <b>VirtualLightSensor</b> já instanciados e configurados.
	 * @param navigator objeto do tipo <b>lejos.navigation.SimpleNavigator</b> 
	 * @param sensor objeto do tipo <b>VirtualLightSensor</b> 
	 */
	public FindLineEx(SimpleNavigator navigator, VirtualLightSensor sensor)
	{
		this.robot = navigator;
		this.sensor = sensor;
		this.leftMotor = ((TachoPilot)robot.getPilot()).getLeft();
		this.rightMotor = ((TachoPilot)robot.getPilot()).getRight();
		this.lastSpeedLeft = leftMotor.getSpeed();
		this.lastSpeedRight = rightMotor.getSpeed();
		this.auxLeft = 0;
		this.auxRight = 0;
		this.incSpeed = 20;
		this.n = 1;
		lastSide = Side.LEFT;
	}

	public void action()
	{
		int angleRotate = 0;
		LCD.drawString("FindLineEx", 1, 2);
		
		while (sensor.getLightPercent() > BLACK)
		{
			LCD.clear();
			angleRotate = (lastSide == Side.LEFT) ? 20 : -20; 
			
			this.rotate(1, false);
			this.rotate(angleRotate, false);
			while (sensor.getLightPercent() > BLACK)
			{
				angleRotate = Math.abs(angleRotate) + 20;
				if ((angleRotate / 20) % 2 == 0)
					angleRotate = (lastSide == Side.LEFT) ? -angleRotate : angleRotate;
				else
					angleRotate = (lastSide == Side.RIGHT) ? -angleRotate : angleRotate;
				this.rotate(angleRotate, false);
			}
		}
		lastSide = (angleRotate > 0) ? Side.LEFT : Side.RIGHT;
		
		compensateMotors();
	}


	public void suppress() 
	{
		/** Nunca será chamado, é o comportamento de maior prioridade */
	}

	public boolean takeControl() 
	{
		return sensor.getLightPercent() > BLACK;
	}

	/** Não pode usar o rotate do SimpleNavigator / TachoPilot, pois ele força os dois motores <BR>
	 * a ficarem com a mesma velocidade, não é esse objetivo do experimento. <BR>
	 * Implementado um rotate local*/		
	private void rotate(int angle, final boolean immediateReturn)
	{
		leftMotor.rotate(-angle, true);		
		rightMotor.rotate(angle, true);
	    if (!immediateReturn) {
	        while (leftMotor.isRotating() || rightMotor.isRotating())
	          Thread.yield();
	      }
	    else
	    	return;
	}
	
	/** Retorna de quanto deve ser incrementada a velocidade do motor. <BR>
	 * A cada chamada da função a velocidade é diminuida, através de uma
	 * progressão geométrica
	 * @param actualIncSpeed último incremento de velocidade aplicado 
	 * @return Incremento da velocidade
	 */
	private int getNewIncSpeedByPG(int actualIncSpeed)
	{
		if (actualIncSpeed <= 2)
			return actualIncSpeed;
		n++;
		return (int) (actualIncSpeed * Math.pow(0.75, (n-1)));
	}
	
	/**
	 * Método responsável por compensar a velocidade dos motores
	 */
	private void compensateMotors()
	{
		if (lastSide == Side.LEFT)
		{
			auxLeft++;
			if (auxRight > 0)
			{
				incSpeed = getNewIncSpeedByPG(incSpeed);
			}
			lastSpeedRight += incSpeed;
			leftMotor.setSpeed(lastSpeedLeft);
			rightMotor.setSpeed(lastSpeedRight);			
		}
		else
		{
			auxRight++;
			if (auxLeft > 0)
			{
				incSpeed = getNewIncSpeedByPG(incSpeed);
			}
			lastSpeedLeft += incSpeed;			
 			rightMotor.setSpeed(lastSpeedRight);
			leftMotor.setSpeed(lastSpeedLeft);
		}
		LCD.clear();
	}
}
