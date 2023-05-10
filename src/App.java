import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    private class Node{
        int combustivel;
        Node pai = null;
        Node filhoEsq, filhoDir;
        HeapTrees vizinhos;
        
    }

    public static void main(String[] args) throws Exception {
        String[][] mapa = readFile("caso01.txt");
        if(mapa == null){
            throw new IllegalArgumentException("Vazia");
        }
    }

    public static String[][] readFile(String nomeArq) {
        Path path1 = Paths.get(nomeArq);
        String[][] mapa = new String[1][1];
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
            String line = null;
            int l = 0;
            while ((line = reader.readLine()) != null) {
                    if(!line.contains(".*")){
                      String[] linhaColuna = line.split(" ");
                      mapa = new String[Integer.parseInt(linhaColuna[0])][Integer.parseInt(linhaColuna[1])];
                    }
                    else{
                        String[] dados = line.split("");
                        for(int c = 0; c < dados.length; c++){
                            mapa[l-1][c]  = dados[c];
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
}
