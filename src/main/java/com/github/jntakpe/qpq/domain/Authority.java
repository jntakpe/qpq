package com.github.jntakpe.qpq.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Entité représentant le rôle d'un {@link com.github.jntakpe.qpq.domain.User}
 *
 * @author jntakpe
 */
@Entity
@Table(name = "t_authority")
public class Authority implements Serializable {

    public static final String ROLE_USER = "ROLE_USER";

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @NotNull
    @Size(min = 0, max = 50)
    @Column(length = 50)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority = (Authority) o;

        return !(name != null ? !name.equals(authority.name) : authority.name != null);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}
