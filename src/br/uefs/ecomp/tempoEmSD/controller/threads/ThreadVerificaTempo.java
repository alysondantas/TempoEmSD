package br.uefs.ecomp.tempoEmSD.controller.threads;

import br.uefs.ecomp.tempoEmSD.controller.ControllerRelogio;

public class ThreadVerificaTempo extends Thread {
	
	private ControllerRelogio controller;
	private int tmpH;
	private int tmpM;
	private int tmpS;
	private int cont;
	
	public ThreadVerificaTempo(){
		controller = ControllerRelogio.getInstance();
		this.tmpH = 0;
		this.tmpM = 0;
		this.tmpS = 0;
		cont = 0;
	}
	
	@Override
	public void run(){
		while(true){
			if(!controller.isSouReferencia()){
				if(!controller.isBufferCheio()){
					cont++;
				}else{
					cont = 0;
					tmpH = controller.getUltimaH();
					System.out.println("hora nova :" + tmpH);
					tmpM = controller.getUltimoM();
					tmpS = controller.getUltimoS();
					if(tmpH > controller.getHoras() || tmpM > controller.getMinutos() || tmpS >= controller.getSegundos()){
						controller.setBufferCheio(false);
						controller.setHoras(tmpH);
						controller.setMinutos(tmpM);
						controller.setSegundos(tmpS);
					}else if(controller.getHoras() == 23 && tmpH == 0){
						controller.setBufferCheio(false);
						controller.setHoras(tmpH);
						controller.setMinutos(tmpM);
						controller.setSegundos(tmpS);
					}else if(controller.getMinutos() == 59 && tmpM == 0){
						controller.setBufferCheio(false);
						controller.setHoras(tmpH);
						controller.setMinutos(tmpM);
						controller.setSegundos(tmpS);
					}else if(controller.getSegundos() == 59 && tmpS == 0){
						controller.setBufferCheio(false);
						controller.setHoras(tmpH);
						controller.setMinutos(tmpM);
						controller.setSegundos(tmpS);
					}else{
						System.out.println("Erro de sincronia tempo incorreto");
					}
				}
				
				if(cont == 5){
					System.err.println("Erro de sincronia precia chamar eleição tempo excedido");
					cont = 0;
				}
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
