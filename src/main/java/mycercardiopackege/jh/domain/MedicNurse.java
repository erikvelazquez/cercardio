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
 * A MedicNurse.
 */
@Entity
@Table(name = "medic_nurse")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "medicnurse")
public class MedicNurse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "idnurse")
    private Integer idnurse;

    @OneToOne
    @JoinColumn(unique = true)
    private Medic medic;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "medic_nurse_nurse",
               joinColumns = @JoinColumn(name="medic_nurses_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="nurses_id", referencedColumnName="id"))
    private Set<Nurse> nurses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdnurse() {
        return idnurse;
    }

    public MedicNurse idnurse(Integer idnurse) {
        this.idnurse = idnurse;
        return this;
    }

    public void setIdnurse(Integer idnurse) {
        this.idnurse = idnurse;
    }

    public Medic getMedic() {
        return medic;
    }

    public MedicNurse medic(Medic medic) {
        this.medic = medic;
        return this;
    }

    public void setMedic(Medic medic) {
        this.medic = medic;
    }

    public Set<Nurse> getNurses() {
        return nurses;
    }

    public MedicNurse nurses(Set<Nurse> nurses) {
        this.nurses = nurses;
        return this;
    }

    public MedicNurse addNurse(Nurse nurse) {
        this.nurses.add(nurse);
        return this;
    }

    public MedicNurse removeNurse(Nurse nurse) {
        this.nurses.remove(nurse);
        return this;
    }

    public void setNurses(Set<Nurse> nurses) {
        this.nurses = nurses;
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
        MedicNurse medicNurse = (MedicNurse) o;
        if (medicNurse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicNurse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicNurse{" +
            "id=" + getId() +
            ", idnurse='" + getIdnurse() + "'" +
            "}";
    }
}
