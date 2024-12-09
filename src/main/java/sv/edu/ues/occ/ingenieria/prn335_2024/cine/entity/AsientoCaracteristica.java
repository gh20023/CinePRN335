package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@Entity
@Table(name = "asiento_caracteristica", schema = "public")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "AsientoCaracteristica.findAll", query = "SELECT a FROM AsientoCaracteristica a"),
        @NamedQuery(name = "AsientoCaracteristica.findByIdAsiento", query = "SELECT pc FROM AsientoCaracteristica pc WHERE pc.idAsiento.idAsiento = :idAsiento order by pc.idAsientoCaracteristica asc"),
        @NamedQuery(name = "AsientoCaracteristica.countByIdAsiento", query = "SELECT COUNT (pc.idAsientoCaracteristica) FROM AsientoCaracteristica pc WHERE pc.idAsiento.idAsiento = :idAsiento"),
        @NamedQuery(name = "AsientoCaracteristica.findByIdAsientoCaracteristica", query = "SELECT a FROM AsientoCaracteristica a WHERE a.idAsientoCaracteristica = :idAsientoCaracteristica"),
        @NamedQuery(name = "AsientoCaracteristica.findByValor", query = "SELECT a FROM AsientoCaracteristica a WHERE a.valor = :valor")})
public class AsientoCaracteristica implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asiento_caracteristica", nullable = false)
    private Long idAsientoCaracteristica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asiento")
    private Asiento idAsiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_asiento")
    private TipoAsiento idTipoAsiento;

    @Lob
    @Column(name = "valor")
    private String valor;

    public AsientoCaracteristica() {}

    public AsientoCaracteristica(Long idAsientoCaracteristica){
        this.idAsientoCaracteristica = idAsientoCaracteristica;
    }

    public Long getIdAsientoCaracteristica() {
        return idAsientoCaracteristica;
    }

    public void setIdAsientoCaracteristica(Long id) {
        this.idAsientoCaracteristica = id;
    }

    public Asiento getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Asiento idAsiento) {
        this.idAsiento = idAsiento;
    }

    public TipoAsiento getIdTipoAsiento() {
        return idTipoAsiento;
    }

    public void setIdTipoAsiento(TipoAsiento idTipoAsiento) {
        this.idTipoAsiento = idTipoAsiento;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}