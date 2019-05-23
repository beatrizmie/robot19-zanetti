package br.insper.robot19;

import br.insper.robot19.*;
import br.insper.robot19.Block;
import br.insper.robot19.BlockType;
import br.insper.robot19.GridMap;

import java.util.*;


/**
 * Classe que implementa o algoritmo de busca
 * @author antonio
 *
 */
public class BuscaGulosa {

    private Block start = null;
    private Block end = null;
    private GridMap map = null;
    private HashSet<Block> blocks;
    private String search;
    private int counter;

    private DesenhoMapa drawing;
    private PriorityQueue<Node> border;

    /**
     * Construtor
     * @param map
     * @param start - ponto inicial
     * @param end - ponto final
     */
    public BuscaGulosa(GridMap map, Block start, Block end) {
        this.map = map;
        this.start = start;
        this.end = end;
        this.search = "Gulosa";
        this.drawing = new DesenhoMapa(map, search);
        drawing.desenha();
        drawing.saveFile("Busca" + search + ".png");
    }

    /**
     * Método que realiza a busca
     * @return
     */
    public Node buscar() {

        Node root = new Node(start, null, null, 0, end, search);

        //Limpa a fronteira e insere o nó raiz
        border = new PriorityQueue<>();
        border.add(root);
        //Inicia o HashSet para a busca em grafo
        blocks = new HashSet<>();

        while(!border.isEmpty()) {

            Node node = border.remove();
            Block atual = node.getValue();




            if(!blocks.contains(atual)){
                blocks.add(atual);

                if(atual.row == end.row && atual.col == end.col) {
                    drawing.desenhaVisitados(atual);//Vermelho
                    return node;
                } else for(RobotAction acao : RobotAction.values()) {

                    Block proximo = map.nextBlock(atual, acao);

                    if(proximo != null && proximo.type != BlockType.WALL) {
                        Node novoNode = new Node(proximo, node, acao, proximo.type.cost, end, search);
                        border.add(novoNode);
                        if(!blocks.contains(proximo)){
                            drawing.desenhaFronteira(proximo);//Verde
                        }
                    }
                }
                drawing.desenhaVisitados(atual);//Vermelho
                drawing.saveFile("Busca" + search + counter + ".png");
                counter += 1;
            }
        }
        return null;
    }

    /**
     * Resolve o problema com base em busca, realizando
     * o backtracking após chegar ao estado final
     *
     * @return A solução encontrada
     */
    public RobotAction[] resolver() {

        // Encontra a solução através da busca
        Node destino = buscar();
        if(destino == null) {
            return null;
        }

        //Faz o backtracking para recuperar o caminho percorrido
        Node atual = destino;
        Deque<RobotAction> caminho = new LinkedList<RobotAction>();
        while(atual.getAction() != null) {
            caminho.addFirst(atual.getAction());
            atual = atual.getParent();
        }
        RobotAction[] solucao = caminho.toArray(new RobotAction[caminho.size()]);
        drawing.setAndDrawSolucao(solucao);
        drawing.saveFile("Resolvido" + search + ".png");

        return caminho.toArray(new RobotAction[caminho.size()]);
    }
}
