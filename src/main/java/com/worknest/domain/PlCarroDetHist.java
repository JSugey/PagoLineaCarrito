package com.worknest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PlCarroDetHist.
 */
@Entity
@Table(name = "pl_carro_det_hist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlCarroDetHist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 300)
    @Column(name = "concepto", length = 300)
    private String concepto;

    @NotNull
    @Column(name = "id_liquidacion", nullable = false)
    private Long idLiquidacion;

    @NotNull
    @Column(name = "fecha_vigencia", nullable = false)
    private LocalDate fechaVigencia;

    @NotNull
    @Column(name = "importe", precision=10, scale=2, nullable = false)
    private BigDecimal importe;

    @NotNull
    @Size(max = 31)
    @Column(name = "llave", length = 31, nullable = false)
    private String llave;

    @NotNull
    @Column(name = "genero_us", nullable = false)
    private Boolean generoUs;

    @ManyToOne
    private PlCarroHist carro;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public PlCarroDetHist concepto(String concepto) {
        this.concepto = concepto;
        return this;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Long getIdLiquidacion() {
        return idLiquidacion;
    }

    public PlCarroDetHist idLiquidacion(Long idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
        return this;
    }

    public void setIdLiquidacion(Long idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
    }

    public LocalDate getFechaVigencia() {
        return fechaVigencia;
    }

    public PlCarroDetHist fechaVigencia(LocalDate fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
        return this;
    }

    public void setFechaVigencia(LocalDate fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public PlCarroDetHist importe(BigDecimal importe) {
        this.importe = importe;
        return this;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getLlave() {
        return llave;
    }

    public PlCarroDetHist llave(String llave) {
        this.llave = llave;
        return this;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public Boolean isGeneroUs() {
        return generoUs;
    }

    public PlCarroDetHist generoUs(Boolean generoUs) {
        this.generoUs = generoUs;
        return this;
    }

    public void setGeneroUs(Boolean generoUs) {
        this.generoUs = generoUs;
    }

    public PlCarroHist getCarro() {
        return carro;
    }

    public PlCarroDetHist carro(PlCarroHist plCarroHist) {
        this.carro = plCarroHist;
        return this;
    }

    public void setCarro(PlCarroHist plCarroHist) {
        this.carro = plCarroHist;
    }

    public PlCarroDetHist() {
    }

    public PlCarroDetHist(String concepto, Long idLiquidacion, LocalDate fechaVigencia, BigDecimal importe, String llave, Boolean generoUs, PlCarroHist carro) {
        this.concepto = concepto;
        this.idLiquidacion = idLiquidacion;
        this.fechaVigencia = fechaVigencia;
        this.importe = importe;
        this.llave = llave;
        this.generoUs = generoUs;
        this.carro = carro;
    }    
    
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlCarroDetHist plCarroDetHist = (PlCarroDetHist) o;
        if (plCarroDetHist.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plCarroDetHist.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlCarroDetHist{" +
            "id=" + getId() +
            ", concepto='" + getConcepto() + "'" +
            ", idLiquidacion='" + getIdLiquidacion() + "'" +
            ", fechaVigencia='" + getFechaVigencia() + "'" +
            ", importe='" + getImporte() + "'" +
            ", llave='" + getLlave() + "'" +
            ", generoUs='" + isGeneroUs() + "'" +
            "}";
    }
}
