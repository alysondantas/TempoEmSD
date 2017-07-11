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
	private ThreadEleitor threadEleitor;
	private ThreadAlterarReferencial threadReferencial;

	public ThreadConexao(String ip, ThreadEleitor threadEleitor, ThreadAlterarReferencial thread){
		controller = ControllerRelogio.getInstance();
		setMsgR("");
		this.ip = ip;
		this.threadEleitor = threadEleitor;
		this.threadReferencial = thread;
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

				String informacoes[] = msg.split(Pattern.quote("$"));
				//if(!informacoes[0].equals("" + controller.getId()) && !controller.isContaSozinho() && !informacoes[2].equals("0")){
					switch(informacoes[1]){
					case "0":
						//if(!informacoes[0].equals("" + controller.getId())){
							System.out.println("Solicitação de eleição");
							String tempo = "";
							controller.setContaSozinho(true);
							tempo = controller.getId() + "$" + "1" + "$" + controller.getHoras() + ":" + controller.getMinutos() + ":" + controller.getSegundos();
							sendTo(tempo);
						//}
						
						break;
					case "1":
						//System.out.println("Novo tempo recebido " + informacoes[2] + " de: " + informacoes[0]);
						threadEleitor.addTempo(informacoes[2] + ":" + informacoes[0]);
						if(threadEleitor.isAlive()){
							System.out.println("thread eleitor rodando");
							/*if(!threadEleitor.isExecutando()){
								threadEleitor.run();
							}*/
						}else{
							threadEleitor.start();
							System.out.println("thread eleitor iniciada");
						}
						
						break;
					case "2":
						System.out.println("Recebido novo eleito por " + informacoes[0] + " é " + informacoes[2]);
						threadReferencial.addNovaEleicao(informacoes[0] + ":" + informacoes[2]);
						if(threadReferencial.isAlive()){
							System.out.println("thread verificacao rodando");
						}else{
							threadReferencial.start();
							System.out.println("thread verificacao iniciada");
						}
						
					}
				//}
				msg = "";

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

			//System.err.println("Enviando msg: " + s);
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
	
	public void setThreadEleitor(ThreadEleitor thread){
		this.threadEleitor = thread;
	}
	
	public void setThreadReferencial(ThreadAlterarReferencial thread){
		this.threadReferencial = thread;
	}


}
