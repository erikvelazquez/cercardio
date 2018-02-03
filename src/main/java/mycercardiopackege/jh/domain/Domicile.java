package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Domicile.
 */
@Entity
@Table(name = "domicile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "domicile")
public class Domicile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "domicile")
    private String domicile;

    @Column(name = "isactive")
    private Boolean isactive;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomicile() {
        return domicile;
    }

    public Domicile domicile(String domicile) {
        this.domicile = domicile;
        return this;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public Boolean isIsactive() {
        return isactive;
    }

    public Domicile isactive(Boolean isactive) {
        this.isactive = isactive;
        return this;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
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
        Domicile domicile = (Domicile) o;
        if (domicile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), domicile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Domicile{" +
            "id=" + getId() +
            ", domicile='" + getDomicile() + "'" +
            ", isactive='" + isIsactive() + "'" +
            "}";
    }
}
