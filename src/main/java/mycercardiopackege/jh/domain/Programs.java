package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Programs.
 */
@Entity
@Table(name = "programs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "programs")
public class Programs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @Column(name = "icon")
    private String icon;

    @Column(name = "programparentid")
    private Integer programparentid;

    @Column(name = "byorder")
    private Integer byorder;

    @Column(name = "isactive")
    private Boolean isactive;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Programs description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public Programs url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public Programs icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getProgramparentid() {
        return programparentid;
    }

    public Programs programparentid(Integer programparentid) {
        this.programparentid = programparentid;
        return this;
    }

    public void setProgramparentid(Integer programparentid) {
        this.programparentid = programparentid;
    }

    public Integer getByorder() {
        return byorder;
    }

    public Programs byorder(Integer byorder) {
        this.byorder = byorder;
        return this;
    }

    public void setByorder(Integer byorder) {
        this.byorder = byorder;
    }

    public Boolean isIsactive() {
        return isactive;
    }

    public Programs isactive(Boolean isactive) {
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
        Programs programs = (Programs) o;
        if (programs.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), programs.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Programs{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", url='" + getUrl() + "'" +
            ", icon='" + getIcon() + "'" +
            ", programparentid='" + getProgramparentid() + "'" +
            ", byorder='" + getByorder() + "'" +
            ", isactive='" + isIsactive() + "'" +
            "}";
    }
}
