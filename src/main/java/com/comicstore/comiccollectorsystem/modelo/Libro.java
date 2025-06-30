/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comicstore.comiccollectorsystem.modelo;

/**
 *
 * @author Andrea
 */
public abstract class Libro {
    protected String titulo;
    protected String autor;
    protected int anioPublicacion;
    protected String isbn;

    public Libro(String titulo, String autor, int anioPublicacion, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.isbn = isbn;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public String getIsbn() {
        return isbn;
    }
    
    public abstract String getTipo();
    
    @Override
    public String toString() {
        return String.format("%s - %s (%d) [ISBN: %s]", titulo, autor, anioPublicacion, isbn);
    }
    
}
