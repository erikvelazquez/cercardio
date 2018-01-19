package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PacientGenerals.
 */
@Entity
@Table(name = "pacient_generals")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pacientgenerals")
public class PacientGenerals implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "curp")
    private String curp;

    @Column(name = "rfc")
    private String rfc;

    @Column(name = "dateofbirth")
    private LocalDate dateofbirth;

    @Column(name = "placeofbirth")
    private String placeofbirth;

    @Column(name = "privatenumber")
    private String privatenumber;

    @Column(name = "socialnumber")
    private String socialnumber;

    @OneToOne
    @JoinColumn(unique = true)
    private UserBD userBD;

    @ManyToOne
    private Gender gender;

    @ManyToOne
    private CivilStatus civilstatus;

    @ManyToOne
    private Religion religion;

    @ManyToOne
    private EthnicGroup ethnicGroup;

    @ManyToOne
    private AcademicDegree academicDegree;

    @ManyToOne
    private SocioeconomicLevel socioeconomicLevel;

    @ManyToOne
    private Occupation occupation;

    @ManyToOne
    private LivingPlace livingPlace;

    @ManyToOne
    private PrivateHealthInsurance privateHealthInsurance;

    @ManyToOne
    private SocialHealthInsurance socialHealthInsurance;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurp() {
        return curp;
    }

    public PacientGenerals curp(String curp) {
        this.curp = curp;
        return this;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public PacientGenerals rfc(String rfc) {
        this.rfc = rfc;
        return this;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public LocalDate getDateofbirth() {
        return dateofbirth;
    }

    public PacientGenerals dateofbirth(LocalDate dateofbirth) {
        this.dateofbirth = dateofbirth;
        return this;
    }

    public void setDateofbirth(LocalDate dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public PacientGenerals placeofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
        return this;
    }

    public void setPlaceofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public String getPrivatenumber() {
        return privatenumber;
    }

    public PacientGenerals privatenumber(String privatenumber) {
        this.privatenumber = privatenumber;
        return this;
    }

    public void setPrivatenumber(String privatenumber) {
        this.privatenumber = privatenumber;
    }

    public String getSocialnumber() {
        return socialnumber;
    }

    public PacientGenerals socialnumber(String socialnumber) {
        this.socialnumber = socialnumber;
        return this;
    }

    public void setSocialnumber(String socialnumber) {
        this.socialnumber = socialnumber;
    }

    public UserBD getUserBD() {
        return userBD;
    }

    public PacientGenerals userBD(UserBD userBD) {
        this.userBD = userBD;
        return this;
    }

    public void setUserBD(UserBD userBD) {
        this.userBD = userBD;
    }

    public Gender getGender() {
        return gender;
    }

    public PacientGenerals gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public CivilStatus getCivilstatus() {
        return civilstatus;
    }

    public PacientGenerals civilstatus(CivilStatus civilStatus) {
        this.civilstatus = civilStatus;
        return this;
    }

    public void setCivilstatus(CivilStatus civilStatus) {
        this.civilstatus = civilStatus;
    }

    public Religion getReligion() {
        return religion;
    }

    public PacientGenerals religion(Religion religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public EthnicGroup getEthnicGroup() {
        return ethnicGroup;
    }

    public PacientGenerals ethnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
        return this;
    }

    public void setEthnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public AcademicDegree getAcademicDegree() {
        return academicDegree;
    }

    public PacientGenerals academicDegree(AcademicDegree academicDegree) {
        this.academicDegree = academicDegree;
        return this;
    }

    public void setAcademicDegree(AcademicDegree academicDegree) {
        this.academicDegree = academicDegree;
    }

    public SocioeconomicLevel getSocioeconomicLevel() {
        return socioeconomicLevel;
    }

    public PacientGenerals socioeconomicLevel(SocioeconomicLevel socioeconomicLevel) {
        this.socioeconomicLevel = socioeconomicLevel;
        return this;
    }

    public void setSocioeconomicLevel(SocioeconomicLevel socioeconomicLevel) {
        this.socioeconomicLevel = socioeconomicLevel;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public PacientGenerals occupation(Occupation occupation) {
        this.occupation = occupation;
        return this;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public LivingPlace getLivingPlace() {
        return livingPlace;
    }

    public PacientGenerals livingPlace(LivingPlace livingPlace) {
        this.livingPlace = livingPlace;
        return this;
    }

    public void setLivingPlace(LivingPlace livingPlace) {
        this.livingPlace = livingPlace;
    }

    public PrivateHealthInsurance getPrivateHealthInsurance() {
        return privateHealthInsurance;
    }

    public PacientGenerals privateHealthInsurance(PrivateHealthInsurance privateHealthInsurance) {
        this.privateHealthInsurance = privateHealthInsurance;
        return this;
    }

    public void setPrivateHealthInsurance(PrivateHealthInsurance privateHealthInsurance) {
        this.privateHealthInsurance = privateHealthInsurance;
    }

    public SocialHealthInsurance getSocialHealthInsurance() {
        return socialHealthInsurance;
    }

    public PacientGenerals socialHealthInsurance(SocialHealthInsurance socialHealthInsurance) {
        this.socialHealthInsurance = socialHealthInsurance;
        return this;
    }

    public void setSocialHealthInsurance(SocialHealthInsurance socialHealthInsurance) {
        this.socialHealthInsurance = socialHealthInsurance;
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
        PacientGenerals pacientGenerals = (PacientGenerals) o;
        if (pacientGenerals.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pacientGenerals.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PacientGenerals{" +
            "id=" + getId() +
            ", curp='" + getCurp() + "'" +
            ", rfc='" + getRfc() + "'" +
            ", dateofbirth='" + getDateofbirth() + "'" +
            ", placeofbirth='" + getPlaceofbirth() + "'" +
            ", privatenumber='" + getPrivatenumber() + "'" +
            ", socialnumber='" + getSocialnumber() + "'" +
            "}";
    }
}
