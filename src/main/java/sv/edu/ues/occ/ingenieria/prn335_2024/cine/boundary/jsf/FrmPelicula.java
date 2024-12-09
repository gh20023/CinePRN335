package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;

import java.io.Serializable;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmPelicula extends AbstractFrm<Pelicula> implements Serializable {

    @Inject
    PeliculaBean peBean;
    @Inject
    FrmPeliculaCaracteristica frmPeliculaCaracteristica;
    @Inject
    FacesContext fc;

    @PostConstruct
    public void init() {
        super.init();
    }

    @Override
    public void instanciarRegistro() {
        this.registro = new Pelicula();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersistence<Pelicula> getAbstractDataPersistence() {
        return peBean;
    }

    @Override
    public String getIdByObject(Pelicula object) {
        if (object.getIdPelicula() != null) {
            return object.getIdPelicula().toString();
        }
        return null;
    }

    @Override
    public Pelicula getObjectById(String id) {
        if (id != null & modelo != null & modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream().
                    filter(r -> id.equals(r.getIdPelicula().toString())).findFirst().
                    orElseGet(() -> {
                        Logger.getLogger("no se ha encontrado el objeto");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Pelicula> event) {
        Pelicula peliculaSelected = (Pelicula) event.getObject();
        FacesMessage mensaje=new FacesMessage("Película seleccionada",peliculaSelected.getNombre());
        fc.addMessage(null, mensaje);
        this.estado=ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public String paginaNombre() {
        return "Película";
    }

    public void cambiarTab(TabChangeEvent tce){
        if(tce.getTab().getTitle().equals("Caracteristicas")){
            if(this.registro != null && this.frmPeliculaCaracteristica != null){
                this.frmPeliculaCaracteristica.setIdPelicula(this.registro.getIdPelicula());
            }
        }
    }

    public sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.FrmPeliculaCaracteristica getFrmPeliculaCaracteristica() {
        return frmPeliculaCaracteristica;
    }
}
