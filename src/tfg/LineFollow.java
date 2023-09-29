package tfg;

import tfg.lejos.*;
import tfg.sensors.VirtualLightSensor;
import tfg.subsumption.*;
import lejos.subsumption.Behavior;
import lejos.navigation.SimpleNavigator;
import lejos.navigation.TachoPilot;
import lejos.nxt.*;

/** Classe que cont�m a main
*<BR><BR>
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
*	<BR>
* @author <a href="mailto:douglas.pasqualin@gmail.com">Douglas Pasqualin</a>
* @version 0.3
*/

public class LineFollow 
{
	/**
	 * M�todo utilizado para configurar a velocidade inicial do NXT.
	 * @param speed velocidade padr�o ou inicial
	 * @return ser� retornada a nova velocidade desejada, caso n�o seja 
	 * configurado outra velocidade, ser� devolvido o valor padr�o.
	 */	
	public static int configSpeed(int speed)
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
	 * M�todo utilizado para calibrar os sensores de luz que ser�o
	 * utilizados pelo sensor virtual. O Sensor deve ser colocado inicialmente
	 * na cor mais clara do trajeto e ap�s na cor mais escura.
	 * @param luz1 objeto do tipo <b>LightSensor</b> previamente instanciado
	 * @param luz2 objeto do tipo <b>LightSensor</b> previamente instanciado
	 */
	public static void calibrateLightSensor(LightSensor luz1, LightSensor luz2)
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
		Behavior b1, b2, b3;
		
		/** Menu Principal */
	    String[] viewPrograms = {"LineFollow", "MotorCompensate", "Exit"};
		TextMenu main = new TextMenu(viewPrograms, 1, "Choose Program");
		
		int selection;
		selection = main.select();
		
		if (selection == -1 || selection == 2)
			System.exit(0);
		LCD.clear();
		
		/** Cria classe principal de navega��o */
		SimpleNavigator robot = new SimpleNavigator(5.6f, 10.9f, Motor.A, Motor.C); //alterado de 11,3 para 10,9
		
		/** Se op��o escolhida no Menu foi "LineFollow" (selection 0) ent�o seta true para regular os motores */
		((TachoPilot) robot.getPilot()).regulateSpeed(selection == 0);

		/** Configura velocidade inicial do rob�
		 *  Para LineFollow inicial � 300, MotorCompensate 200 */
		int speed = (selection == 0) ? 300 : 200;		
		robot.setSpeed(configSpeed(speed));
		LCD.clear();
		
		/** Cria sensores de luz f�sicos, e chama um m�todo para efetuar a calibragem dos mesmos */
		LightSensor light1 = new LightSensor(SensorPort.S1);
		LightSensor light2 = new LightSensor(SensorPort.S2);
		calibrateLightSensor(light1, light2);
		
		/** Cria sensor virtual, respons�vel pela detec��o e toler�ncia de falhas */
		VirtualLightSensor sensor = new VirtualLightSensor(light1, light2);

		if (selection == 0) //"LineFollow" 
		{
			b3 = new WalkForward(robot, sensor);
			b1 = new FindLine(robot, sensor);			
		}
		else //"MotorCompensate"
		{
			b3 = new WalkForwardEx(robot, sensor);
			b1 = new FindLineEx(robot, sensor);			
		}
		b2 = new HalfTurn(robot); 

		LCD.clear();
		LCD.drawString("TFGII - Douglas Pasqualin", 1, 1);
		LCD.refresh();
		
		/** Cria vetor de comportamentos e passa para o arbitrador iniciar o controle de 
		 * qual comportamento deve estar ativo, baseado no takeControl() de cada comportamento
		 */
	    Behavior [] bArray = {b1, b2, b3};
	    ArbitratorEx arby = new ArbitratorEx(bArray);
	    Monitor monitor = new Monitor(bArray, arby); 
	    arby.start(monitor);
	}
}
