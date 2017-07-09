package br.uefs.ecomp.tempoEmSD.controller.threads;

import javax.swing.JLabel;

import br.uefs.ecomp.tempoEmSD.controller.ControllerRelogio;

public class ThreadAtualizaGUI extends Thread {
	
	private ControllerRelogio controller = ControllerRelogio.getInstance();
	private JLabel lblH;
	private JLabel lblM;
	private JLabel lblS;
	private JLabel lblId;
	private JLabel lblIdR;
	
	public ThreadAtualizaGUI(JLabel lblH, JLabel lblM, JLabel lblS,JLabel lblId, JLabel labelIdReferencia){
		this.lblH = lblH;
		this.lblM = lblM;
		this.lblS = lblS;
		this.lblId = lblId;
		this.lblIdR = labelIdReferencia;
	}
	
	@Override
	public void run(){
		while(true){
			//atualiza hora/minuto/segundo
			int horas = controller.getHoras();
			int minutos = controller.getMinutos();
			int segundos = controller.getSegundos();
			int id = controller.getId();
			String sHoras;
			if(horas<10){
				sHoras = "0" + horas;
			}else{
				sHoras = "" + horas;
			}
			String sMinutos;
			if(minutos<10){
				sMinutos = "0" + minutos;
			}else{
				sMinutos = "" + minutos;
			}
			String sSegundos;
			if(segundos<10){
				sSegundos = "0" + segundos;
			}else{
				sSegundos = "" + segundos;
			}
			
			int idR = controller.getIdReferencia();
				
			lblH.setText(sHoras);
			lblM.setText(sMinutos);
			lblS.setText(sSegundos);
			
			lblId.setText(id + "");
			lblIdR.setText(idR + "");
			
			try {
				Thread.sleep(200);//demora meio minuto para atualizar novamente
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
