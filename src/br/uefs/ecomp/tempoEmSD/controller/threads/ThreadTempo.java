package br.uefs.ecomp.tempoEmSD.controller.threads;

import java.text.SimpleDateFormat;
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
		final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		if (timer == null)   
		{      
			timer = new Timer();

			TimerTask tarefa = new TimerTask() {  
				int cont = 0;
				public void run()   
				{      
					cont = controller.getSegundos();
					if(cont == 59){
						cont = 0;
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

						cont++;
					}

					controller.setSegundos(cont);
					System.out.println("tempo " +controller.getHoras() + " : " + controller.getMinutos() + " : " + cont);
					cont = cont +1;

					/*try {      
							System.out.println("Hora: "+format.format(new Date().getTime()));      
						} catch (Exception e) {      
							e.printStackTrace();      
						} */     
				}   
			};      
			timer.scheduleAtFixedRate(tarefa, 0, 1000);      
			
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

