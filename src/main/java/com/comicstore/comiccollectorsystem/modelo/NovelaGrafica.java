/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comicstore.comiccollectorsystem.modelo;

/**
 *
 * @author Andrea
 */
public class NovelaGrafica extends Libro {
    private String ilustrador;

    public NovelaGrafica(String titulo, String autor, int anioPublicacion, String isbn, String editorial, double precio, int stock, String ilustrador) {
        super(titulo, autor, anioPublicacion, isbn, editorial, precio, stock);
        this.ilustrador = ilustrador;
    }

    public String getIlustrador() {
        return ilustrador;
    }

    @Override
    public String getTipo() {
        return "Novela Gráfica";
    }

    @Override
    public String toString() {
        return super.toString() + " - Ilustrador: " + ilustrador;
    }
}
