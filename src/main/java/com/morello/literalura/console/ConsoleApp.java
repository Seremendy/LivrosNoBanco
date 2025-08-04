package com.morello.literalura.console;

import com.morello.literalura.Model.Livro;
import com.morello.literalura.Service.LivroService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApp {

    private final LivroService livroService;

    public ConsoleApp(LivroService livroService) {
        this.livroService = livroService;
    }

    public void executar() {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 9) {
            mostrarMenu();
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // limpar buffer

                switch (opcao) {
                    case 1 -> buscarECadastrarLivro(scanner);
                    case 2 -> listarLivros();
                    case 3 -> listarAutores();
                    case 4 -> listarAutoresVivos(scanner);
                    case 5 -> listarPorIdioma(scanner);
                    case 6 -> listarMaisBaixados();
                    case 7 -> apagarLivroPorId(scanner);
                    case 9 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida.");
                }
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine(); // limpar entrada inválida
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("""
            \n=== MENU ===
            1 - Buscar e cadastrar livros por título
            2 - Listar livros salvos
            3 - Listar todos os autores
            4 - Listar autores vivos em determinado ano
            5 - Listar livros por idioma
            6 - Listar livros mais baixados
            7 - Apagar livro por ID
            9 - Sair
            """);
        System.out.print("Digite a opção desejada: ");
    }

    private void buscarECadastrarLivro(Scanner scanner) {
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();
        livroService.buscarECadastrar(titulo);
    }

    private void listarLivros() {
        List<Livro> livros = livroService.listarTodos();
        livros.forEach(System.out::println);
    }

    private void listarAutores() {
        livroService.listarAutores().forEach(System.out::println);
    }

    private void listarAutoresVivos(Scanner scanner) {
        System.out.print("Digite o ano para buscar autores vivos: ");
        int ano = scanner.nextInt();
        scanner.nextLine(); // limpar buffer
        livroService.listarAutoresVivosNoAno(ano).forEach(System.out::println);
    }

    private void listarPorIdioma(Scanner scanner) {
        System.out.print("Digite o idioma (ex: en, pt): ");
        String idioma = scanner.nextLine();
        livroService.listarPorIdioma(idioma).forEach(System.out::println);
    }

    private void listarMaisBaixados() {
        livroService.listarMaisBaixados().forEach(System.out::println);
    }

    private void apagarLivroPorId(Scanner scanner) {
        System.out.print("Digite o ID do livro: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        livroService.apagarPorId(id);
    }
}
