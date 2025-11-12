package com.livraria.model;

/**
 * Representa um item dentro do carrinho de compras.
 * Esta classe também é um 'Model', contendo dados específicos de um item no
 * carrinho.
 */
public class ItemCarrinho {
    private Livro livro; // O objeto Livro que está no carrinho. Representa uma associação entre
                         // ItemCarrinho e Livro.
    private int quantidade; // A quantidade desse livro no carrinho.

    /**
     * Construtor para criar um ItemCarrinho.
     * 
     * @param livro      O objeto Livro associado a este item.
     * @param quantidade A quantidade deste livro a ser adicionada ao carrinho.
     */
    public ItemCarrinho(Livro livro, int quantidade) {
        this.livro = livro;
        this.quantidade = quantidade;
    }

    // Getters para acessar o livro e a quantidade.
    public Livro getLivro() {
        return livro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // Setter para modificar a quantidade do item no carrinho.
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Calcula o subtotal para este item no carrinho (preço do livro * quantidade).
     * 
     * @return O valor subtotal do item.
     */
    public double getSubtotal() {
        return livro.getPreco() * quantidade;
    }

    /**
     * Retorna uma representação em String do item do carrinho, útil para exibição.
     * 
     * @return Uma string formatada mostrando a quantidade e o título do livro.
     */
    @Override
    public String toString() {
        return quantidade + "x " + livro.getTitulo() + " (R$" + String.format("%.2f", getSubtotal()) + ")";
    }
}