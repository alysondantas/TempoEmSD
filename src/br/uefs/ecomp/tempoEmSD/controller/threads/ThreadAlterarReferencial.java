package br.uefs.ecomp.tempoEmSD.controller.threads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import br.uefs.ecomp.tempoEmSD.controller.ControllerRelogio;

public class ThreadAlterarReferencial extends Thread {
	private ControllerRelogio controller;
	private ArrayList<String> votos;
	
	public ThreadAlterarReferencial(){
		controller = ControllerRelogio.getInstance();
		votos = new ArrayList<>();
	}
	


	@Override
	public void run(){
		//while(true){
		//if(contar){//permite comçar a verificar a eleição
		System.out.println("começou a verificar votação");
		int cont = 0;
		int verificador = votos.size();
		//verifica se add algum novo tempo para comparar
		while(cont < 10){
			int verifica = votos.size();
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

		Iterator<String> itera = votos.iterator();
		String aux = "";
		String informacoes[];
		String aux2 = "";
		String aux3 = "";
		cont = 0;
		while(itera.hasNext()){
			aux = itera.next();
			informacoes =  aux.split(Pattern.quote(":"));
				if(aux2.equals("")){
					aux2 = informacoes[1];
					cont++;
				}else if(aux2.equals(informacoes[1])){
					cont++;
				}else{
					System.out.println("divergencia em voto: " + aux3);
					aux3 = informacoes[1];
				}
			}
		
		if(cont == votos.size()){
			System.out.println("Eleição ocorreu com sucesso, sem divergencias novo eleito é " + aux2 + " votos: " + votos.size());
		}else{
			System.out.println("Houve divergencia na eleição eleito com maior quantidade de votos " + aux2 + " voto divergente foi para " + aux3);
		}


		controller.ocorreuNovaEleicao(aux2);
		
		//contar = false;

		//}
		//}
	}

	public void addNovaEleicao(String s){
		String aux;
		Iterator<String> itera = votos.iterator();
		boolean jaExiste = false;
		while(itera.hasNext()){
			aux = itera.next();
			if(aux.equals(s)){
				jaExiste = true;
				break;
			}
		}
		if(!jaExiste){
			System.out.println("Adicionando novo voto para comparar");
			votos.add(s);
		}
	}

}
