package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PacientMedicalData.
 */
@Entity
@Table(name = "pacient_medical_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pacientmedicaldata")
public class PacientMedicalData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "diseases")
    private String diseases;

    @Column(name = "surgicalinterventions")
    private String surgicalinterventions;

    @Column(name = "useofdrugs")
    private String useofdrugs;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "ahffather")
    private String ahffather;

    @Column(name = "ahfmother")
    private String ahfmother;

    @Column(name = "ahfothers")
    private String ahfothers;

    @OneToOne
    @JoinColumn(unique = true)
    private Pacient pacient;

    @ManyToOne
    private BloodGroup bloodGroup;

    @ManyToOne
    private Disabilities disabilities;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiseases() {
        return diseases;
    }

    public PacientMedicalData diseases(String diseases) {
        this.diseases = diseases;
        return this;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getSurgicalinterventions() {
        return surgicalinterventions;
    }

    public PacientMedicalData surgicalinterventions(String surgicalinterventions) {
        this.surgicalinterventions = surgicalinterventions;
        return this;
    }

    public void setSurgicalinterventions(String surgicalinterventions) {
        this.surgicalinterventions = surgicalinterventions;
    }

    public String getUseofdrugs() {
        return useofdrugs;
    }

    public PacientMedicalData useofdrugs(String useofdrugs) {
        this.useofdrugs = useofdrugs;
        return this;
    }

    public void setUseofdrugs(String useofdrugs) {
        this.useofdrugs = useofdrugs;
    }

    public String getAllergies() {
        return allergies;
    }

    public PacientMedicalData allergies(String allergies) {
        this.allergies = allergies;
        return this;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getAhffather() {
        return ahffather;
    }

    public PacientMedicalData ahffather(String ahffather) {
        this.ahffather = ahffather;
        return this;
    }

    public void setAhffather(String ahffather) {
        this.ahffather = ahffather;
    }

    public String getAhfmother() {
        return ahfmother;
    }

    public PacientMedicalData ahfmother(String ahfmother) {
        this.ahfmother = ahfmother;
        return this;
    }

    public void setAhfmother(String ahfmother) {
        this.ahfmother = ahfmother;
    }

    public String getAhfothers() {
        return ahfothers;
    }

    public PacientMedicalData ahfothers(String ahfothers) {
        this.ahfothers = ahfothers;
        return this;
    }

    public void setAhfothers(String ahfothers) {
        this.ahfothers = ahfothers;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public PacientMedicalData pacient(Pacient pacient) {
        this.pacient = pacient;
        return this;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public PacientMedicalData bloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
        return this;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Disabilities getDisabilities() {
        return disabilities;
    }

    public PacientMedicalData disabilities(Disabilities disabilities) {
        this.disabilities = disabilities;
        return this;
    }

    public void setDisabilities(Disabilities disabilities) {
        this.disabilities = disabilities;
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
        PacientMedicalData pacientMedicalData = (PacientMedicalData) o;
        if (pacientMedicalData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pacientMedicalData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PacientMedicalData{" +
            "id=" + getId() +
            ", diseases='" + getDiseases() + "'" +
            ", surgicalinterventions='" + getSurgicalinterventions() + "'" +
            ", useofdrugs='" + getUseofdrugs() + "'" +
            ", allergies='" + getAllergies() + "'" +
            ", ahffather='" + getAhffather() + "'" +
            ", ahfmother='" + getAhfmother() + "'" +
            ", ahfothers='" + getAhfothers() + "'" +
            "}";
    }
}
