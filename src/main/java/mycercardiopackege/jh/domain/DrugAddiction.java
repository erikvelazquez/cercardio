package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DrugAddiction.
 */
@Entity
@Table(name = "drug_addiction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "drugaddiction")
public class DrugAddiction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "isactive")
    private Boolean isactive;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public DrugAddiction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsactive() {
        return isactive;
    }

    public DrugAddiction isactive(Boolean isactive) {
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
        DrugAddiction drugAddiction = (DrugAddiction) o;
        if (drugAddiction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), drugAddiction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DrugAddiction{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", isactive='" + isIsactive() + "'" +
            "}";
    }
}
