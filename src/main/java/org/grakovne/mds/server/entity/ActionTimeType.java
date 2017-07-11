package org.grakovne.mds.server.entity;

import javax.persistence.*;

@Entity
public class ActionTimeType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.PROPERTY)
    private Integer id;

    private String type;

    @OneToOne
    @JoinColumn(name = "storyId")
    private Story story;

    public ActionTimeType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    @Override
    public String toString() {
        return "ActionTimeType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", story=" + story +
                '}';
    }
}
