package br.uefs.ecomp.tempoEmSD.controller;

import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadRecebeTempo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadEnviaTempo;
import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadTempo;
import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadVerificaTempo;

public class ControllerRelogio {
	private static ControllerRelogio unicaInstancia;
	private int segundos;
	private int minutos;
	private int horas;
	private int ultimoM;
	private int ultimoS;
	private int ultimaH;
	private ThreadRecebeTempo threadConexao;
	private ThreadEnviaTempo threadEnvia;
	private ThreadTempo threadTempo;
	private ThreadVerificaTempo threadVerifica;
	private int idReferencia;
	private boolean souReferencia;
	private int id;
	private boolean bufferCheio;
	
	/**
	 * Construtor
	 */
	private ControllerRelogio(){
		segundos = 0;
		setMinutos(0);
		setHoras(0);
		setBufferCheio(false);
		
		//gera id
		id = 0;
		SimpleDateFormat sdfh = new SimpleDateFormat("HH");
        String horario = sdfh.format(new Date());
        id = id + Integer.parseInt(horario);
        SimpleDateFormat sdfm = new SimpleDateFormat("mm");
        horario = sdfm.format(new Date());
        id = id + Integer.parseInt(horario);
        SimpleDateFormat sdfs = new SimpleDateFormat("ss");
        horario = sdfs.format(new Date());
        id = id + Integer.parseInt(horario);
        Random aleatorio = new Random();
        int multiplica = aleatorio.nextInt(220);
        id = id * multiplica;
        System.err.println("multiplicando id por " + multiplica);
        int divide = aleatorio.nextInt(210);
        if(divide != 0){
        	System.err.println("dividindo id por " + divide);
        	id = id / divide;
        }else{
        	id = id / 105;
        	System.err.println("dividindo id por " + 105);
        }
		setSouReferencia(false);
		
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
		threadConexao = new ThreadRecebeTempo();
		threadConexao.start();
		
		verificaPrimeiro();
		threadVerifica = new ThreadVerificaTempo();
		threadVerifica.start();
		threadTempo = new ThreadTempo();
		threadTempo.start();
		
	}
	
	public void verificaPrimeiro(){
		int cont = 10;
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
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		if(primeiro == true){
			setSouReferencia(true);
			threadEnvia = new ThreadEnviaTempo();
			threadEnvia.start();
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

	public int getId() {
		return id;
	}

	public int getUltimoM() {
		return ultimoM;
	}

	public void setUltimoM(int ultimoM) {
		this.ultimoM = ultimoM;
	}

	public int getUltimoS() {
		return ultimoS;
	}

	public void setUltimoS(int ultimoS) {
		this.ultimoS = ultimoS;
	}

	public int getUltimaH() {
		return ultimaH;
	}

	public void setUltimaH(int ultimaH) {
		this.ultimaH = ultimaH;
	}

	public int getIdReferencia() {
		return idReferencia;
	}

	public void setIdReferencia(int idReferencia) {
		this.idReferencia = idReferencia;
	}

	public boolean isSouReferencia() {
		return souReferencia;
	}

	public void setSouReferencia(boolean souReferencia) {
		this.souReferencia = souReferencia;
	}

	public boolean isBufferCheio() {
		return bufferCheio;
	}

	public void setBufferCheio(boolean bufferCheio) {
		this.bufferCheio = bufferCheio;
	}
	
}
