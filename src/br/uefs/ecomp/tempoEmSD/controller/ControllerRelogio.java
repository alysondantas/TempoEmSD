package br.uefs.ecomp.tempoEmSD.controller;

import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadRecebeTempo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadAlterarReferencial;
import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadConexao;
import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadEleitor;
import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadEnviaTempo;
import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadTempo;
import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadVerificaTempo;

public class ControllerRelogio {
	private static ControllerRelogio unicaInstancia;
	private double segundos;
	private int minutos;
	private int horas;
	private int ultimoM;
	private double ultimoS;
	private int ultimaH;
	private ThreadRecebeTempo threadRecebe;
	private ThreadConexao threadConexao;
	private ThreadEnviaTempo threadEnvia;
	private ThreadTempo threadTempo;
	private ThreadEleitor threadEleitor;
	private ThreadVerificaTempo threadVerifica;
	private ThreadAlterarReferencial threadAReferencial;
	private int idReferencia;
	private boolean souReferencia;
	private int id;
	private boolean bufferCheio;
	private double drift;
	private String ip1;
	private String ip2;
	private boolean contaSozinho;
	
	/**
	 * Construtor
	 */
	private ControllerRelogio(){
		segundos = 0;
		setContaSozinho(false);
		ip1 = "233.4.5.6";
		ip2 = "233.4.5.7";
		setMinutos(0);
		setHoras(0);
		setBufferCheio(false);
		drift = 0.0;
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
		threadRecebe = new ThreadRecebeTempo(ip1);
		threadRecebe.start();
		threadEnvia = new ThreadEnviaTempo(ip1);
		verificaPrimeiro();
		threadEleitor = new ThreadEleitor();
		threadEleitor.start();
		threadAReferencial = new ThreadAlterarReferencial();
		threadAReferencial.start();
		threadConexao = new ThreadConexao(ip2, threadEleitor, threadAReferencial);
		threadConexao.start();
		threadEleitor.setConexao(threadConexao);
		threadVerifica = new ThreadVerificaTempo(threadConexao);
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
			msg = threadRecebe.getMsgR();
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
			souReferencia = true;
			
			threadEnvia.start();
			System.out.println("Sou o primeiro");
		}
		
	}

	public boolean isBufferCheio() {
		return bufferCheio;
	}

	public void setBufferCheio(boolean bufferCheio) {
		this.bufferCheio = bufferCheio;
	}

	public double getDrift() {
		return drift;
	}

	public void setDrift(double drift) {
		this.drift = drift;
	}
	
	public void setIp(String ip){
		this.ip1 = ip;
		String aux[] = ip.split(Pattern.quote("."));
		int i = Integer.parseInt(aux[3]);
		i++;
		String ipaux = aux[0] + "." + aux[1] + "." + aux[2] + "." + i; 
		System.out.println("Novo ip do canal 1 é" + ip);
		System.out.println("Novo ip do canal 2 é: " + ipaux);
		this.ip2 = ipaux;
	}
	
	public void ocorreuNovaEleicao(String novoReferencial){
		int novoR = Integer.parseInt(novoReferencial);
		System.out.println("Novo referencial é " + novoR);
		//threadEleitor = new ThreadEleitor();
		//threadAReferencial = new ThreadAlterarReferencial();
		if(id == novoR){
			souReferencia = true;
			System.out.println("Sou referencia" + souReferencia);
			if(!threadEnvia.isAlive()){
				threadEnvia.start();
			}
			
		}else{
			souReferencia = false;
		}
		idReferencia = novoR;
		System.out.println("Colocou o novo referencial : " + idReferencia);
		contaSozinho = false;
		bufferCheio = false;
		//threadConexao.setThreadEleitor(threadEleitor);
		//threadConexao.setThreadReferencial(threadAReferencial);
		//threadEleitor.setConexao(threadConexao);
		
	}
	
	public void setId(int idN){
		id = idN;
	}
	
	public double getSegundos() {
		return segundos;
	}

	public void setSegundos(double segundos) {
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

	public double getUltimoS() {
		return ultimoS;
	}

	public void setUltimoS(double ultimoS) {
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
	
	public boolean isContaSozinho() {
		return contaSozinho;
	}

	public void setContaSozinho(boolean contaSozinho) {
		this.contaSozinho = contaSozinho;
	}
	
}