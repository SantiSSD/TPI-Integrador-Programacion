/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author autor
 */
public class Categoria extends Base {

    private static long contadorId = 1;
    private String nombre;
    private String descripcion;

    //Constructor completo para datos de prueba o históricos
    public Categoria(String nombre, String descripcion, long id, LocalDateTime createdAt) {
        super(id, createdAt);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Constructor principal para la consola (ID automático)
    public Categoria(String nombre, String descripcion) {
        super(contadorId++);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + " | Categoría: " + nombre + " (" + descripcion + ")";
    }
}
