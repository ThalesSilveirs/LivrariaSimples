// Define o pacote onde esta classe está localizada. 
// Pacotes ajudam a organizar classes e evitar conflitos de nomes. 
package com.livraria.model;

/**
 * Representa um livro no sistema da livraria.
 * Esta é uma classe 'Model' (Modelo) no padrão MVC, ou seja, ela encapsula os
 * dados e o comportamento básico de uma
 * entidade.
 */
public class Livro {
    // Atributos (características) de um livro
    private String isbn; // Identificador único do livro (International Standard Book Number)
    private String titulo;
    private String autor;
    private String categoria;
    private double preco;
    private int estoque; // Quantidade de livros disponível em estoque

    /**
     * Construtor da classe Livro.
     * É usado para criar novas instâncias de Livro, inicializando seus atributos.
     * 
     * @param isbn      O ISBN do livro.
     * @param titulo    O título do livro.
     * @param autor     O autor do livro.
     * @param categoria A categoria ou gênero do livro.
     * @param preco     O preço de venda do livro.
     * @param estoque   A quantidade disponível em estoque.
     */
    public Livro(String isbn, String titulo, String autor, String categoria, double preco, int estoque) {
        this.isbn = isbn; // 'this' refere-se ao atributo da classe, distinguindo-o do parâmetro do
                          // construtor.
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.preco = preco;
        this.estoque = estoque;
    }

    // Métodos 'Getters' (Acessores): Permitem ler os valores dos atributos
    // (normalmente são públicos).
    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }

    // Métodos 'Setters' (Modificadores): Permitem alterar os valores de alguns
    // atributos.
    // Nem todos os atributos precisam de setters, dependendo das regras de negócio.
    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    /**
     * Sobrescreve o método toString() padrão para fornecer uma representação
     * textual mais útil do objeto Livro.
     * Ideal para depuração e exibição simples no console.
     * 
     * @return Uma string formatada com os detalhes do livro.
     */
    @Override // Anotação que indica que este método está sobrescrevendo um método da
              // superclasse (Object).
    public String toString() {
        return "ISBN: " + isbn + ", Título: " + titulo + ", Autor: " + autor +
                ", Categoria: " + categoria + ", Preço: R$" + String.format("%.2f", preco) + // Formata o preço com 2
                                                                                             // casas decimais.
                ", Estoque: " + estoque;
    }
}