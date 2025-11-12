package com.livraria.service; // Pacote para classes de serviço (lógica de negócio). 
 
import com.livraria.data.DadosLivraria; // Importa a classe de acesso a dados. 
import com.livraria.model.Livro;     // Importa a classe de modelo Livro. 
 
import java.util.List; 
import java.util.Optional; // Para lidar com a possibilidade de um livro não ser encontrado. 
import java.util.stream.Collectors; // Usado para coletar resultados de operações de stream. 
 
/** 
 * Classe responsável por gerenciar as operações relacionadas aos livros (catálogo). 
 * Esta é uma classe 'Controller' (Controlador) no padrão MVC, pois contém a lógica de negócio 
 * para manipular os objetos Livro e interage com a camada de persistência (DadosLivraria). 
 */ 
public class GerenciadorLivros { 
    private List<Livro> livros; // Uma lista para manter os livros em memória enquanto o programa roda. 
 
    /** 
     * Construtor do GerenciadorLivros. 
     * Ao ser instanciado, ele carrega todos os livros existentes do arquivo de dados. 
     */ 
    public GerenciadorLivros() { 
        this.livros = DadosLivraria.carregarLivros(); // Carrega os dados ao inicializar. 
    } 
 
    /** 
     * Retorna a lista completa de todos os livros atualmente carregados. 
     * @return Uma lista de objetos Livro. 
     */ 
    public List<Livro> getTodosLivros() { 
        return livros; 
    } 
 
    /** 
     * Busca um livro pelo seu ISBN. 
     * @param isbn O ISBN do livro a ser buscado. 
     * @return Um Optional contendo o Livro se encontrado, ou um Optional vazio se não encontrado. 
     */ 
    public Optional<Livro> buscarLivroPorIsbn(String isbn) { 
        // Usa Stream API para filtrar a lista de livros e encontrar o primeiro que corresponde ao ISBN. 
        return livros.stream() 
                     .filter(l -> l.getIsbn().equals(isbn)) // Filtra os livros pelo ISBN. 
                     .findFirst();                          // Retorna o primeiro que encontrar (ou vazio). 
    } 
 
    /** 
     * Busca livros que contenham um termo específico no título (busca case-insensitive). 
     * @param termo O termo a ser buscado no título. 
     * @return Uma lista de livros cujos títulos contêm o termo. 
     */ 
    public List<Livro> buscarLivrosPorTitulo(String termo) { 
        return livros.stream() 
                     .filter(l -> l.getTitulo().toLowerCase().contains(termo.toLowerCase())) 
                     .collect(Collectors.toList()); // Coleta os resultados em uma nova lista. 
    } 
 
    /** 
     * Adiciona um novo livro ao catálogo. 
     * @param novoLivro O objeto Livro a ser adicionado. 
     */ 
    public void adicionarLivro(Livro novoLivro) { 
        // Verifica se já existe um livro com o mesmo ISBN para evitar duplicatas. 
        if (!buscarLivroPorIsbn(novoLivro.getIsbn()).isPresent()) { 
            livros.add(novoLivro); // Adiciona o livro à lista em memória. 
            DadosLivraria.salvarLivros(livros); // Salva a lista atualizada no arquivo. 
            System.out.println("Livro adicionado: " + novoLivro.getTitulo()); 
        } else { 
            System.out.println("Erro: Livro com ISBN " + novoLivro.getIsbn() + " já existe."); 
        } 
    } 
 
    /** 
     * Atualiza o preço e o estoque de um livro existente. 
     * @param isbn O ISBN do livro a ser atualizado. 
     * @param novoPreco O novo preço do livro. 
     * @param novoEstoque O novo estoque do livro. 
     * @return true se o livro foi encontrado e atualizado, false caso contrário. 
     */ 
    public boolean atualizarLivro(String isbn, double novoPreco, int novoEstoque) { 
        Optional<Livro> livroOpt = buscarLivroPorIsbn(isbn); // Tenta encontrar o livro. 
        if (livroOpt.isPresent()) { // Se o livro foi encontrado... 
            Livro livro = livroOpt.get(); // Obtém o objeto Livro do Optional. 
            livro.setPreco(novoPreco);    // Atualiza o preço. 
            livro.setEstoque(novoEstoque); // Atualiza o estoque. 
            DadosLivraria.salvarLivros(livros); // Salva as alterações no arquivo. 
            System.out.println("Livro " + livro.getTitulo() + " atualizado."); 
            return true; 
        } else { 
            System.out.println("Erro: Livro com ISBN " + isbn + " não encontrado para atualização."); 
            return false; 
        } 
    } 
 
    /** 
     * Remove um livro do catálogo. 
     * @param isbn O ISBN do livro a ser removido. 
     * @return true se o livro foi removido, false caso contrário. 
     */ 
    public boolean removerLivro(String isbn) { 
        // removeIf: Remove todos os elementos da coleção que satisfazem a condição. 
        boolean removido = livros.removeIf(l -> l.getIsbn().equals(isbn)); 
        if (removido) { 
            DadosLivraria.salvarLivros(livros); // Salva a lista após a remoção. 
            System.out.println("Livro com ISBN " + isbn + " removido."); 
        } else { 
            System.out.println("Erro: Livro com ISBN " + isbn + " não encontrado para remoção."); 
        } 
        return removido; 
    } 
}