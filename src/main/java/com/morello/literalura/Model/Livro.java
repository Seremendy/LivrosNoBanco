package com.morello.literalura.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;
    private String idioma;
    private Integer downloads;

    private Integer anoNascimento;
    private Integer anoMorte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoMorte() {
        return anoMorte;
    }

    public void setAnoMorte(Integer anoMorte) {
        this.anoMorte = anoMorte;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Título: %s | Autor: %s | Idioma: %s | Downloads: %d | Nascimento: %s | Morte: %s",
                id,
                titulo,
                autor,
                idioma,
                downloads,
                anoQueEstavaVivo()
        );
    }

    public String anoQueEstavaVivo() {
        if (anoNascimento != null && anoMorte != null) {
            return autor + " viveu de " + anoNascimento + " até " + anoMorte;
        } else if (anoNascimento != null) {
            return autor + " nasceu em " + anoNascimento + " e pode ainda estar vivo";
        } else {
            return "Sem informações sobre nascimento/morte de " + autor;
        }
    }

}
