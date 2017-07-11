package org.grakovne.mds.server.entity;

import javax.persistence.*;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "storyId")
    private Story story;

    private String genre;

    public Genre() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", story=" + story +
                ", genre='" + genre + '\'' +
                '}';
    }
}
