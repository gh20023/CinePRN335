package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPagoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPago;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoPago extends AbstractFrm<TipoPago> implements Serializable {

    @Inject
    TipoPagoBean tpaBean;
    @Inject
    FacesContext fc;
    @Override
    public void instanciarRegistro() {
        this.registro = new TipoPago();
        this.registro.setActivo(true);

    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersistence<TipoPago> getAbstractDataPersistence() {
        return tpaBean;
    }

    @Override
    public String getIdByObject(TipoPago object) {
        if (object!=null){
            return object.getIdTipoPago().toString();
        }
        return null;
    }

    @Override
    public TipoPago getObjectById(String id) {
        if (id!=null && modelo!=null && modelo.getWrappedData()!=null){

            return modelo.getWrappedData().stream().
                    filter(r->id.equals(r.getIdTipoPago().toString())).findFirst().
                    orElseGet(()-> {
                        Logger.getLogger(getClass().getName()).log(Level.INFO,"No se ha encontrado objeto");
                        return null;});
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<TipoPago> event) {
        TipoPago tipoPagoSelected = (TipoPago) event.getObject();
        FacesMessage mensaje=new FacesMessage("Tipo de Pago seleccionado",tipoPagoSelected.getNombre());
        fc.addMessage(null, mensaje);
        this.estado=ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public String paginaNombre() {
        return "Tipo de Pago";
    }
}
