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
import com.comicstore.comiccollectorsystem.modelo.LibroGenerico;
import com.comicstore.comiccollectorsystem.modelo.NovelaGrafica;
import com.comicstore.comiccollectorsystem.modelo.Usuario;
import com.comicstore.comiccollectorsystem.util.ArchivoHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Map<String, List<Libro>> compras = new HashMap<>();
   
    private Set<String> editorialesDisponibles;
    private Set<String> universosDisponibles; // Solo para Comics

    public SistemaGestion() {
        inventario = new ArrayList<>();
        usuarios = new HashMap<>();
        librosPorISBN = new HashMap<>();
        librosPorTitulo = new HashMap<>();
        editorialesDisponibles = new HashSet<>();
        universosDisponibles = new HashSet<>();
        
    }

    public void cargarDatosIniciales() {
        
        cargarInventarioDesdeCSV();
        cargarUsuariosDesdeTXT();
        
       // Inicializar lista de compras para cada usuario
    for (Usuario usuario : usuarios.values()) {
        compras.put(usuario.getRut(), new ArrayList<>());
    }

    }

private void cargarInventarioDesdeCSV() {
    
    try {
        
        List<Libro> libros = ArchivoHelper.leerCSV("libros.csv");
        for (Libro libro : libros) {
            inventario.add(libro);
            librosPorISBN.put(libro.getIsbn(), libro);
            librosPorTitulo.computeIfAbsent(libro.getTitulo(), k -> new ArrayList<>()).add(libro);
            editorialesDisponibles.add(libro.getEditorial());

            if (libro instanceof Comic c && c.getUniverso() != null && !c.getUniverso().isEmpty()) {
                universosDisponibles.add(c.getUniverso());
            }
        }
    } catch (IOException e) {
        System.err.println("Error al cargar libros desde CSV: " + e.getMessage());
    }
}

private void cargarUsuariosDesdeTXT() {
    try {
        List<Usuario> listaUsuarios = ArchivoHelper.leerUsuariosTXT("usuarios.txt");
        for (Usuario u : listaUsuarios) {
            usuarios.put(u.getRut(), u);
        }
    } catch (IOException e) {
        System.err.println("Error al cargar usuarios desde TXT: " + e.getMessage());
    }
}


    public void guardarDatos() {
        guardarUsuariosEnTXT();
    }

private void guardarUsuariosEnTXT() {
    for (Usuario u : usuarios.values()) {
        try {
            ArchivoHelper.escribirUsuarioTXT("usuarios.txt", u);
        } catch (IOException e) {
            System.err.println("Error al guardar usuario " + u.getRut() + ": " + e.getMessage());
        }
    }
}


public void agregarUsuario(Usuario usuario) {
    usuarios.put(usuario.getRut(), usuario);
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

 

    public void registrarCompra(String rut, String isbnLibro, int cantidad) throws UsuarioNoEncontradoException, ProductoNoEncontradoException, StockInsuficienteException {
        Usuario u = buscarUsuario(rut);
        Libro libro = buscarLibroPorISBN(isbnLibro);

        if (libro.getStock() < cantidad) {
            throw new StockInsuficienteException("Stock insuficiente. Solo hay " + libro.getStock() + " unidades disponibles.");
        }
        //Descontar Stock
        libro.setStock(libro.getStock() - cantidad);
        
        // Registrar la compra en memoria
        List<Libro> comprasUsuario = compras.get(rut);
        if (comprasUsuario == null) {
            comprasUsuario = new ArrayList<>();
            compras.put(rut, comprasUsuario);
        }

        for (int i = 0; i < cantidad; i++) {
            comprasUsuario.add(libro);
        }
    }





    

    public Set<String> getUniversosDisponibles() {
        return universosDisponibles;
    }

    public Set<String> getEditorialesDisponibles() {
        return editorialesDisponibles;
    }

 public List<Libro> buscarLibrosPorAutor(String autorBuscado) {
    return inventario.stream()
            .filter(libro -> libro.getAutor() != null &&
                             libro.getAutor().equalsIgnoreCase(autorBuscado))
            .toList();
}

    public Set<Libro> obtenerLibrosOrdenadosPorTitulo() {
       return inventario.stream()
               .collect(Collectors.toCollection(() ->
                   new TreeSet<>(Comparator.comparing(Libro::getTitulo, String.CASE_INSENSITIVE_ORDER))
               ));
   }

public Set<Libro> obtenerLibrosOrdenadosPorAutor() {
    return inventario.stream()
            .collect(Collectors.toCollection(() ->
                new TreeSet<>(
                    Comparator.comparing(Libro::getAutor, String.CASE_INSENSITIVE_ORDER)
                              .thenComparing(Libro::getTitulo, String.CASE_INSENSITIVE_ORDER)
                              .thenComparing(Libro::getIsbn)
                )
            ));
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
    
//public List<Libro> listarComprasUsuario(String idUsuario) throws UsuarioNoEncontradoException {
//    Usuario usuario = buscarUsuario(idUsuario);
//    return usuario.getLibrosComprados();
//}

    public List<Libro> listarComprasUsuario(String rut) {
        return compras.getOrDefault(rut, new ArrayList<>());
    }
    
    public void agregarMapaComprasParaUsuario(String rut) {
    compras.put(rut, new ArrayList<>());
}
    
    public List<Comic> buscarComicsPorUniverso(String universo) {
    return inventario.stream()
            .filter(libro -> libro instanceof Comic)
            .map(libro -> (Comic) libro)
            .filter(comic -> comic.getUniverso().equalsIgnoreCase(universo))
            .collect(Collectors.toList());
}
    
public Set<String> getAutoresDisponibles() {
    return inventario.stream()
            .map(Libro::getAutor)
            .filter(autor -> autor != null && !autor.isBlank())
            .collect(Collectors.toCollection(() -> new TreeSet<>(String.CASE_INSENSITIVE_ORDER)));
}
    
    public Map<String, Usuario> getUsuarios() {
    return usuarios;
}
    
    public List<Libro> obtenerLibrosGenericosOrdenados(){
        return inventario.stream()
                .filter(libro -> libro instanceof LibroGenerico &&
                !(libro instanceof Comic) &&
                        !(libro instanceof NovelaGrafica))
                .sorted(Comparator.comparing(Libro::getTitulo, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }
    
}  
