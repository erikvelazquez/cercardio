package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PacientContact.
 */
@Entity
@Table(name = "pacient_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pacientcontact")
public class PacientContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

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

    public String getName() {
        return name;
    }

    public PacientContact name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public PacientContact lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStreet() {
        return street;
    }

    public PacientContact street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public PacientContact streetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
        return this;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public String getSuitnumber() {
        return suitnumber;
    }

    public PacientContact suitnumber(String suitnumber) {
        this.suitnumber = suitnumber;
        return this;
    }

    public void setSuitnumber(String suitnumber) {
        this.suitnumber = suitnumber;
    }

    public Long getPhonenumber1() {
        return phonenumber1;
    }

    public PacientContact phonenumber1(Long phonenumber1) {
        this.phonenumber1 = phonenumber1;
        return this;
    }

    public void setPhonenumber1(Long phonenumber1) {
        this.phonenumber1 = phonenumber1;
    }

    public Long getPhonenumber2() {
        return phonenumber2;
    }

    public PacientContact phonenumber2(Long phonenumber2) {
        this.phonenumber2 = phonenumber2;
        return this;
    }

    public void setPhonenumber2(Long phonenumber2) {
        this.phonenumber2 = phonenumber2;
    }

    public String getEmail1() {
        return email1;
    }

    public PacientContact email1(String email1) {
        this.email1 = email1;
        return this;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public PacientContact email2(String email2) {
        this.email2 = email2;
        return this;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getFacebook() {
        return facebook;
    }

    public PacientContact facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public PacientContact twitter(String twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public PacientContact instagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getSnapchat() {
        return snapchat;
    }

    public PacientContact snapchat(String snapchat) {
        this.snapchat = snapchat;
        return this;
    }

    public void setSnapchat(String snapchat) {
        this.snapchat = snapchat;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public PacientContact linkedin(String linkedin) {
        this.linkedin = linkedin;
        return this;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getVine() {
        return vine;
    }

    public PacientContact vine(String vine) {
        this.vine = vine;
        return this;
    }

    public void setVine(String vine) {
        this.vine = vine;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public PacientContact pacient(Pacient pacient) {
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
        PacientContact pacientContact = (PacientContact) o;
        if (pacientContact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pacientContact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PacientContact{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastname='" + getLastname() + "'" +
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
            "}";
    }
}
