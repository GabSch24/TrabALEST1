import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;


public class App {
    public static void main(String[] args) {
        //LinkedList<Palavra> lista = new LinkedList<>();
        ListWord listWord = new ListWord();
        WordTree arvore = new WordTree();
        String aux[];
                
        Path path1 = Paths.get("dicionario_utf8.txt");
        
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("UTF-8"))) {// Charset.defaultCharset())
            String line = reader.readLine();
            while (line != null) {
                aux = line.split(";");
                Palavra p = new Palavra(aux[0],aux[1]);
                //lista.add(p);
                listWord.adicionarPalavra(p);
                arvore.addWord(p);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }  
        //System.out.println("Lista de palavras e seus significados" + lista);
        String s = (arvore.toString());

        try (PrintWriter writer = new PrintWriter(new File("output.txt"))){
            writer.write(s);
        } catch (Exception e) {
            System.out.println("Deu ruim");
        }
        
        List temp = arvore.searchAll("");
        System.out.println(temp);
        System.out.println(temp.size());
        System.out.println(arvore.getTotalWords());
        System.out.println(arvore.getTotalNodes());
        //System.out.println(listWord.getPalavras().toString());
        
    }
 
}
