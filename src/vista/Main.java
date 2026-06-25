/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import entities.Categoria;
import entities.Producto;
import entities.Usuario;
import enums.FormaPago;
import enums.Rol;
import excepciones.DatosInvalidosException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;
import excepciones.StockInvalidoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import services.CategoriaService;
import services.GestorPedidos;
import services.ProductoService;
import services.UsuarioService;

/**
 *
 * @author autor
 */
public class Main {
    // Instanciamos los servicios de forma estática para usarlos en todos los submétodos

    private static CategoriaService categoriaService = new CategoriaService();
    private static ProductoService productoService = new ProductoService();
    private static UsuarioService usuarioService = new UsuarioService();
    private static GestorPedidos gestorPedidos = new GestorPedidos(usuarioService, productoService);
    private static Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n===============================================");
            System.out.println("          FOODSTORE - MENU PRINCIPAL           ");
            System.out.println("===============================================");
            System.out.println("1.  Gestion de Categorias");
            System.out.println("2.  Gestion de Productos");
            System.out.println("3.  Gestion de Usuarios");
            System.out.println("4.  Gestion de Pedidos");
            System.out.println("0.  Salir del Sistema");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(leer.nextLine());

                switch (opcion) {
                    case 1:
                        menuCategorias();
                        break;
                    case 2:
                        menuProductos();
                        break;
                    case 3:
                        menuUsuarios();
                        break;
                    case 4:
                        menuPedidos();
                        break;
                    case 0:
                        System.out.println("\n ¡Gracias por usar FoodStore! Cerrando sistema...");
                        break;
                    default:
                        System.out.println(" Opcion invalida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Debe ingresar un número entero válido.");
            }
        }
    }

    // ==========================================
    // 📂 SECCIÓN: GESTIÓN DE CATEGORÍAS
    // ==========================================
    private static void menuCategorias() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- 📁 SUBMENÚ: GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Crear Categoría");
            System.out.println("2. Listar Categorías Activas");
            System.out.println("3. Editar Categoría ");
            System.out.println("4. Eliminar Categoría - Baja Lógica");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            try {
                op = Integer.parseInt(leer.nextLine());
                switch (op) {
                    case 1:
                        System.out.println("\n--- NUEVA CATEGORÍA ---");
                        System.out.print("Nombre: ");
                        String nom = leer.nextLine();
                        System.out.print("Descripción: ");
                        String desc = leer.nextLine();

                        categoriaService.crearCategoria(nom, desc);
                        System.out.println("✅ ¡Categoría guardada con éxito en memoria!");
                        break;
                    case 2:
                        System.out.println("\n--- LISTA DE CATEGORÍAS ACTIVAS ---");
                        List<Categoria> analizadas = categoriaService.listarCategorias();
                        if (analizadas.isEmpty()) {
                            System.out.println("No hay categorías registradas o activas.");
                        } else {
                            analizadas.forEach(System.out::println);
                        }
                        break;
                    case 3:
                        System.out.println("\n--- EDITAR CATEGORÍA ---");
                        System.out.print("Ingrese ID de la categoría a modificar: ");
                        long idEdit = Long.parseLong(leer.nextLine());
                        System.out.print("Nuevo Nombre: ");
                        String nuevoNom = leer.nextLine();
                        System.out.print("Nueva Descripción: ");
                        String nuevaDesc = leer.nextLine();

                        categoriaService.editarCategoria(idEdit, nuevoNom, nuevaDesc);
                        System.out.println("✅ ¡Categoría modificada correctamente!");
                        break;
                    case 4:
                        System.out.println("\n--- ELIMINAR CATEGORÍA (BAJA LÓGICA) ---");
                        System.out.print("Ingrese ID de la categoría a dar de baja: ");
                        long idEli = Long.parseLong(leer.nextLine());

                        categoriaService.eliminarCategoria(idEli);
                        System.out.println("✅ ¡Categoría dada de baja de forma lógica!");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("⚠️ Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Ingrese un ID numérico válido.");
            } catch (DatosInvalidosException | EntidadDuplicadaException | EntidadNoEncontradaException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // ==========================================
    // 🍔 SECCIÓN: GESTIÓN DE PRODUCTOS
    // ==========================================
    private static void menuProductos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- SUBMENÚ: GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Crear Producto");
            System.out.println("2. Listar Productos Activos");
            System.out.println("3. Editar Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                op = Integer.parseInt(leer.nextLine());
                if (op == 1) {
                    System.out.println("\n--- NUEVO PRODUCTO ---");
                    List<Categoria> categoriasDisponibles = categoriaService.listarCategorias();

                    if (categoriasDisponibles.isEmpty()) {
                        System.out.println("❌ Error: No podés crear un producto si no existen categorías activas primero.");
                        return;
                    }

                    System.out.println("Categorías Disponibles:");
                    for (Categoria cat : categoriasDisponibles) {
                        System.out.println("   ➡ [ID: " + cat.getId() + "] - " + cat.getNombre());
                    }
                    System.out.println("---------------------------------------");

                    System.out.print("Nombre: ");
                    String nombre = leer.nextLine();
                    System.out.print("Precio: ");
                    double precio = Double.parseDouble(leer.nextLine());
                    System.out.print("Descripción: ");
                    String desc = leer.nextLine();
                    System.out.print("Stock Inicial: ");
                    int stock = Integer.parseInt(leer.nextLine());
                    System.out.print("URL Imagen: ");
                    String img = leer.nextLine();

                    System.out.print("Ingrese ID de la Categoría asociada: ");
                    long idCat = Long.parseLong(leer.nextLine());
                    Categoria catAsociada = categoriaService.buscarPorId(idCat);

                    productoService.crearProducto(nombre, precio, desc, stock, img, catAsociada);
                    System.out.println("¡Producto guardado con éxito!");

                } else if (op == 2) {
                    System.out.println("\n--- LISTA DE PRODUCTOS ACTIVOS ---");
                    if (productoService.listarProductos().isEmpty()) {
                        System.out.println("No hay productos registrados en el sistema.");
                    } else {
                        productoService.listarProductos().forEach(System.out::println);
                    }
                } else if (op == 3) {
                    System.out.println("\n--- EDITAR PRODUCTO ---");
                    List<Producto> productosActivos = productoService.listarProductos();
                    if (productosActivos.isEmpty()) {
                        System.out.println("No hay productos registrados para editar.");
                        return;
                    }
                    System.out.println("Productos Disponibles:");
                    for (Producto p : productosActivos) {
                        System.out.println("   ➡ [ID: " + p.getId() + "] - " + p.getNombre());
                    }
                    System.out.println("---------------------------------------");
                    System.out.println("Ingrese el ID del producto a editar: ");
                    long idProdEdit = Long.parseLong(leer.nextLine());
                    System.out.print("Nuevo Nombre: ");
                    String nomEdit = leer.nextLine();
                    System.out.print("Nuevo Precio: ");
                    double precEdit = Double.parseDouble(leer.nextLine());
                    System.out.print("Nueva Descripción: ");
                    String descEdit = leer.nextLine();
                    System.out.print("Nuevo Stock: ");
                    int stockEdit = Integer.parseInt(leer.nextLine());
                    System.out.print("Nueva URL Imagen: ");
                    String imgEdit = leer.nextLine();

                    List<Categoria> categoriasDisp = categoriaService.listarCategorias();
                    System.out.println("Categorías Disponibles:");
                    for (Categoria c : categoriasDisp) {
                        System.out.println("   ➡ [ID: " + c.getId() + "] - " + c.getNombre());
                    }
                    System.out.print("Ingrese ID de la nueva Categoría: ");
                    long idCatEdit = Long.parseLong(leer.nextLine());
                    Categoria catEdit = categoriaService.buscarPorId(idCatEdit);
                    productoService.editarProducto(idProdEdit, nomEdit, precEdit, descEdit, stockEdit, imgEdit, catEdit);
                    System.out.println("✅ ¡Producto editado con éxito!");

                } else if (op == 4) {
                    System.out.println("\n--- ELIMINAR PRODUCTO (BAJA LÓGICA) ---");
                    System.out.print("Ingrese ID del producto a eliminar: ");
                    long idProdEl = Long.parseLong(leer.nextLine());
                    System.out.print("¿Está seguro? (S/N): ");
                    if (leer.nextLine().equalsIgnoreCase("S")) {
                        productoService.eliminarProducto(idProdEl);
                        
                        System.out.println("✅ ¡Producto eliminado lógicamente!");
                    } else {
                        System.out.println("⚠️ Operación cancelada.");
                    }
                } else if (op != 0) {
                    System.out.println("⚠️ Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Entrada invalida.");

            } catch (DatosInvalidosException | EntidadNoEncontradaException | StockInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // ==========================================
    // 🛒 SECCIÓN: GESTIÓN DE PEDIDOS
    // ==========================================
    private static void menuPedidos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- SUBMENÚ: GESTIÓN DE PEDIDOS ---");
            System.out.println("1. Registrar Pedido Completo");
            System.out.println("2. Listar Historial de Pedidos");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            try {
                op = Integer.parseInt(leer.nextLine());

                if (op == 1) {
                    System.out.println("\n--- NUEVO PEDIDO ---");

                    List<Usuario> usuariosDisponibles = usuarioService.listarUsuarios();
                    if (usuariosDisponibles.isEmpty()) {
                        System.out.println("❌ Error: No podés crear un pedido sin clientes activos.");
                        return;
                    }

                    System.out.println("👥 Clientes Disponibles:");
                    for (Usuario u : usuariosDisponibles) {
                        System.out.println("   ➡ [ID: " + u.getId() + "] - " + u.getNombre() + " " + u.getApellido());
                    }
                    System.out.println("---------------------------------------");

                    System.out.print("Ingrese ID del Usuario (Cliente): ");
                    long idUser = Long.parseLong(leer.nextLine());

                    System.out.println("Formas de Pago: 1. EFECTIVO | 2. TARJETA | 3. TRANSFERENCIA");
                    System.out.print("Seleccione Forma de Pago: ");
                    int fpOp = Integer.parseInt(leer.nextLine());
                    FormaPago fp = FormaPago.EFECTIVO;
                    if (fpOp == 2) {
                        fp = FormaPago.TARJETA;
                    }
                    if (fpOp == 3) {
                        fp = FormaPago.TRANSFERENCIA;
                    }

                    List<GestorPedidos.DetalleAuxiliar> carrito = new ArrayList<>();
                    boolean agregando = true;

                    while (agregando) {
                        List<Producto> productosDisponibles = productoService.listarProductos();
                        if (productosDisponibles.isEmpty()) {
                            System.out.println("❌ Error: No hay productos cargados para vender.");
                            return;
                        }

                        System.out.println("\n Menú de Productos:");
                        for (Producto p : productosDisponibles) {
                            System.out.println("   ➡ [ID: " + p.getId() + "] - " + p.getNombre() + " | Precio: $" + p.getPrecio() + " | Stock: " + p.getStock());
                        }
                        System.out.println("---------------------------------------");

                        System.out.print("Ingrese ID del Producto a comprar: ");
                        long idProd = Long.parseLong(leer.nextLine());
                        System.out.print("Cantidad: ");
                        int cant = Integer.parseInt(leer.nextLine());

                        carrito.add(new GestorPedidos.DetalleAuxiliar(idProd, cant));
                        System.out.print("¿Quiere agregar otro producto? (S/N): ");
                        String resp = leer.nextLine();
                        if (resp.equalsIgnoreCase("N")) {
                            agregando = false;
                        }
                    }

                    gestorPedidos.registrarPedido(idUser, fp, carrito);
                    System.out.println(" ¡Pedido registrado con éxito!");

                } else if (op == 2) {
                    System.out.println("\n--- HISTORIAL DE PEDIDOS ---");
                    if (gestorPedidos.listarPedidos().isEmpty()) {
                        System.out.println("No hay pedidos registrados.");
                    } else {
                        gestorPedidos.listarPedidos().forEach(System.out::println);
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Formato incorrecto.");
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    // ==========================================
    // 👥 SECCIÓN: GESTIÓN DE USUARIOS 
    // ==========================================
    private static void menuUsuarios() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- 👥 SUBMENÚ: GESTIÓN DE USUARIOS ---");
            System.out.println("1. Registrar Usuario (HU-USR-02)");
            System.out.println("2. Listar Usuarios Activos (HU-USR-01)");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                op = Integer.parseInt(leer.nextLine());
                if (op == 1) {
                    System.out.println("\n--- REGISTRAR NUEVO USUARIO ---");
                    System.out.print("Nombre: ");
                    String n = leer.nextLine();
                    System.out.print("Apellido: ");
                    String a = leer.nextLine();
                    System.out.print("Email: ");
                    String m = leer.nextLine();
                    System.out.print("Celular: ");
                    String c = leer.nextLine();
                    System.out.print("Contraseña: ");
                    String pass = leer.nextLine();

                    usuarioService.registrarUsuario(n, a, m, c, pass, Rol.USUARIO);
                    System.out.println("✅ ¡Usuario creado con éxito!");

                } else if (op == 2) {
                    System.out.println("\n--- LISTA DE USUARIOS ACTIVOS ---");
                    if (usuarioService.listarUsuarios().isEmpty()) {
                        System.out.println("No hay usuarios activos en el sistema.");
                    } else {
                        usuarioService.listarUsuarios().forEach(System.out::println);
                    }
                } else if (op != 0) {
                    System.out.println("⚠️ Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida.");
            } catch (DatosInvalidosException | EntidadDuplicadaException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
