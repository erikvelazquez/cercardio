package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A AcademicDegree.
 */
@Entity
@Table(name = "academic_degree")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "academicdegree")
public class AcademicDegree implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "isactive")
    private Boolean isactive;

    @Column(name = "createdat")
    private ZonedDateTime createdat;

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

    public AcademicDegree description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsactive() {
        return isactive;
    }

    public AcademicDegree isactive(Boolean isactive) {
        this.isactive = isactive;
        return this;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public AcademicDegree createdat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
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
        AcademicDegree academicDegree = (AcademicDegree) o;
        if (academicDegree.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), academicDegree.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AcademicDegree{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", isactive='" + isIsactive() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            "}";
    }
}
