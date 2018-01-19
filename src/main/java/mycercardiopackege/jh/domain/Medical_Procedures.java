package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Medical_Procedures.
 */
@Entity
@Table(name = "medical_procedures")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "medical_procedures")
public class Medical_Procedures implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "catalogkey")
    private String catalogkey;

    @Column(name = "pronombre")
    private String pronombre;

    @Column(name = "procveedia")
    private String procveedia;

    @Column(name = "proedadia")
    private String proedadia;

    @Column(name = "procveedfa")
    private String procveedfa;

    @Column(name = "proedadfa")
    private String proedadfa;

    @Column(name = "sextype")
    private Integer sextype;

    @Column(name = "pornivela")
    private Integer pornivela;

    @Column(name = "procedimientotype")
    private String procedimientotype;

    @Column(name = "proprincipal")
    private String proprincipal;

    @Column(name = "procapitulo")
    private Integer procapitulo;

    @Column(name = "proseccion")
    private Integer proseccion;

    @Column(name = "procategoria")
    private Integer procategoria;

    @Column(name = "prosubcateg")
    private Integer prosubcateg;

    @Column(name = "progrupolc")
    private Integer progrupolc;

    @Column(name = "proescauses")
    private Integer proescauses;

    @Column(name = "pronumcauses")
    private Integer pronumcauses;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatalogkey() {
        return catalogkey;
    }

    public Medical_Procedures catalogkey(String catalogkey) {
        this.catalogkey = catalogkey;
        return this;
    }

    public void setCatalogkey(String catalogkey) {
        this.catalogkey = catalogkey;
    }

    public String getPronombre() {
        return pronombre;
    }

    public Medical_Procedures pronombre(String pronombre) {
        this.pronombre = pronombre;
        return this;
    }

    public void setPronombre(String pronombre) {
        this.pronombre = pronombre;
    }

    public String getProcveedia() {
        return procveedia;
    }

    public Medical_Procedures procveedia(String procveedia) {
        this.procveedia = procveedia;
        return this;
    }

    public void setProcveedia(String procveedia) {
        this.procveedia = procveedia;
    }

    public String getProedadia() {
        return proedadia;
    }

    public Medical_Procedures proedadia(String proedadia) {
        this.proedadia = proedadia;
        return this;
    }

    public void setProedadia(String proedadia) {
        this.proedadia = proedadia;
    }

    public String getProcveedfa() {
        return procveedfa;
    }

    public Medical_Procedures procveedfa(String procveedfa) {
        this.procveedfa = procveedfa;
        return this;
    }

    public void setProcveedfa(String procveedfa) {
        this.procveedfa = procveedfa;
    }

    public String getProedadfa() {
        return proedadfa;
    }

    public Medical_Procedures proedadfa(String proedadfa) {
        this.proedadfa = proedadfa;
        return this;
    }

    public void setProedadfa(String proedadfa) {
        this.proedadfa = proedadfa;
    }

    public Integer getSextype() {
        return sextype;
    }

    public Medical_Procedures sextype(Integer sextype) {
        this.sextype = sextype;
        return this;
    }

    public void setSextype(Integer sextype) {
        this.sextype = sextype;
    }

    public Integer getPornivela() {
        return pornivela;
    }

    public Medical_Procedures pornivela(Integer pornivela) {
        this.pornivela = pornivela;
        return this;
    }

    public void setPornivela(Integer pornivela) {
        this.pornivela = pornivela;
    }

    public String getProcedimientotype() {
        return procedimientotype;
    }

    public Medical_Procedures procedimientotype(String procedimientotype) {
        this.procedimientotype = procedimientotype;
        return this;
    }

    public void setProcedimientotype(String procedimientotype) {
        this.procedimientotype = procedimientotype;
    }

    public String getProprincipal() {
        return proprincipal;
    }

    public Medical_Procedures proprincipal(String proprincipal) {
        this.proprincipal = proprincipal;
        return this;
    }

    public void setProprincipal(String proprincipal) {
        this.proprincipal = proprincipal;
    }

    public Integer getProcapitulo() {
        return procapitulo;
    }

    public Medical_Procedures procapitulo(Integer procapitulo) {
        this.procapitulo = procapitulo;
        return this;
    }

    public void setProcapitulo(Integer procapitulo) {
        this.procapitulo = procapitulo;
    }

    public Integer getProseccion() {
        return proseccion;
    }

    public Medical_Procedures proseccion(Integer proseccion) {
        this.proseccion = proseccion;
        return this;
    }

    public void setProseccion(Integer proseccion) {
        this.proseccion = proseccion;
    }

    public Integer getProcategoria() {
        return procategoria;
    }

    public Medical_Procedures procategoria(Integer procategoria) {
        this.procategoria = procategoria;
        return this;
    }

    public void setProcategoria(Integer procategoria) {
        this.procategoria = procategoria;
    }

    public Integer getProsubcateg() {
        return prosubcateg;
    }

    public Medical_Procedures prosubcateg(Integer prosubcateg) {
        this.prosubcateg = prosubcateg;
        return this;
    }

    public void setProsubcateg(Integer prosubcateg) {
        this.prosubcateg = prosubcateg;
    }

    public Integer getProgrupolc() {
        return progrupolc;
    }

    public Medical_Procedures progrupolc(Integer progrupolc) {
        this.progrupolc = progrupolc;
        return this;
    }

    public void setProgrupolc(Integer progrupolc) {
        this.progrupolc = progrupolc;
    }

    public Integer getProescauses() {
        return proescauses;
    }

    public Medical_Procedures proescauses(Integer proescauses) {
        this.proescauses = proescauses;
        return this;
    }

    public void setProescauses(Integer proescauses) {
        this.proescauses = proescauses;
    }

    public Integer getPronumcauses() {
        return pronumcauses;
    }

    public Medical_Procedures pronumcauses(Integer pronumcauses) {
        this.pronumcauses = pronumcauses;
        return this;
    }

    public void setPronumcauses(Integer pronumcauses) {
        this.pronumcauses = pronumcauses;
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
        Medical_Procedures medical_Procedures = (Medical_Procedures) o;
        if (medical_Procedures.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medical_Procedures.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medical_Procedures{" +
            "id=" + getId() +
            ", catalogkey='" + getCatalogkey() + "'" +
            ", pronombre='" + getPronombre() + "'" +
            ", procveedia='" + getProcveedia() + "'" +
            ", proedadia='" + getProedadia() + "'" +
            ", procveedfa='" + getProcveedfa() + "'" +
            ", proedadfa='" + getProedadfa() + "'" +
            ", sextype='" + getSextype() + "'" +
            ", pornivela='" + getPornivela() + "'" +
            ", procedimientotype='" + getProcedimientotype() + "'" +
            ", proprincipal='" + getProprincipal() + "'" +
            ", procapitulo='" + getProcapitulo() + "'" +
            ", proseccion='" + getProseccion() + "'" +
            ", procategoria='" + getProcategoria() + "'" +
            ", prosubcateg='" + getProsubcateg() + "'" +
            ", progrupolc='" + getProgrupolc() + "'" +
            ", proescauses='" + getProescauses() + "'" +
            ", pronumcauses='" + getPronumcauses() + "'" +
            "}";
    }
}
