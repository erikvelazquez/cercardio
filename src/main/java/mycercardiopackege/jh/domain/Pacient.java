package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Pacient.
 */
@Entity
@Table(name = "pacient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pacient")
public class Pacient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cp")
    private Integer cp;

    @Column(name = "colony")
    private String colony;

    @Column(name = "street")
    private String street;

    @Column(name = "streetnumber")
    private String streetnumber;

    @Column(name = "suitnumber")
    private String suitnumber;

    @Column(name = "phonenumber_1")
    private Integer phonenumber1;

    @Column(name = "phonenumber_2")
    private Integer phonenumber2;

    @Column(name = "email_1")
    private String email1;

    @Column(name = "email_2")
    private String email2;

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

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "snapchat")
    private String snapchat;

    @Column(name = "linkedin")
    private String linkedin;

    @Column(name = "vine")
    private String vine;

    @OneToOne
    @JoinColumn(unique = true)
    private UserBD userBD;

    @ManyToOne
    private Company company;

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
    private SocioEconomicLevel socioEconomicLevel;

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

    public Integer getCp() {
        return cp;
    }

    public Pacient cp(Integer cp) {
        this.cp = cp;
        return this;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getColony() {
        return colony;
    }

    public Pacient colony(String colony) {
        this.colony = colony;
        return this;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getStreet() {
        return street;
    }

    public Pacient street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public Pacient streetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
        return this;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public String getSuitnumber() {
        return suitnumber;
    }

    public Pacient suitnumber(String suitnumber) {
        this.suitnumber = suitnumber;
        return this;
    }

    public void setSuitnumber(String suitnumber) {
        this.suitnumber = suitnumber;
    }

    public Integer getPhonenumber1() {
        return phonenumber1;
    }

    public Pacient phonenumber1(Integer phonenumber1) {
        this.phonenumber1 = phonenumber1;
        return this;
    }

    public void setPhonenumber1(Integer phonenumber1) {
        this.phonenumber1 = phonenumber1;
    }

    public Integer getPhonenumber2() {
        return phonenumber2;
    }

    public Pacient phonenumber2(Integer phonenumber2) {
        this.phonenumber2 = phonenumber2;
        return this;
    }

    public void setPhonenumber2(Integer phonenumber2) {
        this.phonenumber2 = phonenumber2;
    }

    public String getEmail1() {
        return email1;
    }

    public Pacient email1(String email1) {
        this.email1 = email1;
        return this;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public Pacient email2(String email2) {
        this.email2 = email2;
        return this;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getCurp() {
        return curp;
    }

    public Pacient curp(String curp) {
        this.curp = curp;
        return this;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public Pacient rfc(String rfc) {
        this.rfc = rfc;
        return this;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public LocalDate getDateofbirth() {
        return dateofbirth;
    }

    public Pacient dateofbirth(LocalDate dateofbirth) {
        this.dateofbirth = dateofbirth;
        return this;
    }

    public void setDateofbirth(LocalDate dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public Pacient placeofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
        return this;
    }

    public void setPlaceofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public String getPrivatenumber() {
        return privatenumber;
    }

    public Pacient privatenumber(String privatenumber) {
        this.privatenumber = privatenumber;
        return this;
    }

    public void setPrivatenumber(String privatenumber) {
        this.privatenumber = privatenumber;
    }

    public String getSocialnumber() {
        return socialnumber;
    }

    public Pacient socialnumber(String socialnumber) {
        this.socialnumber = socialnumber;
        return this;
    }

    public void setSocialnumber(String socialnumber) {
        this.socialnumber = socialnumber;
    }

    public String getFacebook() {
        return facebook;
    }

    public Pacient facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public Pacient twitter(String twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public Pacient instagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getSnapchat() {
        return snapchat;
    }

    public Pacient snapchat(String snapchat) {
        this.snapchat = snapchat;
        return this;
    }

    public void setSnapchat(String snapchat) {
        this.snapchat = snapchat;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public Pacient linkedin(String linkedin) {
        this.linkedin = linkedin;
        return this;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getVine() {
        return vine;
    }

    public Pacient vine(String vine) {
        this.vine = vine;
        return this;
    }

    public void setVine(String vine) {
        this.vine = vine;
    }

    public UserBD getUserBD() {
        return userBD;
    }

    public Pacient userBD(UserBD userBD) {
        this.userBD = userBD;
        return this;
    }

    public void setUserBD(UserBD userBD) {
        this.userBD = userBD;
    }

    public Company getCompany() {
        return company;
    }

    public Pacient company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Gender getGender() {
        return gender;
    }

    public Pacient gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public CivilStatus getCivilstatus() {
        return civilstatus;
    }

    public Pacient civilstatus(CivilStatus civilStatus) {
        this.civilstatus = civilStatus;
        return this;
    }

    public void setCivilstatus(CivilStatus civilStatus) {
        this.civilstatus = civilStatus;
    }

    public Religion getReligion() {
        return religion;
    }

    public Pacient religion(Religion religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public EthnicGroup getEthnicGroup() {
        return ethnicGroup;
    }

    public Pacient ethnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
        return this;
    }

    public void setEthnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public AcademicDegree getAcademicDegree() {
        return academicDegree;
    }

    public Pacient academicDegree(AcademicDegree academicDegree) {
        this.academicDegree = academicDegree;
        return this;
    }

    public void setAcademicDegree(AcademicDegree academicDegree) {
        this.academicDegree = academicDegree;
    }

    public SocioEconomicLevel getSocioEconomicLevel() {
        return socioEconomicLevel;
    }

    public Pacient socioEconomicLevel(SocioEconomicLevel socioEconomicLevel) {
        this.socioEconomicLevel = socioEconomicLevel;
        return this;
    }

    public void setSocioEconomicLevel(SocioEconomicLevel socioeconomicLevel) {
        this.socioEconomicLevel = socioEconomicLevel;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public Pacient occupation(Occupation occupation) {
        this.occupation = occupation;
        return this;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public LivingPlace getLivingPlace() {
        return livingPlace;
    }

    public Pacient livingPlace(LivingPlace livingPlace) {
        this.livingPlace = livingPlace;
        return this;
    }

    public void setLivingPlace(LivingPlace livingPlace) {
        this.livingPlace = livingPlace;
    }

    public PrivateHealthInsurance getPrivateHealthInsurance() {
        return privateHealthInsurance;
    }

    public Pacient privateHealthInsurance(PrivateHealthInsurance privateHealthInsurance) {
        this.privateHealthInsurance = privateHealthInsurance;
        return this;
    }

    public void setPrivateHealthInsurance(PrivateHealthInsurance privateHealthInsurance) {
        this.privateHealthInsurance = privateHealthInsurance;
    }

    public SocialHealthInsurance getSocialHealthInsurance() {
        return socialHealthInsurance;
    }

    public Pacient socialHealthInsurance(SocialHealthInsurance socialHealthInsurance) {
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
        Pacient pacient = (Pacient) o;
        if (pacient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pacient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pacient{" +
            "id=" + getId() +
            ", cp='" + getCp() + "'" +
            ", colony='" + getColony() + "'" +
            ", street='" + getStreet() + "'" +
            ", streetnumber='" + getStreetnumber() + "'" +
            ", suitnumber='" + getSuitnumber() + "'" +
            ", phonenumber1='" + getPhonenumber1() + "'" +
            ", phonenumber2='" + getPhonenumber2() + "'" +
            ", email1='" + getEmail1() + "'" +
            ", email2='" + getEmail2() + "'" +
            ", curp='" + getCurp() + "'" +
            ", rfc='" + getRfc() + "'" +
            ", dateofbirth='" + getDateofbirth() + "'" +
            ", placeofbirth='" + getPlaceofbirth() + "'" +
            ", privatenumber='" + getPrivatenumber() + "'" +
            ", socialnumber='" + getSocialnumber() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", twitter='" + getTwitter() + "'" +
            ", instagram='" + getInstagram() + "'" +
            ", snapchat='" + getSnapchat() + "'" +
            ", linkedin='" + getLinkedin() + "'" +
            ", vine='" + getVine() + "'" +
            "}";
    }
}
