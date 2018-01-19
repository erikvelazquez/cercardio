package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Disabilities.
 */
@Entity
@Table(name = "disabilities")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "disabilities")
public class Disabilities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "subgroup")
    private String subgroup;

    @Column(name = "disability")
    private String disability;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public Disabilities subgroup(String subgroup) {
        this.subgroup = subgroup;
        return this;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public String getDisability() {
        return disability;
    }

    public Disabilities disability(String disability) {
        this.disability = disability;
        return this;
    }

    public void setDisability(String disability) {
        this.disability = disability;
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
        Disabilities disabilities = (Disabilities) o;
        if (disabilities.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), disabilities.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Disabilities{" +
            "id=" + getId() +
            ", subgroup='" + getSubgroup() + "'" +
            ", disability='" + getDisability() + "'" +
            "}";
    }
}
