package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.FacturaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.FacturaDetalleProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Factura;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.FacturaDetalleProducto;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Producto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class FrmCarrito implements Serializable {

    @Inject
    FacturaBean facturaBean;
    @Inject
    FacturaDetalleProductoBean facturaDetalleProductoBean;
    @Inject
    FacesContext fc;

    private String clienteNombre;
    private String clienteDui;

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
        clienteNombre = null;
        clienteDui = null;
        carrito.clear();
    }

    //Metodo para realizar la compra
    public void realizarCompra(){
        try {
            if(carrito.isEmpty()){
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede realizar la compra.", "El carrito de compras está vacío."));
            }else {
                // Crear y persistir una nueva factura
                Factura factura = new Factura();
                factura.setCliente(clienteNombre);
                factura.setDui(clienteDui);
                factura.setFecha(OffsetDateTime.now());
                factura.setComentarios("CREADA CON ÉXITO");
                facturaBean.create(factura);
                // Crear y persistir detalles de factura
                for (Producto producto : carrito) {
                    FacturaDetalleProducto detalle = new FacturaDetalleProducto();
                    detalle.setIdFactura(factura);
                    detalle.setIdProducto(producto);
                    detalle.setMonto(BigDecimal.valueOf(producto.getCantidad()));
                    facturaDetalleProductoBean.create(detalle);
                }
                limpiarCarrito();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra realizada", "Se ha registrado la compra exitosamente."));
            }
        } catch (Exception e) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo realizar la compra." + e.getMessage()));
        }
    }
    //Getters y setters
    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteDui() {
        return clienteDui;
    }

    public void setClienteDui(String clienteDui) {
        this.clienteDui = clienteDui;
    }
}
