package entities;
import enums.Categoria;

public class Producto {
    private String id;
    private String nombre;
    private double precio;
    private int stock;
    private Categoria categoria;
    private boolean disponible;

    public Producto(String id, String nombre, double precio, int stock, Categoria categoria, boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.disponible = disponible;
    }



    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return nombre + " ($" + precio + ") - Stock: " + stock;
    }
}