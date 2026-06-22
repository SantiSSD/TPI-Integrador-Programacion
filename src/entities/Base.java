/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.time.LocalDateTime;

/**
 *
 * @author autor
 */
public abstract class Base {
    private long id;
    private boolean eliminado = false;
    private LocalDateTime createdAt;
    
    // 2.Por si necesitamos forzar fecha y ID específicos (ej. datos de prueba)
    public Base(long id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Base(long id) {
        this.id = id;
        this.createdAt = createdAt;
        this.eliminado = false;
    }

    public long getId() {
        return id;
    }
   
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    
}
