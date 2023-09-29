package tfg.util;

/**
* Classe est�tica para armazenar o valor da cor preta, para saber se o sensor <BR>
* est� em cima da cor preta.
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

public class Black 
{
	/**
	 * M�ximo inteiro que informa se o sensor de luz est� sobre a cor preta, acima desse valor
	 * o sensor n�o est� em cima da cor preta.
	 */
	public final static int value = 20;

	/**
	 * Construtor privado, garante que a classe n�o ser� instanciada
	 */
	private Black()
	{
		
	}
}
