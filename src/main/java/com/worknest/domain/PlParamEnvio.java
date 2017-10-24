package com.worknest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PlParamEnvio.
 */
@Entity
@Table(name = "pl_param_envio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlParamEnvio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "valor", length = 100, nullable = false)
    private String valor;

    @ManyToOne
    private PlParamBanco plParamBanco;

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

    public String getValor() {
        return valor;
    }

    public PlParamEnvio valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public PlParamBanco getPlParamBanco() {
        return plParamBanco;
    }

    public PlParamEnvio plParamBanco(PlParamBanco plParamBanco) {
        this.plParamBanco = plParamBanco;
        return this;
    }

    public void setPlParamBanco(PlParamBanco plParamBanco) {
        this.plParamBanco = plParamBanco;
    }

    public PlIntentoPago getIntentopago() {
        return intentopago;
    }

    public PlParamEnvio intentopago(PlIntentoPago plIntentoPago) {
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
        PlParamEnvio plParamEnvio = (PlParamEnvio) o;
        if (plParamEnvio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plParamEnvio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlParamEnvio{" +
            "id=" + getId() +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
