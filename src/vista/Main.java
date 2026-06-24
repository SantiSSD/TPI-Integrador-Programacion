/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import entities.Categoria;
import entities.Producto;
import enums.Rol;
import excepciones.DatosInvalidosException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;
import services.CategoriaService;
import services.ProductoService;
import services.UsuarioService;

/**
 *
 * @author autor
 */
  

    public class Main {
        // Instanciamos los servicios de forma estática para usarlos en todos los submétodos
    }
    private static CategoriaService categoriaService = new CategoriaService();
    private static ProductoService productoService = new ProductoService();
    private static UsuarioService usuarioService = new UsuarioService();
    private static Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n===============================================");
            System.out.println("          FOODSTORE - MENÚ PRINCIPAL           ");
            System.out.println("===============================================");
            System.out.println("1. 📂 Gestión de Categorías");
            System.out.println("2. 🍔 Gestión de Productos");
            System.out.println("3. 👥 Gestión de Usuarios");
            System.out.println("0. ❌ Salir del Sistema");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(leer.nextLine());

                switch (opcion) {
                    case 1:
                        menuCategorias();
                        break;
                    case 2:
                        System.out.println("\n🚧 Módulo de Productos");
                        break;
                    case 3:
                        menuUsuarios();
                        break;
                    case 0:
                        System.out.println("\n👋 ¡Gracias por usar FoodStore! Cerrando sistema...");
                        break;
                    default:
                        System.out.println("⚠️ Opción inválida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Debe ingresar un número entero válido.");
            }
        }
    }
    // ==========================================
    // 📂 SECCIÓN: GESTIÓN DE CATEGORÍAS (ÉPICA 1)
    // ==========================================

    private static void menuCategorias() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- 📁 SUBMENÚ: GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Crear Categoría (HU-CAT-02)");
            System.out.println("2. Listar Categorías Activas (HU-CAT-01)");
            System.out.println("3. Editar Categoría (HU-CAT-03)");
            System.out.println("4. Eliminar Categoría - Baja Lógica (HU-CAT-04)");
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
                // Captura el mensaje preciso (ej: "El nombre ya existe.") 
                System.out.println(e.getMessage());
            }
        }
    }

    // ==========================================
    // 👥 SECCIÓN: GESTIÓN DE USUARIOS (ÉPICA 3)
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

                    // Asignamos por defecto el Rol USUARIO para las pruebas
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
