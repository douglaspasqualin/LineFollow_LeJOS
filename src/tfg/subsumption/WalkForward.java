package tfg.subsumption;

import tfg.sensors.VirtualLightSensor;
import lejos.navigation.TachoNavigator;
import lejos.nxt.LCD;
import lejos.subsumption.Behavior;

/**
* Comportamento responsável por andar em linha reta 
* enquanto estiver em cima da cor preta.
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

public class WalkForward implements Behavior
{
	final static int ref = 20;
	
	TachoNavigator robo;
	VirtualLightSensor sensor;
	
	/**
	 * Comportamento responsável por andar em linha reta 
	 * enquanto estiver em cima da cor preta.
	 * Método construtor, deve ser passado um <b>TachoNavigator</b> e
	 * um <b>VirtualLightSensor</b> já instanciados e configurados.
	 * @param tacho objeto do tipo <b>lejos.navigation.TachoNavigator</b> 
	 * @param sensor objeto do tipo <b>VirtualLightSensor</b> 
	 */		
	public WalkForward(TachoNavigator tacho, VirtualLightSensor sensor)
	{
		this.robo = tacho;
		this.sensor = sensor;
	}

	public void action() 
	{
		LCD.drawString("Walk     ", 1, 2);
		robo.forward();
	}

	public void suppress() 
	{
		robo.stop();
	}

	public boolean takeControl() 
	{
		return sensor.getLightPercent() <= ref;
	}

}
