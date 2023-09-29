package tfg.subsumption;

import tfg.sensors.VirtualLightSensor;
import lejos.navigation.TachoNavigator;
import lejos.nxt.LCD;
import lejos.subsumption.Behavior;

/**
* Comportamento responsável por encontrar novamente a linha de cor preta.
* <BR><BR>
* ********************************************************************** <BR>
*	<b>UNIFRA - Centro Universitário Franciscano</b> <BR>
*	Graduação em Sistemas de Informação <BR>
*	Trabalho Final de Graduação, 1º Sem/2009 <BR>
*	<b>Orientador:</b> Prof. MsC. Guilherme Dhein <BR>
*	<b>Orientando:</b> Douglas Pereira Pasqualin <BR>
*   <BR>
*	<b>COPYLEFT</b> (Todos os direitos de reprodução autorizados deste que
*	preservados <BR> o nome da instituição e dos autores). <BR>
* **********************************************************************
* 
* @author <a href="mailto:douglas.pasqualin@gmail.com">Douglas Pasqualin</a>
* @version 0.1
*/
public class FindLine implements Behavior 
{
	final static int ref = 20;
		
	TachoNavigator robo;
	VirtualLightSensor sensor;
	final char ESQUERDA = 'E';
	final char DIREITA = 'D';
	char lastSide;
	
	/**
	 * Comportamento responsável por encontrar novamente a linha de cor preta.
	 * Método construtor, deve ser passado um <b>TachoNavigator</b> e
	 * um <b>VirtualLightSensor</b> já instanciados e configurados.
	 * @param tacho objeto do tipo <b>lejos.navigation.TachoNavigator</b> 
	 * @param sensor objeto do tipo <b>VirtualLightSensor</b> 
	 */
	public FindLine(TachoNavigator tacho, VirtualLightSensor sensor)
	{
		this.robo = tacho;
		this.sensor = sensor;
		lastSide = ESQUERDA;
	}

	
	public void action()
	{
		int initAng = 0;
		LCD.drawString("FindLine", 1, 2);
		
		while (sensor.getLightPercent() > ref)
		{
			initAng = (lastSide == ESQUERDA) ? 10 : -10; 
			
			robo.rotate(initAng, true);
			if (canStop()) break;
			while (sensor.getLightPercent() > ref)
			{
				initAng = Math.abs(initAng) + 10;
				if ((initAng / 10) % 2 == 0)
					initAng = (lastSide == ESQUERDA) ? -initAng : initAng;
				else
					initAng = (lastSide == DIREITA) ? -initAng : initAng;
				robo.rotate(initAng, true);
				if (canStop()) break;
			}
		}
		lastSide = (initAng > 0) ? ESQUERDA : DIREITA;
	}


	public void suppress() 
	{
		robo.stop();
	}


	public boolean takeControl() 
	{
		return sensor.getLightPercent() > ref;
	}
	
	/**
	 * Método que verifica durante um movimento (girar por exemplo), <BR>
	 * se no meio desse movimento ele passou pela linha de cor preta. <BR>
	 * No caso de achar a linha, o robo para de se mover imediatamente.
	 * @return <b>True</b> se achou a linha, <b>False</b> se o movimento foi completado
	 * e não achou a linha.
	 * 
	 */
	private boolean canStop()
	{
		while (robo.isMoving())
		{
			if (sensor.getLightPercent() <= ref)
			{
				robo.stop();
				return true;
			}
		}
		return false;
	}
	
}
