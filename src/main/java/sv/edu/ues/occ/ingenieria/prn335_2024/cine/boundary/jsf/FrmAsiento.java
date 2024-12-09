package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmAsiento extends AbstractFrm<Asiento> implements Serializable {
    @Inject
    AsientoBean asientoBean;
    @Inject
    FrmAsientoCaracteristica frmAsientoCaracteristica;
    @Inject
    FacesContext fc;

    Integer idSala;
    List<Asiento> asientoList;
    Long idAsientoSeleccionado;

    @Override
    @PostConstruct
    public void init() {
        super.init();
    }

    @Override
    public void instanciarRegistro() {
        if(idSala != null){
            this.registro = new Asiento();
            registro.setIdSala(new Sala(idSala));
        }
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersistence<Asiento> getAbstractDataPersistence() {
        return asientoBean;
    }

    @Override
    public String getIdByObject(Asiento object) {
        if (object.getIdAsiento() != null) {
            return object.getIdAsiento().toString();
        }
        return null;
    }

    @Override
    public Asiento getObjectById(String id){
        if(id != null & modelo != null & modelo.getWrappedData() != null){
            return modelo.getWrappedData().stream()
                    .filter(r ->id.equals(r.getIdAsiento().toString())).findFirst().
                    orElseGet(()->{
                        Logger.getLogger("No se ha encontrado el objecto");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Asiento> event) {
        Asiento filaSelelcted = event.getObject();
        FacesMessage mensaje = new FacesMessage("Tipo de pago selecionado co exito");
        fc.addMessage(null, mensaje);
        this.registro = filaSelelcted;
        this.estado = ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public List<Asiento> cargar(int firtsResult, int maxResults) {

        try {
            if (this.idSala != null && asientoBean != null) {
                System.out.println("CARGAR  SE REALIZO");

                return asientoBean.findByIdSala(this.idSala, firtsResult, maxResults);
            }
        } catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    @Override
    public int contar(){
        try{
            if(idSala != null && asientoBean != null){
                System.out.println("CONTAR  SE REALIZO");
                System.out.println("VALOR " + asientoBean.countAsiento(this.idSala));
                return asientoBean.countAsiento(this.idSala);
            }
            System.out.println("CONTAR NO SE REALIZO");
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public String paginaNombre() {
        return "Asiento";
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public List<Asiento> getAsientoList() {
        return asientoList;
    }

    public void setAsientoList(List<Asiento> asientoList) {
        this.asientoList = asientoList;
    }

    public Long getIdAsientoSeleccionado() {
        return idAsientoSeleccionado;
    }

    public Long getIdAsientoSelecionado(){
        if(this.registro != null && this.registro.getIdAsiento() != null){
            return this.registro.getIdAsiento();
        }
        return null;
    }

    public sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.FrmAsientoCaracteristica getFrmAsientoCaracteristica() {
        return frmAsientoCaracteristica;
    }
}
