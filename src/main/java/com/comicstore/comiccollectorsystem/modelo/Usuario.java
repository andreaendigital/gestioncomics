/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comicstore.comiccollectorsystem.modelo;

/**
 *
 * @author Andrea
 */
public class Usuario {
    private String rut;
    private String nombre;
    private String correo;

    public Usuario(String rut, String nombre, String correo) {
        this.rut = rut;
        this.nombre = nombre;
        this.correo = correo;
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

    @Override
    public String toString() {
        return String.format("%s (%s) - RUT: %s", nombre, correo, rut);
    }
}
