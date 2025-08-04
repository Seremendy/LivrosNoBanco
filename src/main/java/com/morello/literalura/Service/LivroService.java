package com.morello.literalura.Service;

import com.morello.literalura.Api.GutendexApi;
import com.morello.literalura.Model.Livro;
import com.morello.literalura.Repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final GutendexApi gutendexApi;

    public LivroService(LivroRepository livroRepository, GutendexApi gutendexApi) {
        this.livroRepository = livroRepository;
        this.gutendexApi = gutendexApi;
    }

    public void buscarECadastrar(String titulo) {
        List<Livro> livros = gutendexApi.buscarLivrosPorTitulo(titulo);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return;
        }

        Livro livro = livros.get(0);

        boolean jaExiste = livroRepository
                .findByTituloAndAutor(livro.getTitulo(), livro.getAutor())
                .isPresent();

        if (jaExiste) {
            System.out.println("Livro já está salvo: " + livro.getTitulo());
            return;
        }

        livroRepository.save(livro);
        System.out.println("Livro salvo: " + livro.getTitulo());
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public void apagarPorId(Long id) {
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
            System.out.println("Livro apagado com sucesso.");
        } else {
            System.out.println("Livro com ID " + id + " não encontrado.");
        }
    }

    public List<Livro> listarAutoresVivosNoAno(int ano) {
        return livroRepository.findAutoresVivosNoAno(ano);
    }

    public List<String> listarAutores() {
        return livroRepository.findAllAutoresDistinct();
    }

    public List<Livro> listarPorIdioma(String idioma) {
        return livroRepository.findByIdiomaContainingIgnoreCase(idioma);
    }

    public List<Livro> listarMaisBaixados() {
        return livroRepository.findTop10ByOrderByDownloadsDesc();
    }
}
