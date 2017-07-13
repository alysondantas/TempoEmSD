package br.uefs.ecomp.tempoEmSD.controller.threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.regex.Pattern;

import br.uefs.ecomp.tempoEmSD.controller.ControllerRelogio;

/**
 * 
 * @author Alyson Dantas
 *
 */
public class ThreadRecebeTempo extends Thread {

	private String msgR;
	private ControllerRelogio controller;
	private String ip;

	/**
	 * Construtor
	 * @param ip
	 */
	public ThreadRecebeTempo(String ip){
		controller = ControllerRelogio.getInstance();
		msgR = "";
		this.ip = ip;
	}

	/**
	 * Metodo run
	 */
	@Override
	public void run(){
		MulticastSocket socket;
		try {
			socket = new MulticastSocket(4545);
			socket.joinGroup(InetAddress.getByName(ip));
			while(true){
				byte[] b = new byte[1024];
				DatagramPacket dgram = new DatagramPacket(b, b.length);

				socket.receive(dgram);
				String msg = new String(b, 0, dgram.getLength()).trim();//limpa a mensagem recebida
				this.msgR = msg;
				dgram.setLength(b.length); 
				
				String informacoes[] = msg.split(Pattern.quote(":"));//coloca o tempo recebido na variavel temporaria
				controller.setUltimaH(Integer.parseInt(informacoes[0]));
				controller.setUltimoM(Integer.parseInt(informacoes[1]));
				controller.setUltimoS(Double.parseDouble(informacoes[2]));
				controller.setIdReferencia(Integer.parseInt(informacoes[3]));
				controller.setBufferCheio(true);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


	}

	/**
	 * Metodo que recupera a mensagem
	 * @return
	 */
	public String getMsgR() {
		return msgR;
	}


}
