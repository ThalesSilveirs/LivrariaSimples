package com.livraria.data; // Pacote para classes que lidam com dados e persistência. 

import com.livraria.model.Livro;
import com.livraria.model.ItemCarrinho;

import java.io.*; // Classes para operações de entrada/saída (arquivos). 
import java.util.ArrayList; // Para usar listas dinâmicas. 
import java.util.List; // Interface para listas. 
import java.util.Optional; // Usado para lidar com valores que podem estar presentes ou ausentes. 
import java.util.stream.Collectors; // Para operações com Streams (Java 8+). 

/**
 * Classe responsável por simular a persistência de dados em arquivos de texto.
 * Esta classe atua como parte da camada 'Model' (Modelo) no padrão MVC,
 * especificamente a parte de acesso a dados.
 * Em um sistema real, isso seria uma conexão com um banco de dados.
 */
public class DadosLivraria {
    // Constantes para os nomes dos arquivos que simulam o banco de dados.
    private static final String ARQUIVO_LIVROS = "data/livros.txt";
    private static final String ARQUIVO_CARRINHO = "data/carrinho_atual.txt";

    /**
     * Carrega a lista de livros do arquivo de texto.
     * 
     * @return Uma lista de objetos Livro.
     */
    public static List<Livro> carregarLivros() {
        List<Livro> livros = new ArrayList<>(); // Cria uma nova lista vazia para armazenar os livros.
        // O bloco try-with-resources garante que o BufferedReader será fechado
        // automaticamente.
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_LIVROS))) {
            String linha;
            // Lê cada linha do arquivo até o final.
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";"); // Divide a linha em partes usando ';' como delimitador.
                // Verifica se a linha tem o número correto de partes para um livro.
                if (partes.length == 6) {
                    // Cria um novo objeto Livro com os dados da linha e o adiciona à lista.
                    livros.add(new Livro(
                            partes[0], partes[1], partes[2], partes[3],
                            Double.parseDouble(partes[4].trim()), // Converte String para double.
                            Integer.parseInt(partes[5].trim()) // Converte String para int.
                    ));
                }
            }
        } catch (IOException e) { // Captura exceções de I/O (ex: arquivo não encontrado, erro de
                                  // leitura/escrita).
            System.err.println("Erro ao carregar livros: " + e.getMessage()); // Exibe o erro no console de erros.
        }
        return livros; // Retorna a lista de livros carregados.
    }

    /**
     * Salva a lista atual de livros no arquivo de texto, sobrescrevendo o conteúdo
     * existente.
     * 
     * @param livros A lista de livros a ser salva.
     */
    public static void salvarLivros(List<Livro> livros) {
        // O bloco try-with-resources garante que o BufferedWriter será fechado
        // automaticamente.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_LIVROS))) {
            // Itera sobre cada livro na lista.
            for (Livro livro : livros) {
                // Escreve os atributos do livro na linha, separados por ';'.
                bw.write(livro.getIsbn() + ";" + livro.getTitulo() + ";" + livro.getAutor() + ";" +
                        livro.getCategoria() + ";" + livro.getPreco() + ";" + livro.getEstoque());
                bw.newLine(); // Adiciona uma nova linha após cada registro de livro.
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar livros: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de itens do carrinho do arquivo de texto.
     * Requer a lista de todos os livros para poder associar o ISBN a um objeto
     * Livro completo.
     * 
     * @param todosLivros A lista de todos os livros disponíveis no sistema.
     * @return Uma lista de objetos ItemCarrinho.
     */
    public static List<ItemCarrinho> carregarCarrinho(List<Livro> todosLivros) {
        List<ItemCarrinho> carrinho = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_CARRINHO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) { // Formato: ISBN;Quantidade
                    String isbn = partes[0];
                    int quantidade = Integer.parseInt(partes[1]);
                    // Busca o objeto Livro correspondente ao ISBN na lista de todos os livros.
                    Optional<Livro> livroOpt = todosLivros.stream().filter(l -> l.getIsbn().equals(isbn)).findFirst();
                    // Se o livro for encontrado, cria um ItemCarrinho e o adiciona à lista.
                    livroOpt.ifPresent(livro -> carrinho.add(new ItemCarrinho(livro, quantidade)));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar carrinho: " + e.getMessage());
        }
        return carrinho;
    }

    /**
     * Salva a lista atual de itens do carrinho no arquivo de texto, sobrescrevendo
     * o conteúdo existente.
     * 
     * @param carrinho A lista de itens do carrinho a ser salva.
     */
    public static void salvarCarrinho(List<ItemCarrinho> carrinho) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_CARRINHO))) {
            for (ItemCarrinho item : carrinho) {
                // Salva apenas o ISBN do livro e a quantidade do item no carrinho.
                bw.write(item.getLivro().getIsbn() + ";" + item.getQuantidade());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar carrinho: " + e.getMessage());
        }
    }
}