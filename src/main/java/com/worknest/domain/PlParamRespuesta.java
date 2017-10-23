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

    @OneToMany(mappedBy = "plParamRespuesta")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlParamBanco> parametrobancos = new HashSet<>();

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

    public Set<PlParamBanco> getParametrobancos() {
        return parametrobancos;
    }

    public PlParamRespuesta parametrobancos(Set<PlParamBanco> plParamBancos) {
        this.parametrobancos = plParamBancos;
        return this;
    }

    public PlParamRespuesta addParametrobanco(PlParamBanco plParamBanco) {
        this.parametrobancos.add(plParamBanco);
        plParamBanco.setPlParamRespuesta(this);
        return this;
    }

    public PlParamRespuesta removeParametrobanco(PlParamBanco plParamBanco) {
        this.parametrobancos.remove(plParamBanco);
        plParamBanco.setPlParamRespuesta(null);
        return this;
    }

    public void setParametrobancos(Set<PlParamBanco> plParamBancos) {
        this.parametrobancos = plParamBancos;
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
