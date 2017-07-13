package org.grakovne.mds.server.entity;

import javax.persistence.*;

/**
 * JPA Entity.
 */

@Entity
public class ActionPlaceType implements MdsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.PROPERTY)
    private Integer id;

    private String value;

    public ActionPlaceType() {
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

        ActionPlaceType that = (ActionPlaceType) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ActionPlaceType{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
