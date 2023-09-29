package tfg.subsumption;


import tfg.util.Side;
import lejos.navigation.SimpleNavigator;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.subsumption.Behavior;

/**
* Comportamento responsável por dar uma volta de 180 graus, invertendo a direção do robô.
* <BR> <BR>
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
* @version 0.2
*/
public class HalfTurn implements Behavior, ButtonListener 
{
	SimpleNavigator robot;
	Side side;
	boolean pressed;
	
	/**
	 * Comportamento responsável por dar uma volta de 180 graus, invertendo a direção do robô.
	 * Método construtor, deve ser passado um <b>SimpleNavigator</b> já instanciado e configurado.
	 * @param navigator objeto do tipo <b>lejos.navigation.SimpleNavigator</b> 
	 */	
	public HalfTurn(SimpleNavigator navigator)
	{
		this.robot = navigator;
		pressed = false;
		Button.LEFT.addButtonListener(this);
		Button.RIGHT.addButtonListener(this);		
	}

	public void action() 
	{
		LCD.drawString("HalfTurn     ", 1, 2);
		robot.rotate((side == Side.LEFT) ? 180 : -180);
		pressed = false;		
	}

	public void suppress() 
	{
		/** Não precisa de código, pois quando terminar a rotação o robô já estará parado*/
	}

	public boolean takeControl() 
	{
		return pressed;
	}

	public void buttonPressed(Button b) 
	{
		if (b.getId() == Button.ID_LEFT)
		{
			side = Side.LEFT;
			pressed = true;
		}
		else if (b.getId() == Button.ID_RIGHT)
		{
			side = Side.RIGHT;
			pressed = true;
		} 		
	}
	
	public void buttonReleased(Button b) {}
}
