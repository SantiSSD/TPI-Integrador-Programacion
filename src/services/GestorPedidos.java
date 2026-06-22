package services;

import entities.Pedido;
import java.util.ArrayList;
import java.util.List;

public class GestorPedidos {
    // La lista se inicializa aquí mismo, es correcto y limpio
    private List<Pedido> pedidos = new ArrayList<>();

    public void agregarPedido(Pedido p) { 
        pedidos.add(p); 
    }

    public void listarPedidos() {
        boolean hayPedidos = false;
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                System.out.println(p);
                hayPedidos = true;
            }
        }
        if (!hayPedidos) {
            System.out.println("No hay pedidos activos para mostrar.");
        }
    }
    
    // Método mejorado: devuelve true si se eliminó, false si no se encontró
    public boolean eliminarPedido(String id) {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id)) {
                p.setEliminado(true);
                return true; // Éxito al eliminar
            }
        }
        return false; // El ID no existía
    }
    
    // Método extra útil: buscar por ID (para cuando necesites validar antes de eliminar)
    public Pedido buscarPedidoPorId(String id) {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        return null;
    }
}