package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Medic.
 */
@Entity
@Table(name = "medic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "medic")
public class Medic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "colony")
    private String colony;

    @Column(name = "street")
    private String street;

    @Column(name = "streetnumber")
    private String streetnumber;

    @Column(name = "suitnumber")
    private String suitnumber;

    @Column(name = "phonenumber_1")
    private Long phonenumber1;

    @Column(name = "phonenumber_2")
    private Long phonenumber2;

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

    @Column(name = "createdat")
    private ZonedDateTime createdat;

    @OneToOne
    @JoinColumn(unique = true)
    private UserBD userBD;

    @ManyToOne
    private Company company;

    @ManyToOne
    private EthnicGroup ethnicGroup;

    @ManyToOne
    private Gender gender;

    @ManyToOne
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColony() {
        return colony;
    }

    public Medic colony(String colony) {
        this.colony = colony;
        return this;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getStreet() {
        return street;
    }

    public Medic street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public Medic streetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
        return this;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public String getSuitnumber() {
        return suitnumber;
    }

    public Medic suitnumber(String suitnumber) {
        this.suitnumber = suitnumber;
        return this;
    }

    public void setSuitnumber(String suitnumber) {
        this.suitnumber = suitnumber;
    }

    public Long getPhonenumber1() {
        return phonenumber1;
    }

    public Medic phonenumber1(Long phonenumber1) {
        this.phonenumber1 = phonenumber1;
        return this;
    }

    public void setPhonenumber1(Long phonenumber1) {
        this.phonenumber1 = phonenumber1;
    }

    public Long getPhonenumber2() {
        return phonenumber2;
    }

    public Medic phonenumber2(Long phonenumber2) {
        this.phonenumber2 = phonenumber2;
        return this;
    }

    public void setPhonenumber2(Long phonenumber2) {
        this.phonenumber2 = phonenumber2;
    }

    public String getEmail1() {
        return email1;
    }

    public Medic email1(String email1) {
        this.email1 = email1;
        return this;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public Medic email2(String email2) {
        this.email2 = email2;
        return this;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getFacebook() {
        return facebook;
    }

    public Medic facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public Medic twitter(String twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public Medic instagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getSnapchat() {
        return snapchat;
    }

    public Medic snapchat(String snapchat) {
        this.snapchat = snapchat;
        return this;
    }

    public void setSnapchat(String snapchat) {
        this.snapchat = snapchat;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public Medic linkedin(String linkedin) {
        this.linkedin = linkedin;
        return this;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getVine() {
        return vine;
    }

    public Medic vine(String vine) {
        this.vine = vine;
        return this;
    }

    public void setVine(String vine) {
        this.vine = vine;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public Medic createdat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
    }

    public UserBD getUserBD() {
        return userBD;
    }

    public Medic userBD(UserBD userBD) {
        this.userBD = userBD;
        return this;
    }

    public void setUserBD(UserBD userBD) {
        this.userBD = userBD;
    }

    public Company getCompany() {
        return company;
    }

    public Medic company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public EthnicGroup getEthnicGroup() {
        return ethnicGroup;
    }

    public Medic ethnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
        return this;
    }

    public void setEthnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public Gender getGender() {
        return gender;
    }

    public Medic gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Category getCategory() {
        return category;
    }

    public Medic category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        Medic medic = (Medic) o;
        if (medic.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medic{" +
            "id=" + getId() +
            ", colony='" + getColony() + "'" +
            ", street='" + getStreet() + "'" +
            ", streetnumber='" + getStreetnumber() + "'" +
            ", suitnumber='" + getSuitnumber() + "'" +
            ", phonenumber1='" + getPhonenumber1() + "'" +
            ", phonenumber2='" + getPhonenumber2() + "'" +
            ", email1='" + getEmail1() + "'" +
            ", email2='" + getEmail2() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", twitter='" + getTwitter() + "'" +
            ", instagram='" + getInstagram() + "'" +
            ", snapchat='" + getSnapchat() + "'" +
            ", linkedin='" + getLinkedin() + "'" +
            ", vine='" + getVine() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            "}";
    }
}
