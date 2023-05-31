import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**Codigo de caminhamento por um Mapa
 * @author Lucas Chagas
 * @version 1.0
 * @since 31-05-23
 */
public class App {

    private static int height = 0;//altura da raiz
    private static int width = 0; //largura da matriz

    private static List<Node> portos = new ArrayList<>(); //lista dos portos nao ordenada
    private static int totalPercorrido = 0;

    private static int total = 0;

    /**SubClasse Node para fazer os nodos do Grafo
     * @param dot_asterix identifica se o nodo eh * ou .
     * @param visitado - armazena 0 ou 1 caso foi visitado ou nao
     * @param arestas eh uma lista de referencias dos vizinhos
     * @param vimDeQuem, como o proprio nome diz, eh a referencia para o nodo que o caminnhamento veio anteriormente
     */
    private static class Node{
        String dot_asterix;
        int visitado;
        LinkedList<Node> arestas;
        Node vimDeQuem;
        /**Metodo getter para a identificacao se eh . ou * 
         * @return String - identificacao se eh . ou * 
         */
        public String getV(){return dot_asterix;}
    }

    /** Metodo de testes, craicao do grafo e leitura do arquivo
     * @param startTime comeca a contagem do tempo de execucao
     * @param mapa eh a matriz de nodos criada apartir do arquivo txt porem nao tem seus vizinhos
     * @param ns lista de nodos da matriz com seus vizinhos
     * @param endTime termina a contagem do tempo de execucao
     * @param executionTime variavel com a subtracao do startTime e do endTime para saber qual foi o tempo
     */
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();

        Node[][] mapa = readFile("caso20.txt");

        if(mapa == null){
            throw new IllegalArgumentException("Vazia");
        }

        LinkedList<Node> ns = criaGrafo(mapa);

        //Ordenacao crescente dos portos na lista portos por via stream
        portos = portos.stream().sorted(Comparator.comparing(Node::getV)).collect(Collectors.toList());

        caminhaInicio(ns, portos.get(0));//caminhamento BFS

        long endTime = System.currentTimeMillis();
        long executionTime = (endTime - startTime);

        System.out.println("Total de combustivel gasto: "+ total);
        System.out.println("Execution time: " + executionTime + " miliseconds");
    }

    /** Metodo para a leitura do 
     * @param mapa Node[][] - declaracao da matriz de nodos
     * @param nomeArq String - nome do arquivo que sera lido
     * @param line String - guarda a linha em que esta o loop
     * @param l Integer - contador de quantas linha o while ja percorreu
     * @param linhaColuna String[] - array de strings que armazena os * e .
     * @param dados String[] - array de string 
     * @param n Node - variavel que armazenara as informacoes de cada letra da linha
     * @param v LinkedList<Node> - lista que armazena os vizinhos de n
     * @return Matriz de Nodos
     */
    public static Node[][] readFile(String nomeArq) {
        Path path1 = Paths.get(nomeArq);
        Node[][] mapa = new Node[1][1];
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
            String line = null;
            int l = 0;
            while ((line = reader.readLine()) != null) {
                    if(!line.contains(".*")){
                      String[] linhaColuna = line.split(" ");//.split separa de acordo com " " a linha, botando-a em uma lista

                      height = Integer.parseInt(linhaColuna[0]);//converte string para int
                      width = Integer.parseInt(linhaColuna[1]);

                      mapa = new Node[height][width];//declara novamente o mapa so que com as dimensoes certas
                    }
                    else{//pega informacoes da linha
                        String[] dados = line.split("");
                        //percorre letra por letra da linha
                        for(int c = 0; c < dados.length; c++){

                            Node n = new Node();
                            LinkedList<Node> v = new LinkedList<>();

                            n.dot_asterix = dados[c]; //armazena o conteudo do index c no dot_asterix do n
                            n.arestas = v;  //armazena v em arestas de n

                            mapa[l-1][c] = n;//depois de preencher as variaveis de n, guarda em mapa

                            //caso dot_asterix seja 1-9 guarda numa lista para uso no caminnhamento
                            switch(n.dot_asterix){
                                case "1":
                                    portos.add(n);
                                    break;
                                case "2":
                                    portos.add(n);
                                    break;
                                case "3":
                                    portos.add(n);
                                    break;
                                case "4":
                                    portos.add(n);
                                    break;
                                case "5":
                                    portos.add(n);
                                    break;
                                case "6":
                                    portos.add(n);
                                    break;
                                case "7":
                                    portos.add(n);
                                    break;
                                case "8":
                                    portos.add(n);
                                    break;
                                case "9":
                                    portos.add(n);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    ++l;
                }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
        return mapa;
    }

    
    /** Metodo para criar a lista de Node com os vizinhos preenchidos
     * @param map Node[][] - matriz do mapa
     * @param m LinkedList<Node> - lista de nodos da matriz 
     * @param n Node - variavel que armazena o nodo[i][j]
     * @param v LinkedList<Node> - lista que armazena os vizinhos de n
     * @param norte Node - armazena o vizinho [i-1][j] do nodo n 
     * @param oeste Node - armazena o vizinho [i][j-1] do nodo n 
     * @param leste Node - armazena o vizinho [i][j+1] do nodo n 
     * @param sul Node - armazena o vizinho [i+1][j] do nodo n 
     * @return LinkedList<Node> 
     */
    public static LinkedList<Node> criaGrafo(Node[][] map){
        LinkedList<Node> m = new LinkedList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                Node n = map[i][j]; //nodo atual  
                LinkedList<Node> v = n.arestas;

                //se a identificao de n for * ele passa se nao ele entre dentro do if       
                if(!n.dot_asterix.equals("*")){
                    //verificacoes se 0 <= i+/-1 < width, 0 <= j+/-1 <= height e se os nodos vizihos nao sao *
                    if(i-1 >= 0){

                        Node norte = map[i-1][j];

                        //se a identificao de norte for * ele passa se nao ele entre dentro do if 
                        if(!norte.dot_asterix.equals("*")){
                            v.add(norte);//adiciona o norte na lista de vizinhos de n
                        }

                    }
                    if((j-1) >= 0){

                        Node oeste = map[i][j-1];

                        if(!oeste.dot_asterix.equals("*")){
                            v.add(oeste);//adiciona o oeste na lista de vizinhos de n
                        }

                    }
                    if((j+1) < width){

                        Node leste = map[i][j+1];

                        if(!leste.dot_asterix.equals("*")){
                            v.add(leste);//adiciona o leste na lista de vizinhos de n
                        }

                    }
                    if((i+1) < height){

                        Node sul = map[i+1][j];

                        if(!sul.dot_asterix.equals("*")){
                            v.add(sul);//adiciona o sul na lista de vizinhos de n
                        }

                    }
                    m.add(n); // adiciona n no mapa
                }         
            }
        }
        return m;
    }

    
    /** Metodo que contabliza quanto precisou para ir de um ponto A-B
     * @param fim ultmio nodo do caminho
     */
    public static void caminho(Node fim){
        if(fim.vimDeQuem != null){
            caminho(fim.vimDeQuem);//faz recursao para ir ate o primeiro do caminho
            ++totalPercorrido;//na volta contabiliza
        }
    }

    
    /** Metodo inicial do BFS
     * @param primeiroP Node - armazena o porto 1
     * @param no Node - armazena o porto que sera iniciado o caminhamento
     * @param g LinkedList<Node> - lista dos nodos da matriz
     * @param r Integer - variavel de verificacao se do porto A-B tem caminho
     * @param inicio Node - variavel que sera usada como porto
     */
    public static void caminhaInicio(LinkedList<Node> g, Node inicio){
            Node primeiroP = inicio;
            Node no;

            int r;

            while(!portos.isEmpty()){ 

                no = portos.remove(0);//remove conteudo (porto) e bota em no

                r = caminha(g, no);

                if(portos.isEmpty()){//caso a lista esteja vazia, no = ultimo da lista,                              
                    
                    portos.add(primeiroP);//readiciona primeiroP na lista    //"no" ira para o porto 1 
                    
                    caminha(g, no);

                    portos.remove(0);

                }
                if(r == -1 && !portos.isEmpty()){//caso nao tenha caminho A-B ele tenta de A-C, assim por diante
                    
                    portos.remove(0);

                    caminha(g, no);

                }
            }
            
    }
    
    /** Metodo BFS
     * @param l LinkedList<Node> - lista de nodos caminhados 
     * @param g LinkedList<Node> - lista dos nodos da matriz
     * @param inicio Node - variavel que sera usada como porto
     * @return int
     */
    public static int caminha(LinkedList<Node> g, Node inicio){
        LinkedList<Node> l = new LinkedList<>();
        Node u = new Node(); //nodos da lista l

        for (Node node : g) {//limpa os nodos
            node.visitado = 0;
            node.vimDeQuem = null;
        }

        inicio.visitado = 1;// 1 para vistado 0 para nao

        l.add(inicio);
        
        while(!l.isEmpty()){
            u = l.removeFirst();//guarda em u e remove da lista l
            for (Node node : u.arestas) {//percorre os vizinhos de u
                if(node.visitado == 0){

                    node.vimDeQuem = u;
                    node.visitado = 1;

                    l.add(node);

                    if(!portos.isEmpty()){
                        if(node.equals(portos.get(0))){

                            l.removeAll(l);//quando for um porto deleta tudo da lista

                            caminho(node);//faz caminha de volta para o porto em que veio

                            System.out.println(inicio.dot_asterix+" - "+node.dot_asterix+" --> "+totalPercorrido);

                            totalPercorrido = 0;

                            return 0;
                        }
                    }
                }
            }
        }
        return -1;
    }
}