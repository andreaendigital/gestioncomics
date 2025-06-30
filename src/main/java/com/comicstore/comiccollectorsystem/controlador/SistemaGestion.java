/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comicstore.comiccollectorsystem.controlador;


import com.comicstore.comiccollectorsystem.excepciones.ProductoNoEncontradoException;
import com.comicstore.comiccollectorsystem.excepciones.StockInsuficienteException;
import com.comicstore.comiccollectorsystem.excepciones.UsuarioNoEncontradoException;
import com.comicstore.comiccollectorsystem.modelo.Comic;
import com.comicstore.comiccollectorsystem.modelo.Libro;
import com.comicstore.comiccollectorsystem.modelo.NovelaGrafica;
import com.comicstore.comiccollectorsystem.modelo.Usuario;
import com.comicstore.comiccollectorsystem.util.ArchivoHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 *
 * @author Andrea
 */
public class SistemaGestion {
   private List<Libro> inventario; // Lista general para todos los libros
    private Map<String, Usuario> usuarios; // clave: RUT
    private Map<String, Libro> librosPorISBN; // clave: ISBN
    private Map<String, List<Libro>> librosPorTitulo; // clave: Título

    private Set<String> generosDisponibles;
    private Set<String> editorialesDisponibles;
    private Set<String> universosDisponibles; // Solo para Comics

    public SistemaGestion() {
        inventario = new ArrayList<>();
        usuarios = new HashMap<>();
        librosPorISBN = new HashMap<>();
        librosPorTitulo = new HashMap<>();

        generosDisponibles = new HashSet<>();
        editorialesDisponibles = new HashSet<>();
        universosDisponibles = new HashSet<>();
    }

    public void cargarDatosIniciales() {
        cargarComicsDesdeCSV();
        cargarUsuariosDesdeTXT();
    }

    private void cargarComicsDesdeCSV() {
        List<Libro> libros = ArchivoHelper.leerCSV("data/libros.csv");
        for (Libro libro : libros) {
            inventario.add(libro);
            librosPorISBN.put(libro.getIsbn(), libro);

            librosPorTitulo.computeIfAbsent(libro.getTitulo(), k -> new ArrayList<>()).add(libro);
            editorialesDisponibles.add(libro.getEditorial());

            if (libro instanceof Comic) {
                Comic c = (Comic) libro;
                if (c.getUniverso() != null && !c.getUniverso().isEmpty()) {
                    universosDisponibles.add(c.getUniverso());
                }
            }
        }
    }

    private void cargarUsuariosDesdeTXT() {
        List<Usuario> listaUsuarios = ArchivoHelper.leerUsuariosTXT("data/usuarios.txt");
        for (Usuario u : listaUsuarios) {
            usuarios.put(u.getRut(), u);
        }
    }

    public void guardarDatos() {
        guardarUsuariosEnTXT();
    }

    private void guardarUsuariosEnTXT() {
        ArchivoHelper.escribirUsuarioTXT("data/usuarios.txt", new ArrayList<>(usuarios.values()));
    }

    public void agregarUsuario(String idUsuario, String nombre, String contrasena) {
        Usuario nuevo = new Usuario(idUsuario, nombre, contrasena);
        usuarios.put(idUsuario, nuevo);
    }

    public Usuario buscarUsuario(String idUsuario) throws UsuarioNoEncontradoException {
        if (!usuarios.containsKey(idUsuario)) {
            throw new UsuarioNoEncontradoException("Usuario con RUT " + idUsuario + " no encontrado.");
        }
        return usuarios.get(idUsuario);
    }

    public void eliminarUsuario(String idUsuario) throws UsuarioNoEncontradoException {
        if (!usuarios.containsKey(idUsuario)) {
            throw new UsuarioNoEncontradoException("Usuario con RUT " + idUsuario + " no existe.");
        }
        usuarios.remove(idUsuario);
    }

    public List<Libro> getInventarioLibros() {
        return inventario;
    }

    public Libro buscarLibroPorISBN(String isbn) throws ProductoNoEncontradoException {
        if (!librosPorISBN.containsKey(isbn)) {
            throw new ProductoNoEncontradoException("Producto con ISBN " + isbn + " no encontrado.");
        }
        return librosPorISBN.get(isbn);
    }

    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        return librosPorTitulo.getOrDefault(titulo, new ArrayList<>());
    }

    public void registrarCompra(String idUsuario, String isbnLibro) throws UsuarioNoEncontradoException, ProductoNoEncontradoException, StockInsuficienteException {
        Usuario u = buscarUsuario(idUsuario);
        Libro libro = buscarLibroPorISBN(isbnLibro);

        if (libro.getStock() <= 0) {
            throw new StockInsuficienteException("No hay stock disponible para este libro.");
        }

        libro.setStock(libro.getStock() - 1);
        u.getLibrosComprados().add(libro);
    }

    public Set<String> getUniversosDisponibles() {
        return universosDisponibles;
    }

    public Set<String> getEditorialesDisponibles() {
        return editorialesDisponibles;
    }

    public Set<String> getGenerosDisponibles() {
        return generosDisponibles;
    }

    public Set<Libro> obtenerLibrosOrdenadosPorTitulo() {
        return new TreeSet<>(Comparator.comparing(Libro::getTitulo));
    }

    public Set<Libro> obtenerLibrosOrdenadosPorAutor() {
        return new TreeSet<>(Comparator.comparing(Libro::getAutor));
    }

    public Set<Comic> obtenerComicsOrdenadosPorUniverso() {
        return inventario.stream()
                .filter(libro -> libro instanceof Comic)
                .map(libro -> (Comic) libro)
                .sorted(Comparator.comparing(Comic::getUniverso))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<NovelaGrafica> obtenerNovelasGraficasOrdenadasPorIlustrador() {
        return inventario.stream()
                .filter(libro -> libro instanceof NovelaGrafica)
                .map(libro -> (NovelaGrafica) libro)
                .sorted(Comparator.comparing(NovelaGrafica::getIlustrador))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}  
