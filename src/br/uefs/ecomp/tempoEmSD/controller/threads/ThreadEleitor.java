package br.uefs.ecomp.tempoEmSD.controller.threads;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import br.uefs.ecomp.tempoEmSD.controller.ControllerRelogio;

/**
 * 
 * @author Alyson Dantas
 *
 */
public class ThreadEleitor extends Thread {
	private ControllerRelogio controller;
	private ArrayList<String> tempos;
	private ThreadConexao threadConexao;
	private boolean executando;

	/**
	 * Construtor thread Eleitor
	 */
	public ThreadEleitor(){
		controller = ControllerRelogio.getInstance();
		tempos = new ArrayList<>();
		executando = false;
	}

	/**
	 * Metodo run
	 */
	@Override
	public void run(){
		//Caso a thread seja interrompida ela lançara uma exceção
		while(true){
			if (Thread.interrupted()){
				try {
					throw new InterruptedException();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(executando){//habilita execução da eleição
				System.out.println("começou a verificar eleição");
				int cont = 0;
				int verificador = tempos.size();
				//verifica se add algum novo tempo para comparar
				while(cont < 10){
					int verifica = tempos.size();
					if(verifica != verificador){//se add ele não deve começar a eleição ainda
						verificador = verifica;
						cont = 0;
					}else{
						cont++;
					}

					try {
						Thread.sleep(200);//dorme thread por 200 mls
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				Iterator<String> itera = tempos.iterator();
				String aux = "";
				int hora = 0;
				int minuto = 0;
				double segundo = 0.0;
				int tmpH = -1;
				int tmpM = -1;
				double tmpS = -1.0;
				String eleito = "";

				while(itera.hasNext()){//realiza verificação dos votos
					aux = itera.next();
					String informacoes[] = aux.split(Pattern.quote(":"));
					hora = Integer.parseInt(informacoes[0]);
					minuto = Integer.parseInt(informacoes[1]);
					segundo = Double.parseDouble(informacoes[2]);


					DecimalFormat df = new DecimalFormat("0.##");
					String dx = df.format(segundo);
					dx = dx.replace(",", ".");
					segundo = Double.parseDouble(dx);

					DecimalFormat df2 = new DecimalFormat("0.##");
					String dx2 = df2.format(tmpS);
					dx2 = dx2.replace(",", ".");
					tmpS = Double.parseDouble(dx2);


					if(!aux.equals("")){//verifica se é um novo
						if(tmpH < hora){
							eleito = aux;
							tmpH = hora;
							tmpM = minuto;
							tmpS = segundo;
						}else if(tmpH <= hora && tmpM < minuto){
							eleito = aux;
							tmpH = hora;
							tmpM = minuto;
							tmpS = segundo;
						}else if(tmpH <= hora && tmpM <= minuto && tmpS <= segundo){
							eleito = aux;
							tmpH = hora;
							tmpM = minuto;
							tmpS = segundo;
						}else if(hora == 23 && tmpH == 0){
							eleito = aux;
							tmpH = hora;
							tmpM = minuto;
							tmpS = segundo;
						}else if(minuto == 59 && tmpM == 0){
							eleito = aux;
							tmpH = hora;
							tmpM = minuto;
							tmpS = segundo;
						}else if(segundo == 59 && tmpS == 0){
							eleito = aux;
							tmpH = hora;
							tmpM = minuto;
							tmpS = segundo;
						}
					}
				}


				String informacoes2[] = eleito.split(Pattern.quote(":"));
				System.out.println("Eleito: " + eleito);
				String s = controller.getId() + "$" + "2" + "$" + informacoes2[3];
				System.out.println("Elegi novo referencial " + s);
				threadConexao.sendTo(s);
				
				executando = false;//desabilita a thread para não reiniciar automaticamente
				tempos = new ArrayList<>();
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * Metodo que adiciona tempo
	 * @param s
	 */
	public void addTempo(String s){
		String aux;
		Iterator<String> itera = tempos.iterator();
		boolean jaExiste = false;
		while(itera.hasNext()){
			aux = itera.next();
			if(aux.equals(s)){
				jaExiste = true;
				break;
			}
		}
		if(!jaExiste){
			System.out.println("Adicionando novo tempo para comparar");
			tempos.add(s);
		}
	}

	/**
	 * Metodo que modifica thread conexao
	 * @param thread
	 */
	public void setConexao(ThreadConexao thread){
		this.threadConexao = thread;
	}

	/**
	 * Metodo que reseta a lista de tempos
	 */
	public void resetTempos(){
		tempos = new ArrayList<>();
	}

	/**
	 * Metodo que verifica se esta sendo executado
	 * @return
	 */
	public boolean isExecutando() {
		return executando;
	}

	/**
	 * Metodo de modificar execução
	 * @param executando
	 */
	public void setExecutando(boolean executando) {
		this.executando = executando;
	}

}
