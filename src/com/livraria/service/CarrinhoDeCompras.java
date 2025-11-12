package com.livraria.service; // Pacote para classes de serviço (lógica de negócio). 

import com.livraria.data.DadosLivraria; // Importa a classe de acesso a dados. 
import com.livraria.model.Livro; // Importa a classe de modelo Livro. 
import com.livraria.model.ItemCarrinho; // Importa a classe de modelo ItemCarrinho. 

import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // Para lidar com a possibilidade de um item ou livro não ser encontrado. 

/**
 * Classe responsável por gerenciar as operações do carrinho de compras.
 * Esta também é uma classe 'Controller', manipulando os itens do carrinho
 * e interagindo com o GerenciadorLivros e DadosLivraria.
 */
public class CarrinhoDeCompras {
    private List<ItemCarrinho> itens; // Lista de itens atualmente no carrinho.
    private GerenciadorLivros gerenciadorLivros; // Dependência: precisa acessar os livros para adicionar ao carrinho.

    /**
     * Construtor do CarrinhoDeCompras.
     * 
     * @param gerenciadorLivros Uma instância de GerenciadorLivros para buscar
     *                          informações de livros.
     */
    public CarrinhoDeCompras(GerenciadorLivros gerenciadorLivros) {
        this.gerenciadorLivros = gerenciadorLivros;
        // Carrega o carrinho existente do arquivo ao inicializar, usando a lista de
        // todos os livros.
        this.itens = DadosLivraria.carregarCarrinho(gerenciadorLivros.getTodosLivros());
    }

    /**
     * Retorna a lista atual de itens no carrinho.
     * 
     * @return Uma lista de objetos ItemCarrinho.
     */
    public List<ItemCarrinho> getItens() {
        return itens;
    }

    /**
     * Adiciona um item ao carrinho de compras.
     * 
     * @param isbn       O ISBN do livro a ser adicionado.
     * @param quantidade A quantidade a ser adicionada.
     */
    public void adicionarItem(String isbn, int quantidade) {
        // Tenta encontrar o livro pelo ISBN usando o GerenciadorLivros.
        Optional<Livro> livroOpt = gerenciadorLivros.buscarLivroPorIsbn(isbn);
        if (livroOpt.isPresent()) { // Se o livro existe...
            Livro livro = livroOpt.get();
            // Verifica se há estoque suficiente antes de adicionar.
            if (livro.getEstoque() >= quantidade) {
                // Tenta encontrar o item no carrinho (se já existe um item para este livro).
                Optional<ItemCarrinho> itemExistente = itens.stream()
                        .filter(item -> item.getLivro().getIsbn().equals(isbn))
                        .findFirst();

                if (itemExistente.isPresent()) {
                    // Se o item já existe, apenas atualiza a quantidade.
                    itemExistente.get().setQuantidade(itemExistente.get().getQuantidade() + quantidade);
                } else {
                    // Se não existe, adiciona um novo ItemCarrinho.
                    itens.add(new ItemCarrinho(livro, quantidade));
                }
                System.out.println(quantidade + "x " + livro.getTitulo() + " adicionado ao carrinho.");
                DadosLivraria.salvarCarrinho(itens); // Salva o estado atual do carrinho.
            } else {
                System.out.println(
                        "Estoque insuficiente para " + livro.getTitulo() + ". Disponível: " + livro.getEstoque());
            }
        } else {
            System.out.println("Livro com ISBN " + isbn + " não encontrado.");
        }
    }

    /**
     * Remove um item do carrinho de compras.
     * 
     * @param isbn O ISBN do livro cujo item deve ser removido.
     */
    public void removerItem(String isbn) {
        // removeIf: remove o item se o ISBN do livro dentro dele for igual ao ISBN
        // fornecido.
        boolean removido = itens.removeIf(item -> item.getLivro().getIsbn().equals(isbn));
        if (removido) {
            System.out.println("Item com ISBN " + isbn + " removido do carrinho.");
            DadosLivraria.salvarCarrinho(itens); // Salva o estado atualizado do carrinho.
        } else {
            System.out.println("Item com ISBN " + isbn + " não encontrado no carrinho.");
        }
    }

    /**
     * Atualiza a quantidade de um item específico no carrinho.
     * Se a nova quantidade for zero ou negativa, o item é removido.
     * 
     * @param isbn           O ISBN do livro cujo item terá a quantidade atualizada.
     * @param novaQuantidade A nova quantidade desejada para o item.
     */
    public void atualizarQuantidadeItem(String isbn, int novaQuantidade) {
        if (novaQuantidade <= 0) { // Se a quantidade for <= 0, remove o item.
            removerItem(isbn);
            return; // Sai do método.
        }

        // Tenta encontrar o item no carrinho.
        Optional<ItemCarrinho> itemOpt = itens.stream()
                .filter(item -> item.getLivro().getIsbn().equals(isbn))
                .findFirst();

        if (itemOpt.isPresent()) { // Se o item foi encontrado...
            ItemCarrinho item = itemOpt.get();
            // Verifica se há estoque suficiente para a nova quantidade.
            if (item.getLivro().getEstoque() >= novaQuantidade) {
                item.setQuantidade(novaQuantidade); // Atualiza a quantidade.
                System.out
                        .println("Quantidade de " + item.getLivro().getTitulo() + " atualizada para " + novaQuantidade);
                DadosLivraria.salvarCarrinho(itens); // Salva o carrinho atualizado.
            } else {
                System.out.println("Estoque insuficiente para " + item.getLivro().getTitulo() + ". Disponível: " +
                        item.getLivro().getEstoque());
            }
        } else {
            System.out.println("Item com ISBN " + isbn + " não encontrado no carrinho.");
        }
    }

    /**
     * Calcula o valor total de todos os itens no carrinho.
     * 
     * @return O valor total.
     */
    public double calcularTotal() {
        // Usa Stream API para somar o subtotal de cada item no carrinho.
        return itens.stream().mapToDouble(ItemCarrinho::getSubtotal).sum();
    }

    /**
     * Exibe o conteúdo atual do carrinho no console.
     * Esta simula a 'View' (Visão) em um sistema MVC, mostrando dados ao usuário.
     */
    public void exibirCarrinho() {
        if (itens.isEmpty()) {
            System.out.println("Seu carrinho está vazio.");
            return;
        }
        System.out.println("\n--- Seu Carrinho ---");
        itens.forEach(System.out::println); // Itera e imprime cada item (usando o toString() de ItemCarrinho).
        System.out.println("Total do Carrinho: R$" + String.format("%.2f", calcularTotal()));
        System.out.println("--------------------");
    }

    /**
     * Simula o processo de finalização da compra.
     * Isso inclui a atualização do estoque dos livros no catálogo e o esvaziamento
     * do carrinho.
     */
    public void finalizarCompra() {
        if (itens.isEmpty()) {
            System.out.println("Não há itens para finalizar a compra.");
            return;
        }

        System.out.println("\nFinalizando compra...");
        // Para cada item no carrinho, atualiza o estoque do livro correspondente.
        for (ItemCarrinho item : itens) {
            Livro livro = item.getLivro();
            // Chama o método do GerenciadorLivros para decrementar o estoque.
            gerenciadorLivros.atualizarLivro(livro.getIsbn(), livro.getPreco(),
                    livro.getEstoque() - item.getQuantidade());
        }
        itens.clear(); // Limpa a lista de itens no carrinho após a compra.
        DadosLivraria.salvarCarrinho(itens); // Salva o carrinho vazio no arquivo.
        System.out.println("Compra finalizada com sucesso! Seu carrinho foi esvaziado.");
    }
}