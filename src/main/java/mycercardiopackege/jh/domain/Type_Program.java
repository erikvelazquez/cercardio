package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Type_Program.
 */
@Entity
@Table(name = "type_program")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "type_program")
public class Type_Program implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "isactive")
    private Boolean isactive;

    @ManyToOne
    private Programs program;

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

    public Type_Program name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsactive() {
        return isactive;
    }

    public Type_Program isactive(Boolean isactive) {
        this.isactive = isactive;
        return this;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Programs getProgram() {
        return program;
    }

    public Type_Program program(Programs programs) {
        this.program = programs;
        return this;
    }

    public void setProgram(Programs programs) {
        this.program = programs;
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
        Type_Program type_Program = (Type_Program) o;
        if (type_Program.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), type_Program.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Type_Program{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isactive='" + isIsactive() + "'" +
            "}";
    }
}
