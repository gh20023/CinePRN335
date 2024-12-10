package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.FacturaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Factura;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.FacturaDetalleProducto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmFactura extends AbstractFrm<Factura> implements Serializable {

    @Inject
    FacturaBean factBean;
    @Inject
    FacesContext fc;
    private List<FacturaDetalleProducto> factDetallesProducto;

    @Override
    public void instanciarRegistro() {
        this.registro=new Factura();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersistence<Factura> getAbstractDataPersistence() {
        return factBean;
    }

    @Override
    public String getIdByObject(Factura object) {
        if(object.getIdFactura() != null){
            return object.getIdFactura().toString();
        }
        return null;
    }

    @Override
    public Factura getObjectById(String id) {
        if (id != null & modelo != null & modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream().
                    filter(r -> id.equals(r.getIdFactura().toString())).findFirst().
                    orElseGet(() -> {
                        Logger.getLogger("no se ha encontrado el objeto");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Factura> event) {
        Factura facturaSelected = (Factura) event.getObject();

        if (facturaSelected != null) {
            // Cargar y almacenar los detalles de la factura
            factDetallesProducto = facturaSelected.getFacturaDetalleProductoList();
        } else {
            factDetallesProducto = new ArrayList<>();
        }

        FacesMessage mensaje = new FacesMessage("Factura seleccionada", facturaSelected.getIdFactura().toString());
        fc.addMessage(null, mensaje);
        this.estado = ESTADO_CRUD.MODIFICAR;
    }

    public List<FacturaDetalleProducto> getFactDetallesProducto() {
        return factDetallesProducto;
    }

    public void setFactDetallesProducto(List<FacturaDetalleProducto> factDetallesProducto) {
        this.factDetallesProducto = factDetallesProducto;
    }

    @Override
    public String paginaNombre() {
        return "Factura";
    }
}
