package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmSucursal extends AbstractFrm<Sucursal> implements Serializable {

    @Inject
    SucursalBean sucursalBean; // Bean para la lógica de Sucursal
    @Inject
    FacesContext fc; // Contexto de Faces

    @Override
    public void instanciarRegistro() {
        this.registro = new Sucursal();
        this.registro.setActivo(true); // Inicializa la sucursal como activa
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersistence<Sucursal> getAbstractDataPersistence() {
        return sucursalBean; // Retorna el bean de Sucursal
    }

    @Override
    public String getIdByObject(Sucursal object) {
        if (object != null) {
            return object.getIdSucursal().toString(); // Retorna el ID de la sucursal como String
        }
        return null;
    }

    @Override
    public Sucursal getObjectById(String id) {
        if (id != null && modelo != null && modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream().
                    filter(s -> id.equals(s.getIdSucursal().toString())).findFirst().
                    orElseGet(() -> {
                        Logger.getLogger(getClass().getName()).log(Level.INFO, "No se ha encontrado la sucursal");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Sucursal> event) {
        Sucursal sucursalSeleccionada = (Sucursal) event.getObject();
        FacesMessage mensaje = new FacesMessage("Sucursal seleccionada", sucursalSeleccionada.getNombre());
        fc.addMessage(null, mensaje);
        this.estado = ESTADO_CRUD.MODIFICAR; // Cambia el estado a MODIFICAR
    }

    @Override
    public String paginaNombre() {
        return "Sucursal"; // Retorna el nombre de la página
    }
}
