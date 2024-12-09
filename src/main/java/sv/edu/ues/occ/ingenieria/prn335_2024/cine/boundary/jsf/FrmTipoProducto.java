package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Producto;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoProducto extends AbstractFrm<TipoProducto> implements Serializable {

    @Inject
    TipoProductoBean tpBean;

    @Inject
    ProductoBean productoBean;

    @Inject
    FacesContext fc;

    private List<Producto> productos;

    @Override
    public void instanciarRegistro() {
        this.registro = new TipoProducto();
        this.registro.setActivo(true);
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersistence<TipoProducto> getAbstractDataPersistence() {
        return tpBean;
    }

    @Override
    public String getIdByObject(TipoProducto object) {
        if (object != null) {
            return object.getIdTipoProducto().toString();
        }
        return null;
    }

    @Override
    public TipoProducto getObjectById(String id) {
        if (id != null && modelo != null && modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream()
                    .filter(r -> id.equals(r.getIdTipoProducto().toString()))
                    .findFirst()
                    .orElseGet(() -> {
                        Logger.getLogger(getClass().getName()).log(Level.INFO, "No se ha encontrado objeto");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<TipoProducto> event) {
        TipoProducto tipoProductoSelected = event.getObject();
        FacesMessage mensaje = new FacesMessage("Tipo de producto seleccionado", tipoProductoSelected.getNombre());
        fc.addMessage(null, mensaje);

        // Cargar productos basados en el tipo seleccionado
        this.productos = productoBean.findProductosByIdTP(tipoProductoSelected.getIdTipoProducto());
        this.estado = ESTADO_CRUD.PRODUCTOS; // Cambiar estado a PRODUCTOS
    }

    public List<Producto> getProductos() {
        return productos;
    }

    @Override
    public String paginaNombre() {
        return "Tipo Producto";
    }
}
