package br.insper.robot19;

import java.awt.*;

public class DesenhoMapa {
    private GridMap map;
    private int width;
    private int height;
    private br.insper.robot19.Canvas screen;

    /**
     *
     * @param g Um objeto do itpo GridMap
     */
    public DesenhoMapa (GridMap g, String busca){
        this(g, 800, 600, busca);
    }

    /**
     *
     * @param g Um objeto do tipo GridMap
     * @param width A largura desejada para a janela
     * @param height A altura desejada para a janela
     */
    public DesenhoMapa(GridMap g, int width, int height, String busca){
        this.map = g;
        this.width = width;
        this.height = height;
        screen = new Canvas("Proj 3 Busca " + busca, this.width, this.height, Color.lightGray);

    }

    /**
     * Desenha uma representação gráfica do map que foi passado
     *
     * Vocês vão precisar alterar esta classe para mostrar nós expandidos e rota
     */
    public void desenha(){
        int h = map.getHeight();
        int w = map.getWidth();

        int sqx = this.width/w;
        int sqy = this.height/h;



        for (int i=0; i < h; i ++ ){
            for (int j = 0; j < w; j++){


                switch  (map.getBlockType(i, j)) {
                    case FREE:
                        screen.setForegroundColor(Color.WHITE);
                        break;
                    case WALL:
                        screen.setForegroundColor(Color.BLACK);
                        break;
                    case SAND:
                        screen.setForegroundColor(Color.YELLOW);
                        break;
                    case METAL:
                        screen.setForegroundColor(Color.BLUE);
                        break;
                }

                screen.fillRectangle(j*sqx, i*sqy, sqx-2, sqy-2);
            }
        }

        int[] goal = map.getGoal();
        int[] start = map.getStart();
        int div = 2000;

        screen.setForegroundColor(Color.BLACK);
        screen.fillCircle(start[1]*sqx+sqx/div, start[0]*sqy+sqy/div, sqx);

        screen.setForegroundColor(Color.CYAN);
        screen.fillCircle(goal[1]*sqx+sqx/div, goal[0]*sqy+sqy/div, sqx);

    }
    /**
     * Desenha no gráfica do mapa os blocos que ainda estão na fila, ou seja, na fronteira

     */
    public void desenhaFronteira(Block block){
        int h = map.getHeight();
        int w = map.getWidth();

        int sqx = this.width/w;
        int sqy = this.height/h;

        screen.setForegroundColor(Color.GREEN);
        screen.fillCircle(block.col*sqx+sqx/4, block.row*sqy+sqy/4, sqx/2);

        //screen.setForegroundColor(Color.BLUE);
        //screen.fillRectangle(node.getValue().row*sqx, node.getValue().col*sqy, sqx-2, sqy-2);

    }
    /**
     * Desenha no gráfica do mapa os blocos que já foram visitados

     */
    public void desenhaVisitados(Block block){
        int h = map.getHeight();
        int w = map.getWidth();

        int sqx = this.width/w;
        int sqy = this.height/h;

        screen.setForegroundColor(Color.RED);
        screen.fillCircle(block.col*sqx+sqx/4, block.row*sqy+sqy/4, sqx/2);

        //screen.setForegroundColor(Color.BLUE);
        //screen.fillRectangle(node.getValue().row*sqx, node.getValue().col*sqy, sqx-2, sqy-2);

    }

    /**
     * Saves a png file with what's shown in the Canvas
     */
    public void saveFile(String filename){
        screen.saveFile(filename);
    }

    /**
     *
     */
    public void setAndDrawSolucao(RobotAction[] solucao){
        if(solucao == null) {
            System.out.println("Nao foi encontrada solucao para o problema");
        } else {
            int[] s = map.getStart();
            Block atual = new Block(s[0], s[1], map.getBlockType(s[0], s[1]));
            System.out.print("Solução: ");
            for (RobotAction a : solucao) {
                System.out.print(", " + a);
                Block next = map.nextBlock(atual, a);
                map.setRoute(next.row, next.col);
                plotStep(atual, next);
                atual = next;
            }
        }

    }

    /**
     * Desenha uma linha entre dois blocos
     * @param atual
     * @param next
     */
    public void plotStep(Block atual, Block next){
        int h = map.getHeight();
        int w = map.getWidth();

        int sqx = this.width/w;
        int sqy = this.height/h;

        screen.setForegroundColor(Color.GREEN);
        screen.drawLine(atual.col*sqx + sqx/2, atual.row*sqy + sqy/2, next.col*sqx + sqx/2, next.row*sqy + sqy/2);

    }
}
