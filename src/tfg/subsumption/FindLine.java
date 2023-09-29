package tfg.subsumption;

import tfg.sensors.VirtualLightSensor;
import tfg.util.*;
import lejos.navigation.SimpleNavigator;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.subsumption.Behavior;

/**
* Comportamento respons�vel por encontrar novamente a linha de cor preta.
* <BR><BR>
* ********************************************************************** <BR>
*	<b>UNIFRA - Centro Universit�rio Franciscano</b> <BR>
*	Gradua��o em Sistemas de Informa��o <BR>
*	Trabalho Final de Gradua��o, 1� Sem/2009 <BR>
*	<b>Orientador:</b> Prof. MSc. Guilherme Dhein <BR>
*	<b>Orientando:</b> Douglas Pereira Pasqualin <BR>
*   <BR>
*	<b>COPYLEFT</b> (Todos os direitos de reprodu��o autorizados deste que
*	preservados <BR> o nome da institui��o e dos autores). <BR>
* **********************************************************************
* 
* @author <a href="mailto:douglas.pasqualin@gmail.com">Douglas Pasqualin</a>
* @version 0.3
*/

public class FindLine implements Behavior 
{
	SimpleNavigator robot;
	VirtualLightSensor sensor;
	Side lastSide;
	final int BLACK = Black.value;
	
	/**
	 * Comportamento respons�vel por encontrar novamente a linha de cor preta.
	 * M�todo construtor, deve ser passado um <b>SimpleNavigator</b> e
	 * um <b>VirtualLightSensor</b> j� instanciados e configurados.
	 * @param navigator objeto do tipo <b>lejos.navigation.SimpleNavigator</b> 
	 * @param sensor objeto do tipo <b>VirtualLightSensor</b> 
	 */
	public FindLine(SimpleNavigator navigator, VirtualLightSensor sensor)
	{
		this.robot = navigator;
		this.sensor = sensor;
		lastSide = Side.LEFT;
	}

	public void action()
	{
		int angleRotate = 0;
		LCD.drawString("FindLine", 1, 2);
		
		while (sensor.getLightPercent() > BLACK)
		{
			LCD.clear();
			angleRotate = (lastSide == Side.LEFT) ? 10 : -10; 
			
			robot.rotate(angleRotate, true);
			if (canStop()) break;
			while (sensor.getLightPercent() > BLACK)
			{
				angleRotate = Math.abs(angleRotate) + 10;
				if ((angleRotate / 10) % 2 == 0)
					angleRotate = (lastSide == Side.LEFT) ? -angleRotate : angleRotate;
				else
					angleRotate = (lastSide == Side.RIGHT) ? -angleRotate : angleRotate;
				robot.rotate(angleRotate, true);
				if (canStop()) break;
				
				/** Angulo = 40 e n�o achou a linha */
				if (Math.abs(angleRotate) == 40) 
				{
					try
					{
						//volta para posi��o de origem
						robot.rotate((angleRotate /2 ) * -1);
						robot.travel(2.8f); //anda meia volta da roda para frente
						
						//Curva bem fechada, roda 180 ou -180 conforme �ltimo caso
						angleRotate = (lastSide == Side.LEFT) ? 180 : -180; ;
						
						//Estima onde o rob� deve estar, caso o 180 seja completado
						float estimateAngle = getEstimateAngle(angleRotate);
						
						robot.rotate(angleRotate, true);
						canStop();
						
						//Verifica se a posi��o atual do rob� � a mesma da estimada
						if (!isPlaceofOrigin(estimateAngle)) break;
						//Se for a mesma, d� um som e tenta para o outro lado
						Sound.beep();

						estimateAngle = getEstimateAngle(-angleRotate);	

						do 
						{
							if ((angleRotate > 0) && (!robot.isMoving())) 
								robot.rotateRight();
							else if ((angleRotate < 0) && (!robot.isMoving())) 
								robot.rotateLeft();
							robot.updatePosition();
						} while (!canContinue(estimateAngle));
						
						if (canStop()) break;

					}
					finally 
					{  
						angleRotate = 0;
					}
				}				
			}
		}
		lastSide = (angleRotate > 0) ? Side.LEFT : Side.RIGHT;
	}


	public void suppress() 
	{
		/** Nunca ser� chamado, � o comportamento de maior prioridade */
	}


	public boolean takeControl() 
	{
		return sensor.getLightPercent() > BLACK;
	}
	
	/**
	 * M�todo que verifica durante um movimento (girar por exemplo), <BR>
	 * se no meio desse movimento ele passou pela linha de cor preta. <BR>
	 * No caso de achar a linha, o rob� para de se mover imediatamente.
	 * @return <b>True</b> se achou a linha, <b>False</b> se o movimento foi completado
	 * e n�o achou a linha.
	 * 
	 */
	private boolean canStop()
	{
		while (robot.isMoving())
		{
			if (sensor.getLightPercent() <= BLACK)
			{
				robot.stop();
				return true;
			}
		}
		return false;
	}

	/**
	 * M�todo que verifica se a dire��o atual do rob� � a mesma conforme �ngulo <BR>
	 * passado por par�metro. A toler�ncia � de 2 graus.
	 * @return <b>True</b> se � a mesma posi��o, <b>False</b> se a posi��o n�o � a mesma da origem
	 * 
	 */
	private boolean isPlaceofOrigin(float estimateAngle)
	{
		robot.updatePosition();
		if ((robot.getAngle() > 0 && estimateAngle > 0) || (robot.getAngle() <= 0 && estimateAngle <= 0))
			return Math.abs(Math.abs(robot.getAngle()) - Math.abs(estimateAngle)) <= 2;
		else
			return false;
	}
	
	/**
	 * M�todo que calculava a posi��o final do rob�, baseado no �ngulo da 
	 * rota��o desejada
	 * @return posi��o final do rob�, caso a rota��o seja completada
	 * 
	 */
	private float getEstimateAngle(int angletoRotate)
	{
		//Guarda posi��o de origem
		float angle = robot.getAngle();
		return normalize(angle + angletoRotate); 
	}
	
	/** M�todo que testa se a posi��o atual do rob� est� perto da estimada. <BR>
	 * A toler�ncia � de 20 graus.
	 * @param estimateAngle �ngulo que se deseja testar
	 * @return <b>True</b> se o rob� pode continuar, <b>False</b> se o �ngulo estimado n�o foi alcan�ado
	 */
	private boolean canContinue(float estimateAngle)
	{
		if ((robot.getAngle() > 0 && estimateAngle > 0) || (robot.getAngle() <= 0 && estimateAngle <= 0))
			return Math.abs(Math.abs(robot.getAngle()) - Math.abs(estimateAngle)) <= 20;
		else
			return false;
	}
	
	/**
	 * returns equivalent angle between -180 and +180
	 * copy from SimpleNavigator class (LeJOS)
	 * @see SimpleNavigator 
	 */
	private float normalize(float angle)
	{
		float a = angle;
		while (a > 180)
		{
			a -= 360;
		}
		while (a < -180)
		{
			a += 360;
		}
		return a;
	}
	
}
