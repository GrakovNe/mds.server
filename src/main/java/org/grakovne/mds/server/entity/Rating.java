package org.grakovne.mds.server.entity;

import javax.persistence.*;

/**
 * JPA Entity.
 */

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "story_id")
    private Story story;

    private Integer value;

    private Integer voters;

    public Rating() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getVoters() {
        return voters;
    }

    public void setVoters(Integer voters) {
        this.voters = voters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        if (story != null ? !story.equals(rating.story) : rating.story != null) return false;
        if (value != null ? !value.equals(rating.value) : rating.value != null) return false;
        return voters != null ? voters.equals(rating.voters) : rating.voters == null;
    }

    @Override
    public int hashCode() {
        int result = story != null ? story.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (voters != null ? voters.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", story=" + story +
                ", value=" + value +
                ", voters=" + voters +
                '}';
    }
}
