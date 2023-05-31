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


public class App {

    private static int height = 0;
    private static int width = 0;
    private static ArrayList<Node> portos = new ArrayList<>();
    private static List<Node> ports = new ArrayList<>();
    private static ArrayList<Node> c = new ArrayList<>();
    private static int total = 0;
    private static class Node{
        String dot_asterix;
        int visitado;
        Arestas arestas;
        Node vimDeQuem;
        public String getV(){return dot_asterix;}
    }
    private static class Arestas{
        LinkedList<Node> vizinhos;
    }

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        Node[][] mapa = readFile("caso20.txt");
        if(mapa == null){
            throw new IllegalArgumentException("Vazia");
        }
        LinkedList<Node> ns = criaGrafo(mapa);
        ports = portos.stream().sorted(Comparator.comparing(Node::getV)).collect(Collectors.toList());
        caminhaInicio(ns, ports.get(0));
        long endTime = System.currentTimeMillis();
        long executionTime = (endTime - startTime);
        System.out.println("Total de combustivel gasto: "+ total);
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
                            n.dot_asterix = dados[c];
                            n.arestas = a;
                            mapa[l-1][c] = n;
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

    public static LinkedList<Node> criaGrafo(Node[][] map){
        LinkedList<Node> m = new LinkedList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Node n = map[i][j]; //nodo atual         
                if(!n.dot_asterix.equals("*")){
                    LinkedList<Node> v = new LinkedList<>(); //lista dos nodos vizinhos
                    n.arestas.vizinhos = v;  //armazenar v nos vizinhos de n
                    if(i-1 >= 0){ 
                        Node norte = map[i-1][j];
                        if(!norte.dot_asterix.equals("*")){
                            v.add(norte);
                        }
                    }
                    if((j-1) >= 0){
                        Node oeste = map[i][j-1];
                        if(!oeste.dot_asterix.equals("*")){
                            v.add(oeste);
                        }
                    }
                    if((j+1) < width){
                        Node leste = map[i][j+1];
                        if(!leste.dot_asterix.equals("*")){
                            v.add(leste);
                        }
                    }
                    if((i+1) < height){
                        Node sul = map[i+1][j];
                        if(!sul.dot_asterix.equals("*")){
                            v.add(sul);
                        }
                    }
                }
                m.add(n);
            }
        }
        return m;
    }

    public static void caminho(Node fim){
        if(fim.vimDeQuem != null){
            caminho(fim.vimDeQuem);
            c.add(fim.vimDeQuem);
        }
    }

    public static void caminhaInicio(LinkedList<Node> g, Node inicio){
            Node primeiroP = inicio;
            Node no;
            int r;
            while(!ports.isEmpty()){
                no = ports.remove(0);
                r = caminha(g, no);
                if(ports.isEmpty()){
                    ports.add(primeiroP);
                    caminha(g, no);
                    ports.remove(0);
                }
                if(r == -1 && !ports.isEmpty()){
                    ports.remove(0);
                    caminha(g, no);
                }
            }
            
    }
    public static int caminha(LinkedList<Node> g, Node inicio){
        for (Node node : g) {
            node.visitado = 0;
            node.vimDeQuem = null;
        }
        inicio.visitado = 1;
        LinkedList<Node> l = new LinkedList<>();
        l.add(inicio);
        Node u = new Node();
        while(!l.isEmpty()){
            u = l.removeFirst();
            for (Node node : u.arestas.vizinhos) {
                if(node.visitado == 0){
                    node.vimDeQuem = u;
                    node.visitado = 1;
                    l.add(node);
                    if(!ports.isEmpty()){
                        if(node.equals(ports.get(0))){
                            l.removeAll(l);
                            caminho(node);
                            System.out.println(inicio.dot_asterix+" - "+node.dot_asterix+" --> "+c.size());
                            total+=c.size();
                            c.removeAll(c);
                            return 0;
                        }
                    }
                }
            }
        }
        return -1;
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