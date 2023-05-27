import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;


public class App {

    private static int height = 0;
    private static int width = 0;
    private static Node primeiroPorto = new Node();
    private static LinkedList<Node> caminho = new LinkedList<>();

    private static class Node{
        String dot_asterix;
        int visitado;
        int[] coordenadas;
        Arestas arestas;
    }

    private static class Arestas{
        int combustivel;
        LinkedList<Node> vizinhos;
    }

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        Node[][] mapa = readFile("caso20.txt");
        if(mapa == null){
            throw new IllegalArgumentException("Vazia");
        }
        LinkedList<Node> ns = criaGrafo(mapa);

        caminhoPortoBFS(ns, primeiroPorto);
        long endTime = System.currentTimeMillis();
        long executionTime = (endTime - startTime);
        System.out.println("Execution time: " + executionTime + " miliseconds");
    }

    public static Node[][] readFile(String nomeArq) {
        Path path1 = Paths.get(nomeArq);
        Node[][] mapa = new Node[1][1];
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
            String line = null;
            int l = 0;
            while ((line = reader.readLine()) != null) {
                    if(!line.contains(".*")){
                      String[] linhaColuna = line.split(" ");
                      height = Integer.parseInt(linhaColuna[0]);
                      width = Integer.parseInt(linhaColuna[1]);
                      mapa = new Node[height][width];
                    }
                    else{//pega informacoes da linha
                        String[] dados = line.split("");
                        for(int c = 0; c < dados.length; c++){
                            Node n = new Node();
                            Arestas a = new Arestas();
                            int[] coordenadas = new int[2];
                            a.combustivel = 1;
                            n.dot_asterix = dados[c];
                            coordenadas[0] = l-1;
                            coordenadas[1] = c;
                            n.arestas = a;
                            n.coordenadas = coordenadas;
                            mapa[l-1][c] = n;
                            if(n.dot_asterix.equals("1")){
                                primeiroPorto = n;
                            }
                        }
                    }
                    l++;
                }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
        return mapa;
    }

    public static LinkedList<Node> criaGrafo(Node[][] map){
        LinkedList<Node> m = new LinkedList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Node n = map[i][j];
                if(!n.dot_asterix.equals("*")){
                    LinkedList<Node> v = new LinkedList<>();
                    n.arestas.vizinhos = v; 
                    if(i-1 >= 0){ 
                        if(map[i-1][j] != null && !map[i-1][j].dot_asterix.equals("*")){
                            v.add(map[i-1][j]);//norte
                        }
                    }
                    if((j-1) >= 0){
                        if(map[i][j-1] != null && !map[i][j-1].dot_asterix.equals("*")){
                            v.add(map[i][j-1]);//oeste
                        }
                    }
                    if((j+1) <= (width-1)){
                        if(map[i][j+1] != null && !map[i][j+1].dot_asterix.equals("*")){
                            v.add(map[i][j+1]);//leste
                        }
                    }
                    if((i+1) <= (height-1)){
                        if(map[i+1][j] != null && !map[i+1][j].dot_asterix.equals("*")){
                            v.add(map[i+1][j]);//sul
                        }
                    }
                    }
                m.add(n);
            }
        }

        return m;
    }

    public static void caminhoPortoBFS(LinkedList<Node> grafo, Node u){
        for (Node node : grafo) {
            node.visitado = 0;
        }
        u.visitado = 1;
        LinkedList<Node> l = new LinkedList<>();
        l.add(u);
        int i = 0;
        while(!l.isEmpty()){
            u = l.removeFirst();
            ++i; 
            LinkedList<Node> a = u.arestas.vizinhos;
            for (Node node : a) { 
                if(node.visitado == 0){
                    node.visitado = 1;
                    l.addLast(node);     
                    switch (node.dot_asterix) {
                        case "2":
                            //caminhoPortoBFS(grafo, node);
                            l.removeAll(l);
                            break;
                        case "3":
                            caminhoPortoBFS(grafo, node);
                            break;
                        //case "4":
                        //    caminhoPortoBFS(grafo, node);
                        //    break;
                        //case "5":
                        //    caminhoPortoBFS(grafo, node);
                        //    break;
                        //case "6":
                        //    caminhoPortoBFS(grafo, node);
                        //    break;
                        //case "7":
                        //    caminhoPortoBFS(grafo, node);
                        //    break;
                        //case "8":
                        //    caminhoPortoBFS(grafo, node);
                        //    break;
                        //case "9":
                        //    l.removeAll(l);
                        //    break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public static int getPortoIndex(LinkedList<Node> n){
        int index = 0;
        if(n != null){
            for (Node node : n) {
                if(node.dot_asterix.equals("1")){
                    index = n.indexOf(node);
                }
            }
        }
        return index;
    }

}