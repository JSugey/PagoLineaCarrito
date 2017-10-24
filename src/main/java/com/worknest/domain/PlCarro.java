package com.worknest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PlCarro.
 */
@Entity
@Table(name = "pl_carro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlCarro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    public PlCarro(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public PlCarro() {
    }
       

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public PlCarro idUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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
        PlCarro plCarro = (PlCarro) o;
        if (plCarro.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plCarro.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlCarro{" +
            "id=" + getId() +
            ", idUsuario='" + getIdUsuario() + "'" +
            "}";
    }
}
