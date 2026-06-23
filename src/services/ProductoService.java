/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Categoria;
import entities.Producto;
import excepciones.DatosInvalidosException;
import excepciones.EntidadNoEncontradaException;
import excepciones.StockInvalidoException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author autor
 */
public class ProductoService {
    // Colección en memoria para los productos
    private final List<Producto> listaProductos = new ArrayList();
    
    // HU-PROD-02: Crear Producto
    public void crearProducto(String nombre, double precio, String descripcion, int stock, String imagen, Categoria categoria)
        throws DatosInvalidosException, StockInvalidoException
    {
      // 1. Validaciones de datos vacíos o incorrectos
        if (nombre == null || nombre.trim().isEmpty() || descripcion == null || descripcion.trim().isEmpty()) {
            throw new DatosInvalidosException("❌ El nombre y la descripción no pueden estar vacíos.");
        }
        if (precio <= 0) {
            throw new DatosInvalidosException("❌ El precio debe ser mayor a $0.");
        }
        
        // 2. Validación de Stock (Regla de negocio del PDF)
        if (stock < 0) {
            throw new StockInvalidoException("❌ El stock inicial no puede ser negativo.");
        }
        if (categoria == null || categoria.isEliminado()) {
            throw new DatosInvalidosException("❌ El producto debe tener una categoría válida asignada.");
        }   
        
       Producto nuevo = new Producto(nombre, precio, descripcion, stock, imagen, true, categoria);
       listaProductos.add(nuevo);
    }
    
    // HU-PROD-01: Listar Productos activos
    public List<Producto> listarProductos() {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : listaProductos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }
    // Método auxiliar para buscar por ID
    public Producto buscarPorId(long id) throws EntidadNoEncontradaException {
        for (Producto p : listaProductos) {
            if (p.getId() == id && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("❌ No se encontró ningún producto activo con el ID: " + id);
    }
    // HU-PROD-03: Editar Producto
    public void editarProducto(long id, String nuevoNombre, double nuevoPrecio, String nuevaDesc, int nuevoStock, String nuevaImg, Categoria nuevaCat) 
            throws EntidadNoEncontradaException, DatosInvalidosException, StockInvalidoException {
        
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty() || nuevoPrecio <= 0 || nuevaCat == null) {
            throw new DatosInvalidosException("❌ Los datos de edición son inválidos o están vacíos.");
        }
        if (nuevoStock < 0) {
            throw new StockInvalidoException("❌ El stock no puede ser negativo.");
        }

        Producto p = buscarPorId(id);
        p.setNombre(nuevoNombre);
        p.setPrecio(nuevoPrecio);
        p.setDescripcion(nuevaDesc);
        p.setStock(nuevoStock);
        p.setImagen(nuevaImg);
        p.setCategoria(nuevaCat);
    }

    // HU-PROD-04: Eliminar Producto (Baja Lógica)
    public void eliminarProducto(long id) throws EntidadNoEncontradaException {
        Producto p = buscarPorId(id);
        p.setEliminado(true); // Soft delete
    }
}
