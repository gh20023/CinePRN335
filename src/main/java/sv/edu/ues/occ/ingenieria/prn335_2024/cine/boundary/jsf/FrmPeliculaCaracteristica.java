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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@ViewScoped
public class FrmPeliculaCaracteristica extends AbstractFrm<PeliculaCaracteristica> implements Serializable {

    @Inject
    PeliculaCaracteristicaBean pcBean;
    @Inject
    TipoPeliculaBean tpBean;
    @Inject
    FacesContext fc;

    Long idPelicula;
    List<TipoPelicula> tipoPeliculaList;
    Integer idTipoPeliculaSeleccionado;

    @PostConstruct
    public void init() {
        super.init();
        try {
            this.tipoPeliculaList = tpBean.findRange(0, Integer.MAX_VALUE);
            System.out.println("SI INICIALIZO");
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            FacesMessage mensaje = new FacesMessage("No se cargaron los datos");
            fc.addMessage(null, mensaje);
        }
    }

    @Override
    public void instanciarRegistro() {
        if(idPelicula!=null){
            this.registro = new PeliculaCaracteristica();
            registro.setIdPelicula(new Pelicula(idPelicula));
            System.out.println("SI INSTANCIO");
        }
        if(tipoPeliculaList!=null && !tipoPeliculaList.isEmpty()){
            registro.setIdTipoPelicula(tipoPeliculaList.getFirst());
        }
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersistence<PeliculaCaracteristica> getAbstractDataPersistence() {
        return pcBean;
    }

    @Override
    public String getIdByObject(PeliculaCaracteristica object) {
        if (object.getIdPeliculaCaracteristica() != null) {
            return object.getIdPeliculaCaracteristica().toString();
        }
        return null;
    }

    @Override
    public PeliculaCaracteristica getObjectById(String id) {
        if (id != null & modelo != null & modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream().
                    filter(r -> id.equals(r.getIdPeliculaCaracteristica().toString())).findFirst().
                    orElseGet(() -> {
                        Logger.getLogger("no se ha encontrado el objeto");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<PeliculaCaracteristica> event) {
        PeliculaCaracteristica peliculaCaracteristicaSelected = (PeliculaCaracteristica) event.getObject();
        FacesMessage mensaje=new FacesMessage("Caracteristica seleccionada",peliculaCaracteristicaSelected.getValor());
        fc.addMessage(null, mensaje);
        this.estado=ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public List<PeliculaCaracteristica> cargar(int firstResult, int maxResults) {
        try {
            if(this.idPelicula!=null && pcBean!=null){
                System.out.println("LA CONSULTA SE REALIZO");
                return pcBean.findByIdPelicula(this.idPelicula, firstResult, maxResults);
            }
        }
        catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    @Override
    public int contar(){
        try{
            if(idPelicula!=null && pcBean!=null){
                return pcBean.countPelicula(this.idPelicula);
            }
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }
    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public List<TipoPelicula> getTipoPeliculaList() {
        System.out.println("VALOR LISTA" + tipoPeliculaList);
        return tipoPeliculaList;
    }

    public void setTipoPeliculaList(List<TipoPelicula> tipoPeliculaList) {
        this.tipoPeliculaList = tipoPeliculaList;
    }

    public Integer getIdTipoPeliculaSeleccionado() {
        if(this.registro != null && this.registro.getIdTipoPelicula() != null){
            System.out.println("PRUEBA PARA LISTA"+ this.registro.getIdTipoPelicula().getIdTipoPelicula());
            System.out.println("VALOR LISTA" + tipoPeliculaList);
            return this.registro.getIdTipoPelicula().getIdTipoPelicula();
        }
        return  null;
    }

    public void setIdTipoPeliculaSeleccionado(final Integer idTipoPelicula) {
        if (this.registro != null && this.tipoPeliculaList != null && !this.tipoPeliculaList.isEmpty()) {
            this.registro.setIdTipoPelicula(
                    this.tipoPeliculaList.stream()
                            .filter(r -> r.getIdTipoPelicula().equals(idTipoPelicula))
                            .findFirst()
                            .orElse(null)
            );
            this.idTipoPeliculaSeleccionado = idTipoPelicula;
        }
    }

    public void validarValor(FacesContext fc, UIComponent component, Object valor){
        UIInput input = (UIInput) component;
        if(registro != null && this.registro.getIdTipoPelicula() != null) {
            String nuevo = valor.toString();
            Pattern patron = Pattern.compile(this.registro.getIdTipoPelicula().getExpresionRegular());
            Matcher validador = patron.matcher(nuevo);
            if(validador.find()){
                input.setValid(true);}
            return;
        }
        input.setValid(false);
    }

    @Override
    public String paginaNombre() {
        return "Película Característica";
    }
}
