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

    public MenuConsola(SistemaGestion sistema) {
        this.sistema = sistema;
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== Comic Collector System =====");
            

        System.out.println("\n===== CATALOGO =====");
        System.out.println("1. Listar todos los libros");
        System.out.println("2. Listar libros ordenados por titulo");
        System.out.println("3. Listar libros ordenados por autor");
        System.out.println("4. Listar comics ordenados por universo");
        System.out.println("5. Listar novelas graficas ordenadas por ilustrador");

        System.out.println("\n===== BUSQUEDA =====");
        System.out.println("6. Buscar libro por titulo");
        System.out.println("7. Buscar libro por ISBN");
        System.out.println("8. Buscar comics por universo");
        System.out.println("9. Buscar libros por genero");

        System.out.println("\n===== INFORMACION DISPONIBLE =====");
        System.out.println("10. Ver generos disponibles");
        System.out.println("11. Ver editoriales disponibles");
        System.out.println("12. Ver universos disponibles");

        System.out.println("\n===== COMPRAS =====");
        System.out.println("13. Registrar compra");
        System.out.println("14. Ver compras de un usuario");

        System.out.println("\n===== USUARIOS =====");
        System.out.println("15. Agregar nuevo usuario");
        System.out.println("16. Eliminar usuario");
        System.out.println("17. Listar usuarios existentes");

        System.out.println("\n0. Salir");
        System.out.print("Elige una opcion: ");


            opcion = scanner.nextInt();
            scanner.nextLine();

                switch (opcion) {
                case 1 -> listarTodosLosLibros();
                case 2 -> listarOrdenadoPorTitulo();
                case 3 -> listarOrdenadoPorAutor();
                case 4 -> listarComicsPorUniverso();
                case 5 -> listarNovelasPorIlustrador();
                case 6 -> buscarPorTitulo();
                case 7 -> buscarPorISBN();
                case 8 -> buscarPorUniverso();
                case 9 -> buscarPorGenero();
                case 10 -> mostrarGeneros();
                case 11 -> mostrarEditoriales();
                case 12 -> mostrarUniversos();
                case 13 -> registrarCompra();
                case 14 -> verComprasUsuario();
                case 15 -> registrarNuevoUsuario();
                case 16 -> eliminarUsuario();
                case 17 -> listarUsuarios();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ===== CATÁLOGO =====
    private void listarTodosLosLibros() {
        System.out.println("Total libros cargados: " + sistema.getInventarioLibros().size());
        sistema.getInventarioLibros().forEach(System.out::println);
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

    // ===== BÚSQUEDA =====
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

    // ===== INFORMACIÓN DISPONIBLE =====
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

    // ===== COMPRAS =====
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

    // ===== USUARIOS =====
    private void registrarNuevoUsuario() {
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

    private void listarUsuarios() {
        System.out.println("Usuarios registrados:");
        sistema.getUsuarios().forEach((rut, usuario) -> System.out.println(usuario));
    }
}

