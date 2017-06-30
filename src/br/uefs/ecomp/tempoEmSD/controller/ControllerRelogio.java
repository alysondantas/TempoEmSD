package br.uefs.ecomp.tempoEmSD.controller;

import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadConexao;
import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadTempo;

public class ControllerRelogio {
	private static ControllerRelogio unicaInstancia;
	private int segundos;
	private int minutos;
	private int horas;
	private ThreadConexao threadConexao;
	private boolean souReferencia;
	
	/**
	 * Construtor
	 */
	private ControllerRelogio(){
		segundos = 0;
		setMinutos(0);
		setHoras(0);
		threadConexao = new ThreadConexao();
		threadConexao.start();
		souReferencia = false;
	}
	
	/**
	 * controla o instanciamento de objetos Controller
	 *
	 * @return unicaInstancia
	 */
	public static synchronized ControllerRelogio getInstance() {
		if (unicaInstancia == null) {
			unicaInstancia = new ControllerRelogio();
		}
		return unicaInstancia;
	}

	/**
	 * reseta o objeto Controller ja instanciado
	 */
	public static void zerarSingleton() {
		unicaInstancia = null;
	}
	
	public void iniciaContador(){
		verificaPrimeiro();
		ThreadTempo threadtempo = new ThreadTempo();
		threadtempo.start();
	}
	
	public void verificaPrimeiro(){
		int cont = 4000;
		String msg = "";
		boolean primeiro = true;
		while(cont>1){
			System.out.println("Perguntou se é o primeiro");
			msg = threadConexao.getMsgR();
			if(!msg.equals("")){
				System.out.println("Tem alguem na sessão");
				primeiro = false;
				break;
			}
				cont--;
		}
		if(primeiro == true){
			souReferencia = true;
			System.out.println("Sou o primeiro");
		}
		
	}

	public int getSegundos() {
		return segundos;
	}

	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}
	public void incrementaMinutos(){
		minutos++;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}
	public void incrementaHoras(){
		horas++;
	}
	
}
