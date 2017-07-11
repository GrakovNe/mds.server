package org.grakovne.mds.server.entity;

import javax.persistence.*;

@Entity
public class ReaderAge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "storyId")
    private Story story;

    private String ageType;

    public ReaderAge() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgeType() {
        return ageType;
    }

    public void setAgeType(String ageType) {
        this.ageType = ageType;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    @Override
    public String toString() {
        return "ReaderAge{" +
                "id=" + id +
                ", story=" + story +
                ", ageType='" + ageType + '\'' +
                '}';
    }
}
