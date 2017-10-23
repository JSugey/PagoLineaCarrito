package com.worknest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PlCarroHist.
 */
@Entity
@Table(name = "pl_carro_hist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlCarroHist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fecha_envio", nullable = false)
    private ZonedDateTime fechaEnvio;

    @NotNull
    @Size(max = 250)
    @Column(name = "referencia", length = 250, nullable = false)
    private String referencia;

    @ManyToOne
    private PlCarro carro;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public PlCarroHist fechaEnvio(ZonedDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
        return this;
    }

    public void setFechaEnvio(ZonedDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getReferencia() {
        return referencia;
    }

    public PlCarroHist referencia(String referencia) {
        this.referencia = referencia;
        return this;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public PlCarro getCarro() {
        return carro;
    }

    public PlCarroHist carro(PlCarro plCarro) {
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
        PlCarroHist plCarroHist = (PlCarroHist) o;
        if (plCarroHist.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plCarroHist.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlCarroHist{" +
            "id=" + getId() +
            ", fechaEnvio='" + getFechaEnvio() + "'" +
            ", referencia='" + getReferencia() + "'" +
            "}";
    }
}
