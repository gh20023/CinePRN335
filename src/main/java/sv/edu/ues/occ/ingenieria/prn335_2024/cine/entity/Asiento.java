package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@Entity
@Table(name = "asiento", schema = "public")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Asiento.findAll", query = "SELECT a FROM Asiento a"),
        @NamedQuery(name = "Sala.countByIdSala", query = "SELECT COUNT (pc.idAsiento) FROM Asiento pc WHERE pc.idSala.idSala = :idSala"),
        @NamedQuery(name="Asiento.findByIdSala", query  ="SELECT sc FROM Asiento sc WHERE sc.idSala.idSala = :idSala ORDER BY sc.idAsiento asc"),
        @NamedQuery(name = "Asiento.findByIdAsiento", query = "SELECT a FROM Asiento a WHERE a.idAsiento = :idAsiento"),
        @NamedQuery(name = "Asiento.findByNombre", query = "SELECT a FROM Asiento a WHERE a.nombre = :nombre"),
        @NamedQuery(name = "Asiento.findByActivo", query = "SELECT a FROM Asiento a WHERE a.activo = :activo")})
public class Asiento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asiento", nullable = false)
    private Long idAsiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala")
    private Sala idSala;

    @Size(max = 155)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    public Asiento(){}

    public Asiento(Long idAsiento){
        this.idAsiento = idAsiento;
    }

    public Long getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Long id) {
        this.idAsiento = id;
    }

    public Sala getIdSala() {
        return idSala;
    }

    public void setIdSala(Sala idSala) {
        this.idSala = idSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}