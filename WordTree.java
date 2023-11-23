// 4645G-04 - Algoritmos e Estruturas de Dados I
// 2023-1

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLongArray;

public class WordTree {
    
    // Classe interna
    private class CharNode {
        private char character;
	    private String significado;
        private boolean isFinal;
        private CharNode father;
        private List<CharNode> children;

        public CharNode(char character) {
            this.character = character;
            significado = null;
            isFinal = false;
            father = null;
            children = new LinkedList<CharNode>();
        }
        
        public CharNode(char character, CharNode father, boolean isFinal, String significado) {
            this.character = character;
            this.significado = significado;
            this.isFinal = isFinal;
            this.father = father;
            children = new LinkedList<CharNode>();
        }

        /**
        * Adiciona um filho (caracter) no nodo. Não pode aceitar caracteres repetidos.
        * @param character - caracter a ser adicionado
        * @param isfinal - se é final da palavra ou não
        */
        public CharNode addChild (char character, boolean isfinal, String sig) {
            for (CharNode charNode : children) {
                if (charNode.character == character){
                    charNode.isFinal = isfinal;
                    charNode.significado = sig;
                    return charNode;
                } 
            }
            children.add(new CharNode(character, this, isfinal, sig));

            for (CharNode charNode : children) {
                if (charNode.character == character) return charNode;
            }
            return null;
        }
        
        public int getNumberOfChildren () {
            return children.size();
        }
        
        public CharNode getChild (int index) {
            return children.get(index);
        }

        /**
         * Obtém a palavra correspondente a este nodo, subindo até a raiz da árvore
         * @return a palavra
         */
        private String getWord() {
            CharNode temp = this;
            String res = "";
            while (temp != root){
                res = temp.character + res;
                temp = temp.father;
            }
            return res;
        }
        
        /**
        * Encontra e retorna o nodo que tem determinado caracter.
        * @param character - caracter a ser encontrado.
        */
        public CharNode findChildChar (char character) {
            for (CharNode charNode : children) {
                if (charNode.character == character) return charNode;
            }
            return null;
        }
        
    }


    
    // Atributos
    private CharNode root;
    private int totalNodes = 0;
    private int totalWords = 0;
    


    // Construtor
    public WordTree() {
      root = new CharNode('0');
    }


    
    // Metodos
    public int getTotalWords() {
        return totalWords;
    }

    public int getTotalNodes() {
        return totalNodes;
    }
    
    /**
    *Adiciona palavra na estrutura em árvore
    *@param word
    */
    public void addWord(Palavra word) {
        char[] temp = word.getPalavra().toUpperCase().toCharArray();
        CharNode next = root;
        for (int i = 0; i < word.getPalavra().length()-1; i++) {
            next = next.addChild(temp[i],false, null);
            totalNodes++;
        }
        next.addChild(temp[word.getPalavra().length()-1],true, word.getSignificado());
        totalNodes++;
        totalWords++;
    }
    
    /**
     * Vai descendo na árvore até onde conseguir encontrar a palavra
     * @param word
     * @return o nodo final encontrado
     */
    private CharNode findCharNodeForWord(String word) {
        return null;
    }

    /**
    * Percorre a árvore e retorna uma lista com as palavras iniciadas pelo prefixo dado.
    * Tipicamente, um método recursivo.
    * @param prefix
    */
    public List<String> searchAll(String prefix) {
        List<String> listaPalavras = new ArrayList<>();
        CharNode temp = root;
        for (int i = 0; i < prefix.length(); i++) {
            temp = temp.findChildChar(prefix.toUpperCase().charAt(i));
            if (temp == null) return null;
        }
        searchAllRec(prefix.toUpperCase(), listaPalavras, temp);
        return listaPalavras;
    }   
    private List<String> searchAllRec(String prefix, List<String> result, CharNode node){
        if (node.isFinal) result.add(node.getWord());
        for (CharNode charNode : node.children) {
            searchAllRec(prefix, result, charNode);
        }
        return result;
    }
    
    private void DumpRelacoes(CharNode ref, List<String> result){
        if(ref.getNumberOfChildren()!=0)
            for(CharNode n: ref.children){
                result.add("  "+ref.character + Integer.decode("#"+ref.toString().substring(18))+" -> "+
                n.character + Integer.decode("#"+n.toString().substring(18)) +";\n");
                DumpRelacoes(n, result);
            }
    }

    private void DumpNodes(CharNode ref, List<String> result){
        if (!ref.isFinal) result.add("  "+ref.character + Integer.decode("#"+ref.toString().substring(18))+ " [label = " + ref.character + "]\n");
        else result.add("  "+ref.character + Integer.decode("#"+ref.toString().substring(18))+ " [label = " + ref.character + ", style = filled, fillcolor = GRAY]\n");
        for(CharNode n: ref.children){
            DumpNodes(n, result);
        }
    }

    public String toString(){
        if(root==null){
            return "[árvore vazia]";
        }
        else{
            List<String> resultado = new ArrayList<>();
            resultado.add("digraph G {\n");
            DumpNodes(root, resultado);
            DumpRelacoes(root, resultado);
            resultado.add("}\n");
            String aux="";
            for(String s: resultado)
                aux += s;
            return aux;
        }
    }
}