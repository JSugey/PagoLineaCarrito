package com.worknest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.worknest.domain.enumeration.TipoParamBanco;

/**
 * A PlParamBanco.
 */
@Entity
@Table(name = "pl_param_banco")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlParamBanco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoParamBanco tipo;

    @Size(max = 100)
    @Column(name = "en_uso", length = 100)
    private String enUso;

    @ManyToOne
    private PlParamEnvio plParamEnvio;

    @ManyToOne
    private PlParamRespuesta plParamRespuesta;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public PlParamBanco nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoParamBanco getTipo() {
        return tipo;
    }

    public PlParamBanco tipo(TipoParamBanco tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoParamBanco tipo) {
        this.tipo = tipo;
    }

    public String getEnUso() {
        return enUso;
    }

    public PlParamBanco enUso(String enUso) {
        this.enUso = enUso;
        return this;
    }

    public void setEnUso(String enUso) {
        this.enUso = enUso;
    }

    public PlParamEnvio getPlParamEnvio() {
        return plParamEnvio;
    }

    public PlParamBanco plParamEnvio(PlParamEnvio plParamEnvio) {
        this.plParamEnvio = plParamEnvio;
        return this;
    }

    public void setPlParamEnvio(PlParamEnvio plParamEnvio) {
        this.plParamEnvio = plParamEnvio;
    }

    public PlParamRespuesta getPlParamRespuesta() {
        return plParamRespuesta;
    }

    public PlParamBanco plParamRespuesta(PlParamRespuesta plParamRespuesta) {
        this.plParamRespuesta = plParamRespuesta;
        return this;
    }

    public void setPlParamRespuesta(PlParamRespuesta plParamRespuesta) {
        this.plParamRespuesta = plParamRespuesta;
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
        PlParamBanco plParamBanco = (PlParamBanco) o;
        if (plParamBanco.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plParamBanco.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlParamBanco{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", enUso='" + getEnUso() + "'" +
            "}";
    }
}
