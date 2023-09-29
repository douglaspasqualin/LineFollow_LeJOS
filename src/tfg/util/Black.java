package tfg.util;

/**
* Classe estática para armazenar o valor da cor preta, para saber se o sensor <BR>
* está em cima da cor preta.
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

public class Black 
{
	/**
	 * Máximo inteiro que informa se o sensor de luz está sobre a cor preta, acima desse valor
	 * o sensor não está em cima da cor preta.
	 */
	public final static int value = 20;

	/**
	 * Construtor privado, garante que a classe não será instanciada
	 */
	private Black()
	{
		
	}
}
