package br.uefs.ecomp.tempoEmSD.controller.threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import br.uefs.ecomp.tempoEmSD.controller.ControllerRelogio;

public class ThreadConexao extends Thread {
	private String msgR;
	private ControllerRelogio controller;
	private String ip;

	public ThreadConexao(String ip){
		controller = ControllerRelogio.getInstance();
		setMsgR("");
		this.ip = ip;
	}

	@Override
	public void run(){
		MulticastSocket socket;
		try {
			socket = new MulticastSocket(4545);
			socket.joinGroup(InetAddress.getByName(ip));
			while(true){
				byte[] b = new byte[1024];
				DatagramPacket dgram = new DatagramPacket(b, b.length);

				//System.out.println("Aguardando receber mensagem:");
				socket.receive(dgram);
				String msg = new String(b, 0, dgram.getLength()).trim();//limpa a mensagem recebida
				this.msgR = msg;
				//System.out.println("Mensagem recebida " + msg + " de " + dgram.getAddress());
				dgram.setLength(b.length); 

				String informacoes[] = msg.split(Pattern.quote(":"));
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

	public void sendTo(String s){
		DatagramSocket socket;
		try {
			socket = new DatagramSocket();
			byte[] b = s.getBytes();
			DatagramPacket dgram;

			dgram = new DatagramPacket(b, b.length, InetAddress.getByName(ip), 4545);

			System.err.println("Enviando msg: " + s + " com " + b.length + " bytes para " + dgram.getAddress() + " : " + dgram.getPort());
			socket.send(dgram);

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getMsgR() {
		return msgR;
	}

	public void setMsgR(String msgR) {
		this.msgR = msgR;
	}


}
