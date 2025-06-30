/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.comicstore.comiccollectorsystem;
import com.comicstore.comiccollectorsystem.controlador.SistemaGestion;
import com.comicstore.comiccollectorsystem.vista.MenuConsola;


/**
 *
 * @author Andrea
 */
public class ComicCollectorSystem {

    public static void main(String[] args) {
        SistemaGestion sistema = new SistemaGestion();         // crear sistema
        sistema.cargarDatosIniciales();                        // cargar datos
        //System.out.println("en main despues del llmado a cargarDatos iinciales");
        MenuConsola menu = new MenuConsola(sistema);           // pasar sistema al menú
        menu.mostrarMenu();                                    // mostrar el menú
    }
}
