package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Way_of_Administration.
 */
@Entity
@Table(name = "way_of_administration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "way_of_administration")
public class Way_of_Administration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "wayofadministration")
    private String wayofadministration;

    @Column(name = "isactive")
    private Boolean isactive;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWayofadministration() {
        return wayofadministration;
    }

    public Way_of_Administration wayofadministration(String wayofadministration) {
        this.wayofadministration = wayofadministration;
        return this;
    }

    public void setWayofadministration(String wayofadministration) {
        this.wayofadministration = wayofadministration;
    }

    public Boolean isIsactive() {
        return isactive;
    }

    public Way_of_Administration isactive(Boolean isactive) {
        this.isactive = isactive;
        return this;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
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
        Way_of_Administration way_of_Administration = (Way_of_Administration) o;
        if (way_of_Administration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), way_of_Administration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Way_of_Administration{" +
            "id=" + getId() +
            ", wayofadministration='" + getWayofadministration() + "'" +
            ", isactive='" + isIsactive() + "'" +
            "}";
    }
}
