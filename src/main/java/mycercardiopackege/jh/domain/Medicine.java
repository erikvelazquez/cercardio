package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Medicine.
 */
@Entity
@Table(name = "medicine")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "medicine")
public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tipoinsumo")
    private String tipoinsumo;

    @Column(name = "dentrofueracuadro")
    private String dentrofueracuadro;

    @Column(name = "nogrupoterapeutico")
    private Integer nogrupoterapeutico;

    @Column(name = "nombregrupoterapeutico")
    private String nombregrupoterapeutico;

    @Column(name = "nivelatencion")
    private String nivelatencion;

    @Column(name = "clavecodigo")
    private String clavecodigo;

    @Column(name = "subclavecodigo")
    private String subclavecodigo;

    @Column(name = "nombregenerico")
    private String nombregenerico;

    @Column(name = "formafarmaceutica")
    private String formafarmaceutica;

    @Column(name = "concentracion")
    private String concentracion;

    @Column(name = "presentacion")
    private String presentacion;

    @Column(name = "principalindicacion")
    private String principalindicacion;

    @Column(name = "demasindicaciones")
    private String demasindicaciones;

    @Column(name = "tipoactualizacion")
    private String tipoactualizacion;

    @Column(name = "noactualizacion")
    private String noactualizacion;

    @Column(name = "descripcion")
    private String descripcion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoinsumo() {
        return tipoinsumo;
    }

    public Medicine tipoinsumo(String tipoinsumo) {
        this.tipoinsumo = tipoinsumo;
        return this;
    }

    public void setTipoinsumo(String tipoinsumo) {
        this.tipoinsumo = tipoinsumo;
    }

    public String getDentrofueracuadro() {
        return dentrofueracuadro;
    }

    public Medicine dentrofueracuadro(String dentrofueracuadro) {
        this.dentrofueracuadro = dentrofueracuadro;
        return this;
    }

    public void setDentrofueracuadro(String dentrofueracuadro) {
        this.dentrofueracuadro = dentrofueracuadro;
    }

    public Integer getNogrupoterapeutico() {
        return nogrupoterapeutico;
    }

    public Medicine nogrupoterapeutico(Integer nogrupoterapeutico) {
        this.nogrupoterapeutico = nogrupoterapeutico;
        return this;
    }

    public void setNogrupoterapeutico(Integer nogrupoterapeutico) {
        this.nogrupoterapeutico = nogrupoterapeutico;
    }

    public String getNombregrupoterapeutico() {
        return nombregrupoterapeutico;
    }

    public Medicine nombregrupoterapeutico(String nombregrupoterapeutico) {
        this.nombregrupoterapeutico = nombregrupoterapeutico;
        return this;
    }

    public void setNombregrupoterapeutico(String nombregrupoterapeutico) {
        this.nombregrupoterapeutico = nombregrupoterapeutico;
    }

    public String getNivelatencion() {
        return nivelatencion;
    }

    public Medicine nivelatencion(String nivelatencion) {
        this.nivelatencion = nivelatencion;
        return this;
    }

    public void setNivelatencion(String nivelatencion) {
        this.nivelatencion = nivelatencion;
    }

    public String getClavecodigo() {
        return clavecodigo;
    }

    public Medicine clavecodigo(String clavecodigo) {
        this.clavecodigo = clavecodigo;
        return this;
    }

    public void setClavecodigo(String clavecodigo) {
        this.clavecodigo = clavecodigo;
    }

    public String getSubclavecodigo() {
        return subclavecodigo;
    }

    public Medicine subclavecodigo(String subclavecodigo) {
        this.subclavecodigo = subclavecodigo;
        return this;
    }

    public void setSubclavecodigo(String subclavecodigo) {
        this.subclavecodigo = subclavecodigo;
    }

    public String getNombregenerico() {
        return nombregenerico;
    }

    public Medicine nombregenerico(String nombregenerico) {
        this.nombregenerico = nombregenerico;
        return this;
    }

    public void setNombregenerico(String nombregenerico) {
        this.nombregenerico = nombregenerico;
    }

    public String getFormafarmaceutica() {
        return formafarmaceutica;
    }

    public Medicine formafarmaceutica(String formafarmaceutica) {
        this.formafarmaceutica = formafarmaceutica;
        return this;
    }

    public void setFormafarmaceutica(String formafarmaceutica) {
        this.formafarmaceutica = formafarmaceutica;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public Medicine concentracion(String concentracion) {
        this.concentracion = concentracion;
        return this;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public Medicine presentacion(String presentacion) {
        this.presentacion = presentacion;
        return this;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getPrincipalindicacion() {
        return principalindicacion;
    }

    public Medicine principalindicacion(String principalindicacion) {
        this.principalindicacion = principalindicacion;
        return this;
    }

    public void setPrincipalindicacion(String principalindicacion) {
        this.principalindicacion = principalindicacion;
    }

    public String getDemasindicaciones() {
        return demasindicaciones;
    }

    public Medicine demasindicaciones(String demasindicaciones) {
        this.demasindicaciones = demasindicaciones;
        return this;
    }

    public void setDemasindicaciones(String demasindicaciones) {
        this.demasindicaciones = demasindicaciones;
    }

    public String getTipoactualizacion() {
        return tipoactualizacion;
    }

    public Medicine tipoactualizacion(String tipoactualizacion) {
        this.tipoactualizacion = tipoactualizacion;
        return this;
    }

    public void setTipoactualizacion(String tipoactualizacion) {
        this.tipoactualizacion = tipoactualizacion;
    }

    public String getNoactualizacion() {
        return noactualizacion;
    }

    public Medicine noactualizacion(String noactualizacion) {
        this.noactualizacion = noactualizacion;
        return this;
    }

    public void setNoactualizacion(String noactualizacion) {
        this.noactualizacion = noactualizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Medicine descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medicine medicine = (Medicine) o;
        if (medicine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medicine{" +
            "id=" + getId() +
            ", tipoinsumo='" + getTipoinsumo() + "'" +
            ", dentrofueracuadro='" + getDentrofueracuadro() + "'" +
            ", nogrupoterapeutico='" + getNogrupoterapeutico() + "'" +
            ", nombregrupoterapeutico='" + getNombregrupoterapeutico() + "'" +
            ", nivelatencion='" + getNivelatencion() + "'" +
            ", clavecodigo='" + getClavecodigo() + "'" +
            ", subclavecodigo='" + getSubclavecodigo() + "'" +
            ", nombregenerico='" + getNombregenerico() + "'" +
            ", formafarmaceutica='" + getFormafarmaceutica() + "'" +
            ", concentracion='" + getConcentracion() + "'" +
            ", presentacion='" + getPresentacion() + "'" +
            ", principalindicacion='" + getPrincipalindicacion() + "'" +
            ", demasindicaciones='" + getDemasindicaciones() + "'" +
            ", tipoactualizacion='" + getTipoactualizacion() + "'" +
            ", noactualizacion='" + getNoactualizacion() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
