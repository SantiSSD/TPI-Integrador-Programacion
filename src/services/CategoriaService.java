/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Categoria;
import excepciones.DatosInvalidosException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author autor
 */
public class CategoriaService {

    // La colección en memoria que guarda todas las categorías del sistema
    private List<Categoria> listaCategorias = new ArrayList<>();

    // HU-CAT-02: Crear categoría (Valida campos vacíos y nombre único)
    public void crearCategoria(String nombre, String descripcion)
            throws EntidadDuplicadaException, DatosInvalidosException {
        // 1. Validamos datos vacíos con tu excepción
        if (nombre == null || nombre.trim().isEmpty() || descripcion == null || descripcion.trim().isEmpty()) {
            throw new DatosInvalidosException("❌ El nombre y la descripción no pueden estar vacíos.");
        }

        //2. Validación de regla de negocio: nombre único
        for (Categoria c : listaCategorias) {
            if (c.getNombre().equalsIgnoreCase(nombre) && !c.isEliminado()) {
                throw new EntidadDuplicadaException("❌ Ya existe una categoría activa con el nombre: " + nombre);
            }
        }

        //Si se pasan los filtros, se crea con el ID automático
        Categoria nueva = new Categoria(nombre, descripcion);
        listaCategorias.add(nueva);
    }

    // HU-CAT-01: Listar categorías (Solo muestra las NO eliminadas)
    public List<Categoria> listarCategorias() {
        List<Categoria> activas = new ArrayList<>();
        for (Categoria c : listaCategorias) {

            if (!c.isEliminado()) {
                activas.add(c);
            }
        }
        return activas;

    }

    public Categoria buscarPorId(long id) throws EntidadNoEncontradaException {
        for (Categoria c : listaCategorias) {
            if (c.getId() == id && !c.isEliminado()) {
                return c;
            }
        }
        throw new EntidadNoEncontradaException("❌ No se encontró ninguna categoría activa con el ID: " + id);
    }

    // HU-CAT-03: Editar categoría
    public void editarCategoria(long id, String nuevoNombre, String nuevaDescripcion) throws EntidadNoEncontradaException, DatosInvalidosException {
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty() || nuevaDescripcion == null || nuevaDescripcion.trim().isEmpty()) {
            throw new DatosInvalidosException("❌ Los campos editados no pueden estar vacíos.");
        }

        Categoria c = buscarPorId(id); 
        c.setNombre(nuevoNombre);
        c.setDescripcion(nuevaDescripcion);
    }

    // HU-CAT-04: Eliminar categoría (Baja Lógica)
    public void eliminarCategoria(long id) throws EntidadNoEncontradaException {
        Categoria c = buscarPorId(id);
        c.setEliminado(true);
    }

}
