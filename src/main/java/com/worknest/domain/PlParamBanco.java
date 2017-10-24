package com.worknest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(mappedBy = "plParamBanco")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlParamRespuesta> paramRespuestas = new HashSet<>();

    @OneToMany(mappedBy = "plParamBanco")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlParamEnvio> paramEnvios = new HashSet<>();

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

    public Set<PlParamRespuesta> getParamRespuestas() {
        return paramRespuestas;
    }

    public PlParamBanco paramRespuestas(Set<PlParamRespuesta> plParamRespuestas) {
        this.paramRespuestas = plParamRespuestas;
        return this;
    }

    public PlParamBanco addParamRespuesta(PlParamRespuesta plParamRespuesta) {
        this.paramRespuestas.add(plParamRespuesta);
        plParamRespuesta.setPlParamBanco(this);
        return this;
    }

    public PlParamBanco removeParamRespuesta(PlParamRespuesta plParamRespuesta) {
        this.paramRespuestas.remove(plParamRespuesta);
        plParamRespuesta.setPlParamBanco(null);
        return this;
    }

    public void setParamRespuestas(Set<PlParamRespuesta> plParamRespuestas) {
        this.paramRespuestas = plParamRespuestas;
    }

    public Set<PlParamEnvio> getParamEnvios() {
        return paramEnvios;
    }

    public PlParamBanco paramEnvios(Set<PlParamEnvio> plParamEnvios) {
        this.paramEnvios = plParamEnvios;
        return this;
    }

    public PlParamBanco addParamEnvio(PlParamEnvio plParamEnvio) {
        this.paramEnvios.add(plParamEnvio);
        plParamEnvio.setPlParamBanco(this);
        return this;
    }

    public PlParamBanco removeParamEnvio(PlParamEnvio plParamEnvio) {
        this.paramEnvios.remove(plParamEnvio);
        plParamEnvio.setPlParamBanco(null);
        return this;
    }

    public void setParamEnvios(Set<PlParamEnvio> plParamEnvios) {
        this.paramEnvios = plParamEnvios;
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
