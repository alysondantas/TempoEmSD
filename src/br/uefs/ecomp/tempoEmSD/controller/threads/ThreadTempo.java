package br.uefs.ecomp.tempoEmSD.controller.threads;

import java.util.Timer;
import java.util.TimerTask;

import br.uefs.ecomp.tempoEmSD.controller.ControllerRelogio;

public class ThreadTempo extends Thread  {

	private ControllerRelogio controller = ControllerRelogio.getInstance();

	public ThreadTempo(){

	}

	@Override
	public void run(){
		//while(true){

		Timer timer = null;      
		if (timer == null)   
		{      
			timer = new Timer();

			TimerTask tarefa = new TimerTask() {  
				double cont = 0.0;
				public void run()   
				{      
					cont = controller.getSegundos();
					if(cont > 58){
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

						cont = cont + 0.1;
						cont = cont + controller.getDrift();
					}
					controller.setSegundos(cont);
					//System.out.println("tempo " +controller.getHoras() + " : " + controller.getMinutos() + " : " + cont);
					cont = cont + 0.1;

					/*try {      
							System.out.println("Hora: "+format.format(new Date().getTime()));      
						} catch (Exception e) {      
							e.printStackTrace();      
						} */     
				}   
			};      
			timer.scheduleAtFixedRate(tarefa, 0, 100);      
			
		}    
	}



	/*try {
			Thread.sleep(1000);//demora um minuto para atualizar novamente
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	//}
}

