import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class ListWord {
    private List<Palavra> palavras;

    public ListWord() {
        this.palavras = new LinkedList<>();
    }

    public void adicionarPalavra(String palavra, String significado) {
        Palavra novaPalavra = new Palavra(palavra, significado);
        palavras.add(novaPalavra);
    }

    public void adicionarPalavra(Palavra palavra) {
        palavras.add(palavra);
    }

    public void carregarDoCsv(String nomeArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    adicionarPalavra(partes[0].trim(), partes[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Palavra> consultarPalavras(String prefixo) {
        List<Palavra> resultados = new LinkedList<>();
        for (Palavra palavra : palavras) {
            if (palavra.getPalavra().startsWith(prefixo)) {
                resultados.add(palavra);
            }
        }
        return resultados;
    }
    public List<Palavra> getPalavras() {
        return palavras;
    }
}