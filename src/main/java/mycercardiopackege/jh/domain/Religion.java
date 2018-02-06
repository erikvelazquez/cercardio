package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Religion.
 */
@Entity
@Table(name = "religion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "religion")
public class Religion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "religion")
    private String religion;

    @Column(name = "isactive")
    private Boolean isactive;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReligion() {
        return religion;
    }

    public Religion religion(String religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Boolean isIsactive() {
        return isactive;
    }

    public Religion isactive(Boolean isactive) {
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
        Religion religion = (Religion) o;
        if (religion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), religion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Religion{" +
            "id=" + getId() +
            ", religion='" + getReligion() + "'" +
            ", isactive='" + isIsactive() + "'" +
            "}";
    }
}
