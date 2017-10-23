package com.worknest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.worknest.domain.enumeration.StatusIntentoPago;

/**
 * A PlIntentoPago.
 */
@Entity
@Table(name = "pl_intento_pago")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlIntentoPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private ZonedDateTime fecha;

    @Column(name = "enviado")
    private Boolean enviado;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusIntentoPago status;

    @Size(max = 30)
    @Column(name = "auth", length = 30)
    private String auth;

    @ManyToOne
    private PlCarroHist historialcarro;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public PlIntentoPago fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean isEnviado() {
        return enviado;
    }

    public PlIntentoPago enviado(Boolean enviado) {
        this.enviado = enviado;
        return this;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public StatusIntentoPago getStatus() {
        return status;
    }

    public PlIntentoPago status(StatusIntentoPago status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusIntentoPago status) {
        this.status = status;
    }

    public String getAuth() {
        return auth;
    }

    public PlIntentoPago auth(String auth) {
        this.auth = auth;
        return this;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public PlCarroHist getHistorialcarro() {
        return historialcarro;
    }

    public PlIntentoPago historialcarro(PlCarroHist plCarroHist) {
        this.historialcarro = plCarroHist;
        return this;
    }

    public void setHistorialcarro(PlCarroHist plCarroHist) {
        this.historialcarro = plCarroHist;
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
        PlIntentoPago plIntentoPago = (PlIntentoPago) o;
        if (plIntentoPago.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plIntentoPago.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlIntentoPago{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", enviado='" + isEnviado() + "'" +
            ", status='" + getStatus() + "'" +
            ", auth='" + getAuth() + "'" +
            "}";
    }
}
