package br.uefs.ecomp.tempoEmSD.controller.threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import br.uefs.ecomp.tempoEmSD.controller.ControllerRelogio;

public class ThreadEnviaTempo extends Thread  {

	private ControllerRelogio controller;
	private String ip;
	
	public ThreadEnviaTempo(String ip){
		controller = ControllerRelogio.getInstance();
		this.ip = ip;
	}

	@Override
	public void run(){
		while(true){
			//System.err.println("");
			//Caso a thread seja interrompida ela lan�ara uma exce��o
			if (Thread.interrupted()){
				try {
					throw new InterruptedException();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(controller.isSouReferencia() || controller.getIdReferencia() == controller.getId()){
				DatagramSocket socket;
				try {
					socket = new DatagramSocket();

					String s = controller.getHoras() + ":" + controller.getMinutos() + ":" + controller.getSegundos() + ":" + controller.getId();

					byte[] b = s.getBytes();
					DatagramPacket dgram;

					dgram = new DatagramPacket(b, b.length, InetAddress.getByName(ip), 4545);

					//System.err.println("Enviando atualiza��o de tempo: " + s + " com " + b.length + " bytes para " + dgram.getAddress() + " : " + dgram.getPort());
					socket.send(dgram);
					
					Thread.sleep(250);//thread dorme em quanto estiver sincronizando por 1/4 segundo
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
