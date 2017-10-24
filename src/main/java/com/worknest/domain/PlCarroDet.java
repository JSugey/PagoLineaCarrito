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
 * A PlCarroDet.
 */
@Entity
@Table(name = "pl_carro_det")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlCarroDet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

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

    @Size(max = 300)
    @Column(name = "concepto", length = 300)
    private String concepto;

    @NotNull
    @Column(name = "genero_us", nullable = false)
    private Boolean generoUs;

    @ManyToOne
    private PlCarro carro;

    public PlCarroDet(Long idLiquidacion, LocalDate fechaVigencia, BigDecimal importe, String llave, String concepto, Boolean generoUs, PlCarro carro) {
        this.idLiquidacion = idLiquidacion;
        this.fechaVigencia = fechaVigencia;
        this.importe = importe;
        this.llave = llave;
        this.concepto = concepto;
        this.generoUs = generoUs;
        this.carro = carro;
    }

    public PlCarroDet() {
    }


    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLiquidacion() {
        return idLiquidacion;
    }

    public PlCarroDet idLiquidacion(Long idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
        return this;
    }

    public void setIdLiquidacion(Long idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
    }

    public LocalDate getFechaVigencia() {
        return fechaVigencia;
    }

    public PlCarroDet fechaVigencia(LocalDate fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
        return this;
    }

    public void setFechaVigencia(LocalDate fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public PlCarroDet importe(BigDecimal importe) {
        this.importe = importe;
        return this;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getLlave() {
        return llave;
    }

    public PlCarroDet llave(String llave) {
        this.llave = llave;
        return this;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getConcepto() {
        return concepto;
    }

    public PlCarroDet concepto(String concepto) {
        this.concepto = concepto;
        return this;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Boolean isGeneroUs() {
        return generoUs;
    }

    public PlCarroDet generoUs(Boolean generoUs) {
        this.generoUs = generoUs;
        return this;
    }

    public void setGeneroUs(Boolean generoUs) {
        this.generoUs = generoUs;
    }

    public PlCarro getCarro() {
        return carro;
    }

    public PlCarroDet carro(PlCarro plCarro) {
        this.carro = plCarro;
        return this;
    }

    public void setCarro(PlCarro plCarro) {
        this.carro = plCarro;
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
        PlCarroDet plCarroDet = (PlCarroDet) o;
        if (plCarroDet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plCarroDet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlCarroDet{" +
            "id=" + getId() +
            ", idLiquidacion='" + getIdLiquidacion() + "'" +
            ", fechaVigencia='" + getFechaVigencia() + "'" +
            ", importe='" + getImporte() + "'" +
            ", llave='" + getLlave() + "'" +
            ", concepto='" + getConcepto() + "'" +
            ", generoUs='" + isGeneroUs() + "'" +
            "}";
    }
}
