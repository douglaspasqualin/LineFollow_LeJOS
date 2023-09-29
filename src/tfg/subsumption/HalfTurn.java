package tfg.subsumption;


import lejos.navigation.TachoNavigator;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.subsumption.Behavior;

/**
* Comportamento respons�vel por dar uma volta de 180 graus, invertendo a dire��o do rob�.
* <BR> <BR>
* ********************************************************************** <BR>
*	<b>UNIFRA - Centro Universit�rio Franciscano</b> <BR>
*	Gradua��o em Sistemas de Informa��o <BR>
*	Trabalho Final de Gradua��o, 1� Sem/2009 <BR>
*	<b>Orientador:</b> Prof. MsC. Guilherme Dhein <BR>
*	<b>Orientando:</b> Douglas Pereira Pasqualin <BR>
*   <BR>
*	<b>COPYLEFT</b> (Todos os direitos de reprodu��o autorizados deste que
*	preservados <BR> o nome da institui��o e dos autores). <BR>
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
	 * Comportamento respons�vel por dar uma volta de 180 graus, invertendo a dire��o do rob�.
	 * M�todo construtor, deve ser passado um <b>TachoNavigator</b> j� instanciado e configurado.
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
