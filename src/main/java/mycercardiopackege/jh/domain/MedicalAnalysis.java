package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A MedicalAnalysis.
 */
@Entity
@Table(name = "medical_analysis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "medicalanalysis")
public class MedicalAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "presentation")
    private String presentation;

    @Column(name = "subjective")
    private String subjective;

    @Column(name = "objective")
    private String objective;

    @Column(name = "analysis")
    private String analysis;

    @Column(name = "disease")
    private String disease;

    @Column(name = "tests")
    private String tests;

    @Column(name = "treatment")
    private String treatment;

    @Column(name = "medicine")
    private String medicine;

    @Column(name = "daytime")
    private ZonedDateTime daytime;

    @OneToOne
    @JoinColumn(unique = true)
    private Medic medic;

    @OneToOne
    @JoinColumn(unique = true)
    private Pacient pacient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPresentation() {
        return presentation;
    }

    public MedicalAnalysis presentation(String presentation) {
        this.presentation = presentation;
        return this;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getSubjective() {
        return subjective;
    }

    public MedicalAnalysis subjective(String subjective) {
        this.subjective = subjective;
        return this;
    }

    public void setSubjective(String subjective) {
        this.subjective = subjective;
    }

    public String getObjective() {
        return objective;
    }

    public MedicalAnalysis objective(String objective) {
        this.objective = objective;
        return this;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getAnalysis() {
        return analysis;
    }

    public MedicalAnalysis analysis(String analysis) {
        this.analysis = analysis;
        return this;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getDisease() {
        return disease;
    }

    public MedicalAnalysis disease(String disease) {
        this.disease = disease;
        return this;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getTests() {
        return tests;
    }

    public MedicalAnalysis tests(String tests) {
        this.tests = tests;
        return this;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getTreatment() {
        return treatment;
    }

    public MedicalAnalysis treatment(String treatment) {
        this.treatment = treatment;
        return this;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getMedicine() {
        return medicine;
    }

    public MedicalAnalysis medicine(String medicine) {
        this.medicine = medicine;
        return this;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public ZonedDateTime getDaytime() {
        return daytime;
    }

    public MedicalAnalysis daytime(ZonedDateTime daytime) {
        this.daytime = daytime;
        return this;
    }

    public void setDaytime(ZonedDateTime daytime) {
        this.daytime = daytime;
    }

    public Medic getMedic() {
        return medic;
    }

    public MedicalAnalysis medic(Medic medic) {
        this.medic = medic;
        return this;
    }

    public void setMedic(Medic medic) {
        this.medic = medic;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public MedicalAnalysis pacient(Pacient pacient) {
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
        MedicalAnalysis medicalAnalysis = (MedicalAnalysis) o;
        if (medicalAnalysis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalAnalysis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalAnalysis{" +
            "id=" + getId() +
            ", presentation='" + getPresentation() + "'" +
            ", subjective='" + getSubjective() + "'" +
            ", objective='" + getObjective() + "'" +
            ", analysis='" + getAnalysis() + "'" +
            ", disease='" + getDisease() + "'" +
            ", tests='" + getTests() + "'" +
            ", treatment='" + getTreatment() + "'" +
            ", medicine='" + getMedicine() + "'" +
            ", daytime='" + getDaytime() + "'" +
            "}";
    }
}
