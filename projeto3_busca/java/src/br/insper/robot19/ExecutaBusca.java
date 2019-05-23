package br.insper.robot19;

import java.io.IOException;
import java.util.LinkedList;

import br.insper.robot19.block.Block;
import br.insper.robot19.block.BlockType;
import br.insper.robot19.busca.BuscaA;
import br.insper.robot19.busca.BuscaGulosa;
import br.insper.robot19.busca.BuscaLargura;

public class ExecutaBusca {

	private static float cellSize = 0.5f; //m

	public static void main(String[] args) {
		
		//Carrega o arquivo a partir do arquivo	
		GridMap map;
		GridMap newMap;
		LinkedList<String> buscas = new LinkedList<>();
		try {
			map = GridMap.fromFile("map_teste.txt");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		if(map == null) 
			throw new RuntimeException("O mapa discretizado não pode ser obtido a partir do modelo");
		
		//Imprimie o mapa em ASCII
		System.out.println("Mapa inicial:");
		System.out.println(map);
		
		//Realiza a busca
		int[] rowColIni = map.getStart();
		int[] rowColFim = map.getGoal();
		Block inicial = new Block(rowColIni[0], rowColIni[1], BlockType.FREE) ;
		Block alvo = new Block(rowColFim[0], rowColFim[1], BlockType.FREE) ;
		//Busca A*
		BuscaA buscaA = new BuscaA(map, inicial, alvo);
		RobotAction[] solucaoA = buscaA.resolver();
		//Busca Gulosa
		BuscaGulosa buscaG = new BuscaGulosa(map, inicial, alvo);
		RobotAction[] solucaoG = buscaG.resolver();
		//Busca em LArgura
		BuscaLargura buscaL = new BuscaLargura(map, inicial, alvo);
		RobotAction[] solucaoL = buscaL.resolver();

		LinkedList<RobotAction[]> solucoes = new LinkedList<>();
		solucoes.add(solucaoA);
		buscas.add("A*");
		solucoes.add(solucaoG);
		buscas.add("Gulosa");
		solucoes.add(solucaoL);
		buscas.add("em Largura");



		for(int i =0; i < solucoes.size(); i++) {
			RobotAction[] solucao = solucoes.get(i);
			try {
				newMap = GridMap.fromFile("map_teste.txt");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			//Mostra a solução
			if (solucao == null) {
				System.out.println("Nao foi encontrada solucao para o problema");
			} else {

				Block atual = inicial;
				System.out.print("Solução busca " + buscas.get(i) + ": ");
				for (RobotAction action : solucao) {
					System.out.print(", " + action);
					Block next = newMap.nextBlock(atual, action);
					newMap.setRoute(next.row, next.col);
					atual = next;
				}

				//Mostra o mapa com a rota encontrada
				System.out.println();
				System.out.println("Rota encontrada:");
				System.out.println(newMap);
			}
		}
	}
}
