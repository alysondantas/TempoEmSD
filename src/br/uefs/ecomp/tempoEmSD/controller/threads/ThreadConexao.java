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

/**
 * 
 * @author Alyson Dantas
 *
 */
public class ThreadConexao extends Thread {
	private String msgR;
	private ControllerRelogio controller;
	private String ip;
	private ThreadEleitor threadEleitor;
	private ThreadAlterarReferencial threadReferencial;

	/**
	 * Construtor conexao
	 * @param ip
	 * @param threadEleitor
	 * @param thread
	 */
	public ThreadConexao(String ip, ThreadEleitor threadEleitor, ThreadAlterarReferencial thread){
		controller = ControllerRelogio.getInstance();
		setMsgR("");
		this.ip = ip;
		this.threadEleitor = threadEleitor;
		this.threadReferencial = thread;
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

				String informacoes[] = msg.split(Pattern.quote("$"));
				switch(informacoes[1]){//pega a opcao recebida
				case "0"://uma nova eleição solicitada
					System.out.println("Solicitação de eleição");
					String tempo = "";
					controller.setContaSozinho(true);
					tempo = controller.getId() + "$" + "1" + "$" + controller.getHoras() + ":" + controller.getMinutos() + ":" + controller.getSegundos();
					sendTo(tempo);

					break;
				case "1"://se for 1 começa uma eleição
					threadEleitor.addTempo(informacoes[2] + ":" + informacoes[0]);
					threadEleitor.setExecutando(true);//habilita a eleição

					break;
				case "2"://caso alguem seja eleito
					System.out.println("Recebido novo eleito por " + informacoes[0] + " é " + informacoes[2]);
					threadReferencial.addNovaEleicao(informacoes[0] + ":" + informacoes[2]);
					threadReferencial.setExecutando(true);//habilita a observação de eleição
				}
				msg = "";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	/**
	 * Metodo que envia mensagem pelo canal
	 * @param s
	 */
	public void sendTo(String s){
		DatagramSocket socket;
		try {
			socket = new DatagramSocket();
			byte[] b = s.getBytes();
			DatagramPacket dgram;

			dgram = new DatagramPacket(b, b.length, InetAddress.getByName(ip), 4545);

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

	/**
	 * Metodo de pegar a msg
	 * @return
	 */
	public String getMsgR() {
		return msgR;
	}

	/**
	 * Metodo de modificar a mensagem
	 * @param msgR
	 */
	public void setMsgR(String msgR) {
		this.msgR = msgR;
	}

	/**
	 * Metodo de modificar a thread Eleitor
	 * @param thread
	 */
	public void setThreadEleitor(ThreadEleitor thread){
		this.threadEleitor = thread;
	}

	/**
	 * Metodo de modificar a thread Referencia
	 * @param thread
	 */
	public void setThreadReferencial(ThreadAlterarReferencial thread){
		this.threadReferencial = thread;
	}


}
