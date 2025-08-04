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
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        buscarECadastrarLivro(scanner);
                        break;
                    case 2:
                        listarLivros();
                        break;
                    case 3:
                        listarAutores();
                        break;
                    case 4:
                        listarAutoresVivos(scanner);
                        break;
                    case 5:
                        listarPorIdioma(scanner);
                        break;
                    case 6:
                        listarMaisBaixados();
                        break;
                    case 7:
                        apagarLivroPorId(scanner);
                        break;
                    case 9:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("""
            \n=== MENU ===
            1 - Buscar e cadastrar livros por título
            2 - Listar livros salvos
            3 - Listar todos os autores
            4 - Listar autores vivos
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

    private void listarAutoresVivos(Scanner leitura) {
        System.out.print("Digite o ano para buscar autores vivos: ");
        int ano = leitura.nextInt();
        leitura.nextLine();

        List<Livro> autoresVivos = livroService.listarAutoresVivosNoAno(ano);

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor encontrado vivo no ano " + ano + ".");
        } else {
            autoresVivos.forEach(livro -> System.out.println(livro.anoQueEstavaVivo()));
        }
    }

    private void listarPorIdioma(Scanner leitura) {
        System.out.print("Digite o idioma (ex: en, pt): ");
        String idioma = leitura.nextLine();
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
