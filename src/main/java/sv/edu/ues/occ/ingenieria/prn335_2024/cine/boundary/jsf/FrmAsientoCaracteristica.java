package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.AsientoCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@ViewScoped
public class FrmAsientoCaracteristica extends AbstractFrm<AsientoCaracteristica> implements Serializable {

    @Inject
    AsientoCaracteristicaBean asiCBean;
    @Inject
    TipoAsientoBean tipoABean;
    @Inject
    FacesContext fc;

    Long idAsiento;
    List<TipoAsiento> tipoAsientoList;
    Integer idTipoAsientoSeleccionado;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        try{
            this.tipoAsientoList = tipoABean.findRange(0, Integer.MAX_VALUE);
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            FacesMessage mensaje = new FacesMessage("No se cargaron los datos");
            fc.addMessage(null, mensaje);
        }
    }

    @Override
    public void instanciarRegistro(){
        if(idAsiento != null){
            this.registro = new AsientoCaracteristica();
            registro.setIdAsiento(new Asiento(idAsiento));
        }
        if(tipoAsientoList != null && !tipoAsientoList.isEmpty()){
            registro.setIdTipoAsiento(tipoAsientoList.getFirst());
        }
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersistence<AsientoCaracteristica> getAbstractDataPersistence(){
        return asiCBean;
    }

    @Override
    public String getIdByObject(AsientoCaracteristica object){
        if(object.getIdAsientoCaracteristica() != null){
            return object.getIdAsientoCaracteristica().toString();
        }
        return null;
    }

    @Override
    public AsientoCaracteristica getObjectById(String id){
        if(id != null && modelo != null && modelo.getWrappedData() != null){
            return modelo.getWrappedData().stream().
                    filter(r -> id.equals(r.getIdAsientoCaracteristica().toString())).findFirst().
                    orElseGet(() -> {
                        Logger.getLogger("no se ha encontrado el objeto");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<AsientoCaracteristica> event){
        AsientoCaracteristica filaSelect = event.getObject();
        FacesMessage mensaje = new FacesMessage("Tipo Asiento Seleccionado");
        fc.addMessage(null, mensaje);
        this.registro = filaSelect;
        this.estado = ESTADO_CRUD.MODIFICAR;
    }

    //Para realizar las consultas
    @Override
    public List<AsientoCaracteristica> cargar(int firstResult, int maxResults) {
        try{
            if(this.idAsiento != null && asiCBean !=null){
                return asiCBean.findByIdAsiento(this.idAsiento, firstResult, maxResults);
            }
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    @Override
    public int contar(){
        try{
            if(idAsiento != null && asiCBean !=null){
                return asiCBean.countAsiento(this.idAsiento);
            }
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public String paginaNombre(){
        return "Asiento Caracteristica";
    }

    public Long getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Long idAsiento) {
        this.idAsiento = idAsiento;
    }

    public List<TipoAsiento> getTipoAsientoList() {
        return tipoAsientoList;
    }

    public void setTipoAsientoList(List<TipoAsiento> tipoAsientoList) {
        this.tipoAsientoList = tipoAsientoList;
    }
    public Integer getIdTipoAsientoSeleccionado() {
        if(this.registro != null && this.registro.getIdTipoAsiento() != null){
            return this.registro.getIdTipoAsiento().getIdTipoAsiento();
        }
        return null;
    }

    public void setIdTipoAsientoSeleccionado(final Integer idTipoAsiento) {
        if (this.registro != null && this.tipoAsientoList != null && !this.tipoAsientoList.isEmpty()) {
            this.registro.setIdTipoAsiento(
                    this.tipoAsientoList.stream()
                            .filter(r -> r.getIdTipoAsiento().equals(idTipoAsiento))
                            .findFirst()
                            .orElse(null)
            );
            this.idTipoAsientoSeleccionado = idTipoAsiento;
        }
    }

    public void validarValor(FacesContext fc, UIComponent component, Object valor){
        UIInput input = (UIInput) component;
        if(registro != null && this.registro.getIdTipoAsiento() != null) {
            String nuevo = valor.toString();
            Pattern patron = Pattern.compile(this.registro.getIdTipoAsiento().getExpresionRegular());
            Matcher validador = patron.matcher(nuevo);
            if(validador.find()){
                input.setValid(true);}
            return;
        }
        input.setValid(false);
    }
}
