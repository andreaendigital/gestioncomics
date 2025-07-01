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
import java.util.Set;
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
        System.out.println("1. Listar todos el catalogo");
        System.out.println("2. Listar catalogo ordenados por titulo");
        System.out.println("3. Listar catalogo ordenados por autor");
        System.out.println("4. Listar comics ordenados por universo");
        System.out.println("5. Listar novelas graficas ordenadas por ilustrador");
        System.out.println("6. Listar libros por titulo");

        System.out.println("\n===== BUSQUEDA =====");
        System.out.println("7. Buscar en catalogo por titulo");
        System.out.println("8. Buscar en catalogo por ISBN");
        System.out.println("9. Buscar comics por universo");
        System.out.println("10. Buscar libros por autor");

        System.out.println("\n===== INFORMACION DISPONIBLE =====");
        System.out.println("11. Ver autores disponibles");
        System.out.println("12. Ver editoriales disponibles");
        System.out.println("13. Ver universos disponibles");

        System.out.println("\n===== COMPRAS =====");
        System.out.println("14. Registrar compra");
        System.out.println("15. Ver compras de un usuario");

        System.out.println("\n===== USUARIOS =====");
        System.out.println("16. Agregar nuevo usuario");
        System.out.println("17. Eliminar usuario");
        System.out.println("18. Listar usuarios existentes");

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
                case 6 -> listarLibrosGenericosOrdenados();
                case 7 -> buscarPorTitulo();
                case 8 -> buscarPorISBN();
                case 9 -> buscarPorUniverso();
                case 10 -> buscarPorAutor();
                case 11 -> mostrarAutores();
                case 12 -> mostrarEditoriales();
                case 13 -> mostrarUniversos();
                case 14 -> registrarCompra();
                case 15 -> verComprasUsuario();
                case 16 -> registrarNuevoUsuario();
                case 17 -> eliminarUsuario();
                case 18 -> listarUsuarios();
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
    
    public void listarLibrosGenericosOrdenados(){
        List<Libro> libros = sistema.obtenerLibrosGenericosOrdenados();
        if(libros.isEmpty()){
            System.out.println("No hay libros en el inventario");
        }else{
            libros.forEach(System.out::println);
        }
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
        String universo = scanner.nextLine().trim().toLowerCase();

        List<Comic> comics = sistema.buscarComicsPorUniverso(universo);

        if (comics.isEmpty()) {
            System.out.println("No hay comics registrados del universo \"" + universo + "\".");
        } else {
            comics.forEach(System.out::println);
        }
    }

    private void buscarPorAutor() {
        System.out.print("Ingrese nombre del autor: ");
        String autor = scanner.nextLine().trim();

        List<Libro> libros = sistema.buscarLibrosPorAutor(autor);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros del autor \"" + autor + "\".");
        } else {
            System.out.println("Libros de " + autor + ":");
            libros.forEach(System.out::println);
        }
    }

    // ===== INFORMACIÓN DISPONIBLE =====
     private void mostrarAutores() {
        Set<String> autores = sistema.getAutoresDisponibles();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            System.out.println("Autores disponibles:");
            autores.forEach(autor -> System.out.println("- " + autor));
        }
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
    String rut = null;
    String isbn = null;
    int cantidad = 0;

    // Validar RUT
    while (true) {
        System.out.print("Ingrese RUT del usuario: ");
        rut = scanner.nextLine().trim();
        try {
            sistema.buscarUsuario(rut);
            break; // válido
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("Usuario no encontrado. Intente nuevamente.");
        }
    }

    // Validar ISBN
    while (true) {
        System.out.print("Ingrese ISBN del libro: ");
        isbn = scanner.nextLine().trim();
        try {
            sistema.buscarLibroPorISBN(isbn);
            break; // válido
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Libro no encontrado. Intente nuevamente.");
        }
    }

    // Validar cantidad
    while (true) {
        System.out.print("Ingrese cantidad a comprar: ");
        try {
            cantidad = Integer.parseInt(scanner.nextLine().trim());
            if (cantidad <= 0) {
                System.out.println("La cantidad debe ser mayor que cero.");
                continue;
            }

            Libro libro = sistema.buscarLibroPorISBN(isbn);
            if (libro.getStock() < cantidad) {
                System.out.println("Stock insuficiente. Solo hay " + libro.getStock() + " unidades disponibles.");
                continue;
            }

            break; // válido
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida. Ingrese un número entero.");
        } catch (ProductoNoEncontradoException e) {
            // Esto no debería ocurrir aquí, pero lo manejamos por seguridad
            System.out.println("Error inesperado al validar el libro.");
        }
    }

    // Registrar la compra
    try {
        sistema.registrarCompra(rut, isbn, cantidad);
        System.out.println("Compra registrada exitosamente.");
    } catch (Exception e) {
        System.out.println("Error al registrar la compra: " + e.getMessage());
    }
}

    private void verComprasUsuario() {
        System.out.print("Ingrese RUT del usuario: ");
        String rut = scanner.nextLine().trim();

        try {
            Usuario usuario = sistema.buscarUsuario(rut);
            System.out.println("==========================");
            System.out.println("Usuario: " + usuario.getNombre() + " (" + usuario.getRut() + ")");
            System.out.println("===== Historial de compras =====");

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
            
            //Agregar entrada vacía en el mapa de compras
            sistema.agregarMapaComprasParaUsuario(nuevo.getRut());


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

