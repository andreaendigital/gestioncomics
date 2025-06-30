/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comicstore.comiccollectorsystem.modelo;

/**
 *
 * @author Andrea
 */
public class LibroGenerico extends Libro {
    public LibroGenerico(String titulo, String autor, int anioPublicacion, String isbn, String editorial, double precio, int stock) {
        super(titulo, autor, anioPublicacion, isbn, editorial, precio, stock);
    }

    @Override
    public String getTipo() {
        return "Libro";
    }
}