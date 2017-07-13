package br.uefs.ecomp.tempoEmSD.controller.threads;

import java.util.Timer;
import java.util.TimerTask;

import br.uefs.ecomp.tempoEmSD.controller.ControllerRelogio;

/**
 * 
 * @author Alyson Dantas
 *
 */
public class ThreadTempo extends Thread  {

	private ControllerRelogio controller = ControllerRelogio.getInstance();

	/**
	 * Construtor
	 */
	public ThreadTempo(){

	}

	/**
	 * Metodo run
	 */
	@Override
	public void run(){

		Timer timer = null;      
		if (timer == null)   
		{      
			timer = new Timer();//timer realiza o incremento dos segundos

			TimerTask tarefa = new TimerTask() {  
				double cont = 0.0;
				public void run()   
				{      
					cont = controller.getSegundos();
					if(cont > 60){
						cont = 0 + controller.getDrift();
						if(controller.getMinutos() == 59){
							controller.setMinutos(0);
							if(controller.getHoras() == 23){
								controller.setHoras(0);
							}else{
								controller.incrementaHoras();
							}
						}else{
							controller.incrementaMinutos();

						}
					}else{

						cont = cont + 0.1;//incrementa os segundos
						cont = cont + controller.getDrift();//incrementa o drift
					}
					controller.setSegundos(cont);
					cont = cont + 0.1;//incrementos de 0.1 de segundo para ter mais precisão   
				}   
			};      
			timer.scheduleAtFixedRate(tarefa, 0, 100);      

		}    
	}
}

