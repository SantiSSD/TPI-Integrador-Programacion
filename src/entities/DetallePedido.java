package entities;

import java.time.LocalDateTime;

public class DetallePedido extends Base {

    //1. Contador estático para autogenerar el ID en memoria
    private static long contadorId = 1;

    private Producto producto;
    private int cantidad;
    private double subtotal;

    public DetallePedido(Producto producto, int cantidad, double subtotal, long id, LocalDateTime createdAt) {
        super(id, createdAt);
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    //CONSTRUCTOR PRINCIPAL: EL que vamos a usar desde el menú de la consola
    public DetallePedido(Producto producto, int cantidad) {
        super(contadorId++);
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal();

    }

// CONSTRUCTOR COMPLETO: Por si necesitamos hardcodear datos de prueba específicos
    public DetallePedido(Long id, LocalDateTime createdAt, Producto producto, int cantidad) {
        super(id, createdAt);
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal();
    }

    private double calcularSubtotal() {
        if (this.producto != null) {
            return this.cantidad * this.producto.getPrecio();
        }
        return 0.0;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }


    public double getSubtotal() {
        return subtotal;
    }
    
    @Override
    public String toString(){
        return "Producto: "+ (producto != null ? producto.getNombre() : "N/A") + 
                " | Cantidad: " + cantidad +
                " | Subtotal: $ " + subtotal;
    }
    
}
