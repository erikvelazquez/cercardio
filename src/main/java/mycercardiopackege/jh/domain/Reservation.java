package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "note")
    private String note;

    @Column(name = "dateat")
    private LocalDate dateat;

    @Column(name = "createdat")
    private ZonedDateTime createdat;

    @Column(name = "symtoms")
    private String symtoms;

    @Column(name = "medicaments")
    private String medicaments;

    @Column(name = "jhi_cost")
    private Integer cost;

    @OneToOne
    @JoinColumn(unique = true)
    private Appreciation appreciation;

    @OneToOne
    @JoinColumn(unique = true)
    private PacientMedicalAnalysis pacientMedicalAnalysis;

    @ManyToOne
    private Pacient pacient;

    @ManyToOne
    private Medic medic;

    @ManyToOne
    private Status status;

    @ManyToOne
    private Payment payment;

    @ManyToOne
    private Timers timers;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Reservation title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public Reservation note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getDateat() {
        return dateat;
    }

    public Reservation dateat(LocalDate dateat) {
        this.dateat = dateat;
        return this;
    }

    public void setDateat(LocalDate dateat) {
        this.dateat = dateat;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public Reservation createdat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
    }

    public String getSymtoms() {
        return symtoms;
    }

    public Reservation symtoms(String symtoms) {
        this.symtoms = symtoms;
        return this;
    }

    public void setSymtoms(String symtoms) {
        this.symtoms = symtoms;
    }

    public String getMedicaments() {
        return medicaments;
    }

    public Reservation medicaments(String medicaments) {
        this.medicaments = medicaments;
        return this;
    }

    public void setMedicaments(String medicaments) {
        this.medicaments = medicaments;
    }

    public Integer getCost() {
        return cost;
    }

    public Reservation cost(Integer cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Appreciation getAppreciation() {
        return appreciation;
    }

    public Reservation appreciation(Appreciation appreciation) {
        this.appreciation = appreciation;
        return this;
    }

    public void setAppreciation(Appreciation appreciation) {
        this.appreciation = appreciation;
    }

    public PacientMedicalAnalysis getPacientMedicalAnalysis() {
        return pacientMedicalAnalysis;
    }

    public Reservation pacientMedicalAnalysis(PacientMedicalAnalysis pacientMedicalAnalysis) {
        this.pacientMedicalAnalysis = pacientMedicalAnalysis;
        return this;
    }

    public void setPacientMedicalAnalysis(PacientMedicalAnalysis pacientMedicalAnalysis) {
        this.pacientMedicalAnalysis = pacientMedicalAnalysis;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public Reservation pacient(Pacient pacient) {
        this.pacient = pacient;
        return this;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public Medic getMedic() {
        return medic;
    }

    public Reservation medic(Medic medic) {
        this.medic = medic;
        return this;
    }

    public void setMedic(Medic medic) {
        this.medic = medic;
    }

    public Status getStatus() {
        return status;
    }

    public Reservation status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public Reservation payment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Timers getTimers() {
        return timers;
    }

    public Reservation timers(Timers timers) {
        this.timers = timers;
        return this;
    }

    public void setTimers(Timers timers) {
        this.timers = timers;
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
        Reservation reservation = (Reservation) o;
        if (reservation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", note='" + getNote() + "'" +
            ", dateat='" + getDateat() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", symtoms='" + getSymtoms() + "'" +
            ", medicaments='" + getMedicaments() + "'" +
            ", cost='" + getCost() + "'" +
            "}";
    }
}
