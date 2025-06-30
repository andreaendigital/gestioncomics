/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comicstore.comiccollectorsystem.modelo;
import com.comicstore.comiccollectorsystem.modelo.Libro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrea
 */
public class Usuario {
    private String rut;
    private String nombre;
    private String correo;
  private List<Libro> librosComprados;

    public Usuario(String rut, String nombre, String correo) {
        this.rut = rut;
        this.nombre = nombre;
        this.correo = correo;
    this.librosComprados = new ArrayList<>();
    }

    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }
public List<Libro> getLibrosComprados() {
        return librosComprados;
    }

    public void agregarLibroComprado(Libro libro) {
        librosComprados.add(libro);
    }


    @Override
    public String toString() {
        return String.format("%s (%s) - RUT: %s", nombre, correo, rut);
    }

}