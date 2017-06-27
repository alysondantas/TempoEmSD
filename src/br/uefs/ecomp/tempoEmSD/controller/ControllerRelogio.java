package br.uefs.ecomp.tempoEmSD.controller;

import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadTempo;

public class ControllerRelogio {
	private static ControllerRelogio unicaInstancia;
	private int segundos;
	private int minutos;
	private int horas;
	
	/**
	 * Construtor
	 */
	private ControllerRelogio(){
		segundos = 0;
		setMinutos(0);
		setHoras(0);
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
		ThreadTempo threadtempo = new ThreadTempo();
		threadtempo.start();
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
