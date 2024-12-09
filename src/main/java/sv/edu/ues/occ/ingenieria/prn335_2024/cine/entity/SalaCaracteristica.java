package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@Entity
@Table(name = "sala_caracteristica", schema = "public")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "SalaCaracteristica.findAll", query = "SELECT s FROM SalaCaracteristica s"),
        @NamedQuery(name = "SalaCaracteristica.findByIdSala", query = "SELECT sc FROM SalaCaracteristica sc WHERE sc.idSala.idSala = :idSalaCaracteristica ORDER BY sc.idSalaCaracteristica asc"),
        @NamedQuery(name = "SalaCaracteristica.countByIdPelicula", query ="SELECT COUNT (sc.idSalaCaracteristica) FROM SalaCaracteristica sc WHERE sc.idSala.idSala = :idSala"),
        @NamedQuery(name = "SalaCaracteristica.findByValor", query = "SELECT s FROM SalaCaracteristica s WHERE s.valor = :valor")})
public class SalaCaracteristica implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala_caracteristica", nullable = false)
    private Long idSalaCaracteristica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_sala")
    private TipoSala idTipoSala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala")
    private Sala idSala;

    @Lob
    @Column(name = "valor")
    private String valor;

    public SalaCaracteristica() {}

    public SalaCaracteristica(Long idSalaCaracteristica){this.idSalaCaracteristica = idSalaCaracteristica;}

    public Long getIdSalaCaracteristica() {
        return idSalaCaracteristica;
    }

    public void setIdSalaCaracteristica(Long id) {
        this.idSalaCaracteristica = id;
    }

    public TipoSala getIdTipoSala() {
        return idTipoSala;
    }

    public void setIdTipoSala(TipoSala idTipoSala) {
        this.idTipoSala = idTipoSala;
    }

    public Sala getIdSala() {
        return idSala;
    }

    public void setIdSala(Sala idSala) {
        this.idSala = idSala;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}