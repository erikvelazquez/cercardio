package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PacientLaboratoy.
 */
@Entity
@Table(name = "pacient_laboratoy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pacientlaboratoy")
public class PacientLaboratoy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "laboratory")
    private String laboratory;

    @Column(name = "jhi_number")
    private String number;

    @Column(name = "dateofelaboration")
    private ZonedDateTime dateofelaboration;

    @ManyToOne
    private Pacient pacient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public PacientLaboratoy laboratory(String laboratory) {
        this.laboratory = laboratory;
        return this;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public String getNumber() {
        return number;
    }

    public PacientLaboratoy number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ZonedDateTime getDateofelaboration() {
        return dateofelaboration;
    }

    public PacientLaboratoy dateofelaboration(ZonedDateTime dateofelaboration) {
        this.dateofelaboration = dateofelaboration;
        return this;
    }

    public void setDateofelaboration(ZonedDateTime dateofelaboration) {
        this.dateofelaboration = dateofelaboration;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public PacientLaboratoy pacient(Pacient pacient) {
        this.pacient = pacient;
        return this;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
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
        PacientLaboratoy pacientLaboratoy = (PacientLaboratoy) o;
        if (pacientLaboratoy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pacientLaboratoy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PacientLaboratoy{" +
            "id=" + getId() +
            ", laboratory='" + getLaboratory() + "'" +
            ", number='" + getNumber() + "'" +
            ", dateofelaboration='" + getDateofelaboration() + "'" +
            "}";
    }
}
