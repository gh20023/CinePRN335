package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.FacturaDetalleProducto;

import java.io.Serializable;

@Named
@ViewScoped
public class FrmFacturaDetalleProducto extends AbstractFrm<FacturaDetalleProducto> implements Serializable {
    @Override
    public void instanciarRegistro() {

    }

    @Override
    public FacesContext getFC() {
        return null;
    }

    @Override
    public AbstractDataPersistence<FacturaDetalleProducto> getAbstractDataPersistence() {
        return null;
    }

    @Override
    public String getIdByObject(FacturaDetalleProducto object) {
        return "";
    }

    @Override
    public FacturaDetalleProducto getObjectById(String id) {
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<FacturaDetalleProducto> event) {

    }

    @Override
    public String paginaNombre() {
        return "";
    }
}
