package br.uefs.ecomp.tempoEmSD.controller.threads;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import br.uefs.ecomp.tempoEmSD.controller.ControllerRelogio;

public class ThreadEleitor extends Thread {
	private ControllerRelogio controller;
	private ArrayList<String> tempos;
	private ThreadConexao threadConexao;
	private boolean executando;

	public ThreadEleitor(){
		controller = ControllerRelogio.getInstance();
		tempos = new ArrayList<>();
		executando = false;
	}

	@Override
	public void run(){
		//Caso a thread seja interrompida ela lan�ara uma exce��o
		while(true){
			if (Thread.interrupted()){
				try {
					throw new InterruptedException();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(executando){

				//while(true){
				//if(contar){//permite com�ar a verificar a elei��o
				System.out.println("come�ou a verificar elei��o");
				int cont = 0;
				int verificador = tempos.size();
				//verifica se add algum novo tempo para comparar
				while(cont < 10){
					int verifica = tempos.size();
					if(verifica != verificador){//se add ele n�o deve come�ar a elei��o ainda
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

				while(itera.hasNext()){
					aux = itera.next();
					//System.out.println("o que tem no aux " + aux);
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


					if(!aux.equals("")){
						if(tmpH < hora){
							//System.out.println("Eleito novo1: " + aux);
							eleito = aux;
							tmpH = hora;
							tmpM = minuto;
							tmpS = segundo;
						}else if(tmpH <= hora && tmpM < minuto){
							//System.out.println("Eleito novo2: " + aux);
							eleito = aux;
							tmpH = hora;
							tmpM = minuto;
							tmpS = segundo;
						}else if(tmpH <= hora && tmpM <= minuto && tmpS <= segundo){
							//System.out.println("Eleito novo3: " + aux);
							eleito = aux;
							tmpH = hora;
							tmpM = minuto;
							tmpS = segundo;
						}else if(hora == 23 && tmpH == 0){
							//System.out.println("Eleito novo4: " + aux);
							eleito = aux;
							tmpH = hora;
							tmpM = minuto;
							tmpS = segundo;
						}else if(minuto == 59 && tmpM == 0){
							//System.out.println("Eleito novo5: " + aux);
							eleito = aux;
							tmpH = hora;
							tmpM = minuto;
							tmpS = segundo;
						}else if(segundo == 59 && tmpS == 0){
							//System.out.println("Eleito novo6: " + aux);
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
				//contar = false;
				executando = false;
				tempos = new ArrayList<>();
				//}
				//}
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

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

	public void setConexao(ThreadConexao thread){
		this.threadConexao = thread;
	}

	public void resetTempos(){
		tempos = new ArrayList<>();
	}

	public boolean isExecutando() {
		return executando;
	}

	public void setExecutando(boolean executando) {
		this.executando = executando;
	}

}
