package org.grakovne.mds.server.entity;

import javax.persistence.*;

/**
 * JPA Entity.
 */

@Entity
public class Genre implements MdsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.PROPERTY)
    private Integer id;

    private String value;

    public Genre() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        return value != null ? value.equals(genre.value) : genre.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
