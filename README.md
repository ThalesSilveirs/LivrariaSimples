# Livraria Simples

Este é um projeto de exemplo de uma aplicação de console para uma livraria simples, desenvolvido em Java. A aplicação simula o gerenciamento de um catálogo de livros e um carrinho de compras, com persistência de dados em arquivos de texto.

## Funcionalidades

A aplicação simula as seguintes operações:

- **Gerenciamento de Livros (Ações de Administrador):**
  - Carregar a lista de livros de um arquivo (`data/livros.txt`).
  - Adicionar novos livros ao catálogo.
  - Atualizar informações (preço, estoque) de livros existentes.
  - Remover livros do catálogo.
  - Salvar as alterações de volta no arquivo `data/livros.txt`.

- **Carrinho de Compras (Ações de Cliente):**
  - Carregar um carrinho de compras salvo (`data/carrinho_atual.txt`).
  - Adicionar livros do catálogo ao carrinho.
  - Exibir o conteúdo do carrinho com o total.
  - Atualizar a quantidade de um item no carrinho.
  - Remover itens do carrinho.
  - Finalizar a compra, atualizando o estoque de livros e limpando o carrinho.
  - Salvar o estado do carrinho no arquivo `data/carrinho_atual.txt` após cada alteração.

## Estrutura do Projeto

- **`src/`**: Contém todo o código-fonte Java.
  - **`App.java`**: Classe principal que inicia a aplicação.
  - **`com/livraria/`**: Pacote principal da aplicação.
    - **`MainApp.java`**: Contém a lógica de simulação, demonstrando as funcionalidades do sistema.
    - **`model/`**: Contém as classes de dados (POJOs).
      - `Livro.java`: Representa um livro.
      - `ItemCarrinho.java`: Representa um item no carrinho de compras.
    - **`service/`**: Contém as classes com a lógica de negócio.
      - `GerenciadorLivros.java`: Gerencia o catálogo de livros.
      - `CarrinhoDeCompras.java`: Gerencia o carrinho de compras.
    - **`data/`**: Contém a classe responsável pela persistência de dados.
      - `DadosLivraria.java`: Lê e escreve os dados dos livros e do carrinho nos arquivos de texto.
- **`bin/`**: Contém os arquivos `.class` compilados.
- **`data/`**: Contém os arquivos de dados.
  - `livros.txt`: Armazena o catálogo de livros no formato `ISBN;Título;Autor;Categoria;Preço;Estoque`.
  - `carrinho_atual.txt`: Armazena os itens do carrinho no formato `ISBN;Quantidade`.
- **`lib/`**: Destinado a bibliotecas de terceiros (atualmente vazio).

## Como Compilar e Executar

### Pré-requisitos

- JDK (Java Development Kit) instalado e configurado no PATH do sistema.

### 1. Compilação

Abra um terminal na raiz do projeto (`LivrariaSimples/`) e execute o seguinte comando para compilar todos os arquivos `.java` e colocar os `.class` no diretório `bin/`:

```bash
javac -d bin src/**/*.java
```
*Observação: Em alguns terminais (como o cmd padrão do Windows), o `**` pode não funcionar. Nesse caso, você pode listar os arquivos manualmente ou usar um comando adaptado para o seu shell.*

### 2. Execução

Após a compilação, execute o seguinte comando a partir da raiz do projeto para iniciar a aplicação:

```bash
java -cp bin com.livraria.MainApp
```

O programa executará a simulação definida em `MainApp.java` e imprimirá os resultados no console, mostrando as ações do administrador, as ações do cliente e o estado final do estoque de livros.