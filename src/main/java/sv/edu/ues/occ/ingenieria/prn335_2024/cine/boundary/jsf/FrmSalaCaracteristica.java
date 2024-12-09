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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@ViewScoped
public class FrmSalaCaracteristica extends AbstractFrm<SalaCaracteristica> implements Serializable {

    @Inject
    SalaCaracteristicaBean scBean;
    @Inject
    TipoSalaBean tsBean;
    @Inject
    FacesContext fc;

    Integer idSala;
    List<TipoSala> tipoSalaList;
    Integer idTipoSalaSeleccionado;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        try {
            this.tipoSalaList = tsBean.findRange(0, Integer.MAX_VALUE);
            System.out.println("SI INICIALIZO");
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            FacesMessage mensaje = new FacesMessage("No se cargaron los datos");
            fc.addMessage(null, mensaje);
        }
    }


    @Override
    public void instanciarRegistro() {
        if(idSala != null){
            this.registro = new SalaCaracteristica();
            registro.setIdSala(new Sala(idSala));
        }
        if(tipoSalaList != null && !tipoSalaList.isEmpty()){
            registro.setIdTipoSala(tipoSalaList.getFirst());
        }
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersistence<SalaCaracteristica> getAbstractDataPersistence() {
        return scBean;
    }

    @Override
    public String getIdByObject(SalaCaracteristica object) {
        if (object.getIdSalaCaracteristica() != null) {
            return object.getIdSalaCaracteristica().toString();
        }
        return null;
    }

    @Override
    public SalaCaracteristica getObjectById(String id) {
        if (id != null & modelo != null & modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream().
                    filter(r -> id.equals(r.getIdSalaCaracteristica().toString())).findFirst().
                    orElseGet(() -> {
                        Logger.getLogger("no se ha encontrado el objeto");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<SalaCaracteristica> event) {
        SalaCaracteristica filaSelelcted = event.getObject();
        FacesMessage mensaje = new FacesMessage("Tipo de pago selecionado co exito");
        fc.addMessage(null, mensaje);
        this.registro = filaSelelcted;
        this.estado = ESTADO_CRUD.MODIFICAR;

    }

    @Override
    public List<SalaCaracteristica> cargar(int firsResult, int maxResult) {
        try {
            System.out.println("ID SALA: " + this.idSala);
            if (this.idSala != null && scBean != null) {
                System.out.println("LA CONSULTA SE REALIZO");

                return scBean.findByIdSala(this.idSala, firsResult, maxResult);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    @Override
    public int contar(){
        try{
            if(idSala != null && scBean != null){
                return scBean.countSala(this.idSala);
            }
            System.out.println("CONTAR NO SE REALIZO");
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public String paginaNombre() {
        return "Sala Caracteristica";
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public List<TipoSala> getTipoSalaList() {
        return tipoSalaList;
    }

    public void setTipoSalaList(List<TipoSala> tipoSalaList) {
        this.tipoSalaList = tipoSalaList;
    }

    public Integer getIdTipoSalaSeleccionado() {
        if(this.registro != null && this.registro.getIdTipoSala() != null){
            return this.registro.getIdTipoSala().getIdTipoSala();
        }
        return null;
    }

    public void setIdTipoSalaSeleccionado(final Integer idTipoSala) {
        if (this.registro != null && this.tipoSalaList != null && !this.tipoSalaList.isEmpty()) {
            this.registro.setIdTipoSala(
                    this.tipoSalaList.stream()
                            .filter(r -> r.getIdTipoSala().equals(idTipoSala))
                            .findFirst()
                            .orElse(null)
            );
            this.idTipoSalaSeleccionado = idTipoSala;
        }
    }

    public void validarValor(FacesContext fc, UIComponent component, Object valor){
        UIInput input = (UIInput) component;
        if(registro != null && this.registro.getIdTipoSala() != null) {
            String nuevo = valor.toString();
            Pattern patron = Pattern.compile(this.registro.getIdTipoSala().getExpresionRegular());
            Matcher validador = patron.matcher(nuevo);
            if(validador.find()){
                input.setValid(true);}
            return;
        }
        input.setValid(false);
    }
}
