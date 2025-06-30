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
    protected String editorial;
    protected double precio;
    protected int stock;

    public Libro(String titulo, String autor, int anioPublicacion, String isbn, String editorial, double precio, int stock) {
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.isbn = isbn;
        this.editorial = editorial;
        this.precio = precio;
        this.stock = stock;
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

    public String getEditorial() {
        return editorial;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public abstract String getTipo();

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s (%d) [ISBN: %s] - Editorial: %s - Precio: $%.2f - Stock: %d",
                titulo, autor, anioPublicacion, isbn, editorial, precio, stock);
    }
    
}
