package com.worknest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PlRespuestaBanco.
 */
@Entity
@Table(name = "pl_respuesta_banco")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlRespuestaBanco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "exitoso", nullable = false)
    private Boolean exitoso;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private ZonedDateTime fecha;

    @OneToOne
    @JoinColumn(unique = true)
    private PlIntentoPago intentopago;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isExitoso() {
        return exitoso;
    }

    public PlRespuestaBanco exitoso(Boolean exitoso) {
        this.exitoso = exitoso;
        return this;
    }

    public void setExitoso(Boolean exitoso) {
        this.exitoso = exitoso;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public PlRespuestaBanco fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public PlIntentoPago getIntentopago() {
        return intentopago;
    }

    public PlRespuestaBanco intentopago(PlIntentoPago plIntentoPago) {
        this.intentopago = plIntentoPago;
        return this;
    }

    public void setIntentopago(PlIntentoPago plIntentoPago) {
        this.intentopago = plIntentoPago;
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
        PlRespuestaBanco plRespuestaBanco = (PlRespuestaBanco) o;
        if (plRespuestaBanco.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plRespuestaBanco.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlRespuestaBanco{" +
            "id=" + getId() +
            ", exitoso='" + isExitoso() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
