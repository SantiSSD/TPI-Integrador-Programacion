package modelo;

import java.util.ArrayList;
import java.util.List;
import excepciones.StockInsuficienteException;

public class Pedido implements Calculable {
    private String id;
    private Usuario usuario;
    private List<DetallePedido> detalles;
    private boolean eliminado;

    public Pedido(String id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
        this.detalles = new ArrayList<>();
        this.eliminado = false;
    }

    public void agregarDetalle(DetallePedido detalle) throws StockInsuficienteException {
        if (detalle.getProducto().getStock() < detalle.getCantidad()) {
            throw new StockInsuficienteException("Stock insuficiente para: " + detalle.getProducto().getNombre());
        }
        this.detalles.add(detalle);
        detalle.getProducto().setStock(detalle.getProducto().getStock() - detalle.getCantidad());
    }

    @Override
    public double calcularTotal() {
        double total = 0;
        for (DetallePedido d : detalles) total += d.getSubtotal();
        return total;
    }
    
    // --- ESTOS SON VITALES ---
    public String getId() { return id; }
    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
    
    @Override
    public String toString() {
        return "Pedido ID: " + id + " | Usuario: " + usuario.getNombre() + " | Total: $" + calcularTotal();
    }
}