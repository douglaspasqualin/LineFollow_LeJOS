package tfg.subsumption;


import lejos.navigation.TachoNavigator;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.subsumption.Behavior;

/**
* Comportamento responsável por dar uma volta de 180 graus, invertendo a direção do robô.
* <BR> <BR>
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
public class HalfTurn implements Behavior 
{
	
	TachoNavigator robo;
	final char ESQUERDA = 'E';
	final char DIREITA = 'D';
	char side;
	
	/**
	 * Comportamento responsável por dar uma volta de 180 graus, invertendo a direção do robô.
	 * Método construtor, deve ser passado um <b>TachoNavigator</b> já instanciado e configurado.
	 * @param tacho objeto do tipo <b>lejos.navigation.TachoNavigator</b> 
	 */	
	public HalfTurn(TachoNavigator tacho)
	{
		this.robo = tacho;
	}

	public void action() 
	{
		LCD.drawString("HalfTurn     ", 1, 2);
		robo.rotate((side == ESQUERDA) ? 180 : -180);
	}

	public void suppress() 
	{
		
	}

	public boolean takeControl() 
	{
		if (Button.LEFT.isPressed())
		{
			side = ESQUERDA;
			return true;
		}
		else if (Button.RIGHT.isPressed())
		{
			side = DIREITA;
			return true;
		} 
		else
			return false;
	}
}
