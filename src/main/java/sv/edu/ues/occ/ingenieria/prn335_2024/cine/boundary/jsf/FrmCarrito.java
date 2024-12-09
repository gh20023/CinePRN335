package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Producto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class FrmCarrito implements Serializable {

    private List<Producto> carrito = new ArrayList<>();

    public List<Producto> getCarrito() {
        return carrito;
    }

    public void agregarAlCarrito(Producto producto) {
        carrito.add(producto);
    }

    public void quitarDelCarrito(Producto producto) {
        carrito.remove(producto);
    }

    public void limpiarCarrito() {
        carrito.clear();
    }
}
