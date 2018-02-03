package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Indigenous_Languages.
 */
@Entity
@Table(name = "indigenous_languages")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "indigenous_languages")
public class Indigenous_Languages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "catalogkey")
    private String catalogkey;

    @Column(name = "indigenouslanguage")
    private String indigenouslanguage;

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

    public Indigenous_Languages catalogkey(String catalogkey) {
        this.catalogkey = catalogkey;
        return this;
    }

    public void setCatalogkey(String catalogkey) {
        this.catalogkey = catalogkey;
    }

    public String getIndigenouslanguage() {
        return indigenouslanguage;
    }

    public Indigenous_Languages indigenouslanguage(String indigenouslanguage) {
        this.indigenouslanguage = indigenouslanguage;
        return this;
    }

    public void setIndigenouslanguage(String indigenouslanguage) {
        this.indigenouslanguage = indigenouslanguage;
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
        Indigenous_Languages indigenous_Languages = (Indigenous_Languages) o;
        if (indigenous_Languages.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), indigenous_Languages.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Indigenous_Languages{" +
            "id=" + getId() +
            ", catalogkey='" + getCatalogkey() + "'" +
            ", indigenouslanguage='" + getIndigenouslanguage() + "'" +
            "}";
    }
}
