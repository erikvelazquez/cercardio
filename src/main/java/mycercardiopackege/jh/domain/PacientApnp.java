package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PacientApnp.
 */
@Entity
@Table(name = "pacient_apnp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pacientapnp")
public class PacientApnp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "datestarts")
    private LocalDate datestarts;

    @Column(name = "dateend")
    private LocalDate dateend;

    @ManyToOne
    private Pacient pacient;

    @ManyToOne
    private DrugAddiction drugAddiction;

    @ManyToOne
    private Background backGround;

    @ManyToOne
    private Time time;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PacientApnp quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDatestarts() {
        return datestarts;
    }

    public PacientApnp datestarts(LocalDate datestarts) {
        this.datestarts = datestarts;
        return this;
    }

    public void setDatestarts(LocalDate datestarts) {
        this.datestarts = datestarts;
    }

    public LocalDate getDateend() {
        return dateend;
    }

    public PacientApnp dateend(LocalDate dateend) {
        this.dateend = dateend;
        return this;
    }

    public void setDateend(LocalDate dateend) {
        this.dateend = dateend;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public PacientApnp pacient(Pacient pacient) {
        this.pacient = pacient;
        return this;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public DrugAddiction getDrugAddiction() {
        return drugAddiction;
    }

    public PacientApnp drugAddiction(DrugAddiction drugAddiction) {
        this.drugAddiction = drugAddiction;
        return this;
    }

    public void setDrugAddiction(DrugAddiction drugAddiction) {
        this.drugAddiction = drugAddiction;
    }

    public Background getBackGround() {
        return backGround;
    }

    public PacientApnp backGround(Background background) {
        this.backGround = background;
        return this;
    }

    public void setBackGround(Background background) {
        this.backGround = background;
    }

    public Time getTime() {
        return time;
    }

    public PacientApnp time(Time time) {
        this.time = time;
        return this;
    }

    public void setTime(Time time) {
        this.time = time;
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
        PacientApnp pacientApnp = (PacientApnp) o;
        if (pacientApnp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pacientApnp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PacientApnp{" +
            "id=" + getId() +
            ", quantity='" + getQuantity() + "'" +
            ", datestarts='" + getDatestarts() + "'" +
            ", dateend='" + getDateend() + "'" +
            "}";
    }
}
