package entities;

import enums.Estado;
import enums.FormaPago;
import interfaces.Calculable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pedido extends Base implements Calculable {

    private static long contadorId = 1;

    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;

    private Usuario usuario;

    private List<DetallePedido> detalles = new ArrayList<>();

    public Pedido(Usuario usuario, FormaPago formaPago) {
        super(contadorId++);
        this.usuario = usuario;
        this.formaPago = formaPago;
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.total = 0.0;
    }

    //CONSTRUCTOR COMPLETO
    public Pedido(LocalDate fecha, Estado estado, FormaPago formaPago, Usuario usuario, long id, LocalDateTime createdAt) {
        super(id, createdAt);
        this.fecha = fecha;
        this.estado = estado;
        this.total = 0.0;
        this.formaPago = formaPago;
        this.usuario = usuario;
    }

    @Override
    public void calcularTotal() 
    {
        double sumaSubTotales = 0.0;
        for (DetallePedido detalle : detalles)
        {
            if (!detalle.isEliminado()) {
                sumaSubTotales += detalle.getSubtotal();

            }
        }
        this.total = sumaSubTotales;
    }



// 🌟 MÉTODOS PROPIOS EXIGIDOS POR EL UML
    // Agrega un detalle a la lista
    
    public void addDetalle(DetallePedido detalle)
    {
        if(detalle != null)
        {
            this.detalles.add(detalle);
            calcularTotal();
            
        }
    }
       
        // Busca un detalle según el producto pasado por parámetro
    public DetallePedido findDetallePedidoByProducto(Producto producto)
    {
        for (DetallePedido detalle : detalles) {
            if(detalle.getProducto().getId() == producto.getId() && !detalle.isEliminado())
            {
                return detalle;
               
            }
            
        }
    return null;
    }
    
    //Eliminacion Logica
    public void deleteDetallePedidoByProducto(Producto producto)
    {
        DetallePedido detalle = findDetallePedidoByProducto(producto);
        if(detalle != null)
        {
            detalle.setEliminado(true);
            calcularTotal();
        }
    }
    // GETTERS Y SETTERS
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public double getTotal() { return total; }

    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<DetallePedido> getDetalles() { return detalles; }

    // toString() útil para listar los pedidos en la consola (HU-PED-01)
    @Override
    public String toString() {
        return "Pedido ID: " + getId() + 
               " | Cliente: " + usuario.getApellido()+ ", " + usuario.getNombre() +
               " | Fecha: " + fecha + 
               " | Estado: " + estado + 
               " | Pago: " + formaPago + 
               " | TOTAL: $" + total;
    }
    
}
