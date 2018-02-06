package mycercardiopackege.jh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Diagnosis.
 */
@Entity
@Table(name = "diagnosis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "diagnosis")
public class Diagnosis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "letra")
    private String letra;

    @Column(name = "catalogkey")
    private String catalogkey;

    @Column(name = "asterisco")
    private String asterisco;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "lsex")
    private String lsex;

    @Column(name = "linf")
    private String linf;

    @Column(name = "lsup")
    private String lsup;

    @Column(name = "trivial")
    private String trivial;

    @Column(name = "erradicado")
    private String erradicado;

    @Column(name = "ninter")
    private String ninter;

    @Column(name = "nin")
    private String nin;

    @Column(name = "ninmtobs")
    private String ninmtobs;

    @Column(name = "nocbd")
    private String nocbd;

    @Column(name = "noaph")
    private String noaph;

    @Column(name = "fetal")
    private String fetal;

    @Column(name = "capitulo")
    private Integer capitulo;

    @Column(name = "lista_1")
    private Integer lista1;

    @Column(name = "grupo_1")
    private Integer grupo1;

    @Column(name = "lista_5")
    private Integer lista5;

    @Column(name = "actualizacionescie_10")
    private String actualizacionescie10;

    @Column(name = "yearmodifi")
    private String yearmodifi;

    @Column(name = "yearaplicacion")
    private String yearaplicacion;

    @Column(name = "valid")
    private String valid;

    @Column(name = "prinmorta")
    private Integer prinmorta;

    @Column(name = "prinmorbi")
    private Integer prinmorbi;

    @Column(name = "lmmorbi")
    private String lmmorbi;

    @Column(name = "lmmorta")
    private String lmmorta;

    @Column(name = "lgbd_165")
    private String lgbd165;

    @Column(name = "iomsbeck")
    private Integer iomsbeck;

    @Column(name = "lgbd_190")
    private String lgbd190;

    @Column(name = "notdiaria")
    private String notdiaria;

    @Column(name = "notsemanal")
    private String notsemanal;

    @Column(name = "sistemaespecial")
    private String sistemaespecial;

    @Column(name = "birmm")
    private String birmm;

    @Column(name = "causatype")
    private Integer causatype;

    @Column(name = "epimorta")
    private String epimorta;

    @Column(name = "edasiras_5_anios")
    private String edasiras5Anios;

    @Column(name = "csvematernasseedepid")
    private String csvematernasseedepid;

    @Column(name = "epimortam_5")
    private String epimortam5;

    @Column(name = "epimorbi")
    private String epimorbi;

    @Column(name = "defmaternas")
    private Integer defmaternas;

    @Column(name = "escauses")
    private String escauses;

    @Column(name = "numcauses")
    private String numcauses;

    @Column(name = "essuivemorta")
    private String essuivemorta;

    @Column(name = "essuivemorb")
    private String essuivemorb;

    @Column(name = "epiclave")
    private String epiclave;

    @Column(name = "epiclavedesc")
    private String epiclavedesc;

    @Column(name = "essuivenotin")
    private String essuivenotin;

    @Column(name = "essuiveestepi")
    private String essuiveestepi;

    @Column(name = "essuiveestbrote")
    private String essuiveestbrote;

    @Column(name = "sinac")
    private String sinac;

    @Column(name = "daga")
    private String daga;

    @Column(name = "manifestaenfer")
    private String manifestaenfer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLetra() {
        return letra;
    }

    public Diagnosis letra(String letra) {
        this.letra = letra;
        return this;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getCatalogkey() {
        return catalogkey;
    }

    public Diagnosis catalogkey(String catalogkey) {
        this.catalogkey = catalogkey;
        return this;
    }

    public void setCatalogkey(String catalogkey) {
        this.catalogkey = catalogkey;
    }

    public String getAsterisco() {
        return asterisco;
    }

    public Diagnosis asterisco(String asterisco) {
        this.asterisco = asterisco;
        return this;
    }

    public void setAsterisco(String asterisco) {
        this.asterisco = asterisco;
    }

    public String getNombre() {
        return nombre;
    }

    public Diagnosis nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLsex() {
        return lsex;
    }

    public Diagnosis lsex(String lsex) {
        this.lsex = lsex;
        return this;
    }

    public void setLsex(String lsex) {
        this.lsex = lsex;
    }

    public String getLinf() {
        return linf;
    }

    public Diagnosis linf(String linf) {
        this.linf = linf;
        return this;
    }

    public void setLinf(String linf) {
        this.linf = linf;
    }

    public String getLsup() {
        return lsup;
    }

    public Diagnosis lsup(String lsup) {
        this.lsup = lsup;
        return this;
    }

    public void setLsup(String lsup) {
        this.lsup = lsup;
    }

    public String getTrivial() {
        return trivial;
    }

    public Diagnosis trivial(String trivial) {
        this.trivial = trivial;
        return this;
    }

    public void setTrivial(String trivial) {
        this.trivial = trivial;
    }

    public String getErradicado() {
        return erradicado;
    }

    public Diagnosis erradicado(String erradicado) {
        this.erradicado = erradicado;
        return this;
    }

    public void setErradicado(String erradicado) {
        this.erradicado = erradicado;
    }

    public String getNinter() {
        return ninter;
    }

    public Diagnosis ninter(String ninter) {
        this.ninter = ninter;
        return this;
    }

    public void setNinter(String ninter) {
        this.ninter = ninter;
    }

    public String getNin() {
        return nin;
    }

    public Diagnosis nin(String nin) {
        this.nin = nin;
        return this;
    }

    public void setNin(String nin) {
        this.nin = nin;
    }

    public String getNinmtobs() {
        return ninmtobs;
    }

    public Diagnosis ninmtobs(String ninmtobs) {
        this.ninmtobs = ninmtobs;
        return this;
    }

    public void setNinmtobs(String ninmtobs) {
        this.ninmtobs = ninmtobs;
    }

    public String getNocbd() {
        return nocbd;
    }

    public Diagnosis nocbd(String nocbd) {
        this.nocbd = nocbd;
        return this;
    }

    public void setNocbd(String nocbd) {
        this.nocbd = nocbd;
    }

    public String getNoaph() {
        return noaph;
    }

    public Diagnosis noaph(String noaph) {
        this.noaph = noaph;
        return this;
    }

    public void setNoaph(String noaph) {
        this.noaph = noaph;
    }

    public String getFetal() {
        return fetal;
    }

    public Diagnosis fetal(String fetal) {
        this.fetal = fetal;
        return this;
    }

    public void setFetal(String fetal) {
        this.fetal = fetal;
    }

    public Integer getCapitulo() {
        return capitulo;
    }

    public Diagnosis capitulo(Integer capitulo) {
        this.capitulo = capitulo;
        return this;
    }

    public void setCapitulo(Integer capitulo) {
        this.capitulo = capitulo;
    }

    public Integer getLista1() {
        return lista1;
    }

    public Diagnosis lista1(Integer lista1) {
        this.lista1 = lista1;
        return this;
    }

    public void setLista1(Integer lista1) {
        this.lista1 = lista1;
    }

    public Integer getGrupo1() {
        return grupo1;
    }

    public Diagnosis grupo1(Integer grupo1) {
        this.grupo1 = grupo1;
        return this;
    }

    public void setGrupo1(Integer grupo1) {
        this.grupo1 = grupo1;
    }

    public Integer getLista5() {
        return lista5;
    }

    public Diagnosis lista5(Integer lista5) {
        this.lista5 = lista5;
        return this;
    }

    public void setLista5(Integer lista5) {
        this.lista5 = lista5;
    }

    public String getActualizacionescie10() {
        return actualizacionescie10;
    }

    public Diagnosis actualizacionescie10(String actualizacionescie10) {
        this.actualizacionescie10 = actualizacionescie10;
        return this;
    }

    public void setActualizacionescie10(String actualizacionescie10) {
        this.actualizacionescie10 = actualizacionescie10;
    }

    public String getYearmodifi() {
        return yearmodifi;
    }

    public Diagnosis yearmodifi(String yearmodifi) {
        this.yearmodifi = yearmodifi;
        return this;
    }

    public void setYearmodifi(String yearmodifi) {
        this.yearmodifi = yearmodifi;
    }

    public String getYearaplicacion() {
        return yearaplicacion;
    }

    public Diagnosis yearaplicacion(String yearaplicacion) {
        this.yearaplicacion = yearaplicacion;
        return this;
    }

    public void setYearaplicacion(String yearaplicacion) {
        this.yearaplicacion = yearaplicacion;
    }

    public String getValid() {
        return valid;
    }

    public Diagnosis valid(String valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public Integer getPrinmorta() {
        return prinmorta;
    }

    public Diagnosis prinmorta(Integer prinmorta) {
        this.prinmorta = prinmorta;
        return this;
    }

    public void setPrinmorta(Integer prinmorta) {
        this.prinmorta = prinmorta;
    }

    public Integer getPrinmorbi() {
        return prinmorbi;
    }

    public Diagnosis prinmorbi(Integer prinmorbi) {
        this.prinmorbi = prinmorbi;
        return this;
    }

    public void setPrinmorbi(Integer prinmorbi) {
        this.prinmorbi = prinmorbi;
    }

    public String getLmmorbi() {
        return lmmorbi;
    }

    public Diagnosis lmmorbi(String lmmorbi) {
        this.lmmorbi = lmmorbi;
        return this;
    }

    public void setLmmorbi(String lmmorbi) {
        this.lmmorbi = lmmorbi;
    }

    public String getLmmorta() {
        return lmmorta;
    }

    public Diagnosis lmmorta(String lmmorta) {
        this.lmmorta = lmmorta;
        return this;
    }

    public void setLmmorta(String lmmorta) {
        this.lmmorta = lmmorta;
    }

    public String getLgbd165() {
        return lgbd165;
    }

    public Diagnosis lgbd165(String lgbd165) {
        this.lgbd165 = lgbd165;
        return this;
    }

    public void setLgbd165(String lgbd165) {
        this.lgbd165 = lgbd165;
    }

    public Integer getIomsbeck() {
        return iomsbeck;
    }

    public Diagnosis iomsbeck(Integer iomsbeck) {
        this.iomsbeck = iomsbeck;
        return this;
    }

    public void setIomsbeck(Integer iomsbeck) {
        this.iomsbeck = iomsbeck;
    }

    public String getLgbd190() {
        return lgbd190;
    }

    public Diagnosis lgbd190(String lgbd190) {
        this.lgbd190 = lgbd190;
        return this;
    }

    public void setLgbd190(String lgbd190) {
        this.lgbd190 = lgbd190;
    }

    public String getNotdiaria() {
        return notdiaria;
    }

    public Diagnosis notdiaria(String notdiaria) {
        this.notdiaria = notdiaria;
        return this;
    }

    public void setNotdiaria(String notdiaria) {
        this.notdiaria = notdiaria;
    }

    public String getNotsemanal() {
        return notsemanal;
    }

    public Diagnosis notsemanal(String notsemanal) {
        this.notsemanal = notsemanal;
        return this;
    }

    public void setNotsemanal(String notsemanal) {
        this.notsemanal = notsemanal;
    }

    public String getSistemaespecial() {
        return sistemaespecial;
    }

    public Diagnosis sistemaespecial(String sistemaespecial) {
        this.sistemaespecial = sistemaespecial;
        return this;
    }

    public void setSistemaespecial(String sistemaespecial) {
        this.sistemaespecial = sistemaespecial;
    }

    public String getBirmm() {
        return birmm;
    }

    public Diagnosis birmm(String birmm) {
        this.birmm = birmm;
        return this;
    }

    public void setBirmm(String birmm) {
        this.birmm = birmm;
    }

    public Integer getCausatype() {
        return causatype;
    }

    public Diagnosis causatype(Integer causatype) {
        this.causatype = causatype;
        return this;
    }

    public void setCausatype(Integer causatype) {
        this.causatype = causatype;
    }

    public String getEpimorta() {
        return epimorta;
    }

    public Diagnosis epimorta(String epimorta) {
        this.epimorta = epimorta;
        return this;
    }

    public void setEpimorta(String epimorta) {
        this.epimorta = epimorta;
    }

    public String getEdasiras5Anios() {
        return edasiras5Anios;
    }

    public Diagnosis edasiras5Anios(String edasiras5Anios) {
        this.edasiras5Anios = edasiras5Anios;
        return this;
    }

    public void setEdasiras5Anios(String edasiras5Anios) {
        this.edasiras5Anios = edasiras5Anios;
    }

    public String getCsvematernasseedepid() {
        return csvematernasseedepid;
    }

    public Diagnosis csvematernasseedepid(String csvematernasseedepid) {
        this.csvematernasseedepid = csvematernasseedepid;
        return this;
    }

    public void setCsvematernasseedepid(String csvematernasseedepid) {
        this.csvematernasseedepid = csvematernasseedepid;
    }

    public String getEpimortam5() {
        return epimortam5;
    }

    public Diagnosis epimortam5(String epimortam5) {
        this.epimortam5 = epimortam5;
        return this;
    }

    public void setEpimortam5(String epimortam5) {
        this.epimortam5 = epimortam5;
    }

    public String getEpimorbi() {
        return epimorbi;
    }

    public Diagnosis epimorbi(String epimorbi) {
        this.epimorbi = epimorbi;
        return this;
    }

    public void setEpimorbi(String epimorbi) {
        this.epimorbi = epimorbi;
    }

    public Integer getDefmaternas() {
        return defmaternas;
    }

    public Diagnosis defmaternas(Integer defmaternas) {
        this.defmaternas = defmaternas;
        return this;
    }

    public void setDefmaternas(Integer defmaternas) {
        this.defmaternas = defmaternas;
    }

    public String getEscauses() {
        return escauses;
    }

    public Diagnosis escauses(String escauses) {
        this.escauses = escauses;
        return this;
    }

    public void setEscauses(String escauses) {
        this.escauses = escauses;
    }

    public String getNumcauses() {
        return numcauses;
    }

    public Diagnosis numcauses(String numcauses) {
        this.numcauses = numcauses;
        return this;
    }

    public void setNumcauses(String numcauses) {
        this.numcauses = numcauses;
    }

    public String getEssuivemorta() {
        return essuivemorta;
    }

    public Diagnosis essuivemorta(String essuivemorta) {
        this.essuivemorta = essuivemorta;
        return this;
    }

    public void setEssuivemorta(String essuivemorta) {
        this.essuivemorta = essuivemorta;
    }

    public String getEssuivemorb() {
        return essuivemorb;
    }

    public Diagnosis essuivemorb(String essuivemorb) {
        this.essuivemorb = essuivemorb;
        return this;
    }

    public void setEssuivemorb(String essuivemorb) {
        this.essuivemorb = essuivemorb;
    }

    public String getEpiclave() {
        return epiclave;
    }

    public Diagnosis epiclave(String epiclave) {
        this.epiclave = epiclave;
        return this;
    }

    public void setEpiclave(String epiclave) {
        this.epiclave = epiclave;
    }

    public String getEpiclavedesc() {
        return epiclavedesc;
    }

    public Diagnosis epiclavedesc(String epiclavedesc) {
        this.epiclavedesc = epiclavedesc;
        return this;
    }

    public void setEpiclavedesc(String epiclavedesc) {
        this.epiclavedesc = epiclavedesc;
    }

    public String getEssuivenotin() {
        return essuivenotin;
    }

    public Diagnosis essuivenotin(String essuivenotin) {
        this.essuivenotin = essuivenotin;
        return this;
    }

    public void setEssuivenotin(String essuivenotin) {
        this.essuivenotin = essuivenotin;
    }

    public String getEssuiveestepi() {
        return essuiveestepi;
    }

    public Diagnosis essuiveestepi(String essuiveestepi) {
        this.essuiveestepi = essuiveestepi;
        return this;
    }

    public void setEssuiveestepi(String essuiveestepi) {
        this.essuiveestepi = essuiveestepi;
    }

    public String getEssuiveestbrote() {
        return essuiveestbrote;
    }

    public Diagnosis essuiveestbrote(String essuiveestbrote) {
        this.essuiveestbrote = essuiveestbrote;
        return this;
    }

    public void setEssuiveestbrote(String essuiveestbrote) {
        this.essuiveestbrote = essuiveestbrote;
    }

    public String getSinac() {
        return sinac;
    }

    public Diagnosis sinac(String sinac) {
        this.sinac = sinac;
        return this;
    }

    public void setSinac(String sinac) {
        this.sinac = sinac;
    }

    public String getDaga() {
        return daga;
    }

    public Diagnosis daga(String daga) {
        this.daga = daga;
        return this;
    }

    public void setDaga(String daga) {
        this.daga = daga;
    }

    public String getManifestaenfer() {
        return manifestaenfer;
    }

    public Diagnosis manifestaenfer(String manifestaenfer) {
        this.manifestaenfer = manifestaenfer;
        return this;
    }

    public void setManifestaenfer(String manifestaenfer) {
        this.manifestaenfer = manifestaenfer;
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
        Diagnosis diagnosis = (Diagnosis) o;
        if (diagnosis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diagnosis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
            "id=" + getId() +
            ", letra='" + getLetra() + "'" +
            ", catalogkey='" + getCatalogkey() + "'" +
            ", asterisco='" + getAsterisco() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", lsex='" + getLsex() + "'" +
            ", linf='" + getLinf() + "'" +
            ", lsup='" + getLsup() + "'" +
            ", trivial='" + getTrivial() + "'" +
            ", erradicado='" + getErradicado() + "'" +
            ", ninter='" + getNinter() + "'" +
            ", nin='" + getNin() + "'" +
            ", ninmtobs='" + getNinmtobs() + "'" +
            ", nocbd='" + getNocbd() + "'" +
            ", noaph='" + getNoaph() + "'" +
            ", fetal='" + getFetal() + "'" +
            ", capitulo='" + getCapitulo() + "'" +
            ", lista1='" + getLista1() + "'" +
            ", grupo1='" + getGrupo1() + "'" +
            ", lista5='" + getLista5() + "'" +
            ", actualizacionescie10='" + getActualizacionescie10() + "'" +
            ", yearmodifi='" + getYearmodifi() + "'" +
            ", yearaplicacion='" + getYearaplicacion() + "'" +
            ", valid='" + getValid() + "'" +
            ", prinmorta='" + getPrinmorta() + "'" +
            ", prinmorbi='" + getPrinmorbi() + "'" +
            ", lmmorbi='" + getLmmorbi() + "'" +
            ", lmmorta='" + getLmmorta() + "'" +
            ", lgbd165='" + getLgbd165() + "'" +
            ", iomsbeck='" + getIomsbeck() + "'" +
            ", lgbd190='" + getLgbd190() + "'" +
            ", notdiaria='" + getNotdiaria() + "'" +
            ", notsemanal='" + getNotsemanal() + "'" +
            ", sistemaespecial='" + getSistemaespecial() + "'" +
            ", birmm='" + getBirmm() + "'" +
            ", causatype='" + getCausatype() + "'" +
            ", epimorta='" + getEpimorta() + "'" +
            ", edasiras5Anios='" + getEdasiras5Anios() + "'" +
            ", csvematernasseedepid='" + getCsvematernasseedepid() + "'" +
            ", epimortam5='" + getEpimortam5() + "'" +
            ", epimorbi='" + getEpimorbi() + "'" +
            ", defmaternas='" + getDefmaternas() + "'" +
            ", escauses='" + getEscauses() + "'" +
            ", numcauses='" + getNumcauses() + "'" +
            ", essuivemorta='" + getEssuivemorta() + "'" +
            ", essuivemorb='" + getEssuivemorb() + "'" +
            ", epiclave='" + getEpiclave() + "'" +
            ", epiclavedesc='" + getEpiclavedesc() + "'" +
            ", essuivenotin='" + getEssuivenotin() + "'" +
            ", essuiveestepi='" + getEssuiveestepi() + "'" +
            ", essuiveestbrote='" + getEssuiveestbrote() + "'" +
            ", sinac='" + getSinac() + "'" +
            ", daga='" + getDaga() + "'" +
            ", manifestaenfer='" + getManifestaenfer() + "'" +
            "}";
    }
}
