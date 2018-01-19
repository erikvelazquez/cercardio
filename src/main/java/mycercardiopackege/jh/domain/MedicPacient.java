package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MedicPacient.
 */
@Entity
@Table(name = "medic_pacient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "medicpacient")
public class MedicPacient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Medic medic;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "medic_pacient_pacient",
               joinColumns = @JoinColumn(name="medic_pacients_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="pacients_id", referencedColumnName="id"))
    private Set<Pacient> pacients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medic getMedic() {
        return medic;
    }

    public MedicPacient medic(Medic medic) {
        this.medic = medic;
        return this;
    }

    public void setMedic(Medic medic) {
        this.medic = medic;
    }

    public Set<Pacient> getPacients() {
        return pacients;
    }

    public MedicPacient pacients(Set<Pacient> pacients) {
        this.pacients = pacients;
        return this;
    }

    public MedicPacient addPacient(Pacient pacient) {
        this.pacients.add(pacient);
        return this;
    }

    public MedicPacient removePacient(Pacient pacient) {
        this.pacients.remove(pacient);
        return this;
    }

    public void setPacients(Set<Pacient> pacients) {
        this.pacients = pacients;
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
        MedicPacient medicPacient = (MedicPacient) o;
        if (medicPacient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicPacient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicPacient{" +
            "id=" + getId() +
            "}";
    }
}
