import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


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
        
        //System.out.println(listWord.getPalavras().toString());
        // Medindo o tempo de pesquisa

        String palavraPesquisada = "berma"; // Substitua pela palavra que deseja pesquisar
        long resultArv = 0L;
        int vezes = 100;
        for (int i = 0; i < vezes; i++) {
            long startTime = System.nanoTime();

            List temp = arvore.searchAll(palavraPesquisada);

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            resultArv += duration;
            //System.out.println(duration);
        }
        long resultList = 0L;
        for (int i = 0; i < vezes; i++) {
            long startTime = System.nanoTime();

            List temp = listWord.consultarPalavras(palavraPesquisada);

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            resultList += duration;
            //System.out.println(duration);
        }
        List temp = listWord.consultarPalavras(palavraPesquisada);
        //System.out.println(temp);

        String s1 = ("Tempo de pesquisa na arvore para a palavra '" + palavraPesquisada + "': " + resultArv/vezes + " nanossegundos");
        String s2 = ("Tempo de pesquisa na lista para a palavra '" + palavraPesquisada + "': " + resultList/vezes + " nanossegundos");
        try (PrintWriter writer = new PrintWriter(new FileWriter("output2.txt", true))){
            writer.print(s1+"\n");
            writer.print(s2+"\n\n");
        } catch (Exception e) {
            System.out.println("Deu ruim");
        }

    }
    
 
}
