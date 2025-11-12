package com.livraria; // Pacote principal da aplicação. 

import com.livraria.model.Livro; // Importa a classe Livro para criar objetos de exemplo. 
import com.livraria.service.CarrinhoDeCompras; // Importa a classe de serviço do carrinho. 
import com.livraria.service.GerenciadorLivros; // Importa a classe de serviço do gerenciador de livros. 

/**
 * Classe principal da aplicação.
 * Esta classe atua como o ponto de entrada do programa (contém o método
 * 'main').
 * Ela simula as interações do usuário e do administrador, agindo como uma
 * 'View' simplificada
 * que invoca as operações dos 'Controllers' (GerenciadorLivros e
 * CarrinhoDeCompras).
 */
public class MainApp {
    /**
     * Método principal que é executado quando o programa é iniciado.
     * 
     * @param args Argumentos da linha de comando (não usados neste projeto).
     */
    public static void main(String[] args) {
        // 1. Cria instâncias dos 'Controllers' (camada de serviço/lógica de negócio).
        // GerenciadorLivros lida com operações do catálogo (CRUD de livros).
        GerenciadorLivros gerenciadorLivros = new GerenciadorLivros();
        // CarrinhoDeCompras lida com operações do carrinho de um cliente.
        // Ele precisa do GerenciadorLivros para buscar informações sobre os livros.
        CarrinhoDeCompras carrinho = new CarrinhoDeCompras(gerenciadorLivros);

        System.out.println("--- Início da Simulação ---");

        // --- Simulação de ações de Administrador ---
        System.out.println("\n--- Ações do Administrador ---");
        System.out.println("Livros disponíveis (antes das ações do admin):");
        gerenciadorLivros.getTodosLivros().forEach(System.out::println); // Exibe todos os livros iniciais.

        // Simula a adição de um novo livro pelo administrador.
        gerenciadorLivros
                .adicionarLivro(new Livro("104", "Viagem ao Centro da Terra", "Julio Verne", "Aventura", 38.00, 8));
        // Simula a atualização do preço e estoque de um livro existente.
        gerenciadorLivros.atualizarLivro("101", 40.00, 8);
        // Simula a remoção de um livro.
        gerenciadorLivros.removerLivro("102");

        System.out.println("\nLivros disponíveis (depois das ações do admin):");
        gerenciadorLivros.getTodosLivros().forEach(System.out::println); // Exibe os livros após as alterações do admin.

        // --- Simulação de ações de Cliente ---
        System.out.println("\n--- Ações do Cliente ---");
        carrinho.exibirCarrinho(); // Exibe o carrinho no início (pode estar vazio ou ter itens carregados do
                                   // arquivo).

        // Simula a adição de itens ao carrinho.
        carrinho.adicionarItem("101", 1); // Adiciona "Dom Quixote".
        carrinho.adicionarItem("104", 2); // Adiciona "Viagem ao Centro da Terra".
        carrinho.adicionarItem("103", 1); // Adiciona "O Pequeno Príncipe" (se já existia, aumenta a quantidade).
        carrinho.adicionarItem("999", 1); // Tenta adicionar um livro inexistente (deve exibir um erro).

        carrinho.exibirCarrinho(); // Exibe o carrinho após as adições.

        // Simula a atualização da quantidade de um item no carrinho.
        carrinho.atualizarQuantidadeItem("101", 2); // Altera a quantidade de "Dom Quixote" para 2.
        // Simula a remoção de um item do carrinho.
        carrinho.removerItem("103"); // Remove "O Pequeno Príncipe".
        carrinho.exibirCarrinho(); // Exibe o carrinho após as atualizações/remoções.
        // Simula a finalização da compra pelo cliente.
        carrinho.finalizarCompra();
        carrinho.exibirCarrinho(); // O carrinho deve estar vazio após a compra.
        System.out.println("\n--- Fim da Simulação ---");
        System.out.println("Livros disponíveis (após a compra, estoque atualizado):");
        gerenciadorLivros.getTodosLivros().forEach(System.out::println); // Exibe os livros novamente para verificar o
                                                                         // estoque atualizado.
    }
}