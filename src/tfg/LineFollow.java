package tfg;

import tfg.lejos.ArbitratorEx;
import tfg.lejos.Monitor;
import tfg.sensors.VirtualLightSensor;
import tfg.subsumption.*;
import lejos.subsumption.Behavior;
import lejos.navigation.TachoNavigator;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;

/**
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
*	<BR>
* @author <a href="mailto:douglas.pasqualin@gmail.com">Douglas Pasqualin</a>
* @version 0.1
*/

public class LineFollow 
{
	/**
	 * Método utilizado para configurar a velocidade inicial do NXT.
	 * @param speed velocidade padrão ou inicial
	 * @return será retornada a nova velocidade desejada, caso não seja 
	 * configurado outra velocidade, será devolvido o valor padrão.
	 */	
	public static int configuraVelocidade(int speed)
	{
		LCD.drawString("Velocidade: " + speed, 1, 1);
		LCD.refresh();
		int button = -1;
		while (button != Button.ENTER.getId())
		{
			button = Button.waitForPress();
			if (button == Button.LEFT.getId())
			{
				speed -= 20;
				if (speed <=0) speed = 0;
			}
			else if (button == Button.RIGHT.getId())
			{
				speed += 20;
				if (speed >=900) speed = 900;
			}
			LCD.clear();
			LCD.drawString("Velocidade: " + speed, 1, 1);
			LCD.refresh();
		}
		return speed;
	}

	/**
	 * Método utilizado para calibrar os sensores de luz que serão
	 * utilizados pelo sensor virtual. O Sensor deve ser colocado inicialmente
	 * na cor mais clara do trajeto e após na cor mais escura.
	 * @param luz1 objeto do tipo LightSensor previamente instanciado
	 * @param luz2 objeto do tipo LightSensor previamente instanciado
	 */
	public static void calibraSensorLuz(LightSensor luz1, LightSensor luz2)
	{
		LCD.drawString("Calibrar Sensores", 1, 1);
		Sound.beep();
		LCD.drawString("Cor na clara", 1, 2);
		Button.waitForPress();
		luz1.calibrateHigh();
		luz2.calibrateHigh();
		Sound.beep();
		LCD.drawString("Cor escura", 1, 3);
		Button.waitForPress();
		luz1.calibrateLow();
		luz2.calibrateLow();
		Sound.beep();		
	}
	

	public static void main(String[] args) 
	{
		TachoNavigator robo = new TachoNavigator(5.6f, 11.3f, Motor.C, Motor.B);

		int speed = 300;		
		robo.setSpeed(configuraVelocidade(speed));
		LCD.clear();
		
		LightSensor luz1 = new LightSensor(SensorPort.S1);
		LightSensor luz2 = new LightSensor(SensorPort.S2);
		calibraSensorLuz(luz1, luz2);
		
		VirtualLightSensor sensor = new VirtualLightSensor(luz1, luz2);

		Behavior b3 = new WalkForward(robo, sensor);
		Behavior b2 = new HalfTurn(robo); 
		Behavior b1 = new FindLine(robo, sensor); 
		
		LCD.clear();
		LCD.drawString("TFGII - Douglas Pasqualin", 1, 1);
		LCD.refresh();
		
	    Behavior [] bArray = {b1, b2, b3};
	    ArbitratorEx arby = new ArbitratorEx(bArray);
	    Monitor monitor = new Monitor(bArray, arby); 
	    arby.start(monitor);
	}
}
