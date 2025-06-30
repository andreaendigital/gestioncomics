/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comicstore.comiccollectorsystem.vista;
import com.comicstore.comiccollectorsystem.controlador.SistemaGestion;
import com.comicstore.comiccollectorsystem.excepciones.*;
import com.comicstore.comiccollectorsystem.modelo.*;
import com.comicstore.comiccollectorsystem.util.ArchivoHelper;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author Andrea
 */
public class MenuConsola {
    private SistemaGestion sistema;
    private Scanner scanner;

    public MenuConsola() {
        sistema = new SistemaGestion();
        sistema.cargarDatosIniciales();
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== Comic Collector System =====");
            
            
            System.out.println("\n===== BUSQUEDA =====");
            System.out.println("2.Buscar libro por titulo");
            System.out.println("3.Buscar libro por ISBN");
            System.out.println("4.Buscar cómics por universo");
            System.out.println("5.Buscar libros por genero");
            
            System.out.println("\n===== CATALOGO =====");
            System.out.println("1.Listar todos los libros");
            System.out.println("6.Listar libros ordenados por titulo");
            System.out.println("7.Listar libros ordenados por autor");
            System.out.println("8.Listar comics ordenados por universo");
            System.out.println("9.Listar novelas gráficas ordenadas por ilustrador");
            System.out.println("14. Ver generos disponibles");
            System.out.println("15. Ver editoriales disponibles");
            System.out.println("16. Ver universos disponibles");
            
            System.out.println("\n===== COMPRAS =====");
            System.out.println("10. 🛒 Registrar compra");
            System.out.println("11. 🧾 Ver compras de un usuario");
            
            System.out.println("\n===== USUARIOS =====");
            System.out.println("12. 👤 Agregar nuevo usuario");
            System.out.println("13. ❌ Eliminar usuario");
            System.out.println("14. Listar Usuarios existentes");
            
            System.out.println("0. 🚪 Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> listarTodosLosLibros();
                case 2 -> buscarPorTitulo();
                case 3 -> buscarPorISBN();
                case 4 -> buscarPorUniverso();
                case 5 -> buscarPorGenero();
                case 6 -> listarOrdenadoPorTitulo();
                case 7 -> listarOrdenadoPorAutor();
                case 8 -> listarComicsPorUniverso();
                case 9 -> listarNovelasPorIlustrador();
                case 10 -> registrarCompra();
                case 11 -> verComprasUsuario();
                case 12 -> agregarUsuario();
                case 13 -> eliminarUsuario();
                case 14 -> mostrarGeneros();
                case 15 -> mostrarEditoriales();
                case 16 -> mostrarUniversos();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void listarTodosLosLibros() {
        sistema.getInventarioLibros().forEach(System.out::println);
    }

    private void buscarPorTitulo() {
        System.out.print("Ingrese título: ");
        String titulo = scanner.nextLine();
        List<Libro> libros = sistema.buscarLibrosPorTitulo(titulo);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void buscarPorISBN() {
        System.out.print("Ingrese ISBN: ");
        String isbn = scanner.nextLine();
        try {
            Libro libro = sistema.buscarLibroPorISBN(isbn);
            System.out.println(libro);
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Libro no encontrado.");
        }
    }

    private void buscarPorUniverso() {
        System.out.print("Ingrese universo (Marvel/DC): ");
        String universo = scanner.nextLine();
        sistema.buscarComicsPorUniverso(universo).forEach(System.out::println);
    }

    private void buscarPorGenero() {
        System.out.print("Ingrese género: ");
        String genero = scanner.nextLine();
        sistema.buscarLibrosPorGenero(genero).forEach(System.out::println);
    }

    private void listarOrdenadoPorTitulo() {
        sistema.obtenerLibrosOrdenadosPorTitulo().forEach(System.out::println);
    }

    private void listarOrdenadoPorAutor() {
        sistema.obtenerLibrosOrdenadosPorAutor().forEach(System.out::println);
    }

    private void listarComicsPorUniverso() {
        sistema.obtenerComicsOrdenadosPorUniverso().forEach(System.out::println);
    }

    private void listarNovelasPorIlustrador() {
        sistema.obtenerNovelasGraficasOrdenadasPorIlustrador().forEach(System.out::println);
    }

    private void registrarCompra() {
        try {
            System.out.print("Ingrese RUT del usuario: ");
            String rut = scanner.nextLine();
            System.out.print("Ingrese ISBN del libro: ");
            String isbn = scanner.nextLine();
            sistema.registrarCompra(rut, isbn);
            System.out.println("Compra registrada exitosamente.");
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("Usuario no encontrado.");
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Libro no encontrado.");
        } catch (StockInsuficienteException e) {
            System.out.println("No hay stock disponible.");
        }
    }

    private void verComprasUsuario() {
        try {
            System.out.print("Ingrese RUT del usuario: ");
            String rut = scanner.nextLine();
            List<Libro> compras = sistema.listarComprasUsuario(rut);
            if (compras.isEmpty()) {
                System.out.println("No hay compras registradas.");
            } else {
                compras.forEach(System.out::println);
            }
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("Usuario no encontrado.");
        }
    }

    private void agregarUsuario() {
 System.out.print("Ingrese RUT del usuario: ");
    String rut = scanner.nextLine();

    try {
        sistema.buscarUsuario(rut);
        System.out.println("Ya existe un usuario con ese RUT.");
    } catch (UsuarioNoEncontradoException e) {
        System.out.print("Ingrese nombre del usuario: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese correo electrónico: ");
        String correo = scanner.nextLine();

        Usuario nuevo = new Usuario(rut, nombre, correo);
        sistema.agregarUsuario(nuevo);

        try {
            ArchivoHelper.escribirUsuarioTXT("usuarios.txt", nuevo);
            System.out.println("Usuario registrado exitosamente.");
        } catch (IOException ex) {
            System.out.println("Error al guardar el usuario: " + ex.getMessage());
        }
    }
}




    private void eliminarUsuario() {
        System.out.print("Ingrese RUT del usuario a eliminar: ");
        String rut = scanner.nextLine();
        try {
            sistema.eliminarUsuario(rut);
            System.out.println("Usuario eliminado.");
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("Usuario no encontrado.");
        }
    }

    private void mostrarGeneros() {
        System.out.println("Géneros disponibles:");
        sistema.getGenerosDisponibles().forEach(System.out::println);
    }

    private void mostrarEditoriales() {
        System.out.println("Editoriales disponibles:");
        sistema.getEditorialesDisponibles().forEach(System.out::println);
    }

    private void mostrarUniversos() {
        System.out.println("Universos disponibles:");
        sistema.getUniversosDisponibles().forEach(System.out::println);
    }
}

