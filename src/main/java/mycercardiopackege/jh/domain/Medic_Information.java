package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Medic_Information.
 */
@Entity
@Table(name = "medic_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "medic_information")
public class Medic_Information implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cp")
    private Integer cp;

    @Column(name = "street")
    private String street;

    @Column(name = "extnumber")
    private String extnumber;

    @Column(name = "intnumber")
    private String intnumber;

    @Column(name = "phone_1")
    private Long phone1;

    @Column(name = "phone_2")
    private Long phone2;

    @Column(name = "email_1")
    private String email1;

    @Column(name = "email_2")
    private String email2;

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

    @Column(name = "recomended")
    private String recomended;

    @Column(name = "cvuconacyt")
    private String cvuconacyt;

    @Column(name = "cedula")
    private String cedula;

    @Column(name = "cedulaesp")
    private String cedulaesp;

    @Column(name = "curp")
    private String curp;

    @Column(name = "rfc")
    private String rfc;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "externalhospitals")
    private String externalhospitals;

    @Column(name = "subid")
    private Integer subid;

    @Column(name = "highid")
    private Integer highid;

    @Column(name = "medicalinsurance")
    private Integer medicalinsurance;

    @Column(name = "socialinsurance")
    private Integer socialinsurance;

    @Column(name = "responsibilityinsurance")
    private String responsibilityinsurance;

    @Column(name = "s_i_nconacyt")
    private String sINconacyt;

    @Column(name = "medicalinnumber")
    private String medicalinnumber;

    @Column(name = "socialinnumber")
    private String socialinnumber;

    @Column(name = "responsibilityinnumber")
    private String responsibilityinnumber;

    @OneToOne
    @JoinColumn(unique = true)
    private Medic medic;

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

    public Medic_Information cp(Integer cp) {
        this.cp = cp;
        return this;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getStreet() {
        return street;
    }

    public Medic_Information street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getExtnumber() {
        return extnumber;
    }

    public Medic_Information extnumber(String extnumber) {
        this.extnumber = extnumber;
        return this;
    }

    public void setExtnumber(String extnumber) {
        this.extnumber = extnumber;
    }

    public String getIntnumber() {
        return intnumber;
    }

    public Medic_Information intnumber(String intnumber) {
        this.intnumber = intnumber;
        return this;
    }

    public void setIntnumber(String intnumber) {
        this.intnumber = intnumber;
    }

    public Long getPhone1() {
        return phone1;
    }

    public Medic_Information phone1(Long phone1) {
        this.phone1 = phone1;
        return this;
    }

    public void setPhone1(Long phone1) {
        this.phone1 = phone1;
    }

    public Long getPhone2() {
        return phone2;
    }

    public Medic_Information phone2(Long phone2) {
        this.phone2 = phone2;
        return this;
    }

    public void setPhone2(Long phone2) {
        this.phone2 = phone2;
    }

    public String getEmail1() {
        return email1;
    }

    public Medic_Information email1(String email1) {
        this.email1 = email1;
        return this;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public Medic_Information email2(String email2) {
        this.email2 = email2;
        return this;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getFacebook() {
        return facebook;
    }

    public Medic_Information facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public Medic_Information twitter(String twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public Medic_Information instagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getSnapchat() {
        return snapchat;
    }

    public Medic_Information snapchat(String snapchat) {
        this.snapchat = snapchat;
        return this;
    }

    public void setSnapchat(String snapchat) {
        this.snapchat = snapchat;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public Medic_Information linkedin(String linkedin) {
        this.linkedin = linkedin;
        return this;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getVine() {
        return vine;
    }

    public Medic_Information vine(String vine) {
        this.vine = vine;
        return this;
    }

    public void setVine(String vine) {
        this.vine = vine;
    }

    public String getRecomended() {
        return recomended;
    }

    public Medic_Information recomended(String recomended) {
        this.recomended = recomended;
        return this;
    }

    public void setRecomended(String recomended) {
        this.recomended = recomended;
    }

    public String getCvuconacyt() {
        return cvuconacyt;
    }

    public Medic_Information cvuconacyt(String cvuconacyt) {
        this.cvuconacyt = cvuconacyt;
        return this;
    }

    public void setCvuconacyt(String cvuconacyt) {
        this.cvuconacyt = cvuconacyt;
    }

    public String getCedula() {
        return cedula;
    }

    public Medic_Information cedula(String cedula) {
        this.cedula = cedula;
        return this;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCedulaesp() {
        return cedulaesp;
    }

    public Medic_Information cedulaesp(String cedulaesp) {
        this.cedulaesp = cedulaesp;
        return this;
    }

    public void setCedulaesp(String cedulaesp) {
        this.cedulaesp = cedulaesp;
    }

    public String getCurp() {
        return curp;
    }

    public Medic_Information curp(String curp) {
        this.curp = curp;
        return this;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public Medic_Information rfc(String rfc) {
        this.rfc = rfc;
        return this;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Medic_Information birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getExternalhospitals() {
        return externalhospitals;
    }

    public Medic_Information externalhospitals(String externalhospitals) {
        this.externalhospitals = externalhospitals;
        return this;
    }

    public void setExternalhospitals(String externalhospitals) {
        this.externalhospitals = externalhospitals;
    }

    public Integer getSubid() {
        return subid;
    }

    public Medic_Information subid(Integer subid) {
        this.subid = subid;
        return this;
    }

    public void setSubid(Integer subid) {
        this.subid = subid;
    }

    public Integer getHighid() {
        return highid;
    }

    public Medic_Information highid(Integer highid) {
        this.highid = highid;
        return this;
    }

    public void setHighid(Integer highid) {
        this.highid = highid;
    }

    public Integer getMedicalinsurance() {
        return medicalinsurance;
    }

    public Medic_Information medicalinsurance(Integer medicalinsurance) {
        this.medicalinsurance = medicalinsurance;
        return this;
    }

    public void setMedicalinsurance(Integer medicalinsurance) {
        this.medicalinsurance = medicalinsurance;
    }

    public Integer getSocialinsurance() {
        return socialinsurance;
    }

    public Medic_Information socialinsurance(Integer socialinsurance) {
        this.socialinsurance = socialinsurance;
        return this;
    }

    public void setSocialinsurance(Integer socialinsurance) {
        this.socialinsurance = socialinsurance;
    }

    public String getResponsibilityinsurance() {
        return responsibilityinsurance;
    }

    public Medic_Information responsibilityinsurance(String responsibilityinsurance) {
        this.responsibilityinsurance = responsibilityinsurance;
        return this;
    }

    public void setResponsibilityinsurance(String responsibilityinsurance) {
        this.responsibilityinsurance = responsibilityinsurance;
    }

    public String getsINconacyt() {
        return sINconacyt;
    }

    public Medic_Information sINconacyt(String sINconacyt) {
        this.sINconacyt = sINconacyt;
        return this;
    }

    public void setsINconacyt(String sINconacyt) {
        this.sINconacyt = sINconacyt;
    }

    public String getMedicalinnumber() {
        return medicalinnumber;
    }

    public Medic_Information medicalinnumber(String medicalinnumber) {
        this.medicalinnumber = medicalinnumber;
        return this;
    }

    public void setMedicalinnumber(String medicalinnumber) {
        this.medicalinnumber = medicalinnumber;
    }

    public String getSocialinnumber() {
        return socialinnumber;
    }

    public Medic_Information socialinnumber(String socialinnumber) {
        this.socialinnumber = socialinnumber;
        return this;
    }

    public void setSocialinnumber(String socialinnumber) {
        this.socialinnumber = socialinnumber;
    }

    public String getResponsibilityinnumber() {
        return responsibilityinnumber;
    }

    public Medic_Information responsibilityinnumber(String responsibilityinnumber) {
        this.responsibilityinnumber = responsibilityinnumber;
        return this;
    }

    public void setResponsibilityinnumber(String responsibilityinnumber) {
        this.responsibilityinnumber = responsibilityinnumber;
    }

    public Medic getMedic() {
        return medic;
    }

    public Medic_Information medic(Medic medic) {
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
        Medic_Information medic_Information = (Medic_Information) o;
        if (medic_Information.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medic_Information.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medic_Information{" +
            "id=" + getId() +
            ", cp='" + getCp() + "'" +
            ", street='" + getStreet() + "'" +
            ", extnumber='" + getExtnumber() + "'" +
            ", intnumber='" + getIntnumber() + "'" +
            ", phone1='" + getPhone1() + "'" +
            ", phone2='" + getPhone2() + "'" +
            ", email1='" + getEmail1() + "'" +
            ", email2='" + getEmail2() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", twitter='" + getTwitter() + "'" +
            ", instagram='" + getInstagram() + "'" +
            ", snapchat='" + getSnapchat() + "'" +
            ", linkedin='" + getLinkedin() + "'" +
            ", vine='" + getVine() + "'" +
            ", recomended='" + getRecomended() + "'" +
            ", cvuconacyt='" + getCvuconacyt() + "'" +
            ", cedula='" + getCedula() + "'" +
            ", cedulaesp='" + getCedulaesp() + "'" +
            ", curp='" + getCurp() + "'" +
            ", rfc='" + getRfc() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", externalhospitals='" + getExternalhospitals() + "'" +
            ", subid='" + getSubid() + "'" +
            ", highid='" + getHighid() + "'" +
            ", medicalinsurance='" + getMedicalinsurance() + "'" +
            ", socialinsurance='" + getSocialinsurance() + "'" +
            ", responsibilityinsurance='" + getResponsibilityinsurance() + "'" +
            ", sINconacyt='" + getsINconacyt() + "'" +
            ", medicalinnumber='" + getMedicalinnumber() + "'" +
            ", socialinnumber='" + getSocialinnumber() + "'" +
            ", responsibilityinnumber='" + getResponsibilityinnumber() + "'" +
            "}";
    }
}
