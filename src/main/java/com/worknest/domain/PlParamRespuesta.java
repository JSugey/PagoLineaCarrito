package com.worknest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PlParamRespuesta.
 */
@Entity
@Table(name = "pl_param_respuesta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlParamRespuesta implements Serializable {

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

    @ManyToOne
    private PlRespuestaBanco respuestabanco;

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

    public PlParamRespuesta valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public PlParamBanco getPlParamBanco() {
        return plParamBanco;
    }

    public PlParamRespuesta plParamBanco(PlParamBanco plParamBanco) {
        this.plParamBanco = plParamBanco;
        return this;
    }

    public void setPlParamBanco(PlParamBanco plParamBanco) {
        this.plParamBanco = plParamBanco;
    }

    public PlRespuestaBanco getRespuestabanco() {
        return respuestabanco;
    }

    public PlParamRespuesta respuestabanco(PlRespuestaBanco plRespuestaBanco) {
        this.respuestabanco = plRespuestaBanco;
        return this;
    }

    public void setRespuestabanco(PlRespuestaBanco plRespuestaBanco) {
        this.respuestabanco = plRespuestaBanco;
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
        PlParamRespuesta plParamRespuesta = (PlParamRespuesta) o;
        if (plParamRespuesta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plParamRespuesta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlParamRespuesta{" +
            "id=" + getId() +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
