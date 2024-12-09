package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoAsiento extends AbstractFrm<TipoAsiento> implements Serializable {

    @Inject
    TipoAsientoBean taBean;
    @Inject
    FacesContext fc;

    @Override
    public void instanciarRegistro() {
        this.registro = new TipoAsiento();
        this.registro.setActivo(true); // Asumiendo que 'activo' es un campo booleano
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersistence<TipoAsiento> getAbstractDataPersistence() {
        return taBean;
    }

    @Override
    public String getIdByObject(TipoAsiento object) {
        if (object != null) {
            return object.getIdTipoAsiento().toString(); // Asumiendo que existe un ID para TipoAsiento
        }
        return null;
    }

    @Override
    public TipoAsiento getObjectById(String id) {
        if (id != null && modelo != null && modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream()
                    .filter(r -> id.equals(r.getIdTipoAsiento().toString()))
                    .findFirst()
                    .orElseGet(() -> {
                        Logger.getLogger(getClass().getName()).log(Level.INFO, "No se ha encontrado objeto");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<TipoAsiento> event) {
        TipoAsiento tipoAsientoSelected = (TipoAsiento) event.getObject();
        FacesMessage mensaje = new FacesMessage("Tipo de asiento seleccionado", tipoAsientoSelected.getNombre());
        fc.addMessage(null, mensaje);
        this.estado = ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public String paginaNombre() {
        return "Tipo de Asiento";
    }
}
