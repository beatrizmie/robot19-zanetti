package br.insper.robot19;

import java.awt.*;

public class DesenhoMapa {
    private GridMap map;
    private int width;
    private int height;
    private Canvas screen;
    private int sqx;
    private int sqy;
    /**
     *
     * @param g Um objeto do itpo GridMap
     */
    public DesenhoMapa (GridMap g){
        this(g, 800, 600    );
    }

    /**
     *
     * @param g Um objeto do tipo GridMap
     * @param width A largura desejada para a janela
     * @param height A altura desejada para a janela
     */
    public DesenhoMapa(GridMap g, int width, int height){
        this.map = g;
        this.width = width;
        this.height = height;
        this.sqx = this.width/map.getHeight();
        this.sqy = this.height/map.getWidth();
        screen = new Canvas("Proj 3 Busca", this.width, this.height, Color.lightGray);

    }

    /**
     * Desenha uma representação gráfica do map que foi passado
     *
     * Vocês vão precisar alterar esta classe para mostrar nós expandidos e rota
     */
    public void desenha(){
        int h = map.getHeight();
        int w = map.getWidth();



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

        screen.setForegroundColor(Color.GREEN);
        screen.fillCircle(start[1]*sqx+sqx/4, start[0]*sqy+sqy/4, sqx/2);

        screen.setForegroundColor(Color.RED);
        screen.fillCircle(goal[1]*sqx+sqx/4, goal[0]*sqy+sqy/4, sqx/2);

    }
    /**
     * Desenha no gráfica do mapa os blocos que ainda estão na fila, ou seja, na fronteira

     */
    public void desenhaFronteira(Node node){
        screen.setForegroundColor(Color.BLUE);
        screen.fillRectangle(node.getValue().row*sqx, node.getValue().col*sqy, sqx-2, sqy-2);

    }
    /**
     * Desenha no gráfica do mapa os blocos que já foram visitados

     */
    public void desenhaVisitados(){

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
