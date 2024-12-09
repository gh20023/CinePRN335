package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoReserva extends AbstractFrm<TipoReserva> implements Serializable {

    @Inject
    TipoReservaBean trBean;
    @Inject
    FacesContext fc;

    @Override
    public void instanciarRegistro() {
        this.registro = new TipoReserva();
        this.registro.setActivo(true);
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersistence<TipoReserva> getAbstractDataPersistence() {
        return trBean;
    }

    @Override
    public String getIdByObject(TipoReserva object) {
        if (object != null) {
            return object.getIdTipoReserva().toString();
        }
        return null;
    }

    @Override
    public TipoReserva getObjectById(String id) {
        if (id != null && modelo != null && modelo.getWrappedData() != null) {

            return modelo.getWrappedData().stream().
                    filter(r -> id.equals(r.getIdTipoReserva().toString())).findFirst().
                    orElseGet(() -> {
                        Logger.getLogger(getClass().getName()).log(Level.INFO, "No se ha encontrado objeto");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<TipoReserva> event) {
        TipoReserva tipoReservaSelected = (TipoReserva) event.getObject();
        FacesMessage mensaje = new FacesMessage("Tipo de Reserva seleccionado", tipoReservaSelected.getNombre());
        fc.addMessage(null, mensaje);
        this.estado = ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public String paginaNombre() {
        return "Tipo de Reserva";
    }
}
