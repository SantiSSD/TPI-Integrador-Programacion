package entities;

import enums.Rol;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Usuario extends Base {

    private static long contadorId = 1;

    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasena;
    private Rol rol;
    private List<Pedido> pedidos = new ArrayList();

    // CONSTRUCTOR COMPLETO:
    public Usuario(String nombre, String apellido, String mail, String celular, String contrasena, Rol rol, long id, LocalDateTime createdAt) {
        super(id, createdAt);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // CONSTRUCTOR PRINCIPAL:
    public Usuario(String nombre, String apellido, String mail, String celular, String contrasena, Rol rol, long id) {
        super(contadorId++);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + " | Nombre: " + apellido + ", " + nombre
                + " | Email: " + mail
                + " | Rol: " + rol;
    }
}
