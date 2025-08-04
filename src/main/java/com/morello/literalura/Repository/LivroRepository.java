package com.morello.literalura.Repository;

import com.morello.literalura.Model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTituloAndAutor(String titulo, String autor);

    @Query("SELECT l FROM Livro l WHERE l.anoNascimento <= :ano AND (l.anoMorte IS NULL OR l.anoMorte > :ano)")
    List<Livro> findAutoresVivosNoAno(int ano);

    @Query("SELECT DISTINCT l.autor FROM Livro l")
    List<String> findAllAutoresDistinct();

    List<Livro> findByIdiomaContainingIgnoreCase(String idioma);

    List<Livro> findTop10ByOrderByDownloadsDesc();
}
