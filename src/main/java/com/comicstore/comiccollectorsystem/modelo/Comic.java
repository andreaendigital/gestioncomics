/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comicstore.comiccollectorsystem.modelo;

/**
 *
 * @author Andrea
 */
public class Comic extends Libro {
    private int numeroEdicion;
    private String universo;

    public Comic(String titulo, String autor, int anioPublicacion, String isbn, String editorial, double precio, int stock, int numeroEdicion, String universo) {
        super(titulo, autor, anioPublicacion, isbn, editorial, precio, stock);
        this.numeroEdicion = numeroEdicion;
        this.universo = universo;
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    public String getUniverso() {
        return universo;
    }

    @Override
    public String getTipo() {
        return "Comic";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" - Edición: %d - Universo: %s", numeroEdicion, universo);
    }
}
