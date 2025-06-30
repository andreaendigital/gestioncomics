/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comicstore.comiccollectorsystem.util;
import com.comicstore.comiccollectorsystem.modelo.Comic;
import com.comicstore.comiccollectorsystem.modelo.Libro;
import com.comicstore.comiccollectorsystem.modelo.LibroGenerico;
import com.comicstore.comiccollectorsystem.modelo.NovelaGrafica;
import com.comicstore.comiccollectorsystem.modelo.Usuario;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Andrea
 */
public class ArchivoHelper {
    private static final String RUTA_DATA = "data/"; //define la ruta base donde se encuentra el archivo
    
    public static List<Libro> leerCSV(String nombreArchivo) throws IOException {  //devuelve una lista de cadenas y cada elemento representa una linea del archivo y lanza excepcion si ocurre error
        List<Libro> datos = new ArrayList<>(); // se crea una lista vacía para almacenar las lineas del archivo que lee

        String rutaCompleta = RUTA_DATA + nombreArchivo;
        
        //try (CSVReader reader = new CSVReader(new FileReader(ruta))) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaCompleta))) {
 String linea;
        boolean esPrimera = true;

        while ((linea = br.readLine()) != null) {
            if (esPrimera) {
                esPrimera = false;
                continue;
            }

            String[] partes = linea.split(",", -1); // -1 para incluir campos vacíos
            if (partes.length >= 12) {
                String tipo = partes[0];
                String titulo = partes[1];
                String autor = partes[2];
                int anio = Integer.parseInt(partes[3]);
                String isbn = partes[4];
                String editorial = partes[5];
                double precio = Double.parseDouble(partes[6]);
                int stock = Integer.parseInt(partes[7]);

                switch (tipo) {
                    case "Comic":
                        int numeroEdicion = Integer.parseInt(partes[8]);
                        String universo = partes[9];
                        datos.add(new Comic(titulo, autor, anio, isbn, editorial, precio, stock, numeroEdicion, universo));
                        break;

                    case "Novela Gráfica":
                        String ilustrador = partes[11];
                        datos.add(new NovelaGrafica(titulo, autor, anio, isbn, editorial, precio, stock, ilustrador));
                        break;

                    case "Libro":
                        datos.add(new LibroGenerico(titulo, autor, anio, isbn, editorial, precio, stock));
                        break;

                    default:
                        System.err.println("Tipo desconocido: " + tipo);
                }
            }
        }
    }

        return datos;
    }

    public static void escribirCSV(String nombreArchivo, List<Libro> datos) throws IOException {
        String ruta = "data/" + nombreArchivo;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            // Escribir encabezado
            bw.write("Tipo,Titulo,Autor,AñoPublicacion,ISBN,Editorial,Precio,Stock,NumeroEdicion,Universo,Ilustrador");
            bw.newLine();

            for (Libro libro : datos) {
                StringBuilder linea = new StringBuilder();
                linea.append(libro.getTipo()).append(",")
                     .append(libro.getTitulo()).append(",")
                     .append(libro.getAutor()).append(",")
                     .append(libro.getAnioPublicacion()).append(",")
                     .append(libro.getIsbn()).append(",")
                     .append(libro.getEditorial()).append(",")
                     .append(libro.getPrecio()).append(",")
                     .append(libro.getStock()).append(",");

                if (libro instanceof Comic comic) {
                    linea.append(comic.getNumeroEdicion()).append(",")
                         .append(comic.getUniverso()).append(",");
                } else if (libro instanceof NovelaGrafica novela) {
                    linea.append(",,").append(novela.getIlustrador());
                } else {
                    linea.append(",,,");
                }

                bw.write(linea.toString());
                bw.newLine();
                 //writer.writeAll(datos); //ingresa al documneto todo lo que está en la variable datos
            }
        }
    }
    
    public static List <Usuario> leerUsuariosTXT(String nombreArchivo) throws IOException{
        List<Usuario> usuarios = new ArrayList<>();
        String ruta = "data/" + nombreArchivo;
        
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            boolean esPrimera = true;
            
            while ((linea = br.readLine()) != null) {
            if (esPrimera) {
                esPrimera = false;
                continue;
            }

                String[] partes = linea.split(",", -1); // -1 para incluir campos vacíos
                if (partes.length == 3) {
                    String rut = partes[0];
                    String nombre = partes[1];
                    String correo = partes[2];
                    usuarios.add(new Usuario(rut, nombre, correo));
                    }
            
            }
        }
        return usuarios;
    }
     
    public static void escribirUsuarioTXT(String nombreArchivo, Usuario usuario) throws IOException {
        String ruta = "data/" + nombreArchivo;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, true))) {
            // Escribir encabezado
            bw.write(usuario.getRut() + "," + usuario.getNombre() + "," + usuario.getCorreo() );
            bw.newLine();
          
            }
        }
    }     
     

