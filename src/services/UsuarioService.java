/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Usuario;
import enums.Rol;
import excepciones.DatosInvalidosException;
import excepciones.EntidadDuplicadaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author autor
 */
public class UsuarioService {
 // Colección en memoria para los usuarios
    private List<Usuario> listaUsuarios = new ArrayList<>();

    // HU-USR-02: Registrar Usuario (Con validación de mail único)
    public void registrarUsuario(String nombre, String apellido, String mail, String celular, String contrasena, Rol rol) 
            throws DatosInvalidosException, EntidadDuplicadaException {
        
        // 1. Validar campos vacíos
        if (nombre == null || nombre.trim().isEmpty() || 
            apellido == null || apellido.trim().isEmpty() || 
            mail == null || mail.trim().isEmpty() || 
            contrasena == null || contrasena.trim().isEmpty()) {
            throw new DatosInvalidosException("❌ Los campos obligatorios (Nombre, Apellido, Email, Contraseña) no pueden estar vacíos.");
        }

        // 2. Validar regla de negocio: Email único (Usa tu EntidadDuplicadaException)
        for (Usuario u : listaUsuarios) {
            if (u.getMail().equalsIgnoreCase(mail) && !u.isEliminado()) {
                throw new EntidadDuplicadaException("❌ Ya existe un usuario activo registrado con el email: " + mail);
            }
        }

        // Si pasa los controles, se crea con el ID autoincremental de ayer
        Usuario nuevo = new Usuario(nombre, apellido, mail, celular, contrasena, rol);
        listaUsuarios.add(nuevo);
    }

    // HU-USR-01: Listar Usuarios activos
    public List<Usuario> listarUsuarios() {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario u : listaUsuarios) {
            if (!u.isEliminado()) {
                activos.add(u);
            }
        }
        return activos;
    }

    // Método auxiliar para buscar por ID
    public Usuario buscarPorId(long id) throws EntidadNoEncontradaException {
        for (Usuario u : listaUsuarios) {
            if (u.getId() == id && !u.isEliminado()) {
                return u;
            }
        }
        throw new EntidadNoEncontradaException("❌ No se encontró ningún usuario activo con el ID: " + id);
    }

    // HU-USR-03: Editar Usuario
    public void editarUsuario(long id, String nuevoNombre, String nuevoApellido, String nuevoCelular, String nuevaContrasena, Rol nuevoRol) 
            throws EntidadNoEncontradaException, DatosInvalidosException {
        
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty() || nuevoApellido == null || nuevoApellido.trim().isEmpty()) {
            throw new DatosInvalidosException("❌ El nombre y el apellido no pueden quedar vacíos al editar.");
        }

        Usuario u = buscarPorId(id);
        u.setNombre(nuevoNombre);
        u.setApellido(nuevoApellido);
        u.setCelular(nuevoCelular);
        u.setContrasena(nuevaContrasena);
        u.setRol(nuevoRol);
    }

    // HU-USR-04: Eliminar Usuario (Baja Lógica)
    public void eliminarUsuario(long id) throws EntidadNoEncontradaException {
        Usuario u = buscarPorId(id);
        u.setEliminado(true); // Soft delete
    }
}
