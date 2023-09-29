package tfg.sensors;


import lejos.nxt.LCD;
import lejos.nxt.LightSensor;

/**
 * Classe respons�vel por gerenciar dois sensores de luz,
 * identificando e tratando poss�veis falhas.<BR>
 * A aplica��o final s� enxerga o sensor virtual (esta classe),
 * ou seja, os dois sensores f�sicos ser�o transparentes para 
 * a aplica��o, que somente se comunica com o virtual.
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
 * @version 0.5
*/

public class VirtualLightSensor 
{
	LightSensor _Sensor1;
	LightSensor _Sensor2;
	private int value1, value2, lastValue1, lastValue2;
	private int qtdeRep1, qtdeRep2;
	private boolean isValid1, isValid2;

	/**
	 * M�todo construtor, deve ser passado por par�metro os sensores j� instanciados. <BR>
	 * Utilizado para passar por par�metro sensores j� "calibrados"
	 * @param Sensor1 Sensor de luz 1 classe <b>lejos.nxt.LightSensor</b>	 
	 * @param Sensor2 Sensor de luz 2 classe <b>lejos.nxt.LightSensor</b>
	 */
	public VirtualLightSensor(LightSensor Sensor1, LightSensor Sensor2)
	{
		_Sensor1 = Sensor1;
		_Sensor2 = Sensor2;
		isValid1 = true;
		isValid2 = true;
		qtdeRep1 = 0;
		qtdeRep2 = 0;
		lastValue1 = -1000;
		lastValue2 = -1000;	
	}

	/**
	 * M�todo respons�vel por retornar a leitura do sensor de luz que estiver ativo. <BR>
	 * @return Retorna valor lido pelo sensor, tenta retornar o valor do primeiro sensor, <BR>
	 *         se o mesmo n�o estiver operante, retorna o do segundo. Se ambos estiverem <BR> 
	 *         inoperantes a execu��o do programa deve ser interrompida.
	 */
	public int getLightPercent()
	{
		readValues();
		
		isValid1 = (value1 >= -30 && value1 <= 130); //&& qtdeRep1 <= 100;
		isValid2 = (value2 >= -30 && value2 <= 130); //&& qtdeRep2 <= 100;
		
		if (!isValid1)
			LCD.drawString("1 falhou", 1, 4);
		else
			LCD.drawString("         ", 1, 4);
		if (!isValid2)
			LCD.drawString("2 falhou", 1, 5); 
		else
			LCD.drawString("         ", 1, 5);

		//Se os dois s�o v�lidos retorna a m�dia das leituras
		if (isValid1 && isValid2)
			return average();
		
		/** Se caiu aqui um dos dois n�o � v�lido ou n�o � para usar a m�dia. 
		   Se o primeiro for v�lido retorna o valor do primeiro */
		if (isValid1)
			return value1;
		/** Se caiu aqui somente o segundo � v�lido ou nenhum dos dois � v�lido
		    se o segundo for v�lido retorna valor do segundo, sen�o -1000 */
		return isValid2 ? value2 : -1000;

	}
	
	/**
	 * M�todo que faz a leitura dos sensores f�sicos e identifica <BR>
	 * se a leitura atual � igual a �ltima leitura. Muitas leituras <BR>
	 * repetidas pode ser indicativo de falha ou defeito no sensor.
	 */
	private void readValues()
	{
		/** procedimentos para o sensor1*/
		value1 = _Sensor1.readValue();
		if (value1 == lastValue1) 
			qtdeRep1++;
		else
			qtdeRep1 = 0;
		lastValue1 = value1;
		
		/** procedimentos para o sensor2*/
		value2 = _Sensor2.readValue();
		if (value2 == lastValue2) 
			qtdeRep2++;
		else
			qtdeRep2 = 0;
		lastValue2 = value2;

	}

	/**
	 * M�todo que retorna a m�dia dos sensores lidos
	 * @return M�dia da leitura dos dois sensores
	 */
	private int average()
	{
		return (value1 + value2) / 2;
	}
}
