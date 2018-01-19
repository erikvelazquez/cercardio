package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PacientApp.
 */
@Entity
@Table(name = "pacient_app")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pacientapp")
public class PacientApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "chronicdegenerative")
    private String chronicdegenerative;

    @Column(name = "traumatic")
    private String traumatic;

    @Column(name = "gynecologicalobstetrics")
    private String gynecologicalobstetrics;

    @Column(name = "others")
    private String others;

    @Column(name = "daytime")
    private ZonedDateTime daytime;

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

    public String getChronicdegenerative() {
        return chronicdegenerative;
    }

    public PacientApp chronicdegenerative(String chronicdegenerative) {
        this.chronicdegenerative = chronicdegenerative;
        return this;
    }

    public void setChronicdegenerative(String chronicdegenerative) {
        this.chronicdegenerative = chronicdegenerative;
    }

    public String getTraumatic() {
        return traumatic;
    }

    public PacientApp traumatic(String traumatic) {
        this.traumatic = traumatic;
        return this;
    }

    public void setTraumatic(String traumatic) {
        this.traumatic = traumatic;
    }

    public String getGynecologicalobstetrics() {
        return gynecologicalobstetrics;
    }

    public PacientApp gynecologicalobstetrics(String gynecologicalobstetrics) {
        this.gynecologicalobstetrics = gynecologicalobstetrics;
        return this;
    }

    public void setGynecologicalobstetrics(String gynecologicalobstetrics) {
        this.gynecologicalobstetrics = gynecologicalobstetrics;
    }

    public String getOthers() {
        return others;
    }

    public PacientApp others(String others) {
        this.others = others;
        return this;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public ZonedDateTime getDaytime() {
        return daytime;
    }

    public PacientApp daytime(ZonedDateTime daytime) {
        this.daytime = daytime;
        return this;
    }

    public void setDaytime(ZonedDateTime daytime) {
        this.daytime = daytime;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public PacientApp pacient(Pacient pacient) {
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
        PacientApp pacientApp = (PacientApp) o;
        if (pacientApp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pacientApp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PacientApp{" +
            "id=" + getId() +
            ", chronicdegenerative='" + getChronicdegenerative() + "'" +
            ", traumatic='" + getTraumatic() + "'" +
            ", gynecologicalobstetrics='" + getGynecologicalobstetrics() + "'" +
            ", others='" + getOthers() + "'" +
            ", daytime='" + getDaytime() + "'" +
            "}";
    }
}
