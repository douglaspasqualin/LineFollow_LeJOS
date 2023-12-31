package tfg.subsumption;

import tfg.sensors.VirtualLightSensor;
import tfg.util.Black;
import lejos.navigation.SimpleNavigator;
import lejos.nxt.LCD;
import lejos.subsumption.Behavior;

/**
* Comportamento respons�vel por andar em linha reta 
* enquanto estiver em cima da cor preta.
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
* @version 0.1
*/

public class WalkForward implements Behavior
{
	SimpleNavigator robot;
	VirtualLightSensor sensor;
	final int BLACK = Black.value;
	
	/**
	 * Comportamento respons�vel por andar em linha reta 
	 * enquanto estiver em cima da cor preta.
	 * M�todo construtor, deve ser passado um <b>SimpleNavigator</b> e
	 * um <b>VirtualLightSensor</b> j� instanciados e configurados.
	 * @param navigator objeto do tipo <b>lejos.navigation.SimpleNavigator</b> 
	 * @param sensor objeto do tipo <b>VirtualLightSensor</b> 
	 */		
	public WalkForward(SimpleNavigator navigator, VirtualLightSensor sensor)
	{
		this.robot = navigator;
		this.sensor = sensor;
	}

	public void action() 
	{
		LCD.drawString("Walk     ", 1, 2);
		robot.forward();
	}

	public void suppress() 
	{
		robot.stop();
	}

	public boolean takeControl() 
	{
		return sensor.getLightPercent() <= BLACK;
	}

}
