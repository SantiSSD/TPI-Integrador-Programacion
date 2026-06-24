/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.DetallePedido;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.FormaPago;
import excepciones.DatosInvalidosException;
import excepciones.EntidadNoEncontradaException;
import excepciones.StockInvalidoException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author autor
 */
public class GestorPedidos {

    //Colección en memoria para almacenar todos los pedidos del sistema
    private List<Pedido> listaPedidos = new ArrayList<>();
    // Necesitamos referencias a los otros servicios para buscar datos y tocar stock
    private UsuarioService usuarioService;
    private ProductoService productoService;

    // Constructor: Obligamos a que nos pasen los servicios existentes
    public GestorPedidos(UsuarioService usuarioService, ProductoService productoService) {
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }

    // HU-PED-02: Crear Pedido Completo
    public void registrarPedido(long idUsuario, FormaPago formaPago, List<DetalleAuxiliar> itemsAComprar)
            throws EntidadNoEncontradaException, StockInvalidoException, DatosInvalidosException {
        // 1. Validar que el usuario exista
        Usuario usuario = usuarioService.buscarPorId(idUsuario);
        if (itemsAComprar == null || itemsAComprar.isEmpty()) {
            throw new DatosInvalidosException("No se puede crear un pedido sin productos.");

        }
        // 2. Instanciar el nuevo pedido (nace en PENDIENTE, total 0.0)
        Pedido nuevoPedido = new Pedido(usuario, formaPago);

        for (DetalleAuxiliar item : itemsAComprar) {
            Producto prodReal = productoService.buscarPorId(item.idProducto);
            //Validar si hay stock suficiente
            if (prodReal.getStock() < item.cantidad) {
                throw new StockInvalidoException("Stock insuficiente para: " + prodReal.getNombre()
                        + " | Solicitado: " + item.cantidad + " | Disponible: " + prodReal.getStock());
            }

            prodReal.setStock(prodReal.getStock() - item.cantidad);
            // Fabricamos el DetallePedido real usando el constructor 
            DetallePedido nuevoDetalle = new DetallePedido(prodReal, item.cantidad);

            // Lo agregamos al pedido (este método adentro ya calcula el total automáticamente)
            nuevoPedido.addDetalle(nuevoDetalle);
        }

        listaPedidos.add(nuevoPedido);
    }

    public List<Pedido> listarPedidos() {
        List<Pedido> activos = new ArrayList<>();
        for (Pedido p : listaPedidos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }
// Buscar pedido específico por ID

    public Pedido buscarPorId(long id) throws EntidadNoEncontradaException {
        for (Pedido p : listaPedidos) {
            if (p.getId() == id && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("❌ No se encontró ningún pedido con el ID: " + id);
    }

// =========================================================================
// Sirve para recibir de la consola un ID de producto y una cantidad de forma temporal
// antes de transformarlos en un DetallePedido real con objetos de memoria.
// =========================================================================
    public static class DetalleAuxiliar {

        public long idProducto;
        public int cantidad;

        public DetalleAuxiliar(long idProducto, int cantidad) {
            this.idProducto = idProducto;
            this.cantidad = cantidad;
        }
    }
}
