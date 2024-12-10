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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmSala extends AbstractFrm<Sala> implements Serializable {

    @Inject
    SalaBean sBean;
    @Inject
    FrmSalaCaracteristica frmSalaCaracteristica;
    @Inject
    SucursalBean sucBean;
    @Inject
    FrmAsiento frmAsiento;
    @Inject
    FacesContext fc;

    List<Sucursal> sucursalList;
    Integer idPeliculaSeleccionado;

    @PostConstruct
    @Override
    public void init(){
        super.init();
        try {
            this.sucursalList = sucBean.findRange(0, Integer.MAX_VALUE);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            FacesMessage mensaje = new FacesMessage("No se cargaron los datos");
            fc.addMessage(null, mensaje);
        }
    }

    @Override
    public void instanciarRegistro(){
        this.registro = new Sala();
        registro.setActivo(true);
    }

    @Override
    public FacesContext getFC(){
        return fc;
    }

    @Override
    public AbstractDataPersistence<Sala> getAbstractDataPersistence() {
        return sBean;
    }

    @Override
    public String getIdByObject(Sala obj){
        if(obj.getIdSala() != null){
            return obj.getIdSala().toString();
        }
        return null;
    }

    @Override
    public Sala getObjectById(String id){
        if(id != null && modelo != null & modelo.getWrappedData() != null){
            return modelo.getWrappedData().stream()
                    .filter(r ->id.equals(r.getIdSala().toString())).findFirst().
                    orElseGet(()->{
                        Logger.getLogger("No se ha encontrado el objecto");
                        return null;
                    });
        }
        return null;
    }


    @Override
    public void selecionarFila(SelectEvent<Sala> event) {
        Sala filaSelelcted = event.getObject();
        FacesMessage mensaje = new FacesMessage("Tipo de pago selecionado con exito");
        fc.addMessage(null, mensaje);
        this.registro = filaSelelcted;
        this.estado = ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public String paginaNombre(){
        return "Sala";
    }

    public void cambiarTap(TabChangeEvent tce){
        if(tce.getTab().getTitle().equals("Caracteristicas")){
            if(this.registro != null && this.frmSalaCaracteristica != null){
                this.frmSalaCaracteristica.setIdSala(this.registro.getIdSala());
            }
        }
        if(tce.getTab().getTitle().equals("Asientos")){
            if(this.registro != null && this.frmAsiento != null){
                this.frmAsiento.setIdSala(this.registro.getIdSala());
            }
        }
    }


    public Integer getIdSucursalSeleccionado() {
        if(this.registro != null && this.registro.getIdSucursal() != null){
            return this.registro.getIdSucursal().getIdSucursal();
        }
        return null;
    }

    public void setIdSucursalSeleccionado(final Integer idSucursal) {
        if(this.registro != null && this.sucursalList != null && !this.sucursalList.isEmpty()){
            this.registro.setIdSucursal(
                    this.sucursalList.stream()
                            .filter(r -> r.getIdSucursal().equals(idSucursal))
                            .findFirst()
                            .orElse(null)
            );
            this.idPeliculaSeleccionado = idSucursal;
        }
    }

    public List<Sucursal> getSucursalList() {
        return sucursalList;
    }

    public sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.FrmSalaCaracteristica getFrmSalaCaracteristica() {
        return frmSalaCaracteristica;
    }

    public sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.FrmAsiento getFrmAsiento() {
        return frmAsiento;
    }
}
