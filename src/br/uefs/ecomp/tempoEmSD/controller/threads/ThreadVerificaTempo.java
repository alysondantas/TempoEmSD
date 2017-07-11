package br.uefs.ecomp.tempoEmSD.controller.threads;

import java.text.DecimalFormat;

import br.uefs.ecomp.tempoEmSD.controller.ControllerRelogio;

public class ThreadVerificaTempo extends Thread {
	
	private ControllerRelogio controller;
	private int tmpH;
	private int tmpM;
	private double tmpS;
	private int meuH;
	private int meuM;
	private double meuS;
	private int cont;
	private ThreadConexao threadConexao;
	
	public ThreadVerificaTempo(ThreadConexao thread){
		controller = ControllerRelogio.getInstance();
		this.tmpH = 0;
		this.tmpM = 0;
		this.tmpS = 0;
		cont = 0;
		this.threadConexao = thread;
	}
	
	@Override
	public void run(){
		while(true){
			if(!controller.isSouReferencia() && !controller.isContaSozinho()){
				if(!controller.isBufferCheio()){
					cont++;
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					cont = 0;
					tmpH = controller.getUltimaH();
					//System.out.println("hora nova :" + tmpH);
					tmpM = controller.getUltimoM();
					tmpS = controller.getUltimoS();
					meuH = controller.getHoras();
					meuM = controller.getMinutos();
					meuS = controller.getSegundos();
					//System.out.println("segundo antes recebido " + meuS);
					
					DecimalFormat df = new DecimalFormat("0.##");
					String dx = df.format(meuS);
					dx = dx.replace(",", ".");
					meuS = Double.parseDouble(dx);
					
					
					//System.out.println("segundos depois convertido " + meuS);
					
					DecimalFormat df2 = new DecimalFormat("0.##");
					String dx2 = df2.format(tmpS);
					dx2 = dx2.replace(",", ".");
					tmpS = Double.parseDouble(dx2);
					
					//System.out.println("segundos depois convertido " + meuS);
					
					if(tmpH > meuH){
						controller.setBufferCheio(false);
						controller.setHoras(tmpH);
						controller.setMinutos(tmpM);
						controller.setSegundos(tmpS);
					}else if(tmpH >= meuH && tmpM > meuM){
						controller.setBufferCheio(false);
						controller.setHoras(tmpH);
						controller.setMinutos(tmpM);
						controller.setSegundos(tmpS);
					}else if(tmpH >= meuH && tmpM >= meuM && tmpS >= meuS){
						controller.setBufferCheio(false);
						controller.setHoras(tmpH);
						controller.setMinutos(tmpM);
						controller.setSegundos(tmpS);
					}else if(meuH == 23 && tmpH == 0){
						controller.setBufferCheio(false);
						controller.setHoras(tmpH);
						controller.setMinutos(tmpM);
						controller.setSegundos(tmpS);
					}else if(meuM == 59 && tmpM == 0){
						controller.setBufferCheio(false);
						controller.setHoras(tmpH);
						controller.setMinutos(tmpM);
						controller.setSegundos(tmpS);
					}else if(meuS == 59 && tmpS == 0){
						controller.setBufferCheio(false);
						controller.setHoras(tmpH);
						controller.setMinutos(tmpM);
						controller.setSegundos(tmpS);
					}else{
						System.out.println("Erro de sincronia tempo incorreto meu tempo" +meuS + " & " + tmpS);
						threadConexao.sendTo(controller.getId() + "$" + "0");
						controller.setContaSozinho(true);
					}
				}
				
				if(cont == 5){
					System.err.println("Erro de sincronia precia chamar eleição tempo excedido");
					cont = 0;
					threadConexao.sendTo(controller.getId() + "$" + "0");
					controller.setContaSozinho(true);
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
