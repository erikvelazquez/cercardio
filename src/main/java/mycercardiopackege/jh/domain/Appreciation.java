package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Appreciation.
 */
@Entity
@Table(name = "appreciation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "appreciation")
public class Appreciation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "height")
    private Float height;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "jhi_size")
    private Float size;

    @Column(name = "bmi")
    private Float bmi;

    @Column(name = "temperature")
    private Float temperature;

    @Column(name = "saturation")
    private Float saturation;

    @Column(name = "bloodpressuere")
    private Float bloodpressuere;

    @Column(name = "heartrate")
    private Float heartrate;

    @Column(name = "breathingfrequency")
    private Float breathingfrequency;

    @Column(name = "others")
    private String others;

    @Column(name = "head")
    private String head;

    @Column(name = "neck")
    private String neck;

    @Column(name = "chest")
    private String chest;

    @Column(name = "abdomen")
    private String abdomen;

    @Column(name = "bodypart")
    private String bodypart;

    @Column(name = "genitals")
    private String genitals;

    @Column(name = "othersphysical")
    private String othersphysical;

    @Column(name = "createdat")
    private ZonedDateTime createdat;

    @ManyToOne
    private Pacient pacient;

    @ManyToOne
    private Medic medic;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getHeight() {
        return height;
    }

    public Appreciation height(Float height) {
        this.height = height;
        return this;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public Appreciation weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getSize() {
        return size;
    }

    public Appreciation size(Float size) {
        this.size = size;
        return this;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Float getBmi() {
        return bmi;
    }

    public Appreciation bmi(Float bmi) {
        this.bmi = bmi;
        return this;
    }

    public void setBmi(Float bmi) {
        this.bmi = bmi;
    }

    public Float getTemperature() {
        return temperature;
    }

    public Appreciation temperature(Float temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getSaturation() {
        return saturation;
    }

    public Appreciation saturation(Float saturation) {
        this.saturation = saturation;
        return this;
    }

    public void setSaturation(Float saturation) {
        this.saturation = saturation;
    }

    public Float getBloodpressuere() {
        return bloodpressuere;
    }

    public Appreciation bloodpressuere(Float bloodpressuere) {
        this.bloodpressuere = bloodpressuere;
        return this;
    }

    public void setBloodpressuere(Float bloodpressuere) {
        this.bloodpressuere = bloodpressuere;
    }

    public Float getHeartrate() {
        return heartrate;
    }

    public Appreciation heartrate(Float heartrate) {
        this.heartrate = heartrate;
        return this;
    }

    public void setHeartrate(Float heartrate) {
        this.heartrate = heartrate;
    }

    public Float getBreathingfrequency() {
        return breathingfrequency;
    }

    public Appreciation breathingfrequency(Float breathingfrequency) {
        this.breathingfrequency = breathingfrequency;
        return this;
    }

    public void setBreathingfrequency(Float breathingfrequency) {
        this.breathingfrequency = breathingfrequency;
    }

    public String getOthers() {
        return others;
    }

    public Appreciation others(String others) {
        this.others = others;
        return this;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getHead() {
        return head;
    }

    public Appreciation head(String head) {
        this.head = head;
        return this;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNeck() {
        return neck;
    }

    public Appreciation neck(String neck) {
        this.neck = neck;
        return this;
    }

    public void setNeck(String neck) {
        this.neck = neck;
    }

    public String getChest() {
        return chest;
    }

    public Appreciation chest(String chest) {
        this.chest = chest;
        return this;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getAbdomen() {
        return abdomen;
    }

    public Appreciation abdomen(String abdomen) {
        this.abdomen = abdomen;
        return this;
    }

    public void setAbdomen(String abdomen) {
        this.abdomen = abdomen;
    }

    public String getBodypart() {
        return bodypart;
    }

    public Appreciation bodypart(String bodypart) {
        this.bodypart = bodypart;
        return this;
    }

    public void setBodypart(String bodypart) {
        this.bodypart = bodypart;
    }

    public String getGenitals() {
        return genitals;
    }

    public Appreciation genitals(String genitals) {
        this.genitals = genitals;
        return this;
    }

    public void setGenitals(String genitals) {
        this.genitals = genitals;
    }

    public String getOthersphysical() {
        return othersphysical;
    }

    public Appreciation othersphysical(String othersphysical) {
        this.othersphysical = othersphysical;
        return this;
    }

    public void setOthersphysical(String othersphysical) {
        this.othersphysical = othersphysical;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public Appreciation createdat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public Appreciation pacient(Pacient pacient) {
        this.pacient = pacient;
        return this;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public Medic getMedic() {
        return medic;
    }

    public Appreciation medic(Medic medic) {
        this.medic = medic;
        return this;
    }

    public void setMedic(Medic medic) {
        this.medic = medic;
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
        Appreciation appreciation = (Appreciation) o;
        if (appreciation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appreciation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Appreciation{" +
            "id=" + getId() +
            ", height='" + getHeight() + "'" +
            ", weight='" + getWeight() + "'" +
            ", size='" + getSize() + "'" +
            ", bmi='" + getBmi() + "'" +
            ", temperature='" + getTemperature() + "'" +
            ", saturation='" + getSaturation() + "'" +
            ", bloodpressuere='" + getBloodpressuere() + "'" +
            ", heartrate='" + getHeartrate() + "'" +
            ", breathingfrequency='" + getBreathingfrequency() + "'" +
            ", others='" + getOthers() + "'" +
            ", head='" + getHead() + "'" +
            ", neck='" + getNeck() + "'" +
            ", chest='" + getChest() + "'" +
            ", abdomen='" + getAbdomen() + "'" +
            ", bodypart='" + getBodypart() + "'" +
            ", genitals='" + getGenitals() + "'" +
            ", othersphysical='" + getOthersphysical() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            "}";
    }
}
