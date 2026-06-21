package vista;

import modelo.*;
import enumeraciones.Categoria;
import servicios.GestorPedidos;
import excepciones.StockInsuficienteException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorPedidos gestor = new GestorPedidos();
        int opcion = 0;

        // Datos de prueba (para que el programa no arranque vacío)
        Producto p1 = new Producto("P01", "Pizza Muzzarella", 5000.0, 10, Categoria.PIZZA);
        
        do {
            System.out.println("\n--- FOOD STORE SYSTEM ---");
            System.out.println("1. Agregar Pedido");
            System.out.println("2. Listar Pedidos");
            System.out.println("3. Eliminar Pedido (Baja lógica)");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(sc.nextLine()); // Validación de entrada numérica
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    // Alta de pedido
                    Usuario u = new Usuario("Cliente Prueba");
                    Pedido nuevoPedido = new Pedido("PED-" + Math.round(Math.random()*100), u);
                    
                    try {
                        // Ejemplo: agregamos 2 pizzas al pedido
                        DetallePedido detalle = new DetallePedido(p1, 2);
                        nuevoPedido.agregarDetalle(detalle);
                        gestor.agregarPedido(nuevoPedido);
                        System.out.println("Pedido agregado con éxito. Total: $" + nuevoPedido.calcularTotal());
                    } catch (StockInsuficienteException e) {
                        System.out.println("Error de Negocio: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n--- LISTADO DE PEDIDOS ---");
                    gestor.listarPedidos();
                    break;

                case 3:
                    System.out.print("Ingrese ID del pedido a eliminar: ");
                    String id = sc.nextLine();
                    if (gestor.eliminarPedido(id)) {
                        System.out.println("Pedido marcado como eliminado.");
                    } else {
                        System.out.println("Pedido no encontrado.");
                    }
                    break;

                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
        
        sc.close();
    }
}