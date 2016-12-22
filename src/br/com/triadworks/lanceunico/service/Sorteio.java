package br.com.triadworks.lanceunico.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;

public class Sorteio {
	
	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	List<Lance> menores;
	
	public void sorteia(Promocao promocao){
		
		if(promocao.getLances().size() == 0){
			throw new RuntimeException("Lista de lances vazia");
		}
		
		encontraMaiorEMenorLance(promocao);
		
		encontraTresMenoresLances(promocao);
	}

	private void encontraMaiorEMenorLance(Promocao promocao) {
		for (Lance l : promocao.getLances()){
			
			if(l.getValor() > maiorDeTodos){
				maiorDeTodos = l.getValor();
			} 
			if (l.getValor() < menorDeTodos){
				menorDeTodos = l.getValor();
			}
		}
	}

	private void encontraTresMenoresLances(Promocao promocao) {
		List<Lance> copia = new ArrayList<Lance>(promocao.getLances()); 
		Collections.sort(copia, new Comparator<Lance>(){

			@Override
			public int compare(Lance o1, Lance o2) {
				if (o1.getValor() < o2.getValor()) return -1;
				if (o1.getValor() > o2.getValor()) return 1;
				return 0;
			}
			
		});
		
		int tamanho = copia.size() < 3 ? copia.size() : 3;
		menores = copia.subList(0, tamanho);
	}
	
	public double getMenorLance(){
		return menorDeTodos;
	}
	
	public double getMaiorLance(){
		return maiorDeTodos;
	}

	public List<Lance> getTresMenoresLances(){
		if (menores.size() == 0){
			throw new RuntimeException("Lista Vazia");
		}
		return menores;
	}
}
